package io.github.blayyke.fabrictoys.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class FTChestBlock extends ChestBlock implements BlockEntityProvider {
    private Identifier texture;
    private Identifier doubleTexture;

    public FTChestBlock(Block.Settings settings, Identifier texture, Identifier doubleTexture) {
        super(settings);
        this.texture = texture;
        this.doubleTexture = doubleTexture;
    }

    public Identifier getTexture() {
        return this.texture;
    }

    public Identifier getDoubleTexture() {
        return this.doubleTexture;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView_1) {
        return new FTChestBlockEntity();
    }
}