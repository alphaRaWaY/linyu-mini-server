package com.cershy.linyuminiserver.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuminiserver.constant.ChatListType;
import com.cershy.linyuminiserver.dto.UserDto;
import com.cershy.linyuminiserver.entity.ChatList;
import com.cershy.linyuminiserver.entity.Group;
import com.cershy.linyuminiserver.entity.Message;
import com.cershy.linyuminiserver.mapper.ChatListMapper;
import com.cershy.linyuminiserver.service.ChatListService;
import com.cershy.linyuminiserver.service.GroupService;
import com.cershy.linyuminiserver.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChatListServiceImpl extends ServiceImpl<ChatListMapper, ChatList> implements ChatListService {

    @Resource
    @Lazy
    UserService userService;

    @Resource
    GroupService groupService;

    @Override
    public List<ChatList> privateList(String userId) {
        LambdaQueryWrapper<ChatList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatList::getUserId, userId)
                .eq(ChatList::getType, ChatListType.User);
        return list(queryWrapper);
    }

    @Override
    public ChatList getGroup(String userId) {
        LambdaQueryWrapper<ChatList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatList::getUserId, userId)
                .eq(ChatList::getType, ChatListType.Group);
        ChatList chat = getOne(queryWrapper);
        if (chat == null) {
            chat = new ChatList();
            chat.setId(IdUtil.simpleUUID());
            chat.setType(ChatListType.Group);
            chat.setUserId(userId);
            chat.setTargetId("1");
            Group group = groupService.getById("1");
            UserDto userDto = new UserDto();
            userDto.setId("1");
            userDto.setName(group.getName());
            userDto.setAvatar(group.getAvatar());
            chat.setTargetInfo(userDto);
            save(chat);
        }
        return chat;
    }

    public ChatList getTargetChatList(String userId, String targetId) {
        LambdaQueryWrapper<ChatList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatList::getTargetId, targetId)
                .eq(ChatList::getUserId, userId)
                .eq(ChatList::getType, ChatListType.User);
        return getOne(queryWrapper);
    }

    @Override
    public ChatList create(String userId, String targetId) {
        if (userId.equals(targetId))
            return null;
        ChatList targetChatList = getTargetChatList(userId, targetId);
        if (targetChatList != null) {
            return targetChatList;
        }
        UserDto user = userService.getUserById(targetId);
        ChatList chatList = new ChatList();
        chatList.setId(IdUtil.simpleUUID());
        chatList.setUserId(userId);
        chatList.setTargetId(targetId);
        chatList.setType(ChatListType.User);
        chatList.setTargetInfo(user);
        chatList.setLastMessage(new Message());
        save(chatList);
        return chatList;
    }

    @Override
    public boolean updateChatListGroup(Message message) {
        LambdaUpdateWrapper<ChatList> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ChatList::getLastMessage, JSONUtil.toJsonStr(message))
                .eq(ChatList::getType, ChatListType.Group);
        return update(updateWrapper);
    }

    public boolean updateChatList(String userId, String targetId, Message message) {
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
            chatList.setTargetInfo(userService.getUserById(userId));
            chatList.setLastMessage(message);
            return save(chatList);
        } else {
            chatList.setUnreadCount(chatList.getUnreadCount() + 1);
            chatList.setLastMessage(message);
            return updateById(chatList);
        }
    }

    @Override
    public boolean updateChatListPrivate(String userId, String targetId, Message message) {
        updateChatList(targetId, userId, message);
        //更新自己的聊天列表
        return updateChatList(userId, targetId, message);
    }

    @Override
    public boolean read(String userId, String targetId) {
        if (targetId == null) return false;
        LambdaUpdateWrapper<ChatList> updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.set(ChatList::getUnreadCount, 0)
                .eq(ChatList::getUserId, userId)
                .eq(ChatList::getTargetId, targetId);
        return update(new ChatList(), updateWrapper);
    }
}
