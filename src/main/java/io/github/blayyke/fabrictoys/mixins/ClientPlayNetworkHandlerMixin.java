package io.github.blayyke.fabrictoys.mixins;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.packet.ChatMessageS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onChatMessage", at = @At("HEAD"))
    private void onMessage(ChatMessageS2CPacket packet, CallbackInfo info) {
        // This method is the one that adds the message to the chat hud.
    }
}