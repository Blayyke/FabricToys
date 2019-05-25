package io.github.blayyke.fabrictoys.blocks.disenchanter;

import io.github.blayyke.fabrictoys.FTContainers;
import io.github.blayyke.fabrictoys.blocks.GenericBlockEntity;
import io.github.blayyke.fabrictoys.blocks.GenericBlockWithEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class DisenchanterBlock extends GenericBlockWithEntity {
    public DisenchanterBlock(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Override
    public GenericBlockEntity createBlockEntity(BlockView var1) {
        return new DisenchanterBlockEntity();
    }

    @Override
    protected Identifier getContainerId() {
        return FTContainers.DISENCHANTER;
    }
}