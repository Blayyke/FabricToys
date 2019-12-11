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

package io.github.blayyke.fabrictoys.blocks;

import io.github.blayyke.fabrictoys.util.LevelEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class EggBlock extends Block {
    private static final VoxelShape SMALL_SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);
    private static final IntProperty HATCH = Properties.HATCH;

    public EggBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(HATCH, 0));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
        return SMALL_SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
        return SMALL_SHAPE;
    }

    private boolean isSand(BlockView blockView_1, BlockPos blockPos_1) {
        Block block = blockView_1.getBlockState(blockPos_1.method_10074()).getBlock();
        return block == Blocks.SAND || block == Blocks.DIRT;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager$Builder_1) {
        stateManager$Builder_1.add(HATCH);
    }

//    @Override
//    public BlockRenderType getRenderType(BlockState blockState_1) {
//        return BlockRenderType.CUTOUT;
//    }

    @Override
    public void onBlockAdded(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
        if (this.isSand(world_1, blockPos_1) && !world_1.isClient) {
            world_1.playLevelEvent(LevelEvents.BONEMEAL.getId(), blockPos_1, 0);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.shouldHatchProgress(world) && this.isSand(world, pos)) {
            int int_1 = state.get(HATCH);
            if (int_1 < 2) {
                world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.setBlockState(pos, state.with(HATCH, int_1 + 1), 2);
            } else {
                world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.removeBlock(pos, false);
                if (!world.isClient) {
                    world.playLevelEvent(LevelEvents.DESTROY_BLOCK.getId(), pos, Block.getRawIdFromState(state));
                    ChickenEntity chicken = EntityType.CHICKEN.create(world);
                    chicken.setBreedingAge(-24000);
                    chicken.setPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), random.nextInt(180 * 2) - 180, 0.0F);
                    world.spawnEntity(chicken);
                }
            }
        }

    }

    private boolean shouldHatchProgress(World world_1) {
        float float_1 = world_1.getSkyAngle(1.0F);
        if ((double) float_1 < 0.69D && (double) float_1 > 0.65D) {
            return true;
        } else {
            return world_1.random.nextInt(500) == 0;
        }
    }

    @Override
    public ItemStack getPickStack(BlockView blockView_1, BlockPos blockPos_1, BlockState blockState_1) {
        return new ItemStack(Items.EGG);
    }
}