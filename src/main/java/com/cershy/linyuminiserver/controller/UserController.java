package com.cershy.linyuminiserver.controller;

import com.cershy.linyuminiserver.entity.User;
import com.cershy.linyuminiserver.service.UserService;
import com.cershy.linyuminiserver.utils.ResultUtil;
import com.cershy.linyuminiserver.vo.user.CreateUserVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/create")
    public Object createUser(@RequestBody @Valid CreateUserVo createUserVo) {
        User result = userService.createUser(createUserVo);
        return ResultUtil.Succeed(result);
    }

}
