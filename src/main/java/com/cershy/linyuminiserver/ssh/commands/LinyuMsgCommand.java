package com.cershy.linyuminiserver.ssh.commands;

import com.cershy.linyuminiserver.annotation.CommandInfo;
import com.cershy.linyuminiserver.ssh.CommandManager;
import com.cershy.linyuminiserver.ssh.CustomCommand;
import com.cershy.linyuminiserver.ssh.InteractionConnect;

@CommandInfo(name = "linyu msg", description = "设置聊天范围，私聊 - linyu msg user [用户名称]，群聊 - linyu msg group")
public class LinyuMsgCommand extends CustomCommand {

    @Override
    public void execute(String content, String username, String[] args, CommandManager commandManager) {
        InteractionConnect connect = ONLINE_USERS.get(username);
        if (connect == null) {
            return;
        }
        if (args.length < 3) {
            echo(username, connect);
            error(connect, "该命令需要参数~");
            return;
        }
        String mode = args[2];
        switch (mode) {
            case "group":
                connect.setPrivateChatUserName(null);
                break;

            case "user":
                if (args.length < 4) {
                    error(connect, "用户名称不能缺失~");
                    break;
                }
                String targetUsername = args[3];
                InteractionConnect targetConnect = ONLINE_USERS.get(targetUsername);

                if (targetConnect == null) {
                    error(connect, "用户不在线~");
                } else {
                    connect.setPrivateChatUserName(targetUsername);
                }
                break;
            default:
                error(connect, "参数错误~");
        }
        echo(username, connect);
    }
}
