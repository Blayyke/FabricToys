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

import io.github.blayyke.fabrictoys.events.ClientDisplayMessageCallback;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow
    public abstract void sendMessage(String string_1, boolean boolean_1);

    private static final ThreadLocal<Boolean> tm$eventSent = ThreadLocal.withInitial(() -> {
        return false;
    });

    @Inject(method = "sendMessage(Ljava/lang/String;Z)V", at = @At("HEAD"), cancellable = true)
    private void ft_onMessage(String message, boolean chat, CallbackInfo info) {
        // Called when the client hits enter with a message in the bar.
        if (!tm$eventSent.get()) {
            ClientDisplayMessageCallback.MutableMessageSendContext result = new ClientDisplayMessageCallback.MutableMessageSendContext(message, false);
            ClientDisplayMessageCallback.EVENT.invoker().onDisplayMessage(result, chat);
            tm$eventSent.set(true);

            if (result.shouldCancelSending() || result.getMessage() == null) {
                info.cancel();
                tm$eventSent.remove();
            } else {
                info.cancel();
                sendMessage(result.getMessage(), chat);
                tm$eventSent.remove();
            }
        }
    }
}