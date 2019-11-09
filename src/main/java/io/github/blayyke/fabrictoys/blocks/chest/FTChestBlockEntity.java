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

package io.github.blayyke.fabrictoys.blocks.chest;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.Identifier;

public class FTChestBlockEntity extends ChestBlockEntity {
    public Identifier getTexture() {
        return ((FTChestBlock) this.getCachedState().getBlock()).getTexture();
    }

    public Identifier getRightTexture() {
        return ((FTChestBlock) this.getCachedState().getBlock()).getRightTexture();
    }

    public Identifier getLeftTexture() {
        return ((FTChestBlock) this.getCachedState().getBlock()).getLeftTexture();
    }
}