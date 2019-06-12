package io.github.blayyke.fabrictoys.client;

import io.github.blayyke.fabrictoys.PrefixedLogger;
import io.github.blayyke.fabrictoys.events.ClientDisplayMessageCallback;
import net.minecraft.ChatFormat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class FabricToysClient {
    public static PrefixedLogger LOGGER = new PrefixedLogger("FabricToys");
    private static List<HudRenderer> HUD_RENDERERS = new ArrayList<>();

    @SuppressWarnings("unused")
    public static void init() {
        FTScreens.init();

        HUD_RENDERERS.add(new ItemFrameHudRenderer());
        HUD_RENDERERS.add(new CompassHudRenderer());

        ClientDisplayMessageCallback.EVENT.register((ctx, isChat) -> {
            if (ctx.getMessage().equalsIgnoreCase("cancel")) {
                // If the user types 'cancel', dont display their message. Noone should see the message
                ctx.setCancelSending(true);

                TextComponent badText = new TextComponent("Your message '" + ctx.getMessage() + "' has been cancelled!");
                badText.setStyle(new Style().setColor(ChatFormat.RED).setBold(true));
                MinecraftClient.getInstance().player.addChatMessage(badText, false);
            } else if (ctx.getMessage().equalsIgnoreCase("test")) {
                // When the user types 'test', change the message that they send. Other players _should_ see the changed message
                ctx.setMessage("hello you typed test : )");
            }
        });

        LOGGER.info("FabricToys client initialized!");
    }

    public static void drawHud(float f, InGameHud hud, PlayerEntity cameraPlayer) {
        HUD_RENDERERS.forEach(hudRenderer -> hudRenderer.draw(f, hud, cameraPlayer));
    }
}