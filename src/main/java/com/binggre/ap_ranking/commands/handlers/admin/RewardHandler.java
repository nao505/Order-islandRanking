package com.binggre.ap_ranking.commands.handlers.admin;

import com.binggre.ap_ranking.gui.RankingSelectGUI;
import com.binggre.ap_ranking.gui.RewardGUI;
import com.binggre.ap_ranking.objects.RankType;
import com.binggre.binggreapi.command.CommandHandler;
import com.binggre.binggreapi.command.SimpleCommandExecutor;
import com.binggre.binggreapi.utils.NumberUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RewardHandler extends CommandHandler {

    public RewardHandler(SimpleCommandExecutor executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c콘솔에서 사용할 수 없습니다.");
            return false;
        }
        if (args.length < 3) {
            sender.sendMessage(super.getDescription());
            return false;
        }
        RankType type = RankType.findByName(args[1]);
        if (type == null) {
            sender.sendMessage("§c존재하지 않는 타입입니다.");
            return false;
        }
        int rank;
        try {
            rank = Integer.parseInt(args[2]);
        } catch (Exception e) {
            sender.sendMessage("§c숫자를 입력하세요.");
            return false;
        }
        if (rank <= 0 || rank > 3) {
            sender.sendMessage("§c1~3 순위만 지정할 수 있습니다.");
            return false;
        }
        player.openInventory(new RewardGUI(type, rank).getInventory());
        return true;
    }
}