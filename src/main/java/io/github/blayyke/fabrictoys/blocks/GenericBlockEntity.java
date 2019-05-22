package io.github.blayyke.fabrictoys.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;

public class GenericBlockEntity extends BlockEntity {
    public GenericBlockEntity(BlockEntityType<?> blockEntityType_1) {
        super(blockEntityType_1);
    }

    public void activate(BlockState block, PlayerEntity player, Hand hand, BlockHitResult hitResult, boolean isClient) {
    }
}