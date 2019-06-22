package io.github.blayyke.fabrictoys.blocks.quarry;

import io.github.blayyke.fabrictoys.Identifiers;
import io.github.blayyke.fabrictoys.client.FTContainerScreen;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;

public class QuarryScreen extends FTContainerScreen {
    private static final Identifier BACKGROUND = new Identifier(Identifiers.MOD_ID, "textures/gui/quarry.png");
    private static final Identifier FURNACE_TEX = new Identifier("textures/gui/container/furnace.png");

    public QuarryScreen(Container container, PlayerInventory playerInv) {
        super(BACKGROUND, container, playerInv, new TranslatableComponent(Identifiers.MOD_ID + ".container.quarry"));
        this.containerHeight = 133;
    }

    @Override
    protected void drawOverlays() {
        this.minecraft.getTextureManager().bindTexture(FURNACE_TEX);

        if (((QuarryContainer) this.container).isBurningFuel()) {
            int fuelProgress = ((QuarryContainer) this.container).getFuelProgress();
            this.blit(this.left + 8, this.top + 20 + 12 - fuelProgress, 176, 12 - fuelProgress, 14, fuelProgress + 1);
        }
    }

    @Override
    protected void drawTextOverlays() {
        this.font.drawWithShadow("Status: " + ((QuarryContainer) this.container).quarry.getStatus(), this.left + 80, this.top + 6, 0x000000);
    }
}