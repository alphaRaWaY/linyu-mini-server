package com.cershy.linyuminiserver.controller;

import com.cershy.linyuminiserver.annotation.Userid;
import com.cershy.linyuminiserver.entity.ChatList;
import com.cershy.linyuminiserver.service.ChatListService;
import com.cershy.linyuminiserver.utils.ResultUtil;
import com.cershy.linyuminiserver.vo.chatList.CreateVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chat-list")
public class ChatListController {

    @Resource
    ChatListService chatListService;

    @GetMapping("/list/private")
    public Object privateList(@Userid String userId) {
        List<ChatList> result = chatListService.privateList(userId);
        return ResultUtil.Succeed(result);
    }

    @GetMapping("/group")
    public Object group() {
        ChatList result = chatListService.getGroup();
        return ResultUtil.Succeed(result);
    }

    @PostMapping("/create")
    public Object create(@Userid String userId, @RequestBody @Valid CreateVo createVo) {
        ChatList result = chatListService.create(userId, createVo.getTargetId());
        return ResultUtil.Succeed(result);
    }
}
