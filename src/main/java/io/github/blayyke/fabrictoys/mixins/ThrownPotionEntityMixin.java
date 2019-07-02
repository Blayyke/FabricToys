package io.github.blayyke.fabrictoys.mixins;

import io.github.blayyke.fabrictoys.FabricToys;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.thrown.ThrownEntity;
import net.minecraft.entity.thrown.ThrownPotionEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ThrownPotionEntity.class)
public abstract class ThrownPotionEntityMixin extends ThrownEntity {
    protected ThrownPotionEntityMixin(EntityType<? extends ThrownEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    //applySplashEffect soon
    @Inject(method = "method_7498", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getEntities(Ljava/lang/Class;Lnet/minecraft/util/math/Box;)Ljava/util/List;"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void ft_onCollision(List<StatusEffectInstance> effects, Entity entityHit, CallbackInfo callback, Box box) {
        if (FabricToys.CONFIG.enablePotionTweak) {
            boolean isDamage = effects.stream().anyMatch(effect -> effect.getEffectType() == StatusEffects.INSTANT_DAMAGE);

            if (isDamage) {
                for (BlockPos pos : BlockPos.iterate((int) box.minX, (int) box.minY, (int) box.minZ, (int) box.maxX, (int) box.maxY, (int) box.maxZ)) {
                    Block block = world.getBlockState(pos).getBlock();
                    if (block instanceof PlantBlock) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    } else if (block instanceof GrassBlock) {
                        world.setBlockState(pos, Blocks.DIRT.getDefaultState());
                    }
                }
            }
        }
    }
}