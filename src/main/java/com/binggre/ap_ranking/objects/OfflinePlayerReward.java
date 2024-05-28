package com.binggre.ap_ranking.objects;

import com.binggre.binggreapi.utils.InventoryManager;
import com.binggre.binggreapi.utils.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class OfflinePlayerReward {

    private final UUID uuid;
    private final RewardItems rewardItems;

    public OfflinePlayerReward(UUID uuid, RewardItems rewardItems) {
        this.uuid = uuid;
        this.rewardItems = rewardItems;
    }

    public UUID getUUID() {
        return uuid;
    }

    public RewardItems getRewardItems() {
        return rewardItems;
    }

    public boolean receive() {
        Player player = Bukkit.getPlayer(this.uuid);
        if (player == null) {
            return false;
        }
        List<ItemStack> list = rewardItems.getItemStacks().stream().filter(Objects::nonNull).toList();
        if (!InventoryManager.hasEmpty(player, list.size())) {
            return false;
        }
        list.forEach(itemStack -> player.getInventory().addItem(itemStack));
        getHolder().remove(uuid);
        getFile().delete();
        return true;
    }

    public File getFile() {
        return new File(getHolder().getDirectory(), this.uuid.toString() + ".json");
    }

    public void save() {
        FileManager.write(getFile(), this);
    }

    public void register() {
        getHolder().put(this.uuid, this);
    }

    public OfflinePlayerRewardHolder getHolder() {
        return OfflinePlayerRewardHolder.getInst();
    }
}