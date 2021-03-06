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

package io.github.blayyke.fabrictoys.blocks.quarry;

import com.mojang.datafixers.util.Pair;
import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.items.FTItems;
import io.github.blayyke.fabrictoys.items.QuarryDrillItem;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.PlayerContainer;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.Identifier;

public class QuarryContainer extends Container {
    private final PlayerEntity player;
    public final QuarryBlockEntity quarry;

    public static final int FUEL_SLOT = 0;
    public static final int TOOL_SLOT = 1;
    public static final int UPGRADE_SLOT = 2;

    public QuarryContainer(int syncId, PlayerEntity player, BlockEntity blockEntity) {
        super(null, syncId);
        this.player = player;

        if (!(blockEntity instanceof QuarryBlockEntity)) {
            throw new IllegalStateException("BlockEntity not of right type! Is: " + blockEntity);
        }
        this.quarry = (QuarryBlockEntity) blockEntity;
        int xOffset = 26;
        int slotY = 19;

        addSlot(new Slot(quarry, FUEL_SLOT, 26, slotY) {
            @Override
            public boolean canInsert(ItemStack itemStack_1) {
                Integer fuel = FuelRegistry.INSTANCE.get(itemStack_1.getItem());
                return fuel != null && fuel > 0;
            }

//            @Override
//            public String getBackgroundSprite() {
//                return Constants.ofString(Constants.Sprites.COAL);
//            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerContainer.field_21668, Constants.of(Constants.Sprites.COAL));
            }
        });

        addSlot(new Slot(quarry, TOOL_SLOT, xOffset + (1 * 18), slotY) {
            @Override
            public boolean canInsert(ItemStack itemStack_1) {
                return itemStack_1.getItem() instanceof PickaxeItem || itemStack_1.getItem() instanceof QuarryDrillItem;
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerContainer.field_21668, Constants.of(Constants.Sprites.PICKAXE));
            }
        });

        addSlot(new Slot(quarry, UPGRADE_SLOT, xOffset + (2 * 18), slotY) {
            @Override
            public boolean canInsert(ItemStack itemStack_1) {
                // TODO maybe support other upgrade types.
                return itemStack_1.getItem() == FTItems.SPEED_UPGRADE;
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerContainer.field_21668, Constants.of(Constants.Sprites.UPGRADE));
            }

//            @Override
//            public String getBackgroundSprite() {
//                return Constants.ofString(Constants.Sprites.UPGRADE);
//            }
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
        return this.quarry.canPlayerUseInv(player);
    }

    public int getFuelProgress() {
        int int_1 = this.quarry.getMaxFuelTime();
        if (int_1 == 0) {
            int_1 = 200;
        }

        return this.quarry.getFuelTime() * 13 / int_1;
    }

    public ItemStack transferSlot(PlayerEntity player, int slotIndex) {
        ItemStack itemStack_1 = ItemStack.EMPTY;
        Slot slot = this.slotList.get(slotIndex);
        if (slot != null && slot.hasStack()) {
            ItemStack stackInSlot = slot.getStack();
            itemStack_1 = stackInSlot.copy();
            if (slotIndex < this.quarry.getInvSize()) {
                if (!this.insertItem(stackInSlot, this.quarry.getInvSize(), this.slotList.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(stackInSlot, 0, this.quarry.getInvSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemStack_1;
    }

    public boolean isBurningFuel() {
        return this.quarry.getFuelTime() > 0;
    }
}