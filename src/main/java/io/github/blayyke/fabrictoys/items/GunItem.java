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

package io.github.blayyke.fabrictoys.items;

import io.github.blayyke.fabrictoys.FabricToys;
import io.github.blayyke.fabrictoys.client.FabricToysClient;
import io.github.blayyke.fabrictoys.entity.BulletEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class GunItem extends Item {
    private final int maxBullets;
    private final int maxReserveBullets;
    private final int gunReloadTime; // Ticks
    private final int maxBulletDamage;

    // NBT tags
    private static final String RELOADING_TAG = "Reloading";
    private static final String RELOAD_TIME_TAG = "ReloadTime";
    private static final String AMMO_TAG = "Bullets";
    private static final String RESERVE_AMMO_TAG = "ReserveBullets";

    public GunItem(Settings item$Settings_1, int maxBullets, int maxReserveBullets, int gunReloadTime, int maxBulletDamage) {
        super(item$Settings_1);
        this.maxBullets = maxBullets;
        this.maxReserveBullets = maxReserveBullets;
        this.gunReloadTime = gunReloadTime;
        this.maxBulletDamage = maxBulletDamage;
    }

    public int getMaxBullets() {
        return maxBullets;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        CompoundTag tag = stack.getOrCreateTag();

        if (isReloading(tag)) {
            return super.use(world, player, hand);
        }

        if (!tag.contains(RESERVE_AMMO_TAG)) {
            tag.putInt(RESERVE_AMMO_TAG, maxReserveBullets);
        }
        if (!tag.contains(AMMO_TAG)) {
            tag.putInt(AMMO_TAG, maxBullets);
        }
        if (!tag.contains(RELOADING_TAG)) {
            tag.putBoolean(RELOADING_TAG, false);
        }
        if (!tag.contains(RELOAD_TIME_TAG)) {
            tag.putInt(RELOAD_TIME_TAG, 0);
        }

        FabricToys.LOGGER.info("Shot bullet!");
        int bullets = tag.getInt(AMMO_TAG);
        if (bullets > 0) {
            tag.putInt(AMMO_TAG, bullets - 1);
            shootBullet(stack, player.getBlockPos().offset(Direction.UP), world, player);
        } else {
            reloadGun(stack, player);
        }

        return super.use(world, player, hand);
    }

    private boolean isReloading(CompoundTag tag) {
        return tag.getBoolean(RELOADING_TAG);
    }

    protected void reloadGun(ItemStack stack, PlayerEntity player) {
        CompoundTag tag = stack.getTag();
        FabricToys.LOGGER.info("Reloading gun!");
        if (tag.getInt(AMMO_TAG) >= maxBullets) {
            FabricToys.LOGGER.info("Gun already full.");
            return;
        }
        if (tag.getInt(RESERVE_AMMO_TAG) <= 0) {
            FabricToys.LOGGER.info("No ammo in reserve!");
            player.playSound(getNoAmmoReloadSound(), SoundCategory.PLAYERS, 1.0F, 1.0F);
            return;
        }

        tag.putBoolean(RELOADING_TAG, true);
        tag.putInt(RELOAD_TIME_TAG, 0);
        int reserveAmmo = tag.getInt(RESERVE_AMMO_TAG);
        int ammoToAdd = Math.min(this.maxBullets, reserveAmmo);

        tag.putInt(AMMO_TAG, ammoToAdd);
        tag.putInt(RESERVE_AMMO_TAG, reserveAmmo - ammoToAdd);
        System.out.println("Reserve Ammo: " + (reserveAmmo - ammoToAdd));
        stack.setTag(tag);
        player.playSound(getReloadSound(), SoundCategory.PLAYERS, 1.0F, 1.0F);
        FabricToys.LOGGER.info("Gun reloaded!");
    }

    protected void shootBullet(ItemStack stack, BlockPos originPos, World world, PlayerEntity player) {
        if (world.isClient) {
            return;
        }

        BulletEntity bullet = new BulletEntity(world);
        Direction direction = Direction.getEntityFacingOrder(player)[0];
        originPos = originPos.offset(direction);
        bullet.setPosition(originPos.getX(), originPos.getY(), originPos.getZ());
        bullet.setVelocity(player, player.pitch, player.yaw, 0.0F, 1.5F, 1.0F);

        world.playSound(null, originPos, getShootSound(), SoundCategory.PLAYERS, 1.0F, 1.0F);
        world.spawnEntity(bullet);
        System.out.println("Bullet entity spawned.");
    }

    private SoundEvent getShootSound() {
        return SoundEvents.ITEM_CROSSBOW_SHOOT;
    }

    private SoundEvent getNoAmmoReloadSound() {
        return SoundEvents.ITEM_CROSSBOW_LOADING_START;
    }

    private SoundEvent getReloadSound() {
        return SoundEvents.BLOCK_LAVA_POP;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world_1, Entity entity_1, int int_1, boolean boolean_1) {
        if (!(entity_1 instanceof PlayerEntity)) {
            return;
        }

        CompoundTag tag = stack.getOrCreateTag();
        if (world_1.isClient) {
            if (FabricToysClient.reloadKeybinding.wasPressed() && !isReloading(tag)) {
                reloadGun(stack, (PlayerEntity) entity_1);
            }
        }

        if (tag.getBoolean(RELOADING_TAG)) {
            int currentReloadTime = tag.getInt(RELOAD_TIME_TAG) + 1;
            if (currentReloadTime >= this.gunReloadTime) {
                tag.putBoolean(RELOADING_TAG, false);
                tag.putInt(RELOAD_TIME_TAG, 0);
            } else {
                tag.putInt(RELOAD_TIME_TAG, currentReloadTime);
            }
        }

        stack.setTag(tag);
        super.inventoryTick(stack, world_1, entity_1, int_1, boolean_1);
    }
}