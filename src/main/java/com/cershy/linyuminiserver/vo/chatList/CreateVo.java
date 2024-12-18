package com.cershy.linyuminiserver.vo.chatList;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateVo {
    @NotNull(message = "目标不能为空~")
    private String targetId;
}
