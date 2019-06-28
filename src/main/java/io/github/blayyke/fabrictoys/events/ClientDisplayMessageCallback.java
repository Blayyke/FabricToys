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

package io.github.blayyke.fabrictoys.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.Objects;

public interface ClientDisplayMessageCallback {
    Event<ClientDisplayMessageCallback> EVENT = EventFactory.createArrayBacked(ClientDisplayMessageCallback.class,
            listeners -> (ctx, isChat) -> {
                for (ClientDisplayMessageCallback listener : listeners) {
                    MutableMessageSendContext oldContext = new MutableMessageSendContext(ctx);
                    listener.onDisplayMessage(ctx, isChat);
                    if (!Objects.equals(oldContext.message, ctx.message)) {
                        break;
                    }
                }
            }
    );

    void onDisplayMessage(MutableMessageSendContext ctx, boolean isChat);

    class MutableMessageSendContext {
        private String message;

        // If true, the message will not be sent to the server.
        private boolean cancelSending;

        public MutableMessageSendContext(String message, boolean cancel) {
            this.message = message;
            this.cancelSending = cancel;
        }

        public MutableMessageSendContext(MutableMessageSendContext ctx) {
            this.message = ctx.message;
            this.cancelSending = ctx.cancelSending;
        }

        public String getMessage() {
            return message;
        }

        public boolean shouldCancelSending() {
            return cancelSending;
        }

        public void setCancelSending(boolean cancel) {
            this.cancelSending = cancel;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}