package io.github.blayyke.fabrictoys.blocks;

import io.github.blayyke.fabrictoys.Identifiers;
import io.github.blayyke.fabrictoys.blocks.disccopier.DiscCopierBlockEntity;
import io.github.blayyke.fabrictoys.blocks.furnace.FTFurnaceBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class FTBlockEntities {
    public static final BlockEntityType<?> DISC_COPIER = register(Identifiers.Blocks.DISC_COPIER, DiscCopierBlockEntity::new, FTBlocks.DISC_COPIER);
    public static final BlockEntityType<?> FURNACE = register(Identifiers.Blocks.FURNACE, FTFurnaceBlockEntity::new, FTBlocks.ANDESITE_FURNACE, FTBlocks.DIORITE_FURNACE, FTBlocks.GRANITE_FURNACE);

    private static <K extends BlockEntity> BlockEntityType<K> register(String id, Supplier<K> entitySupplier, Block... blocks) {
        // ... .build(null) <- null is datafixer, they don't work with mods.
        return Registry.register(Registry.BLOCK_ENTITY, Identifiers.of(id), BlockEntityType.Builder.create(entitySupplier, blocks).build(null));
    }

    public static void init() {
    }
}