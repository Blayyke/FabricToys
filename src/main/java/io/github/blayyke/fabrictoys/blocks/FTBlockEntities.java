/*
 *     This file is part of FabricToys.
 *
 *     FabricToys is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     FabricToys is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with FabricToys.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.blayyke.fabrictoys.blocks;

import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.blocks.chest.FTChestBlockEntity;
import io.github.blayyke.fabrictoys.blocks.cobblegen.CobblestoneGeneratorBlockEntity;
import io.github.blayyke.fabrictoys.blocks.disccopier.DiscCopierBlockEntity;
import io.github.blayyke.fabrictoys.blocks.furnace.FTFurnaceBlockEntity;
import io.github.blayyke.fabrictoys.blocks.quarry.QuarryBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

import static io.github.blayyke.fabrictoys.blocks.FTBlocks.*;

public class FTBlockEntities {
    public static final BlockEntityType<?> DISC_COPIER = register(Constants.Blocks.DISC_COPIER, DiscCopierBlockEntity::new, FTBlocks.DISC_COPIER);
    public static final BlockEntityType<?> FURNACE = register(Constants.Blocks.FURNACE, FTFurnaceBlockEntity::new, ANDESITE_FURNACE, DIORITE_FURNACE, GRANITE_FURNACE);
    public static final BlockEntityType<FTChestBlockEntity> CHEST = register(Constants.Blocks.CHEST, FTChestBlockEntity::new, CHESTS);
    public static final BlockEntityType<?> QUARRY = register(Constants.Blocks.QUARRY, QuarryBlockEntity::new, FTBlocks.QUARRY);
    public static final BlockEntityType<?> COBBLESTONE_GENERATOR = register(Constants.Blocks.COBBLESTONE_GENERATOR_TIER_1, CobblestoneGeneratorBlockEntity::new, COBBLESTONE_GENERATOR_TIER_1, COBBLESTONE_GENERATOR_TIER_2, COBBLESTONE_GENERATOR_TIER_3, COBBLESTONE_GENERATOR_TIER_4);

    private static <K extends BlockEntity> BlockEntityType<K> register(String id, Supplier<K> entitySupplier, Block... blocks) {
        // ... .build(null) <- null is datafixer, they don't work with mods.
        return Registry.register(Registry.BLOCK_ENTITY, Constants.of(id), BlockEntityType.Builder.create(entitySupplier, blocks).build(null));
    }

    public static void init() {
    }
}