package com.binggre.ap_ranking.commands.handlers.admin;

import com.binggre.ap_ranking.config.ApRankingConfig;
import com.binggre.ap_ranking.objects.RankType;
import com.binggre.binggreapi.command.CommandHandler;
import com.binggre.binggreapi.command.SimpleCommandExecutor;
import com.binggre.binggreapi.utils.NumberUtil;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class NpcSetHandler extends CommandHandler {

    public NpcSetHandler(SimpleCommandExecutor executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length < 4) {
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
        int npcId = NumberUtil.parseInt(args[3]);
        if (npcId < 0) {
            sender.sendMessage("§cID는 1이상 이어야 합니다.");
            return false;
        }
        NPC citizen = CitizensAPI.getNPCRegistry().getById(npcId);
        if (citizen == null) {
            sender.sendMessage("§c존재하지 않는 NPC입니다.");
            return false;
        }
        ApRankingConfig config = ApRankingConfig.getInst();
        config.setNpcId(type, rank, npcId);
        config.save();

        sender.sendMessage(String.format("%s %s순위의 NPC를 %s으(로) 설정했습니다.", type.getName(), rank, npcId));
        return true;
    }
}
