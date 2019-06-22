package io.github.blayyke.fabrictoys.client;

import io.github.blayyke.fabrictoys.Identifiers;
import io.github.blayyke.fabrictoys.PrefixedLogger;
import io.github.blayyke.fabrictoys.events.ClientDisplayMessageCallback;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.ChatFormat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class FabricToysClient {
    public static PrefixedLogger LOGGER = new PrefixedLogger("FabricToys");
    private static List<HudRenderer> HUD_RENDERERS = new ArrayList<>();

    @SuppressWarnings("unused")
    public static void init() {
        FTScreens.init();

        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX).register((atlasTexture, registry) -> {
            registry.register(new Identifier(Identifiers.MOD_ID, "slot/coal"));
            registry.register(new Identifier(Identifiers.MOD_ID, "slot/pickaxe"));
        });

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