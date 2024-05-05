package com.binggre.ap_ranking.gui;

import com.binggre.ap_ranking.ApRanking;
import com.binggre.binggreapi.objects.items.CustomItemStack;
import com.binggre.binggreapi.utils.ColorManager;
import com.binggre.binggreapi.utils.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class RankingSelectGUI implements InventoryHolder {

    private static RankingSelectGUI instance = null;
    private static final File FILE = new File(ApRanking.getPlugin().getDataFolder(), "select-gui.json");

    public static RankingSelectGUI getInst() {
        if (instance == null) {
            instance = new RankingSelectGUI();
        }
        return instance;
    }

    private String title;
    private int size;
    private CustomItemStack daily;
    private CustomItemStack weekly;
    private transient Inventory inventory = null;

    private final int DAILY_SLOT = 11;
    private final int WEEKLY_SLOT = 15;

    public void load() {
        instance = FileManager.read(RankingSelectGUI.class, FILE);
        instance.title = ColorManager.format(instance.title);
        instance.daily.update();
        instance.weekly.update();
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        if (inventory == null) {
            inventory = create();
        }
        return inventory;
    }

    public int getDailySlot() {
        return DAILY_SLOT;
    }

    public int getWeeklySlot() {
        return WEEKLY_SLOT;
    }

    public CustomItemStack getDaily() {
        return daily;
    }

    public CustomItemStack getWeekly() {
        return weekly;
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    private Inventory create() {
        Inventory inventory = Bukkit.createInventory(this, size * 9, title);
        inventory.setItem(DAILY_SLOT, daily.getItemStack());
        inventory.setItem(WEEKLY_SLOT, weekly.getItemStack());
        return inventory;
    }
}