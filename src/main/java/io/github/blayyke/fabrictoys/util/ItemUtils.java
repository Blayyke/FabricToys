/*
 *     This file is part of FabricToys.
 *
 *     FabricToys is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     FabricToys is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with FabricToys.  If not, see <https://www.gnu.org/licenses/>.
 */

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