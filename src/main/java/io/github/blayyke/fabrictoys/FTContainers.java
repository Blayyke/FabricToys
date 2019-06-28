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

package io.github.blayyke.fabrictoys;

import io.github.blayyke.fabrictoys.blocks.disccopier.DiscCopierContainer;
import io.github.blayyke.fabrictoys.blocks.quarry.QuarryContainer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.util.Identifier;

public class FTContainers {
    public static final Identifier DISC_COPIER = Constants.of(Constants.Blocks.DISC_COPIER);
    public static final Identifier QUARRY = Constants.of(Constants.Blocks.QUARRY);

    public static void init() {
        ContainerProviderRegistry.INSTANCE.registerFactory(DISC_COPIER, (syncId, identifier, player, buf) -> {
            return new DiscCopierContainer(syncId, player, player.world.getBlockEntity(buf.readBlockPos()));
        });
        ContainerProviderRegistry.INSTANCE.registerFactory(QUARRY, (syncId, identifier, player, buf) -> {
            return new QuarryContainer(syncId, player, player.world.getBlockEntity(buf.readBlockPos()));
        });
    }
}