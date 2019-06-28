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

package io.github.blayyke.fabrictoys.blocks.disccopier;

import io.github.blayyke.fabrictoys.blocks.BlockEntityWithInventory;
import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tickable;

public class DiscCopierBlockEntity extends BlockEntityWithInventory implements Tickable {
    public DiscCopierBlockEntity() {
        super(FTBlockEntities.DISC_COPIER);
    }

    @Override
    public void tick() {
        ItemStack blankStack = getInvStack(0);
        ItemStack toCopy = getInvStack(1);
        ItemStack outputStack = getInvStack(2);

        if (blankStack.isEmpty() || toCopy.isEmpty() || !outputStack.isEmpty()) {
            return;
        }

        if (!world.isClient()) {
            blankStack.decrement(1);
            setInvStack(2, toCopy.copy());
        }
    }

    @Override
    public int getInvSize() {
        return 3;
    }
}