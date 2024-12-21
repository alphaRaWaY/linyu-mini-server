package com.cershy.linyuminiserver.vo.message;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SendMessageVo {
    @NotNull(message = "目标用户不能为空~")
    private String targetId;
    private String source;
    @NotNull(message = "消息内容不能为空~")
    private String msgContent;
    private String userIp;
}
