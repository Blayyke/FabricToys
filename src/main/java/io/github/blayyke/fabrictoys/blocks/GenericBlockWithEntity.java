package io.github.blayyke.fabrictoys.blocks;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public abstract class GenericBlockWithEntity extends Block implements BlockEntityProvider {
    public GenericBlockWithEntity(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Override
    public abstract GenericBlockEntity createBlockEntity(BlockView var1);

    @Override
    public boolean activate(BlockState block, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        ((GenericBlockEntity) world.getBlockEntity(pos)).activate(block, player, hand, hitResult, world.isClient);

        if (!world.isClient && getContainerId() != null) {
            ContainerProviderRegistry.INSTANCE.openContainer(getContainerId(), player, buf -> buf.writeBlockPos(pos));
        }

        return true;
    }

    @Override
    public void onBlockRemoved(BlockState blockState_1, World world, BlockPos pos, BlockState blockState_2, boolean boolean_1) {
        if (blockState_1.getBlock() != blockState_2.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BlockEntityWithInventory) {
                ((BlockEntityWithInventory) blockEntity).scatterContents(world, pos);
                world.updateHorizontalAdjacent(pos, this);
            }
        }
    }

    protected abstract Identifier getContainerId();
}