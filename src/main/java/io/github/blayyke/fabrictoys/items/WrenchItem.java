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

package io.github.blayyke.fabrictoys.items;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.enums.ChestType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;

public class WrenchItem extends Item {
    public WrenchItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        BlockState block = ctx.getWorld().getBlockState(ctx.getBlockPos());

        if (block.getProperties().contains(ChestBlock.CHEST_TYPE) && block.get(ChestBlock.CHEST_TYPE) != ChestType.SINGLE) {
            // Small workaround to prevent breaking double chests.
            return super.useOnBlock(ctx);
        }
        if (block.getProperties().contains(Properties.HORIZONTAL_FACING)) {
            BlockState state = block.with(HorizontalFacingBlock.FACING, block.get(HorizontalFacingBlock.FACING).rotateYClockwise());
            ctx.getWorld().setBlockState(ctx.getBlockPos(), state);
            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(ctx);
    }
}