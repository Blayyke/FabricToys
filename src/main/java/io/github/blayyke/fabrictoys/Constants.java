package io.github.blayyke.fabrictoys;

import net.minecraft.util.Identifier;

public class Constants {
    public static final String MOD_ID = "fabric_toys";

    public static Identifier of(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static String ofString(String path) {
        return MOD_ID + ":" + path;
    }

    public static Identifier gui(String path) {
        return of("gui/" + path);
    }

    public static class Blocks {
        public static final String DISC_COPIER = "disc_copier";

        public static final String ANDESITE_FURNACE = "andesite_furnace";
        public static final String DIORITE_FURNACE = "diorite_furnace";
        public static final String GRANITE_FURNACE = "granite_furnace";
        public static final String FURNACE = "furnace";

        public static final String BIRCH_CHEST = "birch_chest";
        public static final String CHEST = "chest";

        public static final String EGG = "egg";
        public static final String QUARRY = "quarry";

    }

    public class Items {
        public static final String BLANK_DISC = "blank_disc";
        public static final String WRENCH = "wrench";
        public static final String WOODEN_QUARRY_DRILL = "wooden_quarry_drill";
        public static final String STONE_QUARRY_DRILL = "stone_quarry_drill";
        public static final String IRON_QUARRY_DRILL = "iron_quarry_drill";
        public static final String GOLD_QUARRY_DRILL = "gold_quarry_drill";
        public static final String DIAMOND_QUARRY_DRILL = "diamond_quarry_drill";
        public static final String SPEED_UPGRADE = "speed_upgrade";
    }

    public class Sprites {
        public static final String PICKAXE = "slot/pickaxe";
        public static final String COAL = "slot/coal";
        public static final String UPGRADE = "slot/upgrade";
    }

    public class GuiColors {
        public static final int GREEN = 0x3AB700;
        public static final int GRAY = 0x404040;
        public static final int RED = 0xFF5555;
    }
}