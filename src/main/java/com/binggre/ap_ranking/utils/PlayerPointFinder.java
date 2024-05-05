package com.binggre.ap_ranking.utils;

import com.racoboss.Class.playerPointClass;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

public class PlayerPointFinder {

    public static playerPointClass get(UUID uuid) {
        return get().get(uuid);
    }

    public static Map<UUID, playerPointClass> get() {
        try {
            Class<?> functionClass = Class.forName("com.racoboss.Function.Function");
            Field isLandRankingData = functionClass.getField("isLandRankingData");
            return (Map<UUID, playerPointClass>) isLandRankingData.get(functionClass);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}