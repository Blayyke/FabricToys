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

package io.github.blayyke.fabrictoys.mixins;

import io.github.blayyke.fabrictoys.FabricToys;
import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import net.minecraft.item.EggItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EggItem.class)
public abstract class EggItemMixin extends Item {
    public EggItemMixin(Settings item$Settings_1) {
        super(item$Settings_1);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        if (FabricToys.CONFIG.enableEggPlacement) {
            BlockPos blockPos = ctx.getBlockPos().up();
            if (ctx.getWorld().isAir(blockPos)) {
                ctx.getWorld().setBlockState(blockPos, FTBlocks.EGG.getDefaultState());
                return ActionResult.SUCCESS;
            }
        }
        return super.useOnBlock(ctx);
    }
}