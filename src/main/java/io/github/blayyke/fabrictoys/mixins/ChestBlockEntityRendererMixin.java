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

import io.github.blayyke.fabrictoys.blocks.chest.FTChestBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.render.entity.model.ChestEntityModel;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBlockEntityRenderer.class)
public class ChestBlockEntityRendererMixin<T extends BlockEntity & ChestAnimationProgress> extends BlockEntityRenderer<ChestBlockEntity> {
    private static final ThreadLocal<BlockEntity> CURRENT_ENTITY = new ThreadLocal<>();
    @Shadow
    @Final
    private static Identifier NORMAL_DOUBLE_TEX;

    @Shadow
    @Final
    private static Identifier NORMAL_TEX;

    @Inject(method = "method_3562", at = @At(value = "HEAD"))
    private void ft_storeEntity(T blockEntity_1, int int_1, boolean boolean_1, CallbackInfoReturnable<ChestEntityModel> info) {
//        System.out.println(blockEntity_1);
        // TODO minecraft passes a vanilla chest BlockEntity to this to render the hand item, so custom chests will render as vanillas ones.
        CURRENT_ENTITY.set(blockEntity_1);
    }

    @ModifyArg(method = "method_3562", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/ChestBlockEntityRenderer;bindTexture(Lnet/minecraft/util/Identifier;)V"))
    private Identifier ft_applyTextureAndReturnModel(Identifier id) {
        BlockEntity blockEntity = CURRENT_ENTITY.get();
        CURRENT_ENTITY.remove();

        if (NORMAL_DOUBLE_TEX.equals(id)) {
            return ft_getDoubleChestLocation(blockEntity, id);
        } else if (NORMAL_TEX.equals(id)) {
            return ft_getChestLocation(blockEntity, id);
        }

        return id;
    }

    private Identifier ft_getDoubleChestLocation(BlockEntity t, Identifier id) {
        if (t instanceof FTChestBlockEntity) {
            return ((FTChestBlockEntity) t).getDoubleTexture();
        }

        return id;
    }

    private Identifier ft_getChestLocation(BlockEntity t, Identifier id) {
        if (t instanceof FTChestBlockEntity) {
            return ((FTChestBlockEntity) t).getTexture();
        }

        return id;
    }
}