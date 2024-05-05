package com.binggre.ap_ranking.objects;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum Day {

    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일");

    private final String name;
    private static final Map<String, Day> nameOfDay = new HashMap<>();

    static {
        for (Day day : Day.values()) {
            nameOfDay.put(day.getName(), day);
        }
    }

    Day(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public static Day findByName(String name) {
        return nameOfDay.get(name);
    }
}