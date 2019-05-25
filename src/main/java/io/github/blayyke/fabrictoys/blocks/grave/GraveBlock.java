package io.github.blayyke.fabrictoys.blocks.grave;

import io.github.blayyke.fabrictoys.blocks.GenericBlockEntity;
import io.github.blayyke.fabrictoys.blocks.GenericBlockWithEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GraveBlock extends GenericBlockWithEntity {
    public GraveBlock(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Override
    public GenericBlockEntity createBlockEntity(BlockView var1) {
        return new GraveBlockEntity();
    }

    @Override
    protected Identifier getContainerId() {
        return null;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState blockState_1, PlayerEntity player) {
        super.onBreak(world, pos, blockState_1, player);

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof GraveBlockEntity) {
            GraveBlockEntity grave = (GraveBlockEntity) blockEntity;
            if (grave.getPlayerInv() != null) {
                player.inventory.deserialize(grave.getPlayerInv());
            }
        }
    }
}