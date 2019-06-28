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

package io.github.blayyke.fabrictoys.blocks;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public abstract class BlockEntityWithInventory extends GenericBlockEntity implements Inventory, BlockEntityClientSerializable {
    private DefaultedList<ItemStack> contents;

    public BlockEntityWithInventory(BlockEntityType<?> blockEntityType_1) {
        super(blockEntityType_1);
        contents = DefaultedList.create(getInvSize(), ItemStack.EMPTY);
    }

    @Override
    public ItemStack getInvStack(int int_1) {
        return int_1 >= 0 && int_1 < this.contents.size() ? this.contents.get(int_1) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack takeInvStack(int int_1, int int_2) {
        ItemStack itemStack_1 = Inventories.splitStack(this.contents, int_1, int_2);
        if (!itemStack_1.isEmpty()) {
            this.markDirty();
        }

        return itemStack_1;
    }

    @Override
    public void setInvStack(int int_1, ItemStack itemStack_1) {
        this.contents.set(int_1, itemStack_1);
        if (!itemStack_1.isEmpty() && itemStack_1.getCount() > this.getInvMaxStackAmount()) {
            itemStack_1.setCount(this.getInvMaxStackAmount());
        }

        this.markDirty();
    }

    @Override
    public void clear() {
        this.contents.clear();
    }

    @Override
    public boolean isInvEmpty() {
        for (ItemStack item : this.contents) {
            if (!item.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack removeInvStack(int int_1) {
        ItemStack itemStack_1 = this.contents.get(int_1);
        if (itemStack_1.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.contents.set(int_1, ItemStack.EMPTY);
            return itemStack_1;
        }
    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity playerEntity) {
        return true;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.put("Contents", Inventories.toTag(new CompoundTag(), this.contents));

        return tag;
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return toTag(tag);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        Inventories.fromTag(tag.getCompound("Contents"), this.contents);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        fromTag(tag);
    }

    public void scatterContents(World world, BlockPos pos) {
        ItemScatterer.spawn(world, pos, this.contents);
    }
}