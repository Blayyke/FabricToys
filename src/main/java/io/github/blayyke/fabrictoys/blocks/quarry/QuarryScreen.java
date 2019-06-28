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
import net.minecraft.server.network.packet.CustomPayloadC2SPacket;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class QuarryScreen extends FTContainerScreen {
    private static final Identifier BACKGROUND = new Identifier(Constants.MOD_ID, "textures/gui/quarry.png");
    private static final Identifier FURNACE_TEX = new Identifier("textures/gui/container/furnace.png");

    public QuarryScreen(Container container, PlayerInventory playerInv) {
        super(BACKGROUND, container, playerInv, new TranslatableText(Constants.MOD_ID + ".container.quarry"));
        this.containerHeight = 133;
    }

    @Override
    protected void init() {
        super.init();

        QuarryBlockEntity quarry = ((QuarryContainer) container).quarry;

        addButton(new ButtonWidget(this.left + containerWidth - 36 - 8, this.top + 17, 36, 20, quarry.isActive() ? "Stop" : "Start", (button) -> {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            boolean active;
            if (quarry.isActive()) {
                button.setMessage("Start");
                active = false;
            } else {
                button.setMessage("Stop");
                active = true;
            }
            quarry.setActive(active);
            buf.writeBlockPos(quarry.getPos());
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

        this.font.draw("Status: ", 85, 17, 0x404040);
        this.font.draw(new TranslatableText(status.getDisplayText()).asFormattedString(), 85, 28, status.getDisplayColour());
    }
}