package io.github.blayyke.fabrictoys.blocks.cobblegen;

import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.blocks.GenericBlockEntity;
import io.github.blayyke.fabrictoys.blocks.GenericBlockWithEntity;
import net.minecraft.block.Block;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class CobblestoneGeneratorBlock extends GenericBlockWithEntity {
    public final int tier;
    // TODO use an mcmeta file for animated lava + water texture.

    public CobblestoneGeneratorBlock(Block.Settings settings, int tier) {
        super(settings);
        this.tier = tier;
    }

    @Override
    public GenericBlockEntity createBlockEntity(BlockView var1) {
        return new CobblestoneGeneratorBlockEntity();
    }

    @Override
    protected Identifier getContainerId() {
        return null;
    }

    Integer getProductionDelay() {
        switch (this.tier) {
            case 4:
                return 20; // 1 second
            case 3:
                return 70; // 3.5 Seconds
            case 2:
                return 90; // 4.5 Seconds
            default:
            case 1:
                return 120; // 6     seconds
        }
    }

    @Override
    protected TranslatableText getTooltip() {
        return new TranslatableText(Constants.tooltip(Constants.Blocks.COBBLESTONE_GENERATOR), this.getProductionDelay(), ((double) this.getProductionDelay()) / 20);
    }
}