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

    public static String tooltip(String path) {
        return ofString("tooltip." + path);
    }

    public static class Blocks {
        public static final String EGG = "egg";

        public static final String ANDESITE_FURNACE = "andesite_furnace";
        public static final String DIORITE_FURNACE = "diorite_furnace";
        public static final String GRANITE_FURNACE = "granite_furnace";
        public static final String FURNACE = "furnace";

        public static final String ACACIA_CHEST = "acacia_chest";
        public static final String BIRCH_CHEST = "birch_chest";
        public static final String CHEST = "chest";
        public static final String DARK_OAK_CHEST = "dark_oak_chest";
        public static final String SPRUCE_CHEST = "spruce_chest";
        public static final String JUNGLE_CHEST = "jungle_chest";

        public static final String DISC_COPIER = "disc_copier";
        public static final String QUARRY = "quarry";
        public static final String STONE_CRAFTING_TABLE = "stone_crafting_table";
        public static final String COBBLESTONE_GENERATOR = "cobblestone_generator";
        public static final String COBBLESTONE_GENERATOR_TIER_1 = COBBLESTONE_GENERATOR + "_tier_1";
        public static final String COBBLESTONE_GENERATOR_TIER_2 = COBBLESTONE_GENERATOR + "_tier_2";
        public static final String COBBLESTONE_GENERATOR_TIER_3 = COBBLESTONE_GENERATOR + "_tier_3";
        public static final String COBBLESTONE_GENERATOR_TIER_4 = COBBLESTONE_GENERATOR + "_tier_4";

        public static final String COMPRESSED_COBBLESTONE = "compressed_cobblestone";
        public static final String DOUBLE_COMPRESSED_COBBLESTONE = "double_compressed_cobblestone";
        public static final String TRIPLE_COMPRESSED_COBBLESTONE = "triple_compressed_cobblestone";
    }

    public class Items {
        public static final String BLANK_DISC = "blank_disc";
        public static final String WRENCH = "wrench";
        public static final String SPEED_UPGRADE = "speed_upgrade";
        public static final String SLEEPING_BAG = "sleeping_bag";

        public static final String WOODEN_QUARRY_DRILL = "wooden_quarry_drill";
        public static final String STONE_QUARRY_DRILL = "stone_quarry_drill";
        public static final String IRON_QUARRY_DRILL = "iron_quarry_drill";
        public static final String GOLD_QUARRY_DRILL = "gold_quarry_drill";
        public static final String DIAMOND_QUARRY_DRILL = "diamond_quarry_drill";
        public static final String PISTOL = "pistol";
    }

    public class Sprites {
        public static final String PICKAXE = "slot/pickaxe";
        public static final String COAL = "slot/coal";
        public static final String UPGRADE = "slot/upgrade";
        public static final String DISC = "slot/disc";
    }

    public class GuiColors {
        public static final int GREEN = 0x3AB700;
        public static final int GRAY = 0x404040;
        public static final int RED = 0xFF5555;
    }

    public class KeyBindings {
        public static final String RELOAD = "key.reload";
    }

    public class Entities {
        public static final String BULLET = "bullet";
    }
}