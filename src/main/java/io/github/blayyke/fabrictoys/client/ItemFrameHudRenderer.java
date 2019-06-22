package io.github.blayyke.fabrictoys.client;

import io.github.blayyke.fabrictoys.FabricToys;
import io.github.blayyke.fabrictoys.accessor.PlayerLookStackAccessor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

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
            List<Component> tooltipText = stack.getTooltip(player, TooltipContext.Default.ADVANCED);
//        System.out.println("Drawing text : " + tooltipText.size());
            font.drawWithShadow(stack.getCustomName().getText(), 50f, 50f - font.fontHeight - 1, 0xFFFFFFFF);
            for (int i = 0; i < tooltipText.size(); i++) {
                Component component = tooltipText.get(i);
                font.drawWithShadow(new TranslatableComponent(component.getText()).getText(), 50f, 50f + (i * font.fontHeight + 1), 0xFFFFFFFF);
            }
        }
    }
}
