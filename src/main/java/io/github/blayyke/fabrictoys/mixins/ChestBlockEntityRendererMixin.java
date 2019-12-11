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
import io.github.blayyke.fabrictoys.blocks.chest.FTChestBlock;
import io.github.blayyke.fabrictoys.blocks.chest.FTChestBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.class_4722;
import net.minecraft.class_4730;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(class_4722.class)
public abstract class ChestBlockEntityRendererMixin {
    @Shadow
    private static class_4730 method_24063(ChestType chestType, class_4730 arg, class_4730 arg2, class_4730 arg3) {
        return null;
    }

    @Shadow
    protected static class_4730 method_24065(String string) {
        return null;
    }

    //FIXME this completely broke in 19w42a. Fix needed.
    private static final ThreadLocal<BlockEntity> CURRENT_ENTITY = new ThreadLocal<>();

    @Inject(method = "method_24062", at = @At("HEAD"), cancellable = true)
    private static void b(BlockEntity blockEntity, ChestType chestType, boolean bl, CallbackInfoReturnable<class_4730> info) {
        if (blockEntity instanceof FTChestBlockEntity) {
            FTChestBlockEntity chest = (FTChestBlockEntity) blockEntity;
            info.setReturnValue(method_24063(chestType, chest.getNormal(), chest.getLeft(), chest.getRight()));
        }
    }

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void a(CallbackInfo info) {
        for (FTChestBlock chest : FTBlocks.CHESTS) {
            FabricToys.LOGGER.info("Registered chest stuff!");
            chest.setNormal(method_24065(chest.getPath().getPath()));
            chest.setLeft(method_24065(chest.getLeftPath().getPath()));
            chest.setRight(method_24065(chest.getRightPath().getPath()));
        }
    }

    @Inject(method = "method_24066", at = @At("TAIL"))
    private static void acceptTextures(Consumer<class_4730> consumer, CallbackInfo info) {
        for (FTChestBlock chest : FTBlocks.CHESTS) {
            consumer.accept(chest.getNormal());
            consumer.accept(chest.getLeft());
            consumer.accept(chest.getRight());
        }
    }

//    @ModifyArg(
//            method = "render",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/client/render/block/entity/ChestBlockEntityRenderer;getSprite(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/texture/Sprite;"
//            )
//    )
//    private Identifier mod(Identifier id) {
//        BlockEntity be = CURRENT_ENTITY.get();
//        CURRENT_ENTITY.remove();
//
//        if (be instanceof FTChestBlockEntity) {
//            if (id.equals(NORMAL_TEX)) {
//                id = ((FTChestBlockEntity) be).getTexture();
//            } else if (id.equals(field_21477)) {
//                id = ((FTChestBlockEntity) be).getLeftTexture();
//            } else if (id.equals(field_21478)) {
//                id = ((FTChestBlockEntity) be).getRightTexture();
//            }
//        }
//
//        return id;
//    }
//
//     TODO this mod cannot be built due to this damn mixin.
//    @Inject(method = "render", at = @At(value = "HEAD"))
//    private void ft_storeEntity(BlockEntity blockEntity_1, float float_1, MatrixStack matrixStack_1, VertexConsumerProvider vertexConsumerProvider_1, int int_1, int int_2, CallbackInfo info) {
//        TODO minecraft passes a vanilla chest BlockEntity to this to render the hand item, so custom chests will render as vanillas ones.
//         as of 1.15, now they just dont render in hand at all : )
//        CURRENT_ENTITY.set(blockEntity_1);
//    }
}