package com.binggre.ap_ranking.listeners;

import com.binggre.ap_ranking.gui.RankingGUI;
import com.binggre.ap_ranking.gui.RankingSelectGUI;
import com.binggre.ap_ranking.objects.RankType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        Inventory clickedInventory = event.getClickedInventory();
        clickSelectMenu(event, holder, clickedInventory);
        clickRankingGUI(event, holder, clickedInventory);
    }

    private void clickSelectMenu(InventoryClickEvent event, InventoryHolder holder, Inventory clickedInventory) {
        if (!(holder instanceof RankingSelectGUI gui)) {
            return;
        }
        event.setCancelled(true);
        if (holder.getInventory() != clickedInventory) {
            return;
        }
        int slot = event.getSlot();
        int dailySlot = gui.getDailySlot();
        int weeklySlot = gui.getWeeklySlot();

        Player player = (Player) event.getWhoClicked();
        if (slot == dailySlot) {
            new RankingGUI.GUI(RankType.DAILY, player).open();

        } else if (slot == weeklySlot) {
            new RankingGUI.GUI(RankType.WEEKLY, player).open();
        }
    }

    private void clickRankingGUI(InventoryClickEvent event, InventoryHolder holder, Inventory clickedInventory) {
        if (!(holder instanceof RankingGUI.GUI gui)) {
            return;
        }
        event.setCancelled(true);
    }
}