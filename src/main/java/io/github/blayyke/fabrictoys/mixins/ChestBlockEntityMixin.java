package io.github.blayyke.fabrictoys.mixins;

import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import io.github.blayyke.fabrictoys.blocks.FTChestBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public class ChestBlockEntityMixin {
    @Mutable
    @Shadow
    @Final
    private BlockEntityType<?> type;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(CallbackInfo info) {
        if (((BlockEntity) (Object) this) instanceof FTChestBlockEntity) {
            type = FTBlockEntities.CHEST;
        }
    }
}