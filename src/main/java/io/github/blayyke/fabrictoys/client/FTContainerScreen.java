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

package io.github.blayyke.fabrictoys.client;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class FTContainerScreen<C extends Container> extends AbstractContainerScreen<C> {
    private final Identifier backgroundLocation;

    public FTContainerScreen(Identifier backgroundLocation, C container_1, PlayerInventory playerInventory_1, Text Text_1) {
        super(container_1, playerInventory_1, Text_1);
        this.backgroundLocation = backgroundLocation;
    }

    @Override
    public void render(int mouseX, int mouseY, float float_1) {
        renderBackground();
        super.render(mouseX, mouseY, float_1);
        this.drawMouseoverTooltip(mouseX, mouseY);
    }

    @Override
    protected void drawForeground(int int_1, int int_2) {
        this.font.draw(this.playerInventory.getDisplayName().asFormattedString(), 8.0F, (float) (this.containerHeight - 96 + 2), 0x404040);
        this.font.draw(this.title.asFormattedString(), 8.0F, 6.0F, 0x404040);
        this.drawTextOverlays();
    }

    @Override
    protected void drawBackground(float v, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(backgroundLocation);
        int left = this.left;
        int int_4 = (this.height - this.containerHeight) / 2;
        this.blit(left, int_4, 0, 0, this.containerWidth, this.containerHeight);
        this.drawOverlays();
    }

    protected void drawTextOverlays() {
    }

    protected void drawOverlays() {
    }
}