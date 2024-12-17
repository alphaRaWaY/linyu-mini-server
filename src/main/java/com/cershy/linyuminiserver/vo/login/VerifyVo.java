package com.cershy.linyuminiserver.vo.login;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VerifyVo {
    @NotNull(message = "密码不能为空~")
    private String password;
}
