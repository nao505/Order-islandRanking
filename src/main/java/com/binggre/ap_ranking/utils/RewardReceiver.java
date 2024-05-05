package com.binggre.ap_ranking.utils;

import com.binggre.ap_ranking.objects.*;
import com.binggre.binggreapi.utils.InventoryManager;
import com.racoboss.Class.playerPointClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RewardReceiver {

    public static void reward(playerPointClass playerPoint, RankType rankType, int rank) {
        if (playerPoint == null) {
            return;
        }
        RankingReward rankingReward = RankingRewardHolder.getInst().get(rankType);
        if (rankingReward == null) {
            return;
        }

        UUID playerUUID = playerPoint.getPlayerUUID();
        RewardItems rewardItems = rankingReward.getRankRewards().get(rank);

        if (rewardItems == null) {
            return;
        }

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) {
            OfflinePlayerReward offlinePlayerReward = new OfflinePlayerReward(playerUUID, rewardItems);
            offlinePlayerReward.register();
            offlinePlayerReward.save();
        } else {
            List<ItemStack> list = rewardItems.getItemStacks().stream().filter(Objects::nonNull).toList();
            if (!InventoryManager.hasEmpty(player, list.size())) {
                createOfflinePlayerReward(playerUUID, rewardItems);
                player.sendMessage("§c인벤토리 공간이 부족하여 랭킹 보상을 받을 수 없습니다.\n&c공간을 확보 후 다시 접속해 주세요.");
            } else {
                list.forEach(itemStack -> player.getInventory().addItem(itemStack));
            }
        }
    }

    private static void createOfflinePlayerReward(UUID uuid, RewardItems rewardItems) {
        OfflinePlayerReward offlinePlayerReward = new OfflinePlayerReward(uuid, rewardItems);
        offlinePlayerReward.register();
        offlinePlayerReward.save();
    }

}
