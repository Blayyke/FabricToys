package io.github.blayyke.fabrictoys.blocks.quarry;

import io.github.blayyke.fabrictoys.Identifiers;
import io.github.blayyke.fabrictoys.InventoryUtils;
import io.github.blayyke.fabrictoys.blocks.BlockEntityWithInventory;
import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.ChatFormat;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.loot.context.LootContext;
import net.minecraft.world.loot.context.LootContextParameters;

import java.util.List;

public class QuarryBlockEntity extends BlockEntityWithInventory implements Tickable {
    // TODO add quarry mining positions to the GUI, and not statically based on which way the quarry is facing. Do not allow the mining area to be more than 3 blocks away from the quarry at any side. Default to just under the quarry to bedrock.

    private final int mineDelay = 20;
    private int mineTime = 0;
    private BlockPos corner1;
    private BlockPos corner2;

    private int fuelTime;
    private int maxFuelTime = 100; // 5 seconds
    private Inventory inventory;

    private QuarryStatus status;

    public QuarryBlockEntity() {
        super(FTBlockEntities.QUARRY);
    }

    @Override
    public void tick() {
        if (corner1 == null || corner2 == null) {
            int offset = 5;
            Direction facing = world.getBlockState(pos).get(QuarryBlock.FACING).getOpposite();
            switch (facing) {
                case NORTH:
                    corner1 = new BlockPos(pos.getX() - 3, 0, pos.getZ() - 3 - offset);
                    corner2 = new BlockPos(pos.getX() + 4, pos.getY() - 1, pos.getZ() + 4 - offset);
                    break;
                case EAST:
                    corner1 = new BlockPos(pos.getX() - 3 + offset, 0, pos.getZ() - 3);
                    corner2 = new BlockPos(pos.getX() + 4 + offset, pos.getY() - 1, pos.getZ() + 4);
                    break;
                case SOUTH:
                    corner1 = new BlockPos(pos.getX() - 3, 0, pos.getZ() - 3 + offset);
                    corner2 = new BlockPos(pos.getX() + 4, pos.getY() - 1, pos.getZ() + 4 + offset);
                    break;
                case WEST:
                    corner1 = new BlockPos(pos.getX() - 3 - offset, 0, pos.getZ() - 3);
                    corner2 = new BlockPos(pos.getX() + 4 - offset, pos.getY() - 1, pos.getZ() + 4);
                    break;
                default:
                    // This should never happen, but here it is anyway. Don't want a null status that is a PITA to track down the line.
                    throw new RuntimeException("Horizontal facing property should never be anything other than NESW! Got " + facing);
            }
        }

        ItemStack fuel = getInvStack(QuarryContainer.FUEL_SLOT);
        ItemStack pickaxe = getTool();

        if (fuelTime > 0) {
            fuelTime--;
        }

        if (pickaxe.isEmpty()) {
            // No tool to mine with.
            this.status = QuarryStatus.NO_PICKAXE;
            return;
        }
        if (fuelTime <= 0) {
            if (fuel.isEmpty()) {
                // Fuel ran out, and there's no fuel in the fuel slot.
                this.status = QuarryStatus.NO_FUEL;
                return;
            } else {
                maxFuelTime = FuelRegistry.INSTANCE.get(fuel.getItem());
                fuelTime = maxFuelTime;
                fuel.decrement(1);
            }
        }

        this.status = QuarryStatus.MINING;
        mineTime++;

        if (mineTime >= mineDelay) {
            mineTime = 0;
            if (!world.isClient) {
                BlockEntity above = world.getBlockEntity(this.pos.add(0, 1, 0));
                if (above instanceof Inventory) {
                    this.inventory = (Inventory) above;
                } else {
                    this.inventory = null;
                }
                tryMineBlock(pickaxe);
            }
        }
    }

    private void tryMineBlock(ItemStack pickaxe) {
        BlockPos.Mutable pos = new BlockPos.Mutable();
        // Loop Y first, so it goes top to bottom.
        for (int y = corner2.getY(); y > corner1.getY(); y--) {
            for (int x = corner1.getX(); x < corner2.getX(); x++) {
                for (int z = corner1.getZ(); z < corner2.getZ(); z++) {
                    pos.set(x, y, z);
                    if (shouldMineBlock(pos)) {
                        BlockState block = world.getBlockState(pos);
                        world.clearBlockState(pos, true);
                        pickaxe.damage(1, world.random, null);
                        List<ItemStack> stacks = block.getDroppedStacks(new LootContext.Builder((ServerWorld) world)
                                .put(LootContextParameters.TOOL, pickaxe)
                                .put(LootContextParameters.POSITION, pos)
                        );
                        if (pickaxe.getDamage() >= pickaxe.getMaxDamage()) {
                            System.out.println("Gone through a pickaxe!");
                            this.setInvStack(QuarryContainer.PICKAXE_SLOT, ItemStack.EMPTY);
                        }

                        for (ItemStack stack : stacks) {
                            if (inventory != null) {
                                // Inv above, add to inv.
                                if (!InventoryUtils.isInvFull(inventory)) {
                                    for (int i = 0; i < inventory.getInvSize(); i++) {
                                        ItemStack invStack = inventory.getInvStack(i);
                                        if (invStack.isEmpty()) {
                                            inventory.setInvStack(i, stack);
                                            break;
                                        } else if (ItemStack.areItemsEqualIgnoreDamage(invStack, stack)) {
                                            if (invStack.getCount() < invStack.getMaxCount()) {
                                                // TODO If current slot amount + mined item amount > slot stack getMaxAmount(), it will still only be added to the max and then extras are deleted.
                                                int remaining = invStack.getMaxCount() - stack.getCount();
                                                invStack.increment(Math.min(remaining, stack.getCount()));
                                                break;
                                            }
                                        }
                                    }
                                    break;
                                }
                                // If inv is full, drop it as a stack.
                            }

                            // No inv above, drop it instead
                            ItemEntity item = new ItemEntity(world, this.pos.getX(), this.pos.getY() + 1, this.pos.getZ(), stack);
                            item.setToDefaultPickupDelay();
//                    item.addVelocity(0.2D, 0.6D, 0.2D);
                            world.spawnEntity(item);
                        }
//                        System.out.println("Mined block @ " + pos + ".");

                        return;
                    }
                }
            }
        }
    }

    private boolean shouldMineBlock(BlockPos.Mutable pos) {
        // Do not mine air. Do not mine any block with a hardness less than zero.
        return !world.isAir(pos) && world.getBlockState(pos).getHardness(world, pos) > 0;
    }

    private ItemStack getTool() {
        return getInvStack(QuarryContainer.PICKAXE_SLOT);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putInt("FuelTime", this.fuelTime);
        tag.putInt("MaxFuelTime", this.maxFuelTime);
        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        this.fuelTime = tag.getInt("FuelTime");
        this.maxFuelTime = tag.getInt("MaxFuelTime");
    }

    @Override
    public int getInvSize() {
        return 2;
    }

    public int getMaxFuelTime() {
        return maxFuelTime;
    }

    public int getFuelTime() {
        return fuelTime;
    }

    public QuarryStatus getStatus() {
        return this.status;
    }

    public enum QuarryStatus {
        MINING(Identifiers.MOD_ID + ".machine_status.active", 0x3ab700), NO_FUEL(Identifiers.MOD_ID + ".machine_status.no_fuel", ChatFormat.RED.getColor()), NO_PICKAXE(Identifiers.MOD_ID + ".machine_status.no_pickaxe", 0x404040);

        private final String displayText;
        private final Integer color;

        QuarryStatus(String displayText, Integer color) {
            this.displayText = displayText;
            this.color = color;
        }

        public String getDisplayText() {
            return displayText;
        }

        public Integer getColor() {
            return color;
        }
    }
}