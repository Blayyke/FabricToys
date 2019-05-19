package io.github.blayyke.fabrictoys;

import io.github.blayyke.fabrictoys.config.Config;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;

public class FabricToys {
    public static final Config CONFIG = new Config(new File(FabricLoader.getInstance().getConfigDirectory(), "FabricToys/fabricToys.properties"));
    public static PrefixedLogger LOGGER = new PrefixedLogger("FabricToys");

    @SuppressWarnings("unused")
    public static void init() throws IOException {
        CONFIG.read();
        FTCommands.init();

        LOGGER.info("FabricToys initialized!");
    }
}