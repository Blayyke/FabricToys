package io.github.blayyke.fabrictoys.client;

import io.github.blayyke.fabrictoys.FTScreens;
import io.github.blayyke.fabrictoys.PrefixedLogger;
import io.github.blayyke.fabrictoys.client.CompassHudRenderer;
import io.github.blayyke.fabrictoys.client.HudRenderer;
import io.github.blayyke.fabrictoys.client.ItemFrameHudRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;

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

        LOGGER.info("FabricToys client initialized!");
    }

    public static void drawHud(float f, InGameHud hud, PlayerEntity cameraPlayer) {
        HUD_RENDERERS.forEach(hudRenderer -> hudRenderer.draw(f, hud, cameraPlayer));
    }
}