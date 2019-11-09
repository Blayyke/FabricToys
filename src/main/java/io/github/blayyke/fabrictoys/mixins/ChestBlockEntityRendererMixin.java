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
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntityRenderer.class)
public abstract class ChestBlockEntityRendererMixin<T extends BlockEntity & ChestAnimationProgress> extends BlockEntityRenderer<ChestBlockEntity> {
    public ChestBlockEntityRendererMixin(BlockEntityRenderDispatcher blockEntityRenderDispatcher_1) {
        super(blockEntityRenderDispatcher_1);
    }

    //FIXME this completely broke in 19w42a. Fix needed.
    private static final ThreadLocal<BlockEntity> CURRENT_ENTITY = new ThreadLocal<>();
    @Shadow
    @Final
    private static Identifier field_21477; // normal_left

    @Shadow
    @Final
    private static Identifier field_21478; // normal_right

    @Shadow
    @Final
    private static Identifier NORMAL_TEX;

    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/block/entity/ChestBlockEntityRenderer;getSprite(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/texture/Sprite;"
            )
    )
    private Identifier mod(Identifier id) {
        BlockEntity be = CURRENT_ENTITY.get();
        CURRENT_ENTITY.remove();

        if (be instanceof FTChestBlockEntity) {
            if (id.equals(NORMAL_TEX)) {
                id = ((FTChestBlockEntity) be).getTexture();
            } else if (id.equals(field_21477)) {
                id = ((FTChestBlockEntity) be).getLeftTexture();
            } else if (id.equals(field_21478)) {
                id = ((FTChestBlockEntity) be).getRightTexture();
            }
        }

        return id;
    }

    @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
    private void ft_storeEntity(T blockEntity_1, float float_1, MatrixStack matrixStack_1, VertexConsumerProvider vertexConsumerProvider_1, int int_1, int int_2, CallbackInfo info) {
//        TODO minecraft passes a vanilla chest BlockEntity to this to render the hand item, so custom chests will render as vanillas ones.
        // as of 1.15, now they just dont render in hand at all : )
        CURRENT_ENTITY.set(blockEntity_1);
    }
}