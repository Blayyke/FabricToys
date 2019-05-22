package io.github.blayyke.fabrictoys;

import io.github.blayyke.fabrictoys.blocks.bench.CraftingBenchContainer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.util.Identifier;

public class FTContainers {
    public static final Identifier BENCH = new Identifier(Constants.MOD_ID, Constants.Blocks.CRAFTING_BENCH);

    public static void init() {
        ContainerProviderRegistry.INSTANCE.registerFactory(BENCH, (syncId, identifier, player, buf) -> {
            return new CraftingBenchContainer(syncId, player, BlockContext.create(player.world, buf.readBlockPos()));
        });
    }
}