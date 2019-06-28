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

package io.github.blayyke.fabrictoys.blocks.furnace;

import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.FurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class FTFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    public FTFurnaceBlockEntity() {
        super(FTBlockEntities.FURNACE, RecipeType.SMELTING);
    }

    protected Text getContainerName() {
        return new TranslatableText("container.furnace");
    }

    protected Container createContainer(int syncId, PlayerInventory playerInv) {
        return new FurnaceContainer(syncId, playerInv, this, this.propertyDelegate);
    }
}