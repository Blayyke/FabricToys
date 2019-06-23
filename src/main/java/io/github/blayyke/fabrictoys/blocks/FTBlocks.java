package io.github.blayyke.fabrictoys.blocks;

import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.blocks.disccopier.DiscCopierBlock;
import io.github.blayyke.fabrictoys.blocks.furnace.FTFurnaceBlock;
import io.github.blayyke.fabrictoys.blocks.quarry.QuarryBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FTBlocks {
    public static final Block DISC_COPIER = register(Constants.Blocks.DISC_COPIER, new DiscCopierBlock(FabricBlockSettings.copy(Blocks.JUKEBOX).build()));

    public static final Block ANDESITE_FURNACE = register(Constants.Blocks.ANDESITE_FURNACE, new FTFurnaceBlock(FabricBlockSettings.copy(Blocks.FURNACE).build()));
    public static final Block DIORITE_FURNACE = register(Constants.Blocks.DIORITE_FURNACE, new FTFurnaceBlock(FabricBlockSettings.copy(Blocks.FURNACE).build()));
    public static final Block GRANITE_FURNACE = register(Constants.Blocks.GRANITE_FURNACE, new FTFurnaceBlock(FabricBlockSettings.copy(Blocks.FURNACE).build()));

    public static final Block BIRCH_CHEST = register(Constants.Blocks.BIRCH_CHEST, new FTChestBlock(FabricBlockSettings.copy(Blocks.CHEST).build(),
            Constants.of("textures/entity/chest/birch.png"), Constants.of("textures/entity/chest/birch_double.png")));

    public static final Block QUARRY = register(Constants.Blocks.QUARRY, new QuarryBlock(FabricBlockSettings.of(Material.METAL).breakByTool(FabricToolTags.PICKAXES, 1).build()));
    public static final Block EGG = register(Constants.Blocks.EGG, new EggBlock(FabricBlockSettings.of(Material.EGG).ticksRandomly().hardness(0.5F).build()));

    private static <B extends Block> B register(String id, B block) {
        return Registry.register(Registry.BLOCK, new Identifier(Constants.MOD_ID, id), block);
    }

    public static void init() {
    }
}