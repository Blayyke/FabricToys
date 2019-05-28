package io.github.blayyke.fabrictoys.blocks.disccopier;

import io.github.blayyke.fabrictoys.blocks.BlockEntityWithInventory;
import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tickable;

public class DiscCopierBlockEntity extends BlockEntityWithInventory implements Tickable {
    public DiscCopierBlockEntity() {
        super(FTBlockEntities.DISC_COPIER);
    }

    @Override
    public void tick() {
        ItemStack blankStack = getInvStack(0);
        ItemStack toCopy = getInvStack(1);
        ItemStack outputStack = getInvStack(2);

        if (blankStack.isEmpty() || toCopy.isEmpty() || !outputStack.isEmpty()) {
            return;
        }

        if (!world.isClient()) {
            blankStack.subtractAmount(1);
            setInvStack(2, toCopy.copy());
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int getInvSize() {
        return 3;
    }
}