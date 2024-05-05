package com.binggre.ap_ranking.schedulers;

import com.binggre.ap_ranking.config.ApRankingConfig;
import com.binggre.ap_ranking.objects.RankType;
import com.binggre.ap_ranking.utils.RankingFinder;
import com.binggre.binggreapi.utils.command.CommandManager;
import com.racoboss.Class.playerPointClass;
import com.racoboss.MainFunction;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class NpcUpdateScheduler extends SchedulerService {

    @Override
    public void run() {
        Map<RankType, Map<Integer, Integer>> rankNPCs = ApRankingConfig.getInst().getRankNPCs();
        rankNPCs.forEach(this::setRankerPlayer);
    }

    private void setRankerPlayer(RankType rankType, Map<Integer, Integer> rankId) {
        rankId.forEach((rank, id) -> {
            if (id == -1) {
                return;
            }
            NPC npc = CitizensAPI.getNPCRegistry().getById(id);
            if (npc == null) {
                return;
            }
            npc.setName(rank + "");
            playerPointClass playerPointClass = RankingFinder.findRanking(rankType).get(rank - 1);
            String nickname = this.getNickname(playerPointClass);
            String defaultName = MainFunction.playerOriginalName.get(playerPointClass.getPlayerUUID());
            npc.setName(nickname);
            CommandManager.runConsoleCommand(String.format("npc sel %s", id));

            if (defaultName != null) {
                CommandManager.runConsoleCommand(String.format("npc skin %s", defaultName));
            } else {
                CommandManager.runConsoleCommand(String.format("npc skin %s", nickname));
            }
        });
    }

    private String getNickname(playerPointClass playerPoint) {
        UUID playerUUID = playerPoint.getPlayerUUID();
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            return player.getName();
        } else {
            return Bukkit.getOfflinePlayer(playerUUID).getName();
        }
    }
}