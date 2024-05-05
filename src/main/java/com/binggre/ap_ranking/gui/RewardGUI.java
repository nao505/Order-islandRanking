package com.binggre.ap_ranking.gui;

import com.binggre.ap_ranking.objects.RankType;
import com.binggre.ap_ranking.objects.RankingReward;
import com.binggre.ap_ranking.objects.RankingRewardHolder;
import com.binggre.ap_ranking.objects.RewardItems;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RewardGUI implements InventoryHolder {

    private final Inventory inventory;
    private final int rank;
    private final RankingReward reward;
    private final RewardItems rewardItems;

    public RewardGUI(RankType type, int rank) {
        Map<RankType, RankingReward> loader = RankingRewardHolder.getInst().getLoader();
        this.reward = loader.computeIfAbsent(type, type1 -> new RankingReward(type));
        this.rank = rank;
        this.rewardItems = loader.get(type).get(rank);
        this.inventory = create(type);
    }

    private Inventory create(RankType type) {
        Inventory inventory = Bukkit.createInventory(this, 6 * 9, "랭킹 " + type.getName() + "보상 설정 [" + rank + "등]");
        int slot = 0;
        for (ItemStack itemStack : rewardItems.getItemStacks()) {
            inventory.setItem(slot, itemStack);
            slot++;
        }
        return inventory;
    }

    public RewardItems getRewardItems() {
        return rewardItems;
    }

    public RankingReward getReward() {
        return reward;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}