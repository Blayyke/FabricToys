package io.github.blayyke.fabrictoys.mixins;

import io.github.blayyke.fabrictoys.FabricToys;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntegerProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.loot.context.LootContext;
import net.minecraft.world.loot.context.LootContextParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends PlantBlock {
    @Shadow
    protected abstract int getAge(BlockState blockState_1);

    @Shadow
    public abstract int getMaxAge();

    @Shadow
    public abstract IntegerProperty getAgeProperty();

    @Shadow
    public abstract BlockState withAge(int int_1);

    protected CropBlockMixin(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Override
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult_1) {
        if(FabricToys.CONFIG.enableCropHarvestTweak){
            int age = getAge(state);
            if (age == getMaxAge()) {
                if (!world.isClient) {
                    List<ItemStack> droppedStacks = state.getDroppedStacks(new LootContext.Builder((ServerWorld) world).setRandom(world.random).put(LootContextParameters.POSITION, pos).put(LootContextParameters.TOOL, player.getStackInHand(hand)));

                    for (ItemStack drop : droppedStacks) {
                        player.dropStack(drop);
                    }
                    world.setBlockState(pos, withAge(0));
                    world.playSound(player, pos, SoundEvents.BLOCK_CROP_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                return true;
            }
            return false;
        }

        return super.activate(state, world, pos, player, hand, blockHitResult_1);
    }
}