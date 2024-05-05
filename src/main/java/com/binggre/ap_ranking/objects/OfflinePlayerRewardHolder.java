package com.binggre.ap_ranking.objects;

import com.binggre.ap_ranking.ApRanking;
import com.binggre.ap_ranking.config.ApRankingConfig;
import com.binggre.binggreapi.objects.holder.ObjectHolder;
import com.binggre.binggreapi.utils.file.FileManager;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class OfflinePlayerRewardHolder extends ObjectHolder<UUID, OfflinePlayerReward> {

    private static OfflinePlayerRewardHolder instance = null;

    public static OfflinePlayerRewardHolder getInst() {
        if (instance == null) {
            ApRanking plugin = ApRanking.getPlugin();
            String directory = plugin.getDataFolder().getPath() + "/user-data";
            instance = new OfflinePlayerRewardHolder(plugin, directory);
        }
        return instance;
    }

    public OfflinePlayerRewardHolder(Plugin plugin, String directory) {
        super(plugin, directory);
    }

    @Override
    public Set<UUID> keySet() {
        return super.loader.keySet();
    }

    @Override
    public Collection<OfflinePlayerReward> values() {
        return super.loader.values();
    }

    @Override
    public OfflinePlayerReward get(UUID uuid) {
        return super.loader.get(uuid);
    }

    @Override
    public boolean has(UUID uuid) {
        return super.loader.containsKey(uuid);
    }

    @Override
    public void put(UUID uuid, OfflinePlayerReward offlinePlayerReward) {
        super.loader.put(uuid, offlinePlayerReward);
    }

    @Override
    public OfflinePlayerReward remove(UUID uuid) {
        return super.loader.remove(uuid);
    }

    @Override
    public void load() {
        File[] files = new File(directory).listFiles();
        if (files != null) {
            for (File file : files) {
                OfflinePlayerReward read = FileManager.read(OfflinePlayerReward.class, file);
                assert (read != null);
                read.getRewardItems().deserialize();
                put(read.getUUID(), read);
            }
        }
    }

    @Override
    public void unload() {

    }
}
