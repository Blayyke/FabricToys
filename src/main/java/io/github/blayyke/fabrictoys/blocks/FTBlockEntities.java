package io.github.blayyke.fabrictoys.blocks;

import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.blocks.disccopier.DiscCopierBlockEntity;
import io.github.blayyke.fabrictoys.blocks.furnace.FTFurnaceBlockEntity;
import io.github.blayyke.fabrictoys.blocks.quarry.QuarryBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class FTBlockEntities {
    public static final BlockEntityType<?> DISC_COPIER = register(Constants.Blocks.DISC_COPIER, DiscCopierBlockEntity::new, FTBlocks.DISC_COPIER);
    public static final BlockEntityType<?> FURNACE = register(Constants.Blocks.FURNACE, FTFurnaceBlockEntity::new, FTBlocks.ANDESITE_FURNACE, FTBlocks.DIORITE_FURNACE, FTBlocks.GRANITE_FURNACE);
    public static final BlockEntityType<?> CHEST = register(Constants.Blocks.CHEST, FTChestBlockEntity::new, FTBlocks.BIRCH_CHEST);
    public static final BlockEntityType<?> QUARRY = register(Constants.Blocks.QUARRY, QuarryBlockEntity::new, FTBlocks.QUARRY);

    private static <K extends BlockEntity> BlockEntityType<K> register(String id, Supplier<K> entitySupplier, Block... blocks) {
        // ... .build(null) <- null is datafixer, they don't work with mods.
        return Registry.register(Registry.BLOCK_ENTITY, Constants.of(id), BlockEntityType.Builder.create(entitySupplier, blocks).build(null));
    }

    public static void init() {
    }
}