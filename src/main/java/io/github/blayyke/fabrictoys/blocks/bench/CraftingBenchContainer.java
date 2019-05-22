package io.github.blayyke.fabrictoys.blocks.bench;

import io.github.blayyke.fabrictoys.accessor.ContainerSetter;
import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.packet.GuiSlotUpdateS2CPacket;
import net.minecraft.container.BlockContext;
import net.minecraft.container.CraftingContainer;
import net.minecraft.container.CraftingResultSlot;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Optional;

public class CraftingBenchContainer extends CraftingContainer<CraftingInventory> {
    private final PlayerEntity player;
    private final CraftingBenchBlockEntity bench;
    private final CraftingResultInventory resultInv;
    private final BlockContext context;

    public CraftingBenchContainer(int syncId, PlayerEntity player, BlockContext context) {
        super(null, syncId);
        this.player = player;
        this.context = context;

        BlockEntity blockEntity = context.run(World::getBlockEntity).orElseThrow(() -> new IllegalStateException("No BlockEntity @ pos"));

        if (!(blockEntity instanceof CraftingBenchBlockEntity)) {
            throw new IllegalStateException("BlockEntity @ " + blockEntity.getPos() + " not of right type! Is: " + blockEntity);
        }
        this.bench = (CraftingBenchBlockEntity) blockEntity;
        ((ContainerSetter) bench.inventory).setContainer(this);
        int xOffset = 30;
        int yOffset = 30;

        resultInv = new CraftingResultInventory();
        addSlot(new CraftingResultSlot(player, bench.inventory, resultInv, 0, xOffset + (18 * 5) + 4, yOffset + 5));

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                addSlot(new Slot(bench.inventory, x + y * 3, 30 + (x * 18), 17 + (y * 18)) {
                    @Override
                    public boolean canInsert(ItemStack itemStack_1) {
//                        System.out.println("Can insert");
                        return super.canInsert(itemStack_1);
                    }

                    @Override
                    public void onStackChanged(ItemStack itemStack_1, ItemStack itemStack_2) {
//                        System.out.println("STack CHanged");
                        super.onStackChanged(itemStack_1, itemStack_2);
                    }
                });
            }
        }

        int playerInvY;
        int playerInvX;
        for (playerInvY = 0; playerInvY < 3; ++playerInvY) {
            for (playerInvX = 0; playerInvX < 9; ++playerInvX) {
                this.addSlot(new Slot(player.inventory, playerInvX + playerInvY * 9 + 9, 8 + playerInvX * 18, 84 + playerInvY * 18));
            }
        }

        for (playerInvY = 0; playerInvY < 9; ++playerInvY) {
            this.addSlot(new Slot(player.inventory, playerInvY, 8 + playerInvY * 18, 142));
        }

        updateResult();
    }

    private void updateResult() {
        updateResult(this.bench.inventory);
    }

    private void updateResult(CraftingInventory inputInv) {
        World world = this.player.world;
        if (!world.isClient) {
            ServerPlayerEntity serverPlayerEntity_1 = (ServerPlayerEntity) this.player;
            ItemStack result = ItemStack.EMPTY;
            Optional<CraftingRecipe> optional_1 = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, inputInv, world);
            if (optional_1.isPresent()) {
                CraftingRecipe craftingRecipe_1 = optional_1.get();
                result = craftingRecipe_1.craft(inputInv);
            }

            this.resultInv.setInvStack(0, result);
            serverPlayerEntity_1.networkHandler.sendPacket(new GuiSlotUpdateS2CPacket(syncId, 0, result));
        }
    }

    @Override
    public void onContentChanged(Inventory inventory_1) {
        this.context.run((world_1, blockPos_1) -> {
            System.out.println("Content changed.");
            updateResult();
        });
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, FTBlocks.CRAFTING_BENCH);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity playerEntity_1, int int_1) {
        ItemStack itemStack_1 = ItemStack.EMPTY;
        Slot slot_1 = this.slotList.get(int_1);
        if (slot_1 != null && slot_1.hasStack()) {
            ItemStack itemStack_2 = slot_1.getStack();
            itemStack_1 = itemStack_2.copy();
            if (int_1 == 0) {
                this.context.run((world_1, blockPos_1) -> {
                    itemStack_2.getItem().onCrafted(itemStack_2, world_1, playerEntity_1);
                });
                if (!this.insertItem(itemStack_2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot_1.onStackChanged(itemStack_2, itemStack_1);
            } else if (int_1 >= 10 && int_1 < 37) {
                if (!this.insertItem(itemStack_2, 37, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (int_1 >= 37 && int_1 < 46) {
                if (!this.insertItem(itemStack_2, 10, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack_2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack_2.isEmpty()) {
                slot_1.setStack(ItemStack.EMPTY);
            } else {
                slot_1.markDirty();
            }

            if (itemStack_2.getAmount() == itemStack_1.getAmount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemStack_3 = slot_1.onTakeItem(playerEntity_1, itemStack_2);
            if (int_1 == 0) {
                playerEntity_1.dropItem(itemStack_3, false);
            }
        }

        return itemStack_1;
    }

    @Override
    public void populateRecipeFinder(RecipeFinder finder) {
        this.bench.inventory.provideRecipeInputs(finder);
    }

    @Override
    public void clearCraftingSlots() {
        this.resultInv.clear();
        //TODO do i clear main inv?
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack itemStack_1, Slot slot_1) {
        return slot_1.inventory != this.resultInv && super.canInsertIntoSlot(itemStack_1, slot_1);
    }

    @Override
    public boolean matches(Recipe<? super CraftingInventory> recipe) {
        return recipe.matches(this.bench.inventory, this.player.world);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 0;
    }

    @Override
    public int getCraftingWidth() {
        return this.bench.inventory.getWidth();
    }

    @Override
    public int getCraftingHeight() {
        return this.bench.inventory.getHeight();
    }

    @Override
    public int getCraftingSlotCount() {
        return 10;
    }
}