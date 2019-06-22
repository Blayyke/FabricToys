package io.github.blayyke.fabrictoys.blocks.quarry;

import io.github.blayyke.fabrictoys.Identifiers;
import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import net.fabricmc.fabric.api.registry.FuelRegistry;
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

    public boolean isBurningFuel() {
        return this.quarry.getFuelTime() > 0;
    }
}