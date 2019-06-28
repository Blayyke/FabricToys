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

import io.github.blayyke.fabrictoys.FabricToys;
import io.github.blayyke.fabrictoys.accessor.PlayerLookStackAccessor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;

public class ItemFrameHudRenderer implements HudRenderer {
    @Override
    public void draw(float f, InGameHud hud, PlayerEntity player) {
        if (FabricToys.CONFIG.enableItemFrameInfo) {
            PlayerLookStackAccessor stackAccessor = (PlayerLookStackAccessor) player;
            ItemStack stack = stackAccessor.getLookStack();
            if (stack == null || stack.isEmpty()) {
                return;
            }
            TextRenderer font = hud.getFontRenderer();
            List<Text> tooltipText = stack.getTooltip(player, TooltipContext.Default.ADVANCED);
//        System.out.println("Drawing text : " + tooltipText.size());
            font.drawWithShadow(stack.getName().asString(), 50f, 50f - font.fontHeight - 1, 0xFFFFFFFF);
            for (int i = 0; i < tooltipText.size(); i++) {
                Text text = tooltipText.get(i);
                font.drawWithShadow(new TranslatableText(text.asString()).asString(), 50f, 50f + (i * font.fontHeight + 1), 0xFFFFFFFF);
            }
        }
    }
}
