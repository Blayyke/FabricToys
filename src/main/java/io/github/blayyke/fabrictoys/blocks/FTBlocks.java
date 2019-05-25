package io.github.blayyke.fabrictoys.blocks;

import io.github.blayyke.fabrictoys.Identifiers;
import io.github.blayyke.fabrictoys.blocks.bench.CraftingBenchBlock;
import io.github.blayyke.fabrictoys.blocks.disccopier.DiscCopierBlock;
import io.github.blayyke.fabrictoys.blocks.disenchanter.DisenchanterBlock;
import io.github.blayyke.fabrictoys.blocks.grave.GraveBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FTBlocks {
    public static final Block CRAFTING_BENCH = register(Identifiers.Blocks.CRAFTING_BENCH, new CraftingBenchBlock(FabricBlockSettings.copy(Blocks.CRAFTING_TABLE).build()));
    public static final Block DISC_COPIER = register(Identifiers.Blocks.DISC_COPIER, new DiscCopierBlock(FabricBlockSettings.copy(Blocks.JUKEBOX).build()));
    public static final Block DISENCHANTER = register(Identifiers.Blocks.DISENCHANTER, new DisenchanterBlock(FabricBlockSettings.copy(Blocks.ENCHANTING_TABLE).build()));
    public static final Block GRAVE = register(Identifiers.Blocks.GRAVE, new GraveBlock(FabricBlockSettings.copy(Blocks.COBBLESTONE).build()));

    private static <B extends Block> B register(String id, B block) {
        return Registry.register(Registry.BLOCK, new Identifier(Identifiers.MOD_ID, id), block);
    }

    public static void init() {
    }
}