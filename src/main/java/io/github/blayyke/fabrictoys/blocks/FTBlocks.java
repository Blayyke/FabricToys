package io.github.blayyke.fabrictoys.blocks;

import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.blocks.bench.CraftingBenchBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FTBlocks {
    public static final Block CRAFTING_BENCH = register(Constants.Blocks.CRAFTING_BENCH, new CraftingBenchBlock(FabricBlockSettings.copy(Blocks.CRAFTING_TABLE).build()));

    private static <B extends Block> B register(String id, B block) {
        return Registry.register(Registry.BLOCK, new Identifier(Constants.MOD_ID, id), block);
    }

    public static void init() {
    }
}