package io.github.blayyke.fabrictoys;

import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import io.github.blayyke.fabrictoys.config.ConfigVals;
import io.github.blayyke.fabrictoys.config.ModConfig;
import io.github.blayyke.fabrictoys.items.FTItems;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;

import java.io.File;
import java.io.IOException;

public class FabricToys {
    public static ConfigVals CONFIG;
    public static PrefixedLogger LOGGER = new PrefixedLogger("FabricToys");

    @SuppressWarnings("unused")
    public static void init() throws IOException {
        ModConfig modConfig = new ModConfig(new File(FabricLoader.getInstance().getConfigDirectory(), "FabricToys/FabricToys.json5"));
        modConfig.saveDefaultConfig();
        CONFIG = modConfig.read();

        FTBlocks.init();
        FTBlockEntities.init();
        FTItems.init();
        FTContainers.init();

        FTCommands.init();

        LOGGER.info("FabricToys initialized!");
    }
}