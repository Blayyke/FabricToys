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