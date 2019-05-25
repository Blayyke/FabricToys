package io.github.blayyke.fabrictoys.client;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Identifier;

public abstract class FTContainerScreen<C extends Container> extends AbstractContainerScreen<C> {
    private final Identifier backgroundLocation;

    public FTContainerScreen(Identifier backgroundLocation, C container_1, PlayerInventory playerInventory_1, Component component_1) {
        super(container_1, playerInventory_1, component_1);
        this.backgroundLocation = backgroundLocation;
    }

    @Override
    public void render(int int_1, int int_2, float float_1) {
        renderBackground();
        super.render(int_1, int_2, float_1);
        this.drawMouseoverTooltip(int_1, int_2);
    }

    @Override
    protected void drawForeground(int int_1, int int_2) {
        this.font.draw(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.containerHeight - 96 + 2), 0x404040);
        this.font.draw(this.title.getFormattedText(), 28.0F, 6.0F, 0x404040);
    }

    @Override
    protected void drawBackground(float var1, int var2, int var3) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(backgroundLocation);
        int left = this.left;
        int int_4 = (this.height - this.containerHeight) / 2;
        this.blit(left, int_4, 0, 0, this.containerWidth, this.containerHeight);
    }
}