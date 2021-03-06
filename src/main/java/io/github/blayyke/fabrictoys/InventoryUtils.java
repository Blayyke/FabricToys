/*
 *     This file is part of FabricToys.
 *
 *     FabricToys is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     FabricToys is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with FabricToys.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.blayyke.fabrictoys;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class InventoryUtils {
    public static CompoundTag toTag(CompoundTag compoundTag_1, CraftingInventory defaultedList_1) {
        return toTag(compoundTag_1, defaultedList_1, true);
    }

    public static CompoundTag toTag(CompoundTag tag, Inventory inv, boolean boolean_1) {
        ListTag listTag_1 = new ListTag();

        for (int int_1 = 0; int_1 < inv.getInvSize(); ++int_1) {
            ItemStack itemStack_1 = inv.getInvStack(int_1);
            if (!itemStack_1.isEmpty()) {
                CompoundTag compoundTag_2 = new CompoundTag();
                compoundTag_2.putByte("Slot", (byte) int_1);
                itemStack_1.toTag(compoundTag_2);
                listTag_1.add(compoundTag_2);
            }
        }

        if (!listTag_1.isEmpty() || boolean_1) {
            tag.put("Items", listTag_1);
        }

        return tag;
    }

    public static CraftingInventory fromTag(CompoundTag tag, CraftingInventory inv) {
        ListTag listTag_1 = tag.getList("Items", 10);

        for (int int_1 = 0; int_1 < listTag_1.size(); ++int_1) {
            CompoundTag compoundTag_2 = listTag_1.getCompound(int_1);
            int int_2 = compoundTag_2.getByte("Slot") & 255;
            if (int_2 >= 0 && int_2 < inv.getInvSize()) {
                inv.setInvStack(int_2, ItemStack.fromTag(compoundTag_2));
            }
        }

        return inv;
    }

    public static boolean isInvFull(Inventory inventory) {
        for (int i = 0; i < inventory.getInvSize(); i++) {
            if (inventory.getInvStack(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}