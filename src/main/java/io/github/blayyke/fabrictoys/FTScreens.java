package io.github.blayyke.fabrictoys;

import io.github.blayyke.fabrictoys.blocks.bench.CraftingBenchContainer;
import io.github.blayyke.fabrictoys.blocks.bench.CraftingBenchScreen;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.container.BlockContext;

public class FTScreens {
    public static void init() {
        ScreenProviderRegistry.INSTANCE.registerFactory(FTContainers.BENCH, (syncId, id, player, buf) -> {
            return new CraftingBenchScreen(new CraftingBenchContainer(syncId, player, BlockContext.create(player.world, buf.readBlockPos())), player.inventory);
        });
    }
}