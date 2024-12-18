package com.cershy.linyuminiserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cershy.linyuminiserver.dto.UserDto;
import com.cershy.linyuminiserver.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE id = #{userId}")
    UserDto getUserById(String userId);
}
