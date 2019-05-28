package io.github.blayyke.fabrictoys.util;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemUtils {
    public static ItemEntity dropStack(ItemStack stack, World world, BlockPos pos) {
        return dropStack(stack, world, pos, 0.0F);
    }

    public static ItemEntity dropStack(ItemStack stack, World world, BlockPos pos, float yOffset) {
        ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY() + (double) yOffset, pos.getZ(), stack);
        itemEntity.setToDefaultPickupDelay();
        world.spawnEntity(itemEntity);
        return itemEntity;
    }
}