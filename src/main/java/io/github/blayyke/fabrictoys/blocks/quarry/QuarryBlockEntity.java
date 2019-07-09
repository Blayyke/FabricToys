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

package io.github.blayyke.fabrictoys.blocks.quarry;

import io.github.blayyke.fabrictoys.InventoryUtils;
import io.github.blayyke.fabrictoys.blocks.BlockEntityWithInventory;
import io.github.blayyke.fabrictoys.blocks.FTBlockEntities;
import io.github.blayyke.fabrictoys.blocks.MachineStatus;
import io.github.blayyke.fabrictoys.util.ItemUtils;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
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

        if (!active) {
            this.status = MachineStatus.INACTIVE;
            return;
        }

        if (miningTool.isEmpty()) {
            // No tool to mine with.
            this.status = MachineStatus.NO_TOOL;
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

            if (!tryMineBlock(miningTool)) {
                // There were no blocks to mine in the quarries area, deactivate it so it doesn't just keep looping forever. If a block is place
                active = false;
            }
        }
    }

    private int getMineDelay() {
        int speedUpgradeCount = getUpgrades().getCount();

        if (speedUpgradeCount == 4) {
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

    private Inventory getTouchingInventory() {
        if (!world.isClient) {
            for (Direction dir : Direction.values()) {
                BlockPos invPos = this.pos.offset(dir);
                BlockEntity invEntity = world.getBlockEntity(invPos);
                if (invEntity instanceof Inventory) {
                    Inventory inv = (Inventory) invEntity;
                    BlockState aboveBlock = world.getBlockState(invPos);
                    if (invEntity instanceof ChestBlockEntity && aboveBlock.getBlock() instanceof ChestBlock) {
                        inv = ChestBlock.getInventory(aboveBlock, world, invEntity.getPos(), true);
                    }
                    if (InventoryUtils.isInvFull(inv)) {
                        // Don't return a full inventory.
                        continue;
                    }
                    return inv;
                }
            }
        }
        return null;
    }

    private boolean tryMineBlock(ItemStack tool) {
        Inventory inventory = getTouchingInventory();
        BlockPos.Mutable pos = new BlockPos.Mutable();
        // Loop Y first, so it goes top to bottom.
        for (int y = corner2.getY(); y > corner1.getY(); y--) {
            for (int x = corner1.getX(); x < corner2.getX(); x++) {
                for (int z = corner1.getZ(); z < corner2.getZ(); z++) {
                    pos.set(x, y, z);
                    if (shouldMineBlock(pos, tool)) {
                        if (!world.isClient) {
                            BlockState block = world.getBlockState(pos);
                            world.clearBlockState(pos, true);
                            tool.damage(1, world.random, null);
                            List<ItemStack> stacks = block.getDroppedStacks(new LootContext.Builder((ServerWorld) world)
                                    .put(LootContextParameters.TOOL, tool)
                                    .put(LootContextParameters.POSITION, pos)
                            );
                            if (tool.getDamage() >= tool.getMaxDamage()) {
                                this.setInvStack(QuarryContainer.TOOL_SLOT, ItemStack.EMPTY);
                            }

                            for (ItemStack stack : stacks) {
                                storeItemInInv(inventory, stack);
                            }
                        }

                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void storeItemInInv(Inventory inventory, ItemStack stackToStore) {
        if (inventory != null) {
            // Quarry is touching an inventory, add to it.
            for (int i = 0; i < inventory.getInvSize(); i++) {
                ItemStack invStack = inventory.getInvStack(i);
                if (invStack.isEmpty()) {
                    inventory.setInvStack(i, stackToStore);
                    break;
                } else if (ItemStack.areItemsEqualIgnoreDamage(invStack, stackToStore)) {
                    if (invStack.getCount() < invStack.getMaxCount()) {
                        // TODO If current slot amount + mined item amount > slot stack getMaxAmount(), it will still only be added to the max and then extras are deleted.
                        // the below does not work as i intended, however it still works the same as it used to.
                        int newCount = invStack.getCount() + stackToStore.getCount();
                        if (newCount > invStack.getMaxCount()) {
                            ItemStack remaining = new ItemStack(invStack.getItem());
                            remaining.setCount(newCount - invStack.getMaxCount());
                            storeItemInInv(inventory, stackToStore);
                            newCount = invStack.getMaxCount();
                        }

                        invStack.setCount(newCount);
                        break;
                    }
                }
            }
            return;
        }

        // No inv above, drop it instead
        ItemUtils.dropStack(stackToStore, this.world, this.pos, 1.0F);
    }

    private boolean shouldMineBlock(BlockPos.Mutable pos, ItemStack tool) {
        BlockState block = world.getBlockState(pos);
//        if (tool.getItem() instanceof ToolItem) {
//            if (!tool.isEffectiveOn(block)) {
        // Don't attempt to mine things too high ( wooden pickaxe wont mine diamonds )
        // TODO this works almost as intended, however dirt and other blocks that are mineable by anything are skipped.

//                return false;
//            }
//        }

        // TODO this does nothing, not sure how to support hand mineable blocks aswell though.
        if (tool.isEffectiveOn(block)) {
            return true;
        } else {
            // Do not mine a block that cannot also be mined without the correct tool.  Do not mine air. Do not mine any block with a hardness less than zero (bedrock).
            return block.getMaterial().canBreakByHand() && world.getFluidState(pos).isEmpty() && !world.isAir(pos) && block.getHardness(world, pos) > 0;
        }
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