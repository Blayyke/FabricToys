package io.github.blayyke.fabrictoys.blocks.disccopier;

import io.github.blayyke.fabrictoys.Identifiers;
import io.github.blayyke.fabrictoys.client.FTContainerScreen;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;

public class DiscCopierScreen extends FTContainerScreen {
    private static final Identifier BACKGROUND = new Identifier(Identifiers.MOD_ID, "textures/gui/generic_2in1out_screen.png");

    public DiscCopierScreen(Container container, PlayerInventory playerInv) {
        super(BACKGROUND, container, playerInv, new TranslatableComponent(Identifiers.MOD_ID + ".container.disc_copier"));
        this.containerHeight = 133;
    }
}