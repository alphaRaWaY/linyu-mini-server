package com.cershy.linyuminiserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cershy.linyuminiserver.dto.UserDto;
import com.cershy.linyuminiserver.entity.User;
import com.cershy.linyuminiserver.vo.user.CreateUserVo;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    boolean isExist(String name, String email);

    User getUserByNameOrEmail(String name, String email);

    User createUser(CreateUserVo createUserVo);

    UserDto getUserById(String userId);

    List<UserDto> listUser();

    List<String> onlineWeb();

    Map<String, UserDto> listMapUser();

    void online(String userId);

    void offline(String userId);
}
