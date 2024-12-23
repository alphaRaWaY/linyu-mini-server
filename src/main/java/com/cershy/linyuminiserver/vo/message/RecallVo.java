package com.cershy.linyuminiserver.vo.message;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RecallVo {
    @NotNull(message = "消息不能为空~")
    private String msgId;
}
