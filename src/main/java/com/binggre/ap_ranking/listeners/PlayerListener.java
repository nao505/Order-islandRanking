package com.binggre.ap_ranking.listeners;

import com.binggre.ap_ranking.ApRanking;
import com.binggre.ap_ranking.objects.OfflinePlayerReward;
import com.binggre.ap_ranking.objects.OfflinePlayerRewardHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        OfflinePlayerReward offlinePlayerReward = OfflinePlayerRewardHolder.getInst().get(player.getUniqueId());
        if (offlinePlayerReward == null) {
            return;
        }
        Bukkit.getScheduler().runTaskLater(ApRanking.getPlugin(), () -> {
            if (!offlinePlayerReward.receive()) {
                player.sendMessage("§c인벤토리 공간이 부족하여 랭킹 보상을 받을 수 없습니다.\n&c공간을 확보 후 다시 접속해 주세요.");
            }
        }, 60L);
    }
}