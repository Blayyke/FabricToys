package io.github.blayyke.fabrictoys;

import net.minecraft.util.Identifier;

public class Constants {
    public static final String MOD_ID = "fabric_toys";

    public static class Blocks {
        public static final String CRAFTING_BENCH = "crafting_bench";
    }

    public static Identifier gui(String id) {
        return new Identifier(MOD_ID, "gui/" + id);
    }
}