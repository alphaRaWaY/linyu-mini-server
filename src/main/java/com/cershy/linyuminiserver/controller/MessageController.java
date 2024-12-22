package com.cershy.linyuminiserver.controller;

import com.cershy.linyuminiserver.annotation.UrlLimit;
import com.cershy.linyuminiserver.annotation.UserIp;
import com.cershy.linyuminiserver.annotation.Userid;
import com.cershy.linyuminiserver.entity.Message;
import com.cershy.linyuminiserver.service.MessageService;
import com.cershy.linyuminiserver.utils.ResultUtil;
import com.cershy.linyuminiserver.vo.message.RecordVo;
import com.cershy.linyuminiserver.vo.message.SendMessageVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    @Resource
    MessageService messageService;

    @UrlLimit(maxRequests = 100)
    @PostMapping("/send")
    public Object send(@Userid String userId, @UserIp String userIp,
                       @RequestBody @Valid SendMessageVo sendMessageVo) {
        sendMessageVo.setUserIp(userIp);
        Message result = messageService.send(userId, sendMessageVo);
        return ResultUtil.Succeed(result);
    }

    @UrlLimit
    @PostMapping("/record")
    public Object record(@Userid String userId, @RequestBody @Valid RecordVo recordVo) {
        List<Message> result = messageService.record(userId, recordVo);
        return ResultUtil.Succeed(result);
    }
}
