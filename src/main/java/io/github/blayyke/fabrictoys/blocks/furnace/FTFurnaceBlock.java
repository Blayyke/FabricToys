package io.github.blayyke.fabrictoys.blocks.furnace;

import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.NameableContainerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class FTFurnaceBlock extends FurnaceBlock {
    public FTFurnaceBlock(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Override
    protected void openContainer(World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1) {
        BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
        if (blockEntity_1 instanceof FTFurnaceBlockEntity) {
            playerEntity_1.openContainer((NameableContainerProvider) blockEntity_1);
            playerEntity_1.incrementStat(Stats.INTERACT_WITH_FURNACE);
        }

    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView_1) {
        return new FTFurnaceBlockEntity();
    }
}