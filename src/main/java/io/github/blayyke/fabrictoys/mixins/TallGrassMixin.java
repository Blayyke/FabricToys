package io.github.blayyke.fabrictoys.mixins;

import io.github.blayyke.fabrictoys.FabricToys;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TallPlantBlock.class)
public class TallGrassMixin extends PlantBlock {
    protected TallGrassMixin(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Override
    public void onEntityCollision(BlockState blockState_1, World world_1, BlockPos blockPos_1, Entity entity_1) {
        if (FabricToys.CONFIG.enableGrassSlowTweak) {
            entity_1.slowMovement(blockState_1, FabricToys.CONFIG.grassSlownessVec);
            // TODO Pros/cons with this versus using a slowness potion. Try both?
        }
    }
}