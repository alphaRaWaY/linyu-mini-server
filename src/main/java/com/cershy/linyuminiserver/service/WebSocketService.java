package com.cershy.linyuminiserver.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.cershy.linyuminiserver.constant.WsContentType;
import com.cershy.linyuminiserver.dto.NotifyDto;
import com.cershy.linyuminiserver.entity.Message;
import com.cershy.linyuminiserver.utils.CacheUtil;
import com.cershy.linyuminiserver.utils.JwtUtil;
import com.cershy.linyuminiserver.utils.ResultUtil;
import io.jsonwebtoken.Claims;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketService {

    @Data
    public static class WsContent {
        private String type;
        private Object content;
    }

    @Resource
    @Lazy
    UserService userService;

    @Resource
    CacheUtil cacheUtil;

    public static final ConcurrentHashMap<String, Channel> Online_User = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Channel, String> Online_Channel = new ConcurrentHashMap<>();

    public void online(Channel channel, String token) {
        try {
            Claims claims = JwtUtil.parseToken(token);
            String userId = (String) claims.get("userId");
            String cacheToken = cacheUtil.getUserSessionCache(userId);
            if (!token.equals(cacheToken)) {
                sendMsg(channel, ResultUtil.Fail("已在其他地方登录"), WsContentType.Msg);
                channel.close();
                return;
            }
            Online_User.put(userId, channel);
            Online_Channel.put(channel, userId);
            userService.online(userId);
        } catch (Exception e) {
            sendMsg(channel, ResultUtil.Fail("连接错误"), WsContentType.Msg);
            channel.close();
        }
    }

    public void offline(Channel channel) {
        String userId = Online_Channel.get(channel);
        if (StrUtil.isNotBlank(userId)) {
            Online_User.remove(userId);
            Online_Channel.remove(channel);
            userService.offline(userId);
        }
    }

    private void sendMsg(Channel channel, Object msg, String type) {
        WsContent wsContent = new WsContent();
        wsContent.setType(type);
        wsContent.setContent(msg);
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(wsContent)));
    }

    public void sendMsgToUser(Object msg, String userId, String targetId) {
        Channel channel = Online_User.get(userId);
        if (channel != null) {
            sendMsg(channel, msg, WsContentType.Msg);
        }
        channel = Online_User.get(targetId);
        if (channel != null) {
            sendMsg(channel, msg, WsContentType.Msg);
        }
    }

    public void sendMsgToGroup(Message message) {
        Online_Channel.forEach((channel, ext) -> {
            sendMsg(channel, message, WsContentType.Msg);
        });
    }

    public Integer getOnlineNum() {
        return Online_User.size();
    }

    public List<String> getOnlineUser() {
        return new ArrayList<>(Online_User.keySet());
    }

    public void sendNotifyToGroup(NotifyDto notify) {
        Online_Channel.forEach((channel, ext) -> {
            sendMsg(channel, notify, WsContentType.Notify);
        });
    }

}
