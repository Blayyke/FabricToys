package io.github.blayyke.fabrictoys.blocks.quarry;

import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.FabricToys;
import io.github.blayyke.fabrictoys.blocks.MachineStatus;
import io.github.blayyke.fabrictoys.client.FTContainerScreen;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.network.packet.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class QuarryScreen extends FTContainerScreen {
    private static final Identifier BACKGROUND = new Identifier(Constants.MOD_ID, "textures/gui/quarry.png");
    private static final Identifier FURNACE_TEX = new Identifier("textures/gui/container/furnace.png");

    public QuarryScreen(Container container, PlayerInventory playerInv) {
        super(BACKGROUND, container, playerInv, new TranslatableComponent(Constants.MOD_ID + ".container.quarry"));
        //TODO make the screen taller, 3 slots dont fit well.
        this.containerHeight = 133;
    }

    @Override
    protected void init() {
        super.init();

        QuarryBlockEntity quarry = ((QuarryContainer) container).quarry;

        addButton(new ButtonWidget(this.left + 47, this.top + 17, 36, 20, quarry.isActive() ? "Stop" : "Start", (button) -> {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeBlockPos(quarry.getPos());
            boolean active;
            if (quarry.isActive()) {
                button.setMessage("Start");
                active = false;
            } else {
                button.setMessage("Stop");
                active = true;
            }
            quarry.setActive(active);
            buf.writeBoolean(active);
            ClientSidePacketRegistry.INSTANCE.sendToServer(new CustomPayloadC2SPacket(FabricToys.QUARRY_UPDATE, buf));
        }));
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
        QuarryBlockEntity quarry = ((QuarryContainer) this.container).quarry;
        MachineStatus status = quarry.getStatus();

        this.font.draw("Status: ", 110, 17, 0x404040);
        this.font.draw(new TranslatableComponent(status.getDisplayText()).getFormattedText(), 110, 28, status.getDisplayColour());
    }
}