package io.github.blayyke.fabrictoys.mixins;

import io.github.blayyke.fabrictoys.blocks.FTChestBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ChestBlockEntityRenderer.class)
public class ChestBlockEntityRendererMixin<T extends BlockEntity & ChestAnimationProgress> extends BlockEntityRenderer<ChestBlockEntity> {
    @Shadow
    @Final
    private static Identifier NORMAL_DOUBLE_TEX;

    @Shadow
    @Final
    private static Identifier NORMAL_TEX;

    @ModifyArg(method = "method_3562", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/ChestBlockEntityRenderer;bindTexture(Lnet/minecraft/util/Identifier;)V"))
    private Identifier applyTextureAndReturnModel(Identifier id) {
        if (id == NORMAL_DOUBLE_TEX) {
            return getDoubleChestLocation(null, id);
        } else if (id == NORMAL_TEX) {
            return getChestLocation(null, id);
        }

        return id;
    }

    private Identifier getDoubleChestLocation(BlockEntity t, Identifier id) {
        if (t instanceof FTChestBlockEntity) {
            return ((FTChestBlockEntity) t).getDoubleTexture();
        }

        return id;
    }

    private Identifier getChestLocation(BlockEntity t, Identifier id) {
        if (t instanceof FTChestBlockEntity) {
            return ((FTChestBlockEntity) t).getTexture();
        }

        return id;
    }
}