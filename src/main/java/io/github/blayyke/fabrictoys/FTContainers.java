package io.github.blayyke.fabrictoys;

import io.github.blayyke.fabrictoys.blocks.disccopier.DiscCopierContainer;
import io.github.blayyke.fabrictoys.blocks.quarry.QuarryContainer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.util.Identifier;

public class FTContainers {
    public static final Identifier DISC_COPIER = Constants.of(Constants.Blocks.DISC_COPIER);
    public static final Identifier QUARRY = Constants.of(Constants.Blocks.QUARRY);

    public static void init() {
        ContainerProviderRegistry.INSTANCE.registerFactory(DISC_COPIER, (syncId, identifier, player, buf) -> {
            return new DiscCopierContainer(syncId, player, BlockContext.create(player.world, buf.readBlockPos()));
        });
        ContainerProviderRegistry.INSTANCE.registerFactory(QUARRY, (syncId, identifier, player, buf) -> {
            return new QuarryContainer(syncId, player, BlockContext.create(player.world, buf.readBlockPos()));
        });
    }
}