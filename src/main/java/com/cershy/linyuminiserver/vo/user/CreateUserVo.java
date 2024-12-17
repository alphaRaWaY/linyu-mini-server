package com.cershy.linyuminiserver.vo.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateUserVo {
    @NotNull(message = "用户名不能为空~")
    private String name;
    @NotNull(message = "邮箱不能为空~")
    private String email;
}
