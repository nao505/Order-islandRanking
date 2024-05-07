package com.binggre.ap_ranking.schedulers;

import com.binggre.ap_ranking.ApRanking;
import com.binggre.ap_ranking.config.ApRankingConfig;
import com.binggre.ap_ranking.objects.RankType;
import com.binggre.ap_ranking.utils.RankingFinder;
import com.binggre.binggreapi.utils.command.CommandManager;
import com.racoboss.Class.playerPointClass;
import com.racoboss.MainFunction;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramLine;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import eu.decentsoftware.holograms.api.utils.collection.DList;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public class RankingUpdateScheduler extends SchedulerService {

    private static final String DEFAULT_SKIN_NAME = "_Link___";

    @Override
    public void run() {
        updateNPC();
        updateHologram();
    }

    private void updateHologram() {
        ApRankingConfig config = ApRankingConfig.getInst();
        updateHologram(config, RankType.WEEKLY);
        updateHologram(config, RankType.DAILY);
    }

    private void updateHologram(ApRankingConfig config, RankType rankType) {
        List<playerPointClass> rankings = RankingFinder.findRanking(rankType);
        String hologramId = config.getHologramId(rankType);
        Hologram hologram = Hologram.getCachedHologram(hologramId);
        if (hologram == null) {
            return;
        }
        DList<HologramPage> pages = hologram.getPages();
        List<HologramLine> lines = pages.get(0).getLines();

        int maxSize = lines.size();
        int rank = 0;
        for (playerPointClass ranker : rankings) {
            if (rank >= maxSize) {
                break;
            }
            lines.get(rank).setContent(config.getHologramContent(rankType, (rank + 1), ranker));
            rank++;
        }
    }

    private void updateNPC() {
        Map<RankType, Map<Integer, Integer>> rankNPCs = ApRankingConfig.getInst().getRankNPCs();
        rankNPCs.forEach(this::setRankerPlayer);
    }

    private void setRankerPlayer(RankType rankType, Map<Integer, Integer> rankId) {
        List<playerPointClass> rankings = RankingFinder.findRanking(rankType);
        rankId.forEach((rank, id) -> {
            if (id == -1) {
                return;
            }
            NPC npc = CitizensAPI.getNPCRegistry().getById(id);
            if (npc == null) {
                return;
            }
            npc.setName(rank + "");
            playerPointClass playerPointClass = rankings.get(rank - 1);

            switch (rankType) {
                case DAILY -> {
                    if (playerPointClass.getPoint_daily() <= 0) {
                        setSkin(id, DEFAULT_SKIN_NAME);
                        return;
                    }
                }
                case WEEKLY -> {
                    if (playerPointClass.getPoint_accumulated() <= 0) {
                        setSkin(id, DEFAULT_SKIN_NAME);
                        return;
                    }
                }
            }

            String nickname = this.getNickname(playerPointClass);
            String defaultName = MainFunction.playerOriginalName.get(playerPointClass.getPlayerUUID());
            npc.setName(nickname);

            if (defaultName != null) {
                setSkin(id, defaultName);
            } else {
                setSkin(id, nickname);
            }
        });
    }

    private void setSkin(int npcId, String name) {
        CommandManager.runConsoleCommand(String.format("npc sel %s", npcId));
        CommandManager.runConsoleCommand(String.format("npc skin %s", name));
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