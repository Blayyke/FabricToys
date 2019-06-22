package io.github.blayyke.fabrictoys.blocks.quarry;

import io.github.blayyke.fabrictoys.Identifiers;
import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.BlockContext;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class QuarryContainer extends Container {
    private final PlayerEntity player;
    private final BlockContext context;
    public final QuarryBlockEntity quarry;

    public static final int FUEL_SLOT = 0;
    public static final int PICKAXE_SLOT = 1;

    public QuarryContainer(int syncId, PlayerEntity player, BlockContext context) {
        super(null, syncId);
        this.player = player;
        this.context = context;

        BlockEntity blockEntity = context.run(World::getBlockEntity).orElseThrow(() -> new IllegalStateException("No BlockEntity @ pos"));

        if (!(blockEntity instanceof QuarryBlockEntity)) {
            throw new IllegalStateException("BlockEntity not of right type! Is: " + blockEntity);
        }
        this.quarry = (QuarryBlockEntity) blockEntity;
        int xOffset = 53;
        int yOffset = 19;

        // Blank disc slot
        addSlot(new Slot(quarry, FUEL_SLOT, 26, yOffset) {
            @Override
            public boolean canInsert(ItemStack itemStack_1) {
                Integer fuel = FuelRegistry.INSTANCE.get(itemStack_1.getItem());
                return fuel != null && fuel > 0;
            }

            @Nullable
            @Override
            public String getBackgroundSprite() {
                return Identifiers.slot("coal");
            }
        });

        addSlot(new Slot(quarry, PICKAXE_SLOT, xOffset + (2 * 18), yOffset) {
            @Override
            public boolean canInsert(ItemStack itemStack_1) {
                return itemStack_1.getItem() instanceof PickaxeItem;
            }

            @Nullable
            @Override
            public String getBackgroundSprite() {
                return Identifiers.slot("pickaxe");
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
        return canUse(this.context, player, FTBlocks.QUARRY);
    }

    public int getFuelProgress() {
        int int_1 = this.quarry.getMaxFuelTime();
        if (int_1 == 0) {
            int_1 = 200;
        }

        return this.quarry.getFuelTime() * 13 / int_1;
    }

    public ItemStack transferSlot(PlayerEntity playerEntity_1, int int_1) {
        ItemStack itemStack_1 = ItemStack.EMPTY;
        Slot slot_1 = this.slotList.get(int_1);
        if (slot_1 != null && slot_1.hasStack()) {
            ItemStack itemStack_2 = slot_1.getStack();
            itemStack_1 = itemStack_2.copy();
            if (int_1 == 2) {
                if (!this.insertItem(itemStack_2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot_1.onStackChanged(itemStack_2, itemStack_1);
            } else if (int_1 != 1 && int_1 != 0) {
                if (this.isPickaxe(itemStack_2)) {
                    if (!this.insertItem(itemStack_2, PICKAXE_SLOT, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(itemStack_2)) {
                    if (!this.insertItem(itemStack_2, FUEL_SLOT, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (int_1 >= 30 && int_1 < 39 && !this.insertItem(itemStack_2, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack_2, 3, 39, false)) {
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

            slot_1.onTakeItem(playerEntity_1, itemStack_2);
        }

        return itemStack_1;
    }

    protected boolean isPickaxe(ItemStack itemStack_1) {
        return itemStack_1.getItem() instanceof PickaxeItem;//TODO check pickaxe tags.
    }

    private boolean isFuel(ItemStack itemStack_2) {
        return AbstractFurnaceBlockEntity.canUseAsFuel(itemStack_2);
    }

    public boolean isBurningFuel() {
        return this.quarry.getFuelTime() > 0;
    }
}