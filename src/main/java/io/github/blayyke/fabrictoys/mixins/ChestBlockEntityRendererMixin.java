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
import net.minecraft.block.enums.ChestType;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Inject(method = "render", at = @At(value = "HEAD"), remap = false)
    private void ft_storeEntity(T blockEntity_1, float float_1, MatrixStack matrixStack_1, VertexConsumerProvider vertexConsumerProvider_1, int int_1, int int_2, CallbackInfo info) {
        System.out.println("ChestBlockEntityRendererMixin.ft_storeEntity: " + blockEntity_1.getClass().getSimpleName());
//        TODO minecraft passes a vanilla chest BlockEntity to this to render the hand item, so custom chests will render as vanillas ones.
        CURRENT_ENTITY.set(blockEntity_1);
    }

    @Inject(method = "method_23690", at = @At("RETURN"), cancellable = true)
    private void i(ChestType chestType_1, Identifier single, Identifier right, Identifier left, CallbackInfoReturnable<Identifier> id) {
        System.out.println("ChestBlockEntityRendererMixin.i");
        BlockEntity be = CURRENT_ENTITY.get();
        CURRENT_ENTITY.remove();

        if (be instanceof FTChestBlockEntity) {
            System.out.println("Is FTBE");
            if (field_21477.equals(left)) { // normal_left
                System.out.println("Getting left...");
                id.setReturnValue(ft_getLeftChestLocation(be, left));
            }
            if (field_21478.equals(right)) { // normal_right
                System.out.println("Getting right...");
                id.setReturnValue(ft_getRightChestLocation(be, right));
            } else if (NORMAL_TEX.equals(single)) {
                System.out.println("Getting normal...");
                id.setReturnValue(ft_getChestLocation(be, single));
            }
        }
    }

//    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/ChestBlockEntityRenderer;getSprite(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/texture/Sprite;"))
//    private Identifier ft_applyTextureAndReturnModel(Identifier id) {
//        BlockEntity blockEntity = CURRENT_ENTITY.get();
//        CURRENT_ENTITY.remove();
//
//        if (field_21477.equals(id)) { // normal_left
//            System.out.println("Getting left...");
//            return ft_getLeftChestLocation(blockEntity, id);
//        }
//        if (field_21478.equals(id)) { // normal_right
//            System.out.println("Getting right...");
//            return ft_getRightChestLocation(blockEntity, id);
//        } else if (NORMAL_TEX.equals(id)) {
//            System.out.println("Getting normal...");
//            return ft_getChestLocation(blockEntity, id);
//        }
//
//        System.out.println("Other: " + id + " :" + blockEntity.getClass().getSimpleName());
//        return id;
//    }

    private Identifier ft_getLeftChestLocation(BlockEntity t, Identifier id) {
        if (t instanceof FTChestBlockEntity) {
            return ((FTChestBlockEntity) t).getLeftTexture();
        }

        return id;
    }

    private Identifier ft_getRightChestLocation(BlockEntity t, Identifier id) {
        if (t instanceof FTChestBlockEntity) {
            return ((FTChestBlockEntity) t).getRightTexture();
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