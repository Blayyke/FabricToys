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
            LOGGER.info("Got quarry update packet for " + pos + "!");
            World world = context.getPlayer().world;
            BlockState blockState = world.getBlockState(pos);
            BlockEntity entity = world.getBlockEntity(pos);

            System.out.println(blockState.getBlock());
            System.out.println(entity);
            QuarryBlockEntity quarry = (QuarryBlockEntity) entity;

            if (quarry == null) {
                //TODO this happens whenever the packet is received. It makes it to this point, so the block entity should be there.
                throw new NullPointerException("Quarry is null!");
            }
            quarry.setActive(buffer.readBoolean());
        });

        LOGGER.info("FabricToys initialized!");
    }
}