package io.github.blayyke.fabrictoys.mixins;

import io.github.blayyke.fabrictoys.accessor.ContainerSetter;
import net.minecraft.container.Container;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import org.spongepowered.asm.mixin.*;

@Mixin(CraftingInventory.class)
public class CraftingInventoryMixin implements ContainerSetter {
    @Shadow
    @Final
    private DefaultedList<ItemStack> stacks;

    @Mutable
    @Shadow
    @Final
    private Container container;

    /**
     * @author Blayyke
     */
    @Overwrite
    public ItemStack takeInvStack(int int_1, int int_2) {
        ItemStack itemStack_1 = Inventories.splitStack(this.stacks, int_1, int_2);
        if (!itemStack_1.isEmpty() && container != null) {
            this.container.onContentChanged((Inventory) this);
        }

        return itemStack_1;
    }

    /**
     * @author Blayyke
     */
    @Overwrite
    public void setInvStack(int int_1, ItemStack itemStack_1) {
        this.stacks.set(int_1, itemStack_1);
        if (container != null) {
            this.container.onContentChanged((Inventory) this);
        }
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }
}