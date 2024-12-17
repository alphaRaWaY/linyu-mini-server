package com.cershy.linyuminiserver.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cershy.linyuminiserver.entity.User;
import com.cershy.linyuminiserver.mapper.UserMapper;
import com.cershy.linyuminiserver.service.UserService;
import com.cershy.linyuminiserver.vo.user.CreateUserVo;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean isExist(String name, String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, name)
                .or().eq(User::getEmail, email);
        return count(queryWrapper) > 0;
    }

    @Override
    public User getUserByNameOrEmail(String name, String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, name)
                .or().eq(User::getEmail, email);
        return getOne(queryWrapper);
    }

    @Override
    public User createUser(CreateUserVo createUserVo) {
        User user = new User();
        user.setId(IdUtil.simpleUUID());
        user.setName(createUserVo.getName());
        user.setEmail(createUserVo.getEmail());
        save(user);
        return user;
    }
}
