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

package io.github.blayyke.fabrictoys.blocks.disccopier;

import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.client.FTContainerScreen;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class DiscCopierScreen extends FTContainerScreen {
    private static final Identifier BACKGROUND = new Identifier(Constants.MOD_ID, "textures/gui/generic_2in1out_screen.png");

    public DiscCopierScreen(Container container, PlayerInventory playerInv) {
        super(BACKGROUND, container, playerInv, new TranslatableText(Constants.MOD_ID + ".container.disc_copier"));
        this.containerHeight = 133;
    }
}