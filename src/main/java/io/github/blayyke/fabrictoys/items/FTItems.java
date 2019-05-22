package io.github.blayyke.fabrictoys.items;

import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FTItems {
    public static ItemGroup GROUP = FabricItemGroupBuilder.create(new Identifier(Constants.MOD_ID, "item_group")).icon(() -> new ItemStack(FTBlocks.CRAFTING_BENCH)).build();

    public static BlockItem CRAFTING_BENCH = register(Constants.Blocks.CRAFTING_BENCH, new BlockItem(FTBlocks.CRAFTING_BENCH, new Item.Settings().itemGroup(GROUP)));

    public static <I extends Item> I register(String id, I item) {
        return Registry.register(Registry.ITEM, new Identifier(Constants.MOD_ID, id), item);
    }

    public static void init() {
    }
}