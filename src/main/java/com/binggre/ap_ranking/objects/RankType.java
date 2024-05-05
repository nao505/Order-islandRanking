package com.binggre.ap_ranking.objects;

import java.util.HashMap;
import java.util.Map;

public enum RankType {

    DAILY("일일"),
    WEEKLY("주간");

    private final String name;
    private static final Map<String, RankType> nameofRewardType = new HashMap<>();

    static {
        for (RankType type : values()) {
            nameofRewardType.put(type.name, type);
        }
    }

    RankType(String name) {
        this.name = name;
    }

    public static RankType findByName(String name) {
        return nameofRewardType.get(name);
    }

    public String getName() {
        return name;
    }
}