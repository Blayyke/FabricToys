package io.github.blayyke.fabrictoys.blocks.bench;

import io.github.blayyke.fabrictoys.FTContainers;
import io.github.blayyke.fabrictoys.blocks.GenericBlockEntity;
import io.github.blayyke.fabrictoys.blocks.GenericBlockWithEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CraftingBenchBlock extends GenericBlockWithEntity {
    public CraftingBenchBlock(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    private static VoxelShape LEG_1 = createCuboidShape(0, 0, 0, 3, 12, 3);
    private static VoxelShape LEG_2 = createCuboidShape(13, 0, 13, 16, 12, 16);
    private static VoxelShape LEG_3 = createCuboidShape(0, 0, 13, 3, 12, 16);
    private static VoxelShape LEG_4 = createCuboidShape(13, 0, 0, 16, 12, 3);
    private static VoxelShape TOP = createCuboidShape(0, 12, 0, 16, 16, 16);
    private static VoxelShape SHAPE = VoxelShapes.union(LEG_1, LEG_2, LEG_3, LEG_4, TOP);

    @Override
    public GenericBlockEntity createBlockEntity(BlockView var1) {
        return new CraftingBenchBlockEntity();
    }

    @Override
    protected Identifier getContainerId() {
        return FTContainers.BENCH;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
        return SHAPE;
    }

    @Override
    public void onBlockRemoved(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
        if (blockState_1.getBlock() != blockState_2.getBlock()) {
            BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
            if (blockEntity_1 instanceof CraftingBenchBlockEntity) {
                ItemScatterer.spawn(world_1, blockPos_1, ((CraftingBenchBlockEntity) blockEntity_1).inventory);
                world_1.updateHorizontalAdjacent(blockPos_1, this);
            }
            super.onBlockRemoved(blockState_1, world_1, blockPos_1, blockState_2, boolean_1);
        }
    }
}