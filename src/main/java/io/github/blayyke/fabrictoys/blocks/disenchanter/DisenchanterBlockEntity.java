package io.github.blayyke.fabrictoys.blocks.disenchanter;

import io.github.blayyke.fabrictoys.blocks.BlockEntityWithInventory;
import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.InfoEnchantment;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Tickable;

import java.util.ArrayList;
import java.util.Map;

public class DisenchanterBlockEntity extends BlockEntityWithInventory implements Tickable {
    public DisenchanterBlockEntity() {
        super(FTBlockEntities.DISENCHANTER);
    }

    @Override
    public void tick() {
        ItemStack emptyBooks = getInvStack(0);
        ItemStack tool = getInvStack(1);
        ItemStack output = getInvStack(2);

        if (emptyBooks.isEmpty() || tool.isEmpty() || !output.isEmpty()) {
            return;
        }
//        if (!tool.hasEnchantments()) {
//            return;
//        }

        if (!world.isClient()) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(tool);
            Enchantment enchantment = new ArrayList<>(enchantments.keySet()).get(world.getRandom().nextInt(enchantments.size()));

            if (tool.getItem() == Items.ENCHANTED_BOOK) {

            } else {
                emptyBooks.subtractAmount(1);
                ItemStack enchantedBook = EnchantedBookItem.makeStack(new InfoEnchantment(enchantment, enchantments.get(enchantment)));
                setInvStack(2, enchantedBook);

                enchantments.remove(enchantment);
                EnchantmentHelper.set(enchantments, tool);
            }
        }
    }

    @Override
    public int getInvSize() {
        return 3;
    }
}