package com.binggre.ap_ranking.config;

import com.binggre.ap_ranking.ApRanking;
import com.binggre.ap_ranking.objects.Day;
import com.binggre.ap_ranking.objects.RankType;
import com.binggre.ap_ranking.objects.RankingNPC;
import com.binggre.binggreapi.utils.file.FileManager;

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

    private final Map<RankType, Map<Integer, Integer>> rankNPCs = new HashMap<>();

    public void load() {
        instance = FileManager.read(ApRankingConfig.class, FILE);
    }

    public void setNpcId(RankType rankType, int rank, int id) {
        Map<Integer, Integer> rankIdMap = rankNPCs.get(rankType);
        if (rankIdMap == null) {
            rankIdMap = new HashMap<>();
        } else {
            rankIdMap.put(rank, id);
        }
        rankNPCs.put(rankType, rankIdMap);
    }

    public Map<RankType, Map<Integer, Integer>> getRankNPCs() {
        return rankNPCs;
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