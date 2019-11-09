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
import io.github.blayyke.fabrictoys.blocks.chest.FTChestBlock;
import io.github.blayyke.fabrictoys.blocks.cobblegen.CobblestoneGeneratorBlock;
import io.github.blayyke.fabrictoys.blocks.craftingtable.FTCraftingTableBlock;
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
    public static final Block ANDESITE_FURNACE = register(Constants.Blocks.ANDESITE_FURNACE, new FTFurnaceBlock(FabricBlockSettings.copy(Blocks.FURNACE).build()));
    public static final Block DIORITE_FURNACE = register(Constants.Blocks.DIORITE_FURNACE, new FTFurnaceBlock(FabricBlockSettings.copy(Blocks.FURNACE).build()));
    public static final Block GRANITE_FURNACE = register(Constants.Blocks.GRANITE_FURNACE, new FTFurnaceBlock(FabricBlockSettings.copy(Blocks.FURNACE).build()));

    public static final FTChestBlock BIRCH_CHEST = register(Constants.Blocks.BIRCH_CHEST, new FTChestBlock(FabricBlockSettings.copy(Blocks.CHEST).build(), "birch"));
    public static final FTChestBlock ACACIA_CHEST = register(Constants.Blocks.ACACIA_CHEST, new FTChestBlock(FabricBlockSettings.copy(Blocks.CHEST).build(), "acacia"));
    public static final FTChestBlock DARK_OAK_CHEST = register(Constants.Blocks.DARK_OAK_CHEST, new FTChestBlock(FabricBlockSettings.copy(Blocks.CHEST).build(), "dark_oak"));
    public static final FTChestBlock SPRUCE_CHEST = register(Constants.Blocks.SPRUCE_CHEST, new FTChestBlock(FabricBlockSettings.copy(Blocks.CHEST).build(), "spruce"));
    public static final FTChestBlock JUNGLE_CHEST = register(Constants.Blocks.JUNGLE_CHEST, new FTChestBlock(FabricBlockSettings.copy(Blocks.CHEST).build(), "jungle"));

    public static final Block STONE_CRAFTING_TABLE = register(Constants.Blocks.STONE_CRAFTING_TABLE, new FTCraftingTableBlock(FabricBlockSettings.copy(Blocks.CRAFTING_TABLE).build()));
    public static final Block QUARRY = register(Constants.Blocks.QUARRY, new QuarryBlock(FabricBlockSettings.of(Material.METAL).breakByTool(FabricToolTags.PICKAXES, 1).build()));
    public static final Block DISC_COPIER = register(Constants.Blocks.DISC_COPIER, new DiscCopierBlock(FabricBlockSettings.copy(Blocks.JUKEBOX).build()));
    public static final Block COBBLESTONE_GENERATOR_TIER_1 = register(Constants.Blocks.COBBLESTONE_GENERATOR_TIER_1, new CobblestoneGeneratorBlock(FabricBlockSettings.copy(QUARRY).build(), 1));
    public static final Block COBBLESTONE_GENERATOR_TIER_2 = register(Constants.Blocks.COBBLESTONE_GENERATOR_TIER_2, new CobblestoneGeneratorBlock(FabricBlockSettings.copy(QUARRY).build(), 2));
    public static final Block COBBLESTONE_GENERATOR_TIER_3 = register(Constants.Blocks.COBBLESTONE_GENERATOR_TIER_3, new CobblestoneGeneratorBlock(FabricBlockSettings.copy(QUARRY).build(), 3));
    public static final Block COBBLESTONE_GENERATOR_TIER_4 = register(Constants.Blocks.COBBLESTONE_GENERATOR_TIER_4, new CobblestoneGeneratorBlock(FabricBlockSettings.copy(QUARRY).build(), 4));

    public static final Block EGG = register(Constants.Blocks.EGG, new EggBlock(FabricBlockSettings.of(Material.EGG).ticksRandomly().hardness(0.5F).build()));
    public static final Block COMPRESSED_COBBLESTONE = register(Constants.Blocks.COMPRESSED_COBBLESTONE, new Block(FabricBlockSettings.copy(Blocks.COBBLESTONE).hardness(2.5F).build()));
    public static final Block DOUBLE_COMPRESSED_COBBLESTONE = register(Constants.Blocks.DOUBLE_COMPRESSED_COBBLESTONE, new Block(FabricBlockSettings.copy(Blocks.COBBLESTONE).hardness(3.0F).build()));
    public static final Block TRIPLE_COMPRESSED_COBBLESTONE = register(Constants.Blocks.TRIPLE_COMPRESSED_COBBLESTONE, new Block(FabricBlockSettings.copy(Blocks.COBBLESTONE).hardness(4.0F).build()));

    private static <B extends Block> B register(String id, B block) {
        return Registry.register(Registry.BLOCK, new Identifier(Constants.MOD_ID, id), block);
    }

    public static void init() {
    }
}