package com.cershy.linyuminiserver.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chat_list")
public class ChatList {
    private static final long serialVersionUID = 1L;

    /**
     * 聊天记录的唯一标识
     */
    @TableId("id")
    private String id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 目标用户ID
     */
    @TableField("target_id")
    private String targetId;

    /**
     * 未读消息数
     */
    @TableField("unread_count")
    private Integer unreadCount;

    /**
     * 最后一条消息
     */
    @TableField("last_message")
    private String lastMessage;

    /**
     * 聊天类型
     */
    @TableField("type")
    private String type;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
