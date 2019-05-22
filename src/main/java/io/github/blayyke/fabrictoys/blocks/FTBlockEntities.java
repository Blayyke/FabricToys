package io.github.blayyke.fabrictoys.blocks;

import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.blocks.bench.CraftingBenchBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class FTBlockEntities {
    public static final BlockEntityType<?> CRAFTING_BENCH = register(Constants.Blocks.CRAFTING_BENCH, CraftingBenchBlockEntity::new, FTBlocks.CRAFTING_BENCH);

    private static <K extends BlockEntity> BlockEntityType<K> register(String id, Supplier<K> entitySupplier, Block... blocks) {
        return Registry.register(Registry.BLOCK_ENTITY, new Identifier(Constants.MOD_ID, id), BlockEntityType.Builder.create(entitySupplier, blocks).build(null));
    }

    public static void init() {
    }
}