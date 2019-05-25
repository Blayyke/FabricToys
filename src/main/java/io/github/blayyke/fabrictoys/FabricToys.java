package io.github.blayyke.fabrictoys;

import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import io.github.blayyke.fabrictoys.config.ConfigVals;
import io.github.blayyke.fabrictoys.config.ModConfig;
import io.github.blayyke.fabrictoys.items.FTItems;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;

public class FabricToys {
    public static ConfigVals CONFIG;
    public static PrefixedLogger LOGGER = new PrefixedLogger("FabricToys");

    @SuppressWarnings("unused")
    public static void init() throws IOException {
        ModConfig modConfig = new ModConfig(new File(FabricLoader.getInstance().getConfigDirectory(), "FabricToys/FabricToys.json5"));
        System.out.println("Initialized modConfig field");
        modConfig.saveDefaultConfig();
        System.out.println("Saved default confg");
        CONFIG = modConfig.read();
        System.out.println("read config");

        FTBlocks.init();
        FTBlockEntities.init();
        FTItems.init();
        FTContainers.init();

        FTCommands.init();

        LOGGER.info("FabricToys initialized!");
    }
}