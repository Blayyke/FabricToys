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

import io.github.blayyke.fabrictoys.FTContainers;
import io.github.blayyke.fabrictoys.blocks.disccopier.DiscCopierContainer;
import io.github.blayyke.fabrictoys.blocks.disccopier.DiscCopierScreen;
import io.github.blayyke.fabrictoys.blocks.quarry.QuarryContainer;
import io.github.blayyke.fabrictoys.blocks.quarry.QuarryScreen;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.container.BlockContext;

public class FTScreens {
    public static void init() {
        ScreenProviderRegistry.INSTANCE.registerFactory(FTContainers.DISC_COPIER, (syncId, id, player, buf) -> {
            return new DiscCopierScreen(new DiscCopierContainer(syncId, player, BlockContext.create(player.world, buf.readBlockPos())), player.inventory);
        });
        ScreenProviderRegistry.INSTANCE.registerFactory(FTContainers.QUARRY, (syncId, id, player, buf) -> {
            return new QuarryScreen(new QuarryContainer(syncId, player, BlockContext.create(player.world, buf.readBlockPos())), player.inventory);
        });
    }
}