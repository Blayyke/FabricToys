package io.github.blayyke.fabrictoys.mixins;

import com.mojang.authlib.GameProfile;
import io.github.blayyke.fabrictoys.accessor.PlayerLookStackAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

//import net.minecraft.server.network.ServerPlayerEntity;

@Mixin({ClientPlayerEntity.class, /*ServerPlayerEntity.class*/})
public abstract class ClientPlayerMixin extends PlayerEntity implements PlayerLookStackAccessor {
    private ItemStack lookStack;

    public ClientPlayerMixin(World world_1, GameProfile gameProfile_1) {
        super(world_1, gameProfile_1);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo info) {
        this.lookStack = ItemStack.EMPTY;
        HitResult hitResult = MinecraftClient.getInstance().hitResult;
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            List<ItemFrameEntity> entities = world.getEntities(ItemFrameEntity.class, new BoundingBox(new BlockPos(hitResult.getPos())));
            entities.stream().findFirst().ifPresent(frame -> this.lookStack = frame.getHeldItemStack());
        }
    }

    @Override
    public ItemStack getLookStack() {
        return this.lookStack;
    }
}