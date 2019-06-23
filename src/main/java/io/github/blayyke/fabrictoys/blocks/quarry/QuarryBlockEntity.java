package io.github.blayyke.fabrictoys.blocks.quarry;

import io.github.blayyke.fabrictoys.InventoryUtils;
import io.github.blayyke.fabrictoys.blocks.BlockEntityWithInventory;
import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import io.github.blayyke.fabrictoys.blocks.MachineStatus;
import net.fabricmc.fabric.api.registry.FuelRegistry;
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

    private int mineTime = 0;
    private BlockPos corner1;
    private BlockPos corner2;

    private int fuelTime;
    private int maxFuelTime;
    private Inventory inventory;

    private MachineStatus status;
    private boolean active = false;

    public QuarryBlockEntity() {
        super(FTBlockEntities.QUARRY);
    }

    public void updateCorners() {
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

    @Override
    public void tick() {
        if (corner1 == null || corner2 == null) {
            updateCorners();
        }

        ItemStack fuel = getInvStack(QuarryContainer.FUEL_SLOT);
        ItemStack miningTool = getTool();

        if (fuelTime > 0) {
            fuelTime--;
        }

        if (miningTool.isEmpty()) {
            // No tool to mine with.
            this.status = MachineStatus.NO_TOOL;
            return;
        }

        if (!active) {
            this.status = MachineStatus.INACTIVE;
            return;
        }

        if (fuelTime <= 0) {
            if (fuel.isEmpty()) {
                // Fuel ran out, and there's no fuel in the fuel slot.
                this.status = MachineStatus.NO_FUEL;
                return;
            } else {
                maxFuelTime = FuelRegistry.INSTANCE.get(fuel.getItem());
                fuelTime = maxFuelTime;
                fuel.decrement(1);
            }
        }

        this.status = MachineStatus.ACTIVE;
        mineTime++;

        if (mineTime >= getMineDelay()) {
            mineTime = 0;
            if (!world.isClient) {
                for (Direction dir : Direction.values()) {
                    BlockEntity above = world.getBlockEntity(this.pos.offset(dir));
                    if (above instanceof Inventory) {
                        this.inventory = (Inventory) above;
                        break;
                    } else {
                        this.inventory = null;
                    }
                }

                tryMineBlock(miningTool);
            }
        }
    }

    private int getMineDelay() {
        int speedUpgradeCount = getUpgrades().getCount();

        if (speedUpgradeCount >= 4) {
            // Should never be above 4, but if it is we might as well treat it as 4 instead of treating it as having none.
            return 10;
        } else if (speedUpgradeCount == 3) {
            return 15;
        } else if (speedUpgradeCount == 2) {
            return 20;
        } else if (speedUpgradeCount == 1) {
            return 25;
        } else {
            return 30;
        }
    }

    private void tryMineBlock(ItemStack tool) {
        BlockPos.Mutable pos = new BlockPos.Mutable();
        // Loop Y first, so it goes top to bottom.
        for (int y = corner2.getY(); y > corner1.getY(); y--) {
            for (int x = corner1.getX(); x < corner2.getX(); x++) {
                for (int z = corner1.getZ(); z < corner2.getZ(); z++) {
                    pos.set(x, y, z);
                    if (shouldMineBlock(pos, tool)) {
                        BlockState block = world.getBlockState(pos);
                        world.clearBlockState(pos, true);
                        tool.damage(1, world.random, null);
                        List<ItemStack> stacks = block.getDroppedStacks(new LootContext.Builder((ServerWorld) world)
                                .put(LootContextParameters.TOOL, tool)
                                .put(LootContextParameters.POSITION, pos)
                        );
                        if (tool.getDamage() >= tool.getMaxDamage()) {
                            System.out.println("Gone through a pickaxe!");
                            this.setInvStack(QuarryContainer.TOOL_SLOT, ItemStack.EMPTY);
                        }

                        for (ItemStack stack : stacks) {
                            storeItem(stack);
                        }
//                        System.out.println("Mined block @ " + pos + ".");

                        return;
                    }
                }
            }
        }
    }

    private void storeItem(ItemStack stack) {
        if (inventory != null) {
            // Inv above, add to inv.
            //TODO this does not work with double chests. Fills first half then aborts and starts throwing items.
            if (!InventoryUtils.isInvFull(inventory)) {
                for (int i = 0; i < inventory.getInvSize(); i++) {
                    ItemStack invStack = inventory.getInvStack(i);
                    if (invStack.isEmpty()) {
                        inventory.setInvStack(i, stack);
                        break;
                    } else if (ItemStack.areItemsEqualIgnoreDamage(invStack, stack)) {
                        if (invStack.getCount() < invStack.getMaxCount()) {
                            // TODO If current slot amount + mined item amount > slot stack getMaxAmount(), it will still only be added to the max and then extras are deleted.
                            // the below does not work as i intended, however it still works the same as it used to.
                            int newCount = invStack.getCount() + stack.getCount();
                            if (newCount > invStack.getMaxCount()) {
                                ItemStack copy = new ItemStack(invStack.getItem());
                                copy.setCount(newCount - invStack.getMaxCount());
                                storeItem(copy);
                                newCount = invStack.getMaxCount();
                            }

                            invStack.setCount(newCount);
                            break;
                        }
                    }
                }
                return;
            }
            // If inv is full, drop it as a stack.
        }

        // No inv above, drop it instead
        ItemEntity item = new ItemEntity(world, this.pos.getX(), this.pos.getY() + 1, this.pos.getZ(), stack);
        item.setToDefaultPickupDelay();
//                    item.addVelocity(0.2D, 0.6D, 0.2D);
        world.spawnEntity(item);
    }

    private boolean shouldMineBlock(BlockPos.Mutable pos, ItemStack tool) {
        // Do not mine air. Do not mine any block with a hardness less than zero.
        return !world.isAir(pos) && world.getBlockState(pos).getHardness(world, pos) > 0;
    }

    private ItemStack getTool() {
        return getInvStack(QuarryContainer.TOOL_SLOT);
    }

    private ItemStack getUpgrades() {
        return getInvStack(QuarryContainer.UPGRADE_SLOT);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putInt("FuelTime", this.fuelTime);
        tag.putInt("MaxFuelTime", this.maxFuelTime);
        tag.putBoolean("Active", this.isActive());
        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        this.fuelTime = tag.getInt("FuelTime");
        this.maxFuelTime = tag.getInt("MaxFuelTime");
        this.active = tag.getBoolean("Active");
    }

    @Override
    public int getInvSize() {
        return 3;
    }

    public int getMaxFuelTime() {
        return maxFuelTime;
    }

    public int getFuelTime() {
        return fuelTime;
    }

    public MachineStatus getStatus() {
        return this.status;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}