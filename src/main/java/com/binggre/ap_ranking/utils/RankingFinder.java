package com.binggre.ap_ranking.utils;

import com.binggre.ap_ranking.objects.RankType;
import com.racoboss.Class.playerPointClass;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class RankingFinder {

    private static Map<UUID, playerPointClass> getMap() {
        try {
            Class<?> functionClass = Class.forName("com.racoboss.Function.Function");
            Field isLandRankingData = functionClass.getField("isLandRankingData");
            return (Map<UUID, playerPointClass>) isLandRankingData.get(functionClass);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<playerPointClass> findRanking(RankType rankType) {
        Collection<playerPointClass> values = getMap().values();
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