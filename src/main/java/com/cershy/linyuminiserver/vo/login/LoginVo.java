package com.cershy.linyuminiserver.vo.login;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class LoginVo {
    @NotNull(message = "用户名不能为空~")
    @Pattern(
            regexp = "^[a-zA-Z][a-zA-Z0-9]{0,15}$",
            message = "用户名只能包含英文字母和数字，且必须以英文字母开头，最大长度为16位~"
    )
    private String name;
    @NotNull(message = "邮箱不能为空~")
    @Email(message = "邮箱格式不正确~")
    private String email;
}
