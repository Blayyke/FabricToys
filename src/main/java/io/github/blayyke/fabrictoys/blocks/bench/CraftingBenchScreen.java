package io.github.blayyke.fabrictoys.blocks.bench;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.blayyke.fabrictoys.Constants;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;

public class CraftingBenchScreen extends AbstractContainerScreen {
    private static final Identifier BACKGROUND = new Identifier("textures/gui/container/crafting_table.png");

    public CraftingBenchScreen(Container container, PlayerInventory playerInv) {
        super(container, playerInv, new TranslatableComponent(Constants.MOD_ID + ".container.crafting_bench"));
    }

    @Override
    protected void drawBackground(float var1, int var2, int var3) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND);
        int left = this.left;
        int int_4 = (this.height - this.containerHeight) / 2;
        this.blit(left, int_4, 0, 0, this.containerWidth, this.containerHeight);
    }

    @Override
    protected void drawForeground(int int_1, int int_2) {
        this.font.draw(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.containerHeight - 96 + 2), 0x404040);
        this.font.draw(this.title.getFormattedText(), 28.0F, 6.0F, 0x404040);
    }

    @Override
    public void render(int int_1, int int_2, float float_1) {
        renderBackground();
        super.render(int_1, int_2, float_1);
        this.drawMouseoverTooltip(int_1, int_2);
    }
}