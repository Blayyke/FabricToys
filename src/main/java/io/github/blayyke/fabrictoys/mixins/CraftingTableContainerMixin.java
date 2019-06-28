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

import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import net.minecraft.container.BlockContext;
import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.container.CraftingTableContainer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(CraftingTableContainer.class)
public abstract class CraftingTableContainerMixin extends Container {
    @Shadow
    @Final
    private BlockContext context;

    protected CraftingTableContainerMixin(@Nullable ContainerType<?> containerType_1, int int_1) {
        super(containerType_1, int_1);
    }

    @Inject(method = "canUse", at = @At("RETURN"), cancellable = true)
    private void ft_canUse(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
        if (!info.getReturnValue()) {
            // If it failed the canUse check, it is not a vanilla table so see if it is ours.
            info.setReturnValue(canUse(this.context, player, FTBlocks.STONE_CRAFTING_TABLE));
        }
    }
}