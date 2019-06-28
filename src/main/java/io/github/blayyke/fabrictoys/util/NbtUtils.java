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

package io.github.blayyke.fabrictoys.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.GlobalPos;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class NbtUtils {
    public static CompoundTag globalPosToTag(GlobalPos pos) {
        CompoundTag tag = new CompoundTag();
        tag.putString("Dimension", Registry.DIMENSION.getId(pos.getDimension()).toString());
        tag.put("Position", blockPosToTag(pos.getPos()));
        return tag;
    }

    public static CompoundTag blockPosToTag(BlockPos pos) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("X", pos.getX());
        tag.putInt("Y", pos.getY());
        tag.putInt("Z", pos.getZ());
        return tag;
    }

    public static GlobalPos globalPosFromTag(CompoundTag tag) {
        return GlobalPos.create(Registry.DIMENSION.get(new Identifier(tag.getString("Dimension"))), blockPosFromTag(tag.getCompound("Position")));
    }

    private static BlockPos blockPosFromTag(CompoundTag tag) {
        return new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z"));
    }
}