package io.github.blayyke.fabrictoys.blocks.cobblegen;

import io.github.blayyke.fabrictoys.blocks.GenericBlockEntity;
import io.github.blayyke.fabrictoys.blocks.GenericBlockWithEntity;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class CobblestoneGeneratorBlock extends GenericBlockWithEntity {
    // TODO use an mcmeta file for animated lava + water texture.

    public CobblestoneGeneratorBlock(Block.Settings settings) {
        super(settings);
    }

    @Override
    public GenericBlockEntity createBlockEntity(BlockView var1) {
        return new CobblestoneGeneratorBlockEntity();
    }

    @Override
    protected Identifier getContainerId() {
        return null;
    }
}