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