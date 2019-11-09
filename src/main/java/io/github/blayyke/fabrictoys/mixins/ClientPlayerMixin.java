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

import com.mojang.authlib.GameProfile;
import io.github.blayyke.fabrictoys.accessor.PlayerLookStackAccessor;
import io.github.blayyke.fabrictoys.util.ItemFramePredicate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

//import net.minecraft.server.network.ServerPlayerEntity;

@Mixin({ClientPlayerEntity.class, /*ServerPlayerEntity.class*/})
public abstract class ClientPlayerMixin extends PlayerEntity implements PlayerLookStackAccessor {
    private ItemStack lookStack;

    public ClientPlayerMixin(World world_1, GameProfile gameProfile_1) {
        super(world_1, gameProfile_1);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo info) {
        this.lookStack = ItemStack.EMPTY;
        HitResult hitResult = MinecraftClient.getInstance().crosshairTarget;
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            List<ItemFrameEntity> entities = world.getEntities(ItemFrameEntity.class, new Box(new BlockPos(hitResult.getPos())), new ItemFramePredicate());
            entities.stream().findFirst().ifPresent(frame -> this.lookStack = frame.getHeldItemStack());
        }
    }

    @Override
    public ItemStack getLookStack() {
        return this.lookStack;
    }
}