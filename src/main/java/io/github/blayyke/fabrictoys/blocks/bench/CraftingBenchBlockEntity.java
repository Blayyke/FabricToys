package io.github.blayyke.fabrictoys.blocks.bench;

import io.github.blayyke.fabrictoys.InventoryUtils;
import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import io.github.blayyke.fabrictoys.blocks.GenericBlockEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.nbt.CompoundTag;

public class CraftingBenchBlockEntity extends GenericBlockEntity {
    public CraftingInventory inventory;

    public CraftingBenchBlockEntity() {
        super(FTBlockEntities.CRAFTING_BENCH);
        inventory = new CraftingInventory(null, 3, 3);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        CompoundTag invTag = new CompoundTag();
        tag.put("Inventory", InventoryUtils.toTag(invTag, this.inventory));
        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);

        InventoryUtils.fromTag(tag.getCompound("Inventory"), inventory);
    }
}