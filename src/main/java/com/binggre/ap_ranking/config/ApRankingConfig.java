package com.binggre.ap_ranking.config;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.binggre.ap_ranking.ApRanking;
import com.binggre.ap_ranking.objects.Day;
import com.binggre.ap_ranking.objects.RankType;
import com.binggre.binggreapi.utils.ColorManager;
import com.binggre.binggreapi.utils.NumberUtil;
import com.binggre.binggreapi.utils.file.FileManager;
import com.racoboss.Class.playerPointClass;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApRankingConfig {

    private static ApRankingConfig instance = null;
    private static final File FILE = new File(ApRanking.getPlugin().getDataFolder(), "config.json");

    public static ApRankingConfig getInst() {
        if (instance == null) {
            instance = new ApRankingConfig();
        }
        return instance;
    }

    private Day resetDay;
    private int resetTime;
    private String rankingHologram;

    private final Map<RankType, Map<Integer, Integer>> rankNPCs = new HashMap<>();
    private final Map<RankType, String> rankHolograms = new HashMap<>();

    public void load() {
        instance = FileManager.read(ApRankingConfig.class, FILE);
        instance.rankingHologram = ColorManager.format(instance.rankingHologram);
    }

    public void setNpcId(RankType rankType, int rank, int id) {
        Map<Integer, Integer> rankIdMap = rankNPCs.computeIfAbsent(rankType, k -> new HashMap<>());
        rankIdMap.put(rank, id);
    }

    public void setHologramId(RankType rankType, String id) {
        rankHolograms.put(rankType, id);
    }

    public String getHologramContent(RankType rankType, int rank, playerPointClass pointClass) {
        SuperiorPlayer player = SuperiorSkyblockAPI.getPlayer(pointClass.getPlayerUUID());
        String islandName = player.getIsland().getName();
        islandName = !islandName.isEmpty() ? islandName : player.getName();

        String replaced = this.rankingHologram
                .replace("<rank>", rank + "")
                .replace("<island>", islandName);

        return switch (rankType) {
            case DAILY -> replaced.replace("<point>", NumberUtil.applyComma(pointClass.getPoint_daily()));
            case WEEKLY -> replaced.replace("<point>", NumberUtil.applyComma(pointClass.getPoint_accumulated()));
        };
    }

    public Map<RankType, Map<Integer, Integer>> getRankNPCs() {
        return rankNPCs;
    }

    public String getHologramId(RankType rankType) {
        return rankHolograms.get(rankType);
    }

    public Map<RankType, String> getRankHolograms() {
        return rankHolograms;
    }

    public Day getResetDay() {
        return resetDay;
    }

    public int getResetTime() {
        return resetTime;
    }

    public void setResetDay(Day resetDay) {
        this.resetDay = resetDay;
    }

    public void setResetTime(int resetTime) {
        this.resetTime = resetTime;
    }

    public void save() {
        FileManager.write(FILE, this);
    }
}