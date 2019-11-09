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

package io.github.blayyke.fabrictoys.blocks;

import io.github.blayyke.fabrictoys.Constants;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public abstract class GenericBlockWithEntity extends Block implements BlockEntityProvider {
    public GenericBlockWithEntity(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Override
    public abstract GenericBlockEntity createBlockEntity(BlockView var1);

    @Override
    public ActionResult onUse(BlockState block, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        ((GenericBlockEntity) world.getBlockEntity(pos)).activate(block, player, hand, hitResult, world.isClient);

        if (getContainerId() != null) {
            if (!world.isClient) {
                ContainerProviderRegistry.INSTANCE.openContainer(getContainerId(), player, buf -> buf.writeBlockPos(pos));
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
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

    @Override
    public void buildTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltips, TooltipContext ctx) {
        if (this.getTooltip() != null) {
            Style style = new Style().setItalic(true).setColor(Formatting.GRAY);

            if (Screen.hasShiftDown()) {
                tooltips.add(getTooltip().setStyle(style));
            } else {
                tooltips.add(new TranslatableText(Constants.tooltip("hold_shift")).setStyle(style));
            }
        }
    }

    protected abstract TranslatableText getTooltip();
}