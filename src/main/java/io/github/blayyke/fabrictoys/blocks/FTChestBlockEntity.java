package io.github.blayyke.fabrictoys.blocks;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.Identifier;

public class FTChestBlockEntity extends ChestBlockEntity {
    public Identifier getTexture() {
        return ((FTChestBlock) this.getCachedState().getBlock()).getTexture();
    }

    public Identifier getDoubleTexture() {
        return ((FTChestBlock) this.getCachedState().getBlock()).getDoubleTexture();
    }
}