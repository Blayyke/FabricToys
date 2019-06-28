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

import net.minecraft.item.Item;

public class QuarryDrillItem extends Item {
    private final int miningLevel;

    public QuarryDrillItem(Item.Settings settings, int miningLevel) {
        super(settings);
        this.miningLevel = miningLevel;
    }

    //TODO use this in quarrys mining logic.
    public int getMiningLevel() {
        return miningLevel;
    }
}