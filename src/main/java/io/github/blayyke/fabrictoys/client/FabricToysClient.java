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

package io.github.blayyke.fabrictoys.client;

import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.PrefixedLogger;
import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import io.github.blayyke.fabrictoys.blocks.chest.FTChestBlock;
import io.github.blayyke.fabrictoys.events.ClientDisplayMessageCallback;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class FabricToysClient {
    public static FabricKeyBinding reloadKeybinding;
    public static PrefixedLogger LOGGER = new PrefixedLogger("FabricToys");
    private static List<HudRenderer> HUD_RENDERERS = new ArrayList<>();

    @SuppressWarnings("unused")
    public static void init() {
        FTScreens.init();

        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX).register((atlasTexture, registry) -> {
            registry.register(Constants.of(Constants.Sprites.COAL));
            registry.register(Constants.of(Constants.Sprites.PICKAXE));
            registry.register(Constants.of(Constants.Sprites.UPGRADE));
            registry.register(Constants.of(Constants.Sprites.DISC));

            for (FTChestBlock chest : FTBlocks.CHESTS) {
                registry.register(chest.getPath());
                registry.register(chest.getLeftPath());
                registry.register(chest.getRightPath());
            }
        });

        HUD_RENDERERS.add(new ItemFrameHudRenderer());
        HUD_RENDERERS.add(new CompassHudRenderer());

        ClientDisplayMessageCallback.EVENT.register((ctx, isChat) -> {
            if (ctx.getMessage().equalsIgnoreCase("cancel")) {
                // If the user types 'cancel', dont display their message. Noone should see the message
                ctx.setCancelSending(true);

                Text badText = new LiteralText("Your message '" + ctx.getMessage() + "' has been cancelled!");
                badText.setStyle(new Style().setColor(Formatting.RED).setBold(true));
                MinecraftClient.getInstance().player.addChatMessage(badText, false);
            } else if (ctx.getMessage().equalsIgnoreCase("test")) {
                // When the user types 'test', change the message that they send. Other players _should_ see the changed message
                ctx.setMessage("hello you typed test : )");
            }
        });

        //TODO add hunger to horse GUI
        String category = "key.categories.fabric_toys";
        if (!KeyBindingRegistry.INSTANCE.addCategory(category)) {
            LOGGER.error("Failed to add keybinding category!");
            category = "key.categories.gameplay";
        }
        reloadKeybinding = FabricKeyBinding.Builder.create(Constants.of(Constants.KeyBindings.RELOAD), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, category).build();
        KeyBindingRegistry.INSTANCE.register(reloadKeybinding);

        LOGGER.info("FabricToys client initialized!");
    }

    public static void drawHud(float f, InGameHud hud, PlayerEntity cameraPlayer) {
        HUD_RENDERERS.forEach(hudRenderer -> hudRenderer.draw(f, hud, cameraPlayer));
    }
}