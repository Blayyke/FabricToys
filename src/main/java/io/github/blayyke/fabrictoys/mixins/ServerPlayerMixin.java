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

package io.github.blayyke.fabrictoys.mixins;

import io.github.blayyke.fabrictoys.accessor.ServerPlayerAccessor;
import io.github.blayyke.fabrictoys.util.NbtUtils;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.GlobalPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedList;
import java.util.List;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin implements ServerPlayerAccessor {
    private List<GlobalPos> homes = new LinkedList<>();

    @Override
    public List<GlobalPos> getHomes() {
        return homes;
    }

    @Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
    public void ft_writePlayerData(CompoundTag tag, CallbackInfo info) {
        ListTag homesTag = new ListTag();
        for (GlobalPos pos : homes) {
            homesTag.add(NbtUtils.globalPosToTag(pos));
        }
        tag.put("Homes", homesTag);
    }

    @Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
    public void ft_readPlayerData(CompoundTag tag, CallbackInfo info) {
        ListTag homes = tag.getList("Homes", NbtType.COMPOUND);
        homes.forEach(homeTag -> {
            this.homes.add(NbtUtils.globalPosFromTag((CompoundTag) homeTag));
        });
    }
}