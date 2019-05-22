package io.github.blayyke.fabrictoys.mixins;

import io.github.blayyke.fabrictoys.accessor.PlayerLookStackAccessor;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow
    protected abstract PlayerEntity getCameraPlayer();

    @Shadow
    public abstract TextRenderer getFontRenderer();

    @Inject(method = "draw", at = @At("RETURN"))
    public void draw(float f, CallbackInfo info) {
        PlayerLookStackAccessor cameraPlayer = (PlayerLookStackAccessor) getCameraPlayer();
        ItemStack stack = cameraPlayer.getLookStack();
        if (stack == null || stack.isEmpty()) {
            return;
        }
        TextRenderer font = getFontRenderer();
        List<Component> tooltipText = stack.getTooltipText(getCameraPlayer(), TooltipContext.Default.ADVANCED);
//        System.out.println("Drawing text : " + tooltipText.size());
        font.drawWithShadow(stack.getDisplayName().getText()    , 50f, 50f - font.fontHeight - 1, 0xFFFFFFFF);
        for (int i = 0; i < tooltipText.size(); i++) {
            Component component = tooltipText.get(i);
            font.drawWithShadow(new TranslatableComponent(component.getText()).getText(), 50f, 50f + (i * font.fontHeight + 1), 0xFFFFFFFF);
        }
    }
}