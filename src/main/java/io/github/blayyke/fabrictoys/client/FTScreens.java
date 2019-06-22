package io.github.blayyke.fabrictoys.client;

import io.github.blayyke.fabrictoys.FTContainers;
import io.github.blayyke.fabrictoys.Identifiers;
import io.github.blayyke.fabrictoys.blocks.disccopier.DiscCopierContainer;
import io.github.blayyke.fabrictoys.blocks.disccopier.DiscCopierScreen;
import io.github.blayyke.fabrictoys.blocks.quarry.QuarryContainer;
import io.github.blayyke.fabrictoys.blocks.quarry.QuarryScreen;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.container.BlockContext;
import net.minecraft.util.Identifier;

public class FTScreens {
    public static void init() {
        ScreenProviderRegistry.INSTANCE.registerFactory(FTContainers.DISC_COPIER, (syncId, id, player, buf) -> {
            return new DiscCopierScreen(new DiscCopierContainer(syncId, player, BlockContext.create(player.world, buf.readBlockPos())), player.inventory);
        });
        ScreenProviderRegistry.INSTANCE.registerFactory(FTContainers.QUARRY, (syncId, id, player, buf) -> {
            return new QuarryScreen(new QuarryContainer(syncId, player, BlockContext.create(player.world, buf.readBlockPos())), player.inventory);
        });

        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.STATUS_EFFECT_ATLAS_TEX).register((atlasTexture, registry) -> {
            registry.register(new Identifier(Identifiers.MOD_ID, "slot/coal"));
            registry.register(new Identifier(Identifiers.MOD_ID, "slot/pickaxe"));
        });
    }
}