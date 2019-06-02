package io.github.blayyke.fabrictoys.blocks.furnace;

import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.FurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.recipe.RecipeType;

public class FTFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    public FTFurnaceBlockEntity() {
        super(FTBlockEntities.FURNACE, RecipeType.SMELTING);
    }

    protected Component getContainerName() {
        return new TranslatableComponent("container.furnace");
    }

    protected Container createContainer(int syncId, PlayerInventory playerInv) {
        return new FurnaceContainer(syncId, playerInv, this, this.propertyDelegate);
    }
}