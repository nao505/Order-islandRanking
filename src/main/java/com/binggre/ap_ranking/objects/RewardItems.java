package com.binggre.ap_ranking.objects;

import com.binggre.binggreapi.utils.serializers.ItemStackSerializer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class RewardItems {

    private String serializedItemStacks;
    private transient List<ItemStack> itemStacks;

    public RewardItems() {
        this.itemStacks = Arrays.stream(Bukkit.createInventory(null, 6 * 9).getContents()).toList();
        this.serialize();
    }

    public void setItemStacks(Inventory inventory) {
        this.itemStacks = Arrays.stream(inventory.getContents()).toList();
    }

    public void serialize() {
        this.serializedItemStacks = ItemStackSerializer.serializeItems(itemStacks);
    }

    public void deserialize() {
        this.itemStacks = ItemStackSerializer.deserializeItems(serializedItemStacks);
    }

    public List<ItemStack> getItemStacks() {
        return itemStacks;
    }
}
