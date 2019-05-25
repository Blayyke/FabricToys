package io.github.blayyke.fabrictoys.blocks.bench;

import io.github.blayyke.fabrictoys.Identifiers;
import io.github.blayyke.fabrictoys.client.FTContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;

public class CraftingBenchScreen extends FTContainerScreen<CraftingBenchContainer> {
    private static final Identifier BACKGROUND = new Identifier("textures/gui/container/crafting_table.png");

    public CraftingBenchScreen(CraftingBenchContainer container, PlayerInventory playerInv) {
        super(BACKGROUND, container, playerInv, new TranslatableComponent(Identifiers.MOD_ID + ".container.crafting_bench"));
    }
}