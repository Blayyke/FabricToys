package io.github.blayyke.fabrictoys.items;

import io.github.blayyke.fabrictoys.Identifiers;
import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FTItems {
    public static ItemGroup GROUP = FabricItemGroupBuilder.create(new Identifier(Identifiers.MOD_ID, "item_group")).icon(() -> new ItemStack(FTBlocks.CRAFTING_BENCH)).build();

    public static BlockItem CRAFTING_BENCH = register(Identifiers.Blocks.CRAFTING_BENCH, new BlockItem(FTBlocks.CRAFTING_BENCH, new Item.Settings().itemGroup(GROUP)));
    public static BlockItem DISC_COPIER = register(Identifiers.Blocks.DISC_COPIER, new BlockItem(FTBlocks.DISC_COPIER, new Item.Settings().itemGroup(GROUP)));
    public static BlockItem DISENCHANTER = register(Identifiers.Blocks.DISENCHANTER, new BlockItem(FTBlocks.DISENCHANTER, new Item.Settings().itemGroup(GROUP)));

    public static Item BLANK_DISC = register(Identifiers.Items.BLANK_DISC, new BlankDiscItem(new Item.Settings().itemGroup(GROUP).stackSize(1)));

    public static <I extends Item> I register(String id, I item) {
        return Registry.register(Registry.ITEM, new Identifier(Identifiers.MOD_ID, id), item);
    }

    public static void init() {
    }
}