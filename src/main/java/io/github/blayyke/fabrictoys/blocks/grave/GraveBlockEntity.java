package io.github.blayyke.fabrictoys.blocks.grave;

import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import io.github.blayyke.fabrictoys.blocks.GenericBlockEntity;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class GraveBlockEntity extends GenericBlockEntity {
    private ListTag playerInv;

    public GraveBlockEntity() {
        super(FTBlockEntities.GRAVE);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.put("Inventory", playerInv);
        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        this.playerInv = tag.getList("Inventory", NbtType.COMPOUND);
    }

    public ListTag getPlayerInv() {
        return playerInv;
    }

    public void setPlayerInv(ListTag playerInv) {
        this.playerInv = playerInv;
    }
}