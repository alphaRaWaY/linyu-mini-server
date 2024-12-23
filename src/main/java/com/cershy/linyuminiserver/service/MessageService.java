package com.cershy.linyuminiserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuminiserver.entity.Message;
import com.cershy.linyuminiserver.vo.message.RecallVo;
import com.cershy.linyuminiserver.vo.message.RecordVo;
import com.cershy.linyuminiserver.vo.message.SendMessageVo;

import java.util.List;

public interface MessageService extends IService<Message> {
    Message send(String userId, SendMessageVo sendMessageVo);

    List<Message> record(String userId, RecordVo recordVo);

    Message recall(String userId, RecallVo recallVo);
}
