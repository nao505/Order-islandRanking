package com.binggre.ap_ranking.objects;

import com.binggre.ap_ranking.ApRanking;
import com.binggre.binggreapi.objects.holder.ObjectHolder;
import com.binggre.binggreapi.utils.file.FileManager;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Collection;
import java.util.Set;

public class RankingRewardHolder extends ObjectHolder<RankType, RankingReward> {

    private static RankingRewardHolder instance = null;

    public static RankingRewardHolder getInst() {
        if (instance == null) {
            ApRanking plugin = ApRanking.getPlugin();
            String directory = plugin.getDataFolder().getPath() + "/rewards";
            instance = new RankingRewardHolder(plugin, directory);
        }
        return instance;
    }

    public RankingRewardHolder(Plugin plugin, String directory) {
        super(plugin, directory);
    }

    @Override
    public Set<RankType> keySet() {
        return super.loader.keySet();
    }

    @Override
    public Collection<RankingReward> values() {
        return super.loader.values();
    }

    @Override
    public RankingReward get(RankType rankType) {
        return super.loader.get(rankType);
    }

    @Override
    public boolean has(RankType rankType) {
        return super.loader.containsKey(rankType);
    }

    @Override
    public void put(RankType rankType, RankingReward rankingReward) {
        super.loader.put(rankType, rankingReward);
    }

    @Override
    public RankingReward remove(RankType rankType) {
        return super.loader.remove(rankType);
    }

    @Override
    public void load() {
        File[] files = new File(directory).listFiles();
        if (files != null) {
            for (File file : files) {
                RankingReward read = FileManager.read(RankingReward.class, file);
                assert (read != null);
                read.getRankRewards().values().forEach(RewardItems::deserialize);
                super.loader.put(read.getType(), read);
            }
        }
    }

    @Override
    public void unload() {

    }

}