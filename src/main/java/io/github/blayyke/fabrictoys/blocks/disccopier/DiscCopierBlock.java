package io.github.blayyke.fabrictoys.blocks.disccopier;

import io.github.blayyke.fabrictoys.FTContainers;
import io.github.blayyke.fabrictoys.blocks.GenericBlockEntity;
import io.github.blayyke.fabrictoys.blocks.GenericBlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class DiscCopierBlock extends GenericBlockWithEntity {
    public DiscCopierBlock(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Override
    public GenericBlockEntity createBlockEntity(BlockView var1) {
        return new DiscCopierBlockEntity();
    }

    @Override
    protected Identifier getContainerId() {
        return FTContainers.DISC_COPIER;
    }
}