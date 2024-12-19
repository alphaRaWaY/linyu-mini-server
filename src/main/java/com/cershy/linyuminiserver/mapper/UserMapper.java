package com.cershy.linyuminiserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cershy.linyuminiserver.dto.UserDto;
import com.cershy.linyuminiserver.entity.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE id = #{userId}")
    UserDto getUserById(String userId);

    @Select("SELECT * FROM user")
    List<UserDto> listUser();

    @Select("SELECT * FROM user")
    @MapKey("id")
    Map<String, UserDto> listMapUser();
}
