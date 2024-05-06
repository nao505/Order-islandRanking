package com.binggre.ap_ranking.gui;

import com.binggre.ap_ranking.ApRanking;
import com.binggre.ap_ranking.objects.RankType;
import com.binggre.ap_ranking.utils.PlayerPointFinder;
import com.binggre.ap_ranking.utils.RankingFinder;
import com.binggre.binggreapi.objects.items.CustomItemStack;
import com.binggre.binggreapi.utils.ColorManager;
import com.binggre.binggreapi.utils.ItemManager;
import com.binggre.binggreapi.utils.file.FileManager;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.racoboss.Class.playerPointClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class RankingGUI {

    private static final int SIZE = 4 * 9;

    private static final int FIRST_HAT_SLOT = 4;
    private static final int FIRST_PLAYER_SLOT = 13;

    private static final int SECOND_HAT_SLOT = 11;
    private static final int SECOND_PLAYER_SLOT = 20;

    private static final int THIRD_HAT_SLOT = 15;
    private static final int THIRD_PLAYER_SLOT = 24;

    private static final int MY_SLOT = 31;


    private static RankingGUI instance = null;
    private static final File FILE = new File(ApRanking.getPlugin().getDataFolder(), "ranking-gui.json");

    public static RankingGUI getInst() {
        if (instance == null) {
            instance = new RankingGUI();
        }
        return instance;
    }

    private String title;

    private String colorFirst;
    private String colorSecond;
    private String colorThird;

    private CustomItemStack hatItem;
    private CustomItemStack playerItem;

    public void load() {
        instance = FileManager.read(RankingGUI.class, FILE);
        instance.title = ColorManager.format(instance.title);
        instance.colorFirst = ColorManager.format(instance.colorFirst);
        instance.colorSecond = ColorManager.format(instance.colorSecond);
        instance.colorThird = ColorManager.format(instance.colorThird);
        instance.playerItem.update();
        instance.hatItem.update();
    }

    public void open(Player player, RankType type) {
        GUI gui = new GUI(type, player);
        player.openInventory(gui.inventory);
    }

    public static class GUI implements InventoryHolder {

        private final Player player;
        private final Inventory inventory;
        private final RankType type;

        public GUI(RankType type, Player player) {
            this.type = type;
            this.inventory = create();
            this.player = player;
            this.refresh();
        }

        public RankType getType() {
            return type;
        }

        @NotNull
        @Override
        public Inventory getInventory() {
            return inventory;
        }

        public void open() {
            player.openInventory(inventory);
        }

        private Inventory create() {
            String title = instance.title.replace("<rank_type>", type.getName());
            return Bukkit.createInventory(this, SIZE, title);
        }

        private void refresh() {
            List<playerPointClass> ranking = RankingFinder.findRanking(type);

            int index = 0;
            for (playerPointClass playerPoint : ranking) {
                if (index == 3) {
                    break;
                }
                switch (index) {
                    case 0 -> {
                        inventory.setItem(FIRST_HAT_SLOT, replaceItemStack(playerPoint, 1, instance.hatItem.getItemStack()));
                        inventory.setItem(FIRST_PLAYER_SLOT, replaceItemStack(playerPoint, 1, instance.playerItem.getItemStack()));
                    }
                    case 1 -> {
                        inventory.setItem(SECOND_HAT_SLOT, replaceItemStack(playerPoint, 2, instance.hatItem.getItemStack()));
                        inventory.setItem(SECOND_PLAYER_SLOT, replaceItemStack(playerPoint, 2, instance.playerItem.getItemStack()));
                    }
                    case 2 -> {
                        inventory.setItem(THIRD_HAT_SLOT, replaceItemStack(playerPoint, 3, instance.hatItem.getItemStack()));
                        inventory.setItem(THIRD_PLAYER_SLOT, replaceItemStack(playerPoint, 3, instance.playerItem.getItemStack()));
                    }
                }
                index++;
            }
            int rank = RankingFinder.findRanking(player, type);
            playerPointClass playerPoint = PlayerPointFinder.get(player.getUniqueId());
            inventory.setItem(MY_SLOT, replaceItemStack(playerPoint, rank, instance.playerItem.getItemStack()));
        }

        private ItemStack replaceItemStack(playerPointClass playerPoint, int rank, ItemStack itemStack) {
            itemStack = itemStack.clone();
            UUID playerUUID = playerPoint.getPlayerUUID();
            String nickname;
            Player player = Bukkit.getPlayer(playerUUID);
            if (player == null) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUID);
                nickname = offlinePlayer.getName();
                updatePlayerHead(offlinePlayer, itemStack);
            } else {
                nickname = player.getName();
                updatePlayerHead(player, itemStack);
            }

            String rankColor = getRankColor(rank) + rank;
            ItemManager.replaceDisplayName(itemStack, "<nickname>", nickname);
            ItemManager.replaceDisplayName(itemStack, "<rank>", rankColor);
            ItemManager.replaceLore(itemStack, "<daily_point>", playerPoint.getPoint_daily() + "");
            ItemManager.replaceLore(itemStack, "<weekly_point>", playerPoint.getPoint_accumulated() + "");
            ItemManager.replaceLore(itemStack, "<rank>", rankColor);

            return ItemManager.replaceLore(itemStack, "<nickname>", nickname);
        }

        private String getRankColor(int rank) {
            return switch (rank) {
                case 1 -> instance.colorFirst;
                case 2 -> instance.colorSecond;
                case 3 -> instance.colorThird;
                default -> "§f";
            };
        }


        public void updatePlayerHead(OfflinePlayer player, ItemStack itemStack) {
            if (itemStack.getType() != Material.PLAYER_HEAD) {
                return;
            }
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            PlayerProfile profile = player.getPlayerProfile();
            skullMeta.setPlayerProfile(profile);
            skullMeta.setOwningPlayer(player);
            itemStack.setItemMeta(skullMeta);
        }
    }
}