package io.github.blayyke.fabrictoys.blocks.cobblegen;

import io.github.blayyke.fabrictoys.blocks.BlockEntityWithInventory;
import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;

public class CobblestoneGeneratorBlockEntity extends BlockEntityWithInventory implements Tickable, SidedInventory {
    private static final int PRODUCTION_DELAY = 30; // 1.5 Seconds
    private int elapsedTicks;

    public CobblestoneGeneratorBlockEntity() {
        super(FTBlockEntities.COBBLESTONE_GENERATOR);
    }

    @Override
    public int getInvSize() {
        return 1;
    }

    @Override
    public void tick() {
        elapsedTicks++;
        if (elapsedTicks >= PRODUCTION_DELAY) {
            elapsedTicks = 0;
            produceCobblestone();
        }
    }

    private void produceCobblestone() {
        if (!world.isClient && !isInventoryFull()) {
            if (isInvEmpty()) {
                this.setInvStack(0, new ItemStack(Items.COBBLESTONE));
            } else {
                ItemStack cobble = this.getInvStack(0);
                cobble.increment(1);
            }
        }
    }

    private boolean isInventoryFull() {
        ItemStack cobble = getInvStack(0);
        if (cobble.isEmpty()) {
            return false;
        }
        return cobble.getCount() >= cobble.getMaxCount();
    }

    @Override
    public int[] getInvAvailableSlots(Direction direction) {
        return direction == Direction.DOWN ? new int[]{0} : new int[0];
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction direction) {
        return direction == Direction.DOWN;
    }
}