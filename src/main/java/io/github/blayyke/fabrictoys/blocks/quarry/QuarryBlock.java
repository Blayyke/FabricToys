package io.github.blayyke.fabrictoys.blocks.quarry;

import io.github.blayyke.fabrictoys.FTContainers;
import io.github.blayyke.fabrictoys.blocks.GenericBlockEntity;
import io.github.blayyke.fabrictoys.blocks.GenericBlockWithEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class QuarryBlock extends GenericBlockWithEntity {
    public QuarryBlock(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Override
    public GenericBlockEntity createBlockEntity(BlockView var1) {
        return new QuarryBlockEntity();
    }

    @Override
    protected Identifier getContainerId() {
        return FTContainers.QUARRY;
    }
}