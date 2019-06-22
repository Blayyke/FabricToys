package io.github.blayyke.fabrictoys.blocks;

import io.github.blayyke.fabrictoys.Identifiers;
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
    public static final Block DISC_COPIER = register(Identifiers.Blocks.DISC_COPIER, new DiscCopierBlock(FabricBlockSettings.copy(Blocks.JUKEBOX).build()));

    public static final Block ANDESITE_FURNACE = register(Identifiers.Blocks.ANDESITE_FURNACE, new FTFurnaceBlock(FabricBlockSettings.copy(Blocks.FURNACE).build()));
    public static final Block DIORITE_FURNACE = register(Identifiers.Blocks.DIORITE_FURNACE, new FTFurnaceBlock(FabricBlockSettings.copy(Blocks.FURNACE).build()));
    public static final Block GRANITE_FURNACE = register(Identifiers.Blocks.GRANITE_FURNACE, new FTFurnaceBlock(FabricBlockSettings.copy(Blocks.FURNACE).build()));
    public static final Block QUARRY = register(Identifiers.Blocks.QUARRY, new QuarryBlock(FabricBlockSettings.of(Material.METAL).breakByTool(FabricToolTags.PICKAXES, 1).build()));
    public static final Block EGG = register(Identifiers.Blocks.EGG, new EggBlock(FabricBlockSettings.of(Material.EGG).ticksRandomly().hardness(0.5F).build()));

    private static <B extends Block> B register(String id, B block) {
        return Registry.register(Registry.BLOCK, new Identifier(Identifiers.MOD_ID, id), block);
    }

    public static void init() {
    }
}