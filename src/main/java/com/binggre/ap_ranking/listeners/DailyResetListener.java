package com.binggre.ap_ranking.listeners;

import com.binggre.ap_ranking.objects.RankType;
import com.binggre.ap_ranking.utils.RankingFinder;
import com.binggre.ap_ranking.utils.RewardReceiver;
import com.racoboss.Class.playerPointClass;
import com.racoboss.events.DailyResetEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class DailyResetListener implements Listener {

    @EventHandler
    public void onDailyReset(DailyResetEvent event) {
        List<playerPointClass> ranking = RankingFinder.findRanking(RankType.DAILY);
        for (int i = 0; i < Math.min(ranking.size(), 3); i++) {
            RewardReceiver.reward(ranking.get(i), RankType.DAILY, i + 1);
        }
    }
}