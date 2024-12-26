package com.cershy.linyuminiserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cershy.linyuminiserver.dto.UserDto;
import com.cershy.linyuminiserver.entity.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE id = #{userId}")
    @ResultMap("UserDtoResultMap")
    UserDto getUserById(String userId);

    @Select("SELECT * FROM user")
    @ResultMap("UserDtoResultMap")
    List<UserDto> listUser();

    @Select("SELECT * FROM user")
    @MapKey("id")
    @ResultMap("UserDtoResultMap")
    Map<String, UserDto> listMapUser();
}
