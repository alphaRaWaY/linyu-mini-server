package com.cershy.linyuminiserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuminiserver.entity.ChatList;
import com.cershy.linyuminiserver.entity.Message;

import java.util.List;

public interface ChatListService extends IService<ChatList> {
    List<ChatList> privateList(String userId);

    ChatList getGroup();

    ChatList create(String userId, String targetId);

    boolean updateChatListGroup(Message message);

    boolean updateChatListPrivate(String userId, String targetId, Message message);
}
