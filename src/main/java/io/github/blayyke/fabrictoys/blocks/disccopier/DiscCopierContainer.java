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

package io.github.blayyke.fabrictoys.blocks.disccopier;

import com.mojang.datafixers.util.Pair;
import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.items.BlankDiscItem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.PlayerContainer;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.Identifier;

public class DiscCopierContainer extends Container {
    private final PlayerEntity player;
    private final DiscCopierBlockEntity copier;

    public DiscCopierContainer(int syncId, PlayerEntity player, BlockEntity blockEntity) {
        super(null, syncId);
        this.player = player;

        if (!(blockEntity instanceof DiscCopierBlockEntity)) {
            throw new IllegalStateException("BlockEntity not of right type! Is: " + blockEntity);
        }
        this.copier = (DiscCopierBlockEntity) blockEntity;
        int xOffset = 35;
        int yOffset = 20;

        // Blank disc slot
        addSlot(new Slot(copier, 0, xOffset + (1 * 18), yOffset) {
            @Override
            public boolean canInsert(ItemStack itemStack_1) {
                return itemStack_1.getItem() instanceof BlankDiscItem;
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerContainer.field_21668, Constants.of(Constants.Sprites.DISC));
            }

//            @Override
//            public String getBackgroundSprite() {
//                return Constants.ofString(Constants.Sprites.DISC);
//            }
        });

        // Disc slot
        addSlot(new Slot(copier, 1, xOffset + (2 * 18), yOffset) {
            @Override
            public boolean canInsert(ItemStack itemStack_1) {
                return itemStack_1.getItem() instanceof MusicDiscItem;
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerContainer.field_21668, Constants.of(Constants.Sprites.DISC));
            }

//            @Override
//            public String getBackgroundSprite() {
//                return Constants.ofString(Constants.Sprites.DISC);
//            }
        });

        // Output slot
        addSlot(new Slot(copier, 2, xOffset + (4 * 18), yOffset) {
            @Override
            public boolean canInsert(ItemStack itemStack_1) {
                return false;
            }
        });

        int playerInvYOffset = 51;
        int playerInvRow;
        int playerInvColumn;
        for (playerInvRow = 0; playerInvRow < 3; ++playerInvRow) {
            for (playerInvColumn = 0; playerInvColumn < 9; ++playerInvColumn) {
                this.addSlot(new Slot(player.inventory, playerInvColumn + playerInvRow * 9 + 9, 8 + playerInvColumn * 18, playerInvYOffset + playerInvRow * 18));
            }
        }

        for (playerInvRow = 0; playerInvRow < 9; ++playerInvRow) {
            this.addSlot(new Slot(player.inventory, playerInvRow, 8 + playerInvRow * 18, playerInvYOffset + 58));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.copier.canPlayerUseInv(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int int_1) {
        ItemStack itemStack_1 = ItemStack.EMPTY;
        Slot slot_1 = this.slotList.get(int_1);
        if (slot_1 != null && slot_1.hasStack()) {
            ItemStack itemStack_2 = slot_1.getStack();
            itemStack_1 = itemStack_2.copy();
            if (int_1 == 0) {
                itemStack_2.getItem().onCraft(itemStack_2, player.world, player);
                if (!this.insertItem(itemStack_2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot_1.onStackChanged(itemStack_2, itemStack_1);
            } else if (int_1 >= 10 && int_1 < 37) {
                if (!this.insertItem(itemStack_2, 37, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (int_1 >= 37 && int_1 < 46) {
                if (!this.insertItem(itemStack_2, 10, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack_2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack_2.isEmpty()) {
                slot_1.setStack(ItemStack.EMPTY);
            } else {
                slot_1.markDirty();
            }

            if (itemStack_2.getCount() == itemStack_1.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemStack_3 = slot_1.onTakeItem(player, itemStack_2);
            if (int_1 == 0) {
                player.dropItem(itemStack_3, false);
            }
        }

        return itemStack_1;
    }
}