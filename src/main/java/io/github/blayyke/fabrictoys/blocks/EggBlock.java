package io.github.blayyke.fabrictoys.blocks;

import io.github.blayyke.fabrictoys.util.LevelEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.IntegerProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class EggBlock extends Block {
    private static final VoxelShape SMALL_SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 9.0D, 11.0D);
    private static final IntegerProperty HATCH = Properties.HATCH;

    public EggBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateFactory.getDefaultState().with(HATCH, 0));
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
        Block block = blockView_1.getBlockState(blockPos_1.down()).getBlock();
        return BlockTags.DIRT_LIKE.contains(block);
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        stateFactory$Builder_1.add(HATCH);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void onBlockAdded(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
        if (this.isSand(world_1, blockPos_1) && !world_1.isClient) {
            world_1.playLevelEvent(LevelEvents.BONEMEAL.getId(), blockPos_1, 0);
        }
    }

    @Override
    public void onScheduledTick(BlockState blockState_1, World world, BlockPos pos, Random random_1) {
        if (this.shouldHatchProgress(world) && this.isSand(world, pos)) {
            int int_1 = blockState_1.get(HATCH);
            if (int_1 < 2) {
                world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7F, 0.9F + random_1.nextFloat() * 0.2F);
                world.setBlockState(pos, blockState_1.with(HATCH, int_1 + 1), 2);
            } else {
                world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F + random_1.nextFloat() * 0.2F);
                world.clearBlockState(pos, false);
                if (!world.isClient) {
                    world.playLevelEvent(LevelEvents.DESTROY_BLOCK.getId(), pos, Block.getRawIdFromState(blockState_1));
                    ChickenEntity chicken = EntityType.CHICKEN.create(world);
                    chicken.setBreedingAge(-24000);
                    chicken.setPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), random_1.nextInt(180 * 2) - 180, 0.0F);
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
        return Items.EGG.getDefaultStack();
    }
}