package com.binggre.ap_ranking.commands.handlers.admin;

import com.binggre.ap_ranking.config.ApRankingConfig;
import com.binggre.ap_ranking.objects.RankType;
import com.binggre.ap_ranking.utils.RankingFinder;
import com.binggre.binggreapi.command.CommandHandler;
import com.binggre.binggreapi.command.SimpleCommandExecutor;
import com.racoboss.Class.playerPointClass;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramLine;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import eu.decentsoftware.holograms.api.utils.collection.DList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestHandler extends CommandHandler {

    public TestHandler(SimpleCommandExecutor executor, String description) {
        super(executor, description);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {


//        config.getRankHolograms().forEach((rankType, rankIdMap) -> {
//            rankIdMap.forEach((rank, hologramId) -> {
//                System.out.println("rankType = " + rankType);
//                System.out.println("rank = " + rank);
//                System.out.println("hologramId = " + hologramId);
//                Hologram hologram = Hologram.getCachedHologram(hologramId);
//                if (hologram == null) {
//                    System.out.println(hologramId + " = null return");
//                    return;
//                }
//                HologramPage page = hologram.getPage(0);
//                if (page == null) {
//                    System.out.println("page = null return");
//                    return;
//                }
//                playerPointClass playerPointClass = rankings.get(rank - 1);
//                String hologramContent = config.getHologramContent(rankType, rank, playerPointClass);
//                page.setLine(0, hologramContent);
//            });
//        });
        return true;
    }
}
