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

package io.github.blayyke.fabrictoys.items;

import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FTItems {
    public static ItemGroup GROUP = FabricItemGroupBuilder.create(new Identifier(Constants.MOD_ID, "item_group")).icon(() -> new ItemStack(FTBlocks.ANDESITE_FURNACE)).build();

    public static final BlockItem ANDESITE_FURNACE = register(Constants.Blocks.ANDESITE_FURNACE, new BlockItem(FTBlocks.ANDESITE_FURNACE, new Item.Settings().group(GROUP)));
    public static final BlockItem DIORITE_FURNACE = register(Constants.Blocks.DIORITE_FURNACE, new BlockItem(FTBlocks.DIORITE_FURNACE, new Item.Settings().group(GROUP)));
    public static final BlockItem GRANITE_FURNACE = register(Constants.Blocks.GRANITE_FURNACE, new BlockItem(FTBlocks.GRANITE_FURNACE, new Item.Settings().group(GROUP)));


    public static final BlockItem ACACIA_CHEST = register(Constants.Blocks.ACACIA_CHEST, new BlockItem(FTBlocks.ACACIA_CHEST, new Item.Settings().group(GROUP)));
    public static final BlockItem BIRCH_CHEST = register(Constants.Blocks.BIRCH_CHEST, new BlockItem(FTBlocks.BIRCH_CHEST, new Item.Settings().group(GROUP)));
    public static final BlockItem DARK_OAK_CHEST = register(Constants.Blocks.DARK_OAK_CHEST, new BlockItem(FTBlocks.DARK_OAK_CHEST, new Item.Settings().group(GROUP)));
    public static final BlockItem SPRUCE_CHEST = register(Constants.Blocks.SPRUCE_CHEST, new BlockItem(FTBlocks.SPRUCE_CHEST, new Item.Settings().group(GROUP)));
    public static final BlockItem JUNGLE_CHEST = register(Constants.Blocks.JUNGLE_CHEST, new BlockItem(FTBlocks.JUNGLE_CHEST, new Item.Settings().group(GROUP)));

    public static final BlockItem STONE_CRAFTING_TABLE = register(Constants.Blocks.STONE_CRAFTING_TABLE, new BlockItem(FTBlocks.STONE_CRAFTING_TABLE, new Item.Settings().group(GROUP)));
    public static final BlockItem QUARRY = register(Constants.Blocks.QUARRY, new BlockItem(FTBlocks.QUARRY, new Item.Settings().group(GROUP)));
    public static final BlockItem DISC_COPIER = register(Constants.Blocks.DISC_COPIER, new BlockItem(FTBlocks.DISC_COPIER, new Item.Settings().group(GROUP)));
    public static final BlockItem COBBLESTONE_GENERATOR_TIER_1 = register(Constants.Blocks.COBBLESTONE_GENERATOR_TIER_1, new BlockItem(FTBlocks.COBBLESTONE_GENERATOR_TIER_1, new Item.Settings().group(GROUP)));
    public static final BlockItem COBBLESTONE_GENERATOR_TIER_2 = register(Constants.Blocks.COBBLESTONE_GENERATOR_TIER_2, new BlockItem(FTBlocks.COBBLESTONE_GENERATOR_TIER_2, new Item.Settings().group(GROUP)));
    public static final BlockItem COBBLESTONE_GENERATOR_TIER_3 = register(Constants.Blocks.COBBLESTONE_GENERATOR_TIER_3, new BlockItem(FTBlocks.COBBLESTONE_GENERATOR_TIER_3, new Item.Settings().group(GROUP)));
    public static final BlockItem COBBLESTONE_GENERATOR_TIER_4 = register(Constants.Blocks.COBBLESTONE_GENERATOR_TIER_4, new BlockItem(FTBlocks.COBBLESTONE_GENERATOR_TIER_4, new Item.Settings().group(GROUP)));

    public static final BlockItem COMPRESSED_COBBLESTONE = register(Constants.Blocks.COMPRESSED_COBBLESTONE, new BlockItem(FTBlocks.COMPRESSED_COBBLESTONE, new Item.Settings().group(GROUP)));
    public static final BlockItem DOUBLE_COMPRESSED_COBBLESTONE = register(Constants.Blocks.DOUBLE_COMPRESSED_COBBLESTONE, new BlockItem(FTBlocks.DOUBLE_COMPRESSED_COBBLESTONE, new Item.Settings().group(GROUP)));
    public static final BlockItem TRIPLE_COMPRESSED_COBBLESTONE = register(Constants.Blocks.TRIPLE_COMPRESSED_COBBLESTONE, new BlockItem(FTBlocks.TRIPLE_COMPRESSED_COBBLESTONE, new Item.Settings().group(GROUP)));

    public static Item BLANK_DISC = register(Constants.Items.BLANK_DISC, new BlankDiscItem(new Item.Settings().group(GROUP).maxCount(1)));
    public static Item WRENCH = register(Constants.Items.WRENCH, new WrenchItem(new Item.Settings().group(GROUP).maxCount(1)));
    public static Item SLEEPING_BAG = register(Constants.Items.SLEEPING_BAG, new SleepingBagItem(new Item.Settings().group(GROUP).maxCount(1)));
    public static Item SPEED_UPGRADE = register(Constants.Items.SPEED_UPGRADE, new Item(new Item.Settings().group(GROUP).maxCount(4)));

    public static Item WOODEN_QUARRY_DRILL = register(Constants.Items.WOODEN_QUARRY_DRILL, new QuarryDrillItem(new Item.Settings().group(GROUP).maxCount(1).maxDamage(150), ToolMaterials.WOOD.getMiningLevel())); // 59->150
    public static Item STONE_QUARRY_DRILL = register(Constants.Items.STONE_QUARRY_DRILL, new QuarryDrillItem(new Item.Settings().group(GROUP).maxCount(1).maxDamage(350), ToolMaterials.STONE.getMiningLevel())); // 131->350
    public static Item IRON_QUARRY_DRILL = register(Constants.Items.IRON_QUARRY_DRILL, new QuarryDrillItem(new Item.Settings().group(GROUP).maxCount(1).maxDamage(800), ToolMaterials.IRON.getMiningLevel())); // 250->800
    public static Item GOLD_QUARRY_DRILL = register(Constants.Items.GOLD_QUARRY_DRILL, new QuarryDrillItem(new Item.Settings().group(GROUP).maxCount(1).maxDamage(75), ToolMaterials.GOLD.getMiningLevel())); // 32->75
    public static Item DIAMOND_QUARRY_DRILL = register(Constants.Items.DIAMOND_QUARRY_DRILL, new QuarryDrillItem(new Item.Settings().group(GROUP).maxCount(1).maxDamage(4500), ToolMaterials.DIAMOND.getMiningLevel())); // 1561->4500

    public static Item PISTOL = register(Constants.Items.PISTOL, new GunItem(new Item.Settings().group(GROUP).maxCount(1).maxDamage(4500), 6, 6 * 5, 100, 10));

    //TODO foods?
    //tODO tools?
    //todo armors?
    //todo plants?

    public static <I extends Item> I register(String id, I item) {
        return Registry.register(Registry.ITEM, new Identifier(Constants.MOD_ID, id), item);
    }

    public static void init() {
    }
}