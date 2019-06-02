package io.github.blayyke.fabrictoys;

import io.github.blayyke.fabrictoys.blocks.disccopier.DiscCopierContainer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.util.Identifier;

public class FTContainers {
    public static final Identifier BENCH = Identifiers.of(Identifiers.Blocks.CRAFTING_BENCH);
    public static final Identifier DISC_COPIER = Identifiers.of(Identifiers.Blocks.DISC_COPIER);

    public static void init() {
        ContainerProviderRegistry.INSTANCE.registerFactory(DISC_COPIER, (syncId, identifier, player, buf) -> {
            return new DiscCopierContainer(syncId, player, BlockContext.create(player.world, buf.readBlockPos()));
        });
    }
}