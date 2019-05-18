package io.github.blayyke.fabrictoys;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FabricToys {
    public static Logger LOGGER = LogManager.getLogger("FabricToys");

    public static void init() {
        LOGGER.info("FabricToys initialized!");
    }
}
