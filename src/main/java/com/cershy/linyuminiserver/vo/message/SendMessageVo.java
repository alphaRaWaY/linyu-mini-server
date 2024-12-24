package com.cershy.linyuminiserver.vo.message;

import com.cershy.linyuminiserver.constant.MessageType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SendMessageVo {
    @NotNull(message = "目标用户不能为空~")
    private String targetId;
    private String source;
    private String type = MessageType.Text;
    @NotNull(message = "消息内容不能为空~")
    private String msgContent;
    private String referenceMsgId;
    private String userIp;
}
