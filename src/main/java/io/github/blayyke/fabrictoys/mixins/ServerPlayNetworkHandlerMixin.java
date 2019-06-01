package io.github.blayyke.fabrictoys.mixins;

import io.github.blayyke.fabrictoys.FabricToys;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow
    @Final
    private static Logger LOGGER;

    @Shadow
    public ServerPlayerEntity player;


    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;disconnect(Lnet/minecraft/network/chat/Component;)V", shift = At.Shift.BEFORE))
    public void ft_beforeDisconnect(CallbackInfo i) {
        if (FabricToys.CONFIG.logOnTimeout) {
            LOGGER.info("Player " + player.getName().getText() + " timed out. (No keepalive received for " + FabricToys.CONFIG.keepaliveTimeout + "ms.)");
        }
    }

    @ModifyConstant(method = "tick", constant = @Constant(longValue = 15000L))
    private long modifyKeepaliveTimeout(long l) {
        return FabricToys.CONFIG.keepaliveTimeout;
    }
}