package com.cershy.linyuminiserver.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuminiserver.constant.MessageSource;
import com.cershy.linyuminiserver.dto.UserDto;
import com.cershy.linyuminiserver.entity.Message;
import com.cershy.linyuminiserver.mapper.MessageMapper;
import com.cershy.linyuminiserver.service.ChatListService;
import com.cershy.linyuminiserver.service.MessageService;
import com.cershy.linyuminiserver.service.UserService;
import com.cershy.linyuminiserver.service.WebSocketService;
import com.cershy.linyuminiserver.utils.CacheUtil;
import com.cershy.linyuminiserver.utils.IpUtil;
import com.cershy.linyuminiserver.vo.message.RecordVo;
import com.cershy.linyuminiserver.vo.message.SendMessageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Resource
    MessageMapper messageMapper;

    @Resource
    ChatListService chatListService;

    @Resource
    UserService userService;

    @Resource
    WebSocketService webSocketService;

    @Resource
    CacheUtil cacheUtil;

    @Override
    public Message send(String userId, SendMessageVo sendMessageVo) {
        if (MessageSource.Group.equals(sendMessageVo.getSource())) {
            return sendMessageToGroup(userId, sendMessageVo);
        } else {
            return sendMessageToUser(userId, sendMessageVo);
        }
    }

    @Override
    public List<Message> record(String userId, RecordVo recordVo) {
        List<Message> messages = messageMapper.record(userId, recordVo.getTargetId(),
                recordVo.getIndex(), recordVo.getNum());
        cacheUtil.putUserReadCache(userId, recordVo.getTargetId());
        return messages;
    }

    public Message sendMessageToGroup(String userId, SendMessageVo sendMessageVo) {
        Message message = sendMessage(userId, "1", sendMessageVo, MessageSource.Group, "text");
        //更新群聊列表
        chatListService.updateChatListGroup(message);
        webSocketService.sendMsgToGroup(message);
        return message;
    }

    public Message sendMessageToUser(String userId, SendMessageVo sendMessageVo) {
        Message message = sendMessage(userId, sendMessageVo.getTargetId(), sendMessageVo, MessageSource.User, "text");
        //更新私聊列表
        chatListService.updateChatListPrivate(userId, sendMessageVo.getTargetId(), message);
        webSocketService.sendMsgToUser(message, userId, sendMessageVo.getTargetId());
        return message;
    }

    public Message sendMessage(String userId, String targetId, SendMessageVo sendMessageVo, String source, String type) {
        //获取上一条显示时间的消息
        Message previousMessage = messageMapper.getPreviousShowTimeMsg(userId, targetId);
        //存入数据库
        Message message = new Message();
        message.setId(IdUtil.randomUUID());
        message.setFromId(userId);
        message.setSource(source);
        message.setToId(targetId);
        message.setMessage(sendMessageVo.getMsgContent());
        message.setType(type);
        UserDto user = userService.getUserById(userId);
        user.setIpOwnership(IpUtil.getIpRegion(sendMessageVo.getUserIp()));
        message.setFromInfo(user);
        if (null == previousMessage) {
            message.setIsShowTime(true);
        } else {
            message.setIsShowTime(DateUtil.between(new Date(), previousMessage.getUpdateTime(), DateUnit.MINUTE) > 5);
        }
        if (save(message)) {
            return message;
        }
        return null;
    }
}
