package io.github.blayyke.fabrictoys.mixins;

import com.mojang.authlib.GameProfile;
import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import io.github.blayyke.fabrictoys.blocks.grave.GraveBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin extends PlayerEntity {
    @Shadow @Final public MinecraftServer server;

    public ServerPlayerMixin(World world_1, GameProfile gameProfile_1) {
        super(world_1, gameProfile_1);
    }

    @Inject(method = "onDeath", at = @At("RETURN"))
    public void onDeath(DamageSource source, CallbackInfo info) {
        BlockPos gravePos = getBlockPos();
        world.setBlockState(gravePos, FTBlocks.GRAVE.getDefaultState());
        GraveBlockEntity grave = (GraveBlockEntity) world.getBlockEntity(gravePos);
        ListTag serialize = inventory.serialize(new ListTag());

        grave.setPlayerInv(serialize);
    }
}