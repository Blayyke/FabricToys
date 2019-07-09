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

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.Item;

public class QuarryDrillItem extends Item {
    private final int miningLevel;

    public QuarryDrillItem(Item.Settings settings, int miningLevel) {
        super(settings);
        this.miningLevel = miningLevel;
    }

    @Override
    public boolean isEffectiveOn(BlockState blockState_1) {
        // TODO pls mojang. this is taken from PickaxeItem but improved readability, I need to remove this when or if they eventually add a better way of doing it
        Block block = blockState_1.getBlock();

        if (block == Blocks.OBSIDIAN) {
            return this.miningLevel >= 3;
        }
        if (block == Blocks.DIAMOND_BLOCK || block == Blocks.DIAMOND_ORE || block == Blocks.EMERALD_BLOCK || block == Blocks.EMERALD_ORE || block == Blocks.GOLD_BLOCK || block == Blocks.GOLD_ORE || block == Blocks.REDSTONE_ORE) {
            return this.miningLevel >= 2;
        }
        if (block == Blocks.IRON_BLOCK || block == Blocks.IRON_ORE || block == Blocks.LAPIS_BLOCK || block == Blocks.LAPIS_ORE) {
            return this.miningLevel >= 1;
        }
        Material material = blockState_1.getMaterial();
        return material == Material.STONE || material == Material.METAL || material == Material.ANVIL;
    }
}