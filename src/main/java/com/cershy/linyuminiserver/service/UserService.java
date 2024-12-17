package com.cershy.linyuminiserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuminiserver.entity.User;
import com.cershy.linyuminiserver.vo.user.CreateUserVo;

public interface UserService extends IService<User> {
    boolean isExist(String name, String email);

    User getUserByNameOrEmail(String name, String email);

    User createUser(CreateUserVo createUserVo);
}
