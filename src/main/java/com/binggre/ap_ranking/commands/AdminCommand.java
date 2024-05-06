package com.binggre.ap_ranking.commands;

import com.binggre.ap_ranking.commands.handlers.admin.*;
import com.binggre.binggreapi.command.SimpleCommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AdminCommand extends SimpleCommandExecutor {

    public AdminCommand(String command) {
        super(command);
    }

    @Override
    public void load() {
        super.registerHandler("초기화", new RankingResetTimeHandler(this, "[요일] [시간]"));
        super.registerHandler("보상설정", new RewardHandler(this, "[일일/주간] [순위]"));
        super.registerHandler("홀로그램", new HologramSetHandler(this, "[일일/주간] [Holo-ID]"));
        super.registerHandler("npc", new NpcSetHandler(this, "[일일/주간] [순위] [NPC-ID]"));
        super.registerHandler("test", new TestHandler(this, ""));
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
