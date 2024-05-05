package com.binggre.ap_ranking.utils;

import com.binggre.ap_ranking.objects.RankType;
import com.racoboss.Class.playerPointClass;
import com.racoboss.Function.Function;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RankingFinder {

    public static List<playerPointClass> findRanking(RankType rankType) {
        Collection<playerPointClass> values = Function.isLandRankingData.values();
        return switch (rankType) {
            case DAILY -> values.stream()
                    .sorted(Comparator.comparing(playerPointClass::getPoint_daily).reversed())
                    .collect(Collectors.toList());

            case WEEKLY -> values.stream()
                    .sorted(Comparator.comparing(playerPointClass::getPoint_accumulated).reversed())
                    .collect(Collectors.toList());
        };
    }

    public static int findRanking(Player player, RankType rankType) {
        UUID uuid = player.getUniqueId();
        List<playerPointClass> ranking = findRanking(rankType);

        int rank = 1;
        for (playerPointClass playerPointClass : ranking) {
            if (playerPointClass.getPlayerUUID().equals(uuid)) {
                break;
            }
            rank++;
        }
        return rank;
    }
}