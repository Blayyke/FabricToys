package io.github.blayyke.fabrictoys;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;

public class InventoryUtils {
    public static CraftingInventory fromTag(CompoundTag tag, int invSize) {
        DefaultedList<ItemStack> items = DefaultedList.create(invSize, ItemStack.EMPTY);
        Inventories.fromTag(tag, items);
        CraftingInventory inv = new CraftingInventory(null, 3, 3);
        for (int i = 0; i < items.size(); i++) {
            inv.setInvStack(i, items.get(i));
        }
        return inv;
    }

    public static void toTag(CompoundTag tag, Inventory inventory) {
        DefaultedList<ItemStack> items = DefaultedList.create(inventory.getInvSize(), ItemStack.EMPTY);
        for (int i = 0; i < inventory.getInvSize(); i++) {
            ItemStack invStack = inventory.getInvStack(i);
            if (!invStack.isEmpty()) {
                items.add(invStack);
            }
        }
        Inventories.toTag(tag, items);
    }
}