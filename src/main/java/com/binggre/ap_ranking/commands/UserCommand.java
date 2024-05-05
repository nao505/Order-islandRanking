package com.binggre.ap_ranking.commands;

import com.binggre.ap_ranking.commands.handlers.user.RankingMenuOpenHandler;
import com.binggre.binggreapi.command.SimpleSingleCommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UserCommand extends SimpleSingleCommandExecutor {

    @Override
    public void load() {

    }

    public UserCommand(String command) {
        super(command);
        super.commandHandler = new RankingMenuOpenHandler(this, "");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return super.execute(sender, command, label, args);
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
