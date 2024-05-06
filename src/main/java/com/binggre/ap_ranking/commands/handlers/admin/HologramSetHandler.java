package com.binggre.ap_ranking.commands.handlers.admin;

import com.binggre.ap_ranking.config.ApRankingConfig;
import com.binggre.ap_ranking.objects.RankType;
import com.binggre.binggreapi.command.CommandHandler;
import com.binggre.binggreapi.command.SimpleCommandExecutor;
import com.binggre.binggreapi.utils.NumberUtil;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class HologramSetHandler extends CommandHandler {

    public HologramSetHandler(SimpleCommandExecutor executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
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
        String hologramId = super.space(args, 3);
        Hologram hologram = Hologram.getCachedHologram(hologramId);
        if (hologram == null) {
            sender.sendMessage("§c존재하지 않는 홀로그램입니다.");
            return false;
        }
        ApRankingConfig config = ApRankingConfig.getInst();
        config.setHologramId(type, hologramId);
        config.save();

        sender.sendMessage(String.format("%s %s순위의 홀로그램을 %s으(로) 설정했습니다.", type.getName(), rank, hologramId));
        return true;
    }
}
