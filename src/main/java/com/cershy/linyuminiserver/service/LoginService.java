package com.cershy.linyuminiserver.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.cershy.linyuminiserver.entity.User;
import com.cershy.linyuminiserver.exception.LinyuException;
import com.cershy.linyuminiserver.utils.CacheUtil;
import com.cershy.linyuminiserver.utils.JwtUtil;
import com.cershy.linyuminiserver.utils.SecurityUtil;
import com.cershy.linyuminiserver.vo.login.LoginVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Resource
    UserService userService;

    @Value("${linyu.password}")
    private String linyuPassword;

    @Value("${linyu.limit}")
    private int linyuLimit;

    @Resource
    CacheUtil cacheUtil;

    @Resource
    WebSocketService webSocketService;

    public String verify(String password) {
        if (webSocketService.getOnlineNum() >= linyuLimit) {
            throw new LinyuException("聊天室人数已满，请稍后再试~");
        }
        String decryptedPassword = SecurityUtil.decryptPassword(password);
        if (!linyuPassword.equals(decryptedPassword)) {
            throw new LinyuException("密码错误~");
        }
        Map tokenInfo = new HashMap<String, String>();
        tokenInfo.put("type", "verify");
        return JwtUtil.createToken(tokenInfo);
    }

    public JSONObject login(LoginVo loginVo) {
        if (webSocketService.getOnlineNum() >= linyuLimit) {
            throw new LinyuException("聊天室人数已满，请稍后再试~");
        }
        User user = userService.getUserByNameOrEmail(loginVo.getName(), loginVo.getEmail());
        if (user != null) {
            if (loginVo.getName().equals(user.getName()) &&
                    !loginVo.getEmail().equals(user.getEmail())) {
                throw new LinyuException("用户名已被使用~");
            }
            if (!loginVo.getName().equals(user.getName()) &&
                    loginVo.getEmail().equals(user.getEmail())) {
                throw new LinyuException("邮箱已被使用~");
            }
        } else {
            user = new User();
            user.setId(IdUtil.simpleUUID());
            user.setName(loginVo.getName());
            user.setEmail(loginVo.getEmail());
            userService.save(user);
        }
        JSONObject userinfo = new JSONObject();
        userinfo.put("type", "user");
        userinfo.put("userId", user.getId());
        userinfo.put("userName", user.getName());
        String token = JwtUtil.createToken(userinfo);
        userinfo.put("token", token);
        cacheUtil.putUserSessionCache(user.getId(), token);
        return userinfo;
    }
}
