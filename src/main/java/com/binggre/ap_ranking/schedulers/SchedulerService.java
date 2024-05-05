package com.binggre.ap_ranking.schedulers;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class SchedulerService extends BukkitRunnable {

    protected boolean enable;

    public SchedulerService() {
        this.enable = true;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}