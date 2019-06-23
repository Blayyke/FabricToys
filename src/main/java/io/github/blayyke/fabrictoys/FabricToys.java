package io.github.blayyke.fabrictoys;

import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import io.github.blayyke.fabrictoys.blocks.quarry.QuarryBlockEntity;
import io.github.blayyke.fabrictoys.config.ConfigVals;
import io.github.blayyke.fabrictoys.config.ModConfig;
import io.github.blayyke.fabrictoys.items.FTItems;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.File;
import java.io.IOException;

public class FabricToys {
    public static final Identifier QUARRY_UPDATE = Constants.of("quarry_update");
    public static ConfigVals CONFIG;
    public static PrefixedLogger LOGGER = new PrefixedLogger("FabricToys");

    public static void init() throws IOException {
        ModConfig modConfig = new ModConfig(new File(FabricLoader.getInstance().getConfigDirectory(), "FabricToys/FabricToys.json5"));
        modConfig.saveDefaultConfig();
        CONFIG = modConfig.read();

        FTBlocks.init();
        FTBlockEntities.init();
        FTItems.init();

        FTCommands.init();
        FTContainers.init();

        ServerSidePacketRegistry.INSTANCE.register(QUARRY_UPDATE, (context, buffer) -> {
            BlockPos pos = buffer.readBlockPos();
            boolean active = buffer.readBoolean();
            LOGGER.debug("Got quarry update packet for " + pos + "! (active = " + active + ")");

            context.getTaskQueue().execute(() -> {
                World world = context.getPlayer().world;
                BlockEntity entity = world.getBlockEntity(pos);

                if (entity instanceof QuarryBlockEntity) {
                    QuarryBlockEntity quarry = (QuarryBlockEntity) entity;
                    quarry.setActive(active);
                } else {
                    LOGGER.error("Received " + QUARRY_UPDATE + " packet but the BlockEntity is null!");
                }
            });
        });

        LOGGER.info("FabricToys initialized!");
    }
}