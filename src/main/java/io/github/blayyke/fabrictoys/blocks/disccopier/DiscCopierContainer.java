package io.github.blayyke.fabrictoys.blocks.disccopier;

import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import io.github.blayyke.fabrictoys.items.BlankDiscItem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.BlockContext;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.world.World;

public class DiscCopierContainer extends Container {
    private final PlayerEntity player;
    private final DiscCopierBlockEntity copier;
    private final BlockContext context;

    public DiscCopierContainer(int syncId, PlayerEntity player, BlockContext context) {
        super(null, syncId);
        this.player = player;
        this.context = context;

        BlockEntity blockEntity = context.run(World::getBlockEntity).orElseThrow(() -> new IllegalStateException("No BlockEntity @ pos"));

        if (!(blockEntity instanceof DiscCopierBlockEntity)) {
            throw new IllegalStateException("BlockEntity @ " + blockEntity.getPos() + " not of right type! Is: " + blockEntity);
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
        });

        // Disc slot
        addSlot(new Slot(copier, 1, xOffset + (2 * 18), yOffset) {
            @Override
            public boolean canInsert(ItemStack itemStack_1) {
                return itemStack_1.getItem() instanceof MusicDiscItem;
            }
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
        return canUse(this.context, player, FTBlocks.DISC_COPIER);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity playerEntity_1, int int_1) {
        ItemStack itemStack_1 = ItemStack.EMPTY;
        Slot slot_1 = this.slotList.get(int_1);
        if (slot_1 != null && slot_1.hasStack()) {
            ItemStack itemStack_2 = slot_1.getStack();
            itemStack_1 = itemStack_2.copy();
            if (int_1 == 0) {
                this.context.run((world_1, blockPos_1) -> {
                    itemStack_2.getItem().onCrafted(itemStack_2, world_1, playerEntity_1);
                });
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

            if (itemStack_2.getAmount() == itemStack_1.getAmount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemStack_3 = slot_1.onTakeItem(playerEntity_1, itemStack_2);
            if (int_1 == 0) {
                playerEntity_1.dropItem(itemStack_3, false);
            }
        }

        return itemStack_1;
    }
}