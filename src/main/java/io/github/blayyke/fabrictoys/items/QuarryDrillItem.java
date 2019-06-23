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