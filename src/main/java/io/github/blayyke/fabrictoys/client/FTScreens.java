package io.github.blayyke.fabrictoys.client;

import io.github.blayyke.fabrictoys.FTContainers;
import io.github.blayyke.fabrictoys.blocks.bench.CraftingBenchContainer;
import io.github.blayyke.fabrictoys.blocks.bench.CraftingBenchScreen;
import io.github.blayyke.fabrictoys.blocks.disccopier.DiscCopierContainer;
import io.github.blayyke.fabrictoys.blocks.disccopier.DiscCopierScreen;
import io.github.blayyke.fabrictoys.blocks.disenchanter.DisenchanterContainer;
import io.github.blayyke.fabrictoys.blocks.disenchanter.DisenchanterScreen;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.container.BlockContext;

public class FTScreens {
    public static void init() {
        ScreenProviderRegistry.INSTANCE.registerFactory(FTContainers.BENCH, (syncId, id, player, buf) -> {
            return new CraftingBenchScreen(new CraftingBenchContainer(syncId, player, BlockContext.create(player.world, buf.readBlockPos())), player.inventory);
        });
        ScreenProviderRegistry.INSTANCE.registerFactory(FTContainers.DISC_COPIER, (syncId, id, player, buf) -> {
            return new DiscCopierScreen(new DiscCopierContainer(syncId, player, BlockContext.create(player.world, buf.readBlockPos())), player.inventory);
        });
        ScreenProviderRegistry.INSTANCE.registerFactory(FTContainers.DISENCHANTER, (syncId, id, player, buf) -> {
            return new DisenchanterScreen(new DisenchanterContainer(syncId, player, BlockContext.create(player.world, buf.readBlockPos())), player.inventory);
        });
    }
}