package io.github.blayyke.fabrictoys.items;

import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FTItems {
    public static ItemGroup GROUP = FabricItemGroupBuilder.create(new Identifier(Constants.MOD_ID, "item_group")).icon(() -> new ItemStack(FTBlocks.ANDESITE_FURNACE)).build();

    public static final BlockItem DISC_COPIER = register(Constants.Blocks.DISC_COPIER, new BlockItem(FTBlocks.DISC_COPIER, new Item.Settings().group(GROUP)));

    public static final BlockItem ANDESITE_FURNACE = register(Constants.Blocks.ANDESITE_FURNACE, new BlockItem(FTBlocks.ANDESITE_FURNACE, new Item.Settings().group(GROUP)));
    public static final BlockItem DIORITE_FURNACE = register(Constants.Blocks.DIORITE_FURNACE, new BlockItem(FTBlocks.DIORITE_FURNACE, new Item.Settings().group(GROUP)));
    public static final BlockItem GRANITE_FURNACE = register(Constants.Blocks.GRANITE_FURNACE, new BlockItem(FTBlocks.GRANITE_FURNACE, new Item.Settings().group(GROUP)));

    public static final BlockItem BIRCH_CHEST = register(Constants.Blocks.BIRCH_CHEST, new BlockItem(FTBlocks.BIRCH_CHEST, new Item.Settings().group(GROUP)));

    public static final BlockItem QUARRY = register(Constants.Blocks.QUARRY, new BlockItem(FTBlocks.QUARRY, new Item.Settings().group(GROUP)));

    public static Item BLANK_DISC = register(Constants.Items.BLANK_DISC, new BlankDiscItem(new Item.Settings().group(GROUP).maxCount(1)));
    public static Item WRENCH = register(Constants.Items.WRENCH, new WrenchItem(new Item.Settings().group(GROUP).maxCount(1)));

    public static Item WOODEN_QUARRY_DRILL = register(Constants.Items.WOODEN_QUARRY_DRILL, new QuarryDrillItem(new Item.Settings().group(GROUP).maxCount(1).maxDamage(150), ToolMaterials.WOOD.getMiningLevel())); // 59->150
    public static Item STONE_QUARRY_DRILL = register(Constants.Items.STONE_QUARRY_DRILL, new QuarryDrillItem(new Item.Settings().group(GROUP).maxCount(1).maxDamage(350), ToolMaterials.STONE.getMiningLevel())); // 131->350
    public static Item IRON_QUARRY_DRILL = register(Constants.Items.IRON_QUARRY_DRILL, new QuarryDrillItem(new Item.Settings().group(GROUP).maxCount(1).maxDamage(800), ToolMaterials.IRON.getMiningLevel())); // 250->800
    public static Item GOLD_QUARRY_DRILL = register(Constants.Items.GOLD_QUARRY_DRILL, new QuarryDrillItem(new Item.Settings().group(GROUP).maxCount(1).maxDamage(75), ToolMaterials.GOLD.getMiningLevel())); // 32->75
    public static Item DIAMOND_QUARRY_DRILL = register(Constants.Items.DIAMOND_QUARRY_DRILL, new QuarryDrillItem(new Item.Settings().group(GROUP).maxCount(1).maxDamage(4500), ToolMaterials.DIAMOND.getMiningLevel())); // 1561->4500

    public static Item SPEED_UPGRADE = register(Constants.Items.SPEED_UPGRADE, new Item(new Item.Settings().group(GROUP).maxCount(4)));
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