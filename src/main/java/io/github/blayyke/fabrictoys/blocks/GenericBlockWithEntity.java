package io.github.blayyke.fabrictoys.blocks;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
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

        if (!world.isClient) {
            ContainerProviderRegistry.INSTANCE.openContainer(getContainerId(), player, buf -> buf.writeBlockPos(pos));
        }

        return true;
    }

    protected abstract Identifier getContainerId();
}