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

package io.github.blayyke.fabrictoys.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;

public class SleepingBagItem extends Item {
    public SleepingBagItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world_1, PlayerEntity player, Hand hand) {
        if (!world_1.isClient) {
            return super.use(world_1, player, hand);
        }

        //TODO flashes asleep then awake. no time changee.
        if (player.onGround && world_1.dimension.canPlayersSleep() && world_1.getBiomeAccess().getBiome(player.getBlockPos()) != Biomes.NETHER) {
            player.trySleep(player.getBlockPos()).ifLeft((failureReason) -> {
                if (failureReason != null) {
                    player.addChatMessage(failureReason.toText(), true);
                }
            });
        }
        return super.use(world_1, player, hand);
    }
}