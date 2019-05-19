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
//    @ModifyVariable(method = "getPlacementState", at = @At(value = "INVOKE"), print = true)
//    public boolean setBoolean_1(boolean current) {
//        return true;
//    }

    //    @ModifyArg(
//            method = "getPlacementState",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"
//            ),
//            index = 1
//    )
//    private Comparable<?> modifyLitState(final Property<?> property, final Comparable<?> value) {
//        if (FabricToys.CONFIG.enableCampfireTweak) {
//            if (LIT == property) {
//                return false; // or your value
//            } else {
//                return value;
//            }
//        } else {
//            return value;
//        }
//    }
//  Below is safer..
    @ModifyArg(
            method = "getPlacementState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"
            ),
            slice = @Slice(
                    from = @At(value = "FIELD", target = "Lnet/minecraft/block/CampfireBlock;LIT:Lnet/minecraft/state/property/BooleanProperty;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemPlacementContext;getPlayerHorizontalFacing()Lnet/minecraft/util/math/Direction;")
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