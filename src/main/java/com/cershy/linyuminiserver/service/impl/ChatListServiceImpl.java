package com.cershy.linyuminiserver.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuminiserver.constant.ChatListType;
import com.cershy.linyuminiserver.entity.ChatList;
import com.cershy.linyuminiserver.entity.Message;
import com.cershy.linyuminiserver.mapper.ChatListMapper;
import com.cershy.linyuminiserver.service.ChatListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatListServiceImpl extends ServiceImpl<ChatListMapper, ChatList> implements ChatListService {

    @Override
    public List<ChatList> privateList(String userId) {
        LambdaQueryWrapper<ChatList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatList::getUserId, userId)
                .eq(ChatList::getType, ChatListType.User);
        return list(queryWrapper);
    }

    @Override
    public ChatList getGroup() {
        LambdaQueryWrapper<ChatList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatList::getType, ChatListType.Group);
        ChatList group = getOne(queryWrapper);
        if (group == null) {
            group = new ChatList();
            group.setId("1");
            group.setType(ChatListType.Group);
            group.setUserId("1");
            group.setTargetId("1");
            save(group);
        }
        return group;
    }

    @Override
    public ChatList create(String userId, String targetId) {
        ChatList chatList = new ChatList();
        chatList.setId(IdUtil.simpleUUID());
        chatList.setUserId(userId);
        chatList.setTargetId(targetId);
        chatList.setType(ChatListType.User);
        save(chatList);
        return chatList;
    }

    @Override
    public boolean updateChatListGroup(Message message) {
        ChatList group = getGroup();
        group.setLastMessage(JSONUtil.toJsonStr(message));
        return updateById(group);
    }

    @Override
    public boolean updateChatListPrivate(String userId, String targetId, Message message) {
        //判断聊天列表是否存在
        LambdaQueryWrapper<ChatList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatList::getUserId, targetId)
                .eq(ChatList::getTargetId, userId);
        ChatList chatList = getOne(queryWrapper);
        if (null == chatList) {
            chatList = new ChatList();
            chatList.setId(IdUtil.randomUUID());
            chatList.setUserId(targetId);
            chatList.setType(ChatListType.User);
            chatList.setTargetId(userId);
            chatList.setUnreadCount(1);
            chatList.setLastMessage(JSONUtil.toJsonStr(message));
            save(chatList);
        } else {
            chatList.setUnreadCount(chatList.getUnreadCount() + 1);
            chatList.setLastMessage(JSONUtil.toJsonStr(message));
            updateById(chatList);
        }
        //更新自己的聊天列表
        LambdaUpdateWrapper<ChatList> updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.set(ChatList::getLastMessage, JSONUtil.toJsonStr(message))
                .eq(ChatList::getUserId, userId)
                .eq(ChatList::getTargetId, targetId);
        return update(new ChatList(), updateWrapper);
    }
}
