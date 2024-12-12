package com.cershy.linyuminiserver.ssh.commands;

import com.cershy.linyuminiserver.annotation.CommandInfo;
import com.cershy.linyuminiserver.ssh.CommandManager;
import com.cershy.linyuminiserver.ssh.CustomCommand;
import com.cershy.linyuminiserver.ssh.InteractionConnect;


@CommandInfo(name = "linyu help", description = "获取linyu命令列表及其用法")
public class LinyuHelpCommand extends CustomCommand {

    @Override
    public void execute(String content, String username, String[] args, CommandManager commandManager) {
        InteractionConnect connect = ONLINE_USERS.get(username);
        connect.getWriter().println(ANSI_YELLOW + "[linyu命令列表]");
        commandManager.getDetailsMap().forEach((name, info) -> {
            connect.getWriter().print(ANSI_YELLOW + name + " - ");
            connect.getWriter().println(ANSI_YELLOW2 + info);
        });
        connect.getWriter().print(ANSI_RESET);
        echo(username, connect);
    }
}
