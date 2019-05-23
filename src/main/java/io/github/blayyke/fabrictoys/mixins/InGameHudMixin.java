package io.github.blayyke.fabrictoys.mixins;

import io.github.blayyke.fabrictoys.client.FabricToysClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow
    protected abstract PlayerEntity getCameraPlayer();

    @Inject(method = "draw", at = @At("RETURN"))
    public void draw(float f, CallbackInfo info) {
        FabricToysClient.drawHud(f, (InGameHud) (Object) this, getCameraPlayer());
    }
}