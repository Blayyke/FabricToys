package io.github.blayyke.fabrictoys;

import java.io.IOException;

public class FabricToysClient {
    public static PrefixedLogger LOGGER = new PrefixedLogger("FabricToys");

    @SuppressWarnings("unused")
    public static void init() throws IOException {
        FTScreens.init();

        LOGGER.info("FabricToys client initialized!");
    }
}