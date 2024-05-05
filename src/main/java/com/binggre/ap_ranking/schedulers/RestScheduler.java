package com.binggre.ap_ranking.schedulers;

import com.binggre.ap_ranking.ApRanking;
import com.binggre.ap_ranking.config.ApRankingConfig;
import com.binggre.ap_ranking.objects.Day;
import com.binggre.ap_ranking.objects.RankType;
import com.binggre.ap_ranking.utils.PlayerPointFinder;
import com.binggre.ap_ranking.utils.RankingFinder;
import com.binggre.ap_ranking.utils.RewardReceiver;
import com.racoboss.Class.playerPointClass;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;

public class RestScheduler extends SchedulerService {

    @Override
    public void run() {
        if (!enable) {
            return;
        }
        resetAndRewardReceive();
        ApRankingConfig config = ApRankingConfig.getInst();
        LocalDateTime now = LocalDateTime.now();
        int resetTime = config.getResetTime();

        if (resetTime == now.getHour() && now.getMinute() == 0 && now.getSecond() == 0) {
            enable = false;

            Day resetDay = config.getResetDay();
            String nowDay = now.getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("ko"));

            if (resetDay.getName().equalsIgnoreCase(nowDay)) {
                resetAndRewardReceive();
            }
            Bukkit.getScheduler().runTaskLater(ApRanking.getPlugin(), () -> {
                enable = true;
            }, 40L);
        }
    }

    private void resetAndRewardReceive() {
        List<playerPointClass> ranking = RankingFinder.findRanking(RankType.WEEKLY);

        Map<UUID, playerPointClass> map = PlayerPointFinder.get();
        for (playerPointClass value : map.values()) {
            value.setPoint_accumulated(0);
            value.sqlUpdate();
        }
        for (int i = 0; i < Math.min(ranking.size(), 3); i++) {
            RewardReceiver.reward(ranking.get(i), RankType.WEEKLY, i + 1);
        }
    }
}