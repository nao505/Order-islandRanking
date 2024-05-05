package com.binggre.ap_ranking.objects;

public class RankingNPC {

    private final int rank;
    private int id;

    public RankingNPC(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}