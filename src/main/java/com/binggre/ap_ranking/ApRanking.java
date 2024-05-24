package com.binggre.ap_ranking;

import com.binggre.ap_ranking.commands.AdminCommand;
import com.binggre.ap_ranking.commands.UserCommand;
import com.binggre.ap_ranking.config.ApRankingConfig;
import com.binggre.ap_ranking.gui.RankingGUI;
import com.binggre.ap_ranking.gui.RankingSelectGUI;
import com.binggre.ap_ranking.listeners.DailyResetListener;
import com.binggre.ap_ranking.listeners.InventoryListener;
import com.binggre.ap_ranking.listeners.PlayerListener;
import com.binggre.ap_ranking.listeners.RewardInventoryListener;
import com.binggre.ap_ranking.objects.OfflinePlayerRewardHolder;
import com.binggre.ap_ranking.objects.RankingRewardHolder;
import com.binggre.ap_ranking.schedulers.RankingUpdateScheduler;
import com.binggre.ap_ranking.schedulers.RestScheduler;
import com.binggre.binggreapi.BinggrePlugin;

public final class ApRanking extends BinggrePlugin {

    private static ApRanking plugin = null;

    public static ApRanking getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        super.onEnable();

        saveResource("user-data/", false);
        saveResource("ranking-gui.json", false);
        saveResource("select-gui.json", false);
        saveResource("config.json", false);

        super.registerObjectHolder(RankingRewardHolder.getInst());
        super.registerObjectHolder(OfflinePlayerRewardHolder.getInst());
        super.executorCommand(this, new AdminCommand("랭킹관리"));
        super.executorCommand(this, new UserCommand("섬랭킹"));

        super.registerEvents(this,
                new RewardInventoryListener(),
                new InventoryListener(),
                new DailyResetListener(),
                new PlayerListener()
        );
        ApRankingConfig.getInst().load();
        RankingSelectGUI.getInst().load();
        RankingGUI.getInst().load();

        new RestScheduler().runTaskTimer(this, 5L, 5L);
        new RankingUpdateScheduler().runTaskTimer(this, 0, 6000);
    }

    @Override
    public void onDisable() {
        super.unregisterObjectHolder(RankingRewardHolder.getInst());
        super.unregisterObjectHolder(OfflinePlayerRewardHolder.getInst());
    }
}
