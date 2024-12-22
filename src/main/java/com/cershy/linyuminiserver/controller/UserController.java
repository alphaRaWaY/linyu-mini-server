package com.cershy.linyuminiserver.controller;

import com.cershy.linyuminiserver.annotation.UrlLimit;
import com.cershy.linyuminiserver.dto.UserDto;
import com.cershy.linyuminiserver.entity.User;
import com.cershy.linyuminiserver.service.UserService;
import com.cershy.linyuminiserver.utils.ResultUtil;
import com.cershy.linyuminiserver.vo.user.CreateUserVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Resource
    UserService userService;

    //    @PostMapping("/create")
    public Object createUser(@RequestBody @Valid CreateUserVo createUserVo) {
        User result = userService.createUser(createUserVo);
        return ResultUtil.Succeed(result);
    }

    @UrlLimit
    @GetMapping("/list")
    public Object listUser() {
        List<UserDto> result = userService.listUser();
        return ResultUtil.Succeed(result);
    }

    @UrlLimit
    @GetMapping("/list/map")
    public Object listMapUser() {
        Map<String, UserDto> result = userService.listMapUser();
        return ResultUtil.Succeed(result);
    }

    @UrlLimit
    @GetMapping("/online/web")
    public Object onlineWeb() {
        List<String> result = userService.onlineWeb();
        return ResultUtil.Succeed(result);
    }

}
