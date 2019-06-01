package io.github.blayyke.fabrictoys;

import net.minecraft.util.Identifier;

public class Identifiers {
    public static final String MOD_ID = "fabric_toys";

    public static Identifier of(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static Identifier gui(String id) {
        return of("gui/" + id);
    }

    public static class Blocks {
        public static final String CRAFTING_BENCH = "crafting_bench";
        public static final String DISC_COPIER = "disc_copier";
        public static final String DISENCHANTER = "disenchanter";
        public static final String ANDESITE_FURNACE = "andesite_furnace";
        public static final String DIORITE_FURNACE = "diorite_furnace";
        public static final String GRANITE_FURNACE = "granite_furnace";
        public static final String FURNACE = "furnace";
    }

    public class Items {
        public static final String BLANK_DISC = "blank_disc";
    }
}