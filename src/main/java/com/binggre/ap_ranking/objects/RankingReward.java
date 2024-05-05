package com.binggre.ap_ranking.objects;

import com.binggre.binggreapi.utils.file.FileManager;

import java.io.File;
import java.util.*;

public class RankingReward {

    private final RankType type;
    private final Map<Integer, RewardItems> rankRewards;

    public RankingReward(RankType type) {
        this.type = type;
        this.rankRewards = new HashMap<>();
        this.rankRewards.put(1, new RewardItems());
        this.rankRewards.put(2, new RewardItems());
        this.rankRewards.put(3, new RewardItems());
    }

    public Map<Integer, RewardItems> getRankRewards() {
        return rankRewards;
    }

    public RewardItems get(int rank) {
        if (rank < 0 || rank > 3) {
            throw new RuntimeException("랭킹은 1에서 3까지 가능합니다." + "[" + rank + "]");
        }
        return rankRewards.get(rank);
    }

    public RankType getType() {
        return type;
    }

    public void save() {
        FileManager.write(getFile(), this);
    }

    public File getFile() {
        return new File(RankingRewardHolder.getInst().getDirectory(), this.type.getName() + ".json");
    }
}