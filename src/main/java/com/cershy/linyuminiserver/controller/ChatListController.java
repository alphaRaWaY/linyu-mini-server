package com.cershy.linyuminiserver.controller;

import com.cershy.linyuminiserver.annotation.UrlLimit;
import com.cershy.linyuminiserver.annotation.Userid;
import com.cershy.linyuminiserver.entity.ChatList;
import com.cershy.linyuminiserver.service.ChatListService;
import com.cershy.linyuminiserver.utils.ResultUtil;
import com.cershy.linyuminiserver.vo.chatList.CreateVo;
import com.cershy.linyuminiserver.vo.chatList.DeleteVo;
import com.cershy.linyuminiserver.vo.chatList.ReadVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chat-list")
public class ChatListController {

    @Resource
    ChatListService chatListService;

    @UrlLimit
    @GetMapping("/list/private")
    public Object privateList(@Userid String userId) {
        List<ChatList> result = chatListService.privateList(userId);
        return ResultUtil.Succeed(result);
    }

    @UrlLimit
    @GetMapping("/group")
    public Object group(@Userid String userId) {
        ChatList result = chatListService.getGroup(userId);
        return ResultUtil.Succeed(result);
    }

    @UrlLimit
    @PostMapping("/create")
    public Object create(@Userid String userId, @RequestBody @Valid CreateVo createVo) {
        ChatList result = chatListService.create(userId, createVo.getTargetId());
        return ResultUtil.Succeed(result);
    }

    @UrlLimit
    @PostMapping("/read")
    public Object read(@Userid String userId, @RequestBody @Valid ReadVo readVo) {
        boolean result = chatListService.read(userId, readVo.getTargetId());
        return ResultUtil.Succeed(result);
    }

    @UrlLimit
    @PostMapping("/delete")
    public Object delete(@Userid String userId, @RequestBody @Valid DeleteVo deleteVo) {
        boolean result = chatListService.delete(userId, deleteVo.getChatListId());
        return ResultUtil.Succeed(result);
    }
}
