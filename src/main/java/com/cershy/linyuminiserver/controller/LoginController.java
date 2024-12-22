package com.cershy.linyuminiserver.controller;

import cn.hutool.json.JSONObject;
import com.cershy.linyuminiserver.annotation.UrlFree;
import com.cershy.linyuminiserver.annotation.UrlLimit;
import com.cershy.linyuminiserver.constant.LimitKeyType;
import com.cershy.linyuminiserver.service.LoginService;
import com.cershy.linyuminiserver.utils.ResultUtil;
import com.cershy.linyuminiserver.utils.SecurityUtil;
import com.cershy.linyuminiserver.vo.login.LoginVo;
import com.cershy.linyuminiserver.vo.login.VerifyVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @UrlFree
    @PostMapping("/verify")
    @UrlLimit(keyType = LimitKeyType.IP)
    public Object verify(@RequestBody @Valid VerifyVo verifyVo) {
        String result = loginService.verify(verifyVo.getPassword());
        return ResultUtil.Succeed(result);
    }

    @UrlFree
    @GetMapping("/public-key")
    @UrlLimit(keyType = LimitKeyType.IP)
    public Object getPublicKey() {
        String result = SecurityUtil.getPublicKey();
        return ResultUtil.Succeed(result);
    }

    @UrlFree
    @PostMapping("")
    @UrlLimit(keyType = LimitKeyType.IP)
    public Object login(@RequestBody @Valid LoginVo loginVo) {
        JSONObject result = loginService.login(loginVo);
        return ResultUtil.Succeed(result);
    }
}
