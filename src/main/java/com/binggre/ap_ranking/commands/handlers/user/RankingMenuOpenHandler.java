package com.binggre.ap_ranking.commands.handlers.user;

import com.binggre.ap_ranking.gui.RankingSelectGUI;
import com.binggre.binggreapi.command.CommandHandler;
import com.binggre.binggreapi.command.SimpleCommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RankingMenuOpenHandler extends CommandHandler {

    public RankingMenuOpenHandler(SimpleCommandExecutor executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c콘솔에서 사용할 수 없습니다.");
            return false;
        }
        player.openInventory(RankingSelectGUI.getInst().getInventory());
        return true;
    }
}
