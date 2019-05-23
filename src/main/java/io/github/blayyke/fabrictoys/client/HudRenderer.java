package io.github.blayyke.fabrictoys.client;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;

public interface HudRenderer {
    void draw(float f, InGameHud hud, PlayerEntity cameraPlayer);
}