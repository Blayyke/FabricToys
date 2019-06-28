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
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.packet.ChatMessageC2SPacket;
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


    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;disconnect(Lnet/minecraft/text/Text;)V", shift = At.Shift.BEFORE))
    public void ft_beforeDisconnect(CallbackInfo i) {
        if (FabricToys.CONFIG.logOnTimeout) {
            LOGGER.info("Player " + player.getName().asString() + " timed out. (No keepalive received for " + FabricToys.CONFIG.keepaliveTimeout + "ms.)");
        }
    }

    @ModifyConstant(method = "tick", constant = @Constant(longValue = 15000L))
    private long modifyKeepaliveTimeout(long l) {
        return FabricToys.CONFIG.keepaliveTimeout;
    }

    @Inject(method = "onChatMessage", at = @At("HEAD"))
    private void onMessage(ChatMessageC2SPacket packet, CallbackInfo info) {
        // Process the message from the player. Cancel this if I dont want it to be sent to others?
    }
}