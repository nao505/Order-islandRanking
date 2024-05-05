package com.binggre.ap_ranking.listeners;

import com.binggre.ap_ranking.gui.RewardGUI;
import com.binggre.ap_ranking.objects.RankingReward;
import com.binggre.ap_ranking.objects.RewardItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class RewardInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof RewardGUI gui)) {
            return;
        }
        RewardItems rewardItems = gui.getRewardItems();
        RankingReward reward = gui.getReward();
        rewardItems.setItemStacks(gui.getInventory());
        rewardItems.serialize();
        reward.save();
    }
}