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

import io.github.blayyke.fabrictoys.FabricToys;
import net.minecraft.block.CampfireBlock;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(CampfireBlock.class)
public class CampfireMixin {
    @ModifyArg(
            method = "getPlacementState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"
            ),
            slice = @Slice(
                    from = @At(value = "FIELD", target = "Lnet/minecraft/block/CampfireBlock;LIT:Lnet/minecraft/state/property/BooleanProperty;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemPlacementContext;getPlayerFacing()Lnet/minecraft/util/math/Direction;")
            ),
            index = 1,
            allow = 1
    )
    private Comparable<Boolean> modifyLitState(final Property<Boolean> property, final Comparable<Boolean> value) {
        if (FabricToys.CONFIG.enableCampfireTweak) {
            if (property == CampfireBlock.LIT) {
                return Boolean.FALSE; // or your value
            }
            return value;
        }

        return value;
    }
}