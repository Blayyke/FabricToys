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

package io.github.blayyke.fabrictoys.entity;

import net.minecraft.block.BlockState;
import net.minecraft.client.network.packet.EntitySpawnS2CPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BulletEntity extends Entity {
    public BulletEntity(EntityType<Entity> type, World world) {
        super(type, world);
    }

    public BulletEntity(World world) {
        this(FTEntities.BULLET, world);
        System.out.println("New bullteentity!");
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromTag(CompoundTag var1) {

    }

    @Override
    protected void writeCustomDataToTag(CompoundTag var1) {

    }

    @Override
    protected void onBlockCollision(BlockState blockState_1) {
        System.out.println("Hit a block, removing.");
        this.destroy();
    }

    @Override
    public void onPlayerCollision(PlayerEntity playerEntity_1) {
        System.out.println("Hit a player, removing.");
        if (!playerEntity_1.abilities.creativeMode) {
            playerEntity_1.damage(DamageSource.MAGIC, 5.0F);
        }

        remove();
    }

    public void setVelocity(Entity entity_1, float float_1, float float_2, float float_3, float float_4, float float_5) {
        float float_6 = -MathHelper.sin(float_2 * 0.017453292F) * MathHelper.cos(float_1 * 0.017453292F);
        float float_7 = -MathHelper.sin((float_1 + float_3) * 0.017453292F);
        float float_8 = MathHelper.cos(float_2 * 0.017453292F) * MathHelper.cos(float_1 * 0.017453292F);
        this.setVelocity((double) float_6, (double) float_7, (double) float_8, float_4, float_5);
        Vec3d vec3d_1 = entity_1.getVelocity();
        this.setVelocity(this.getVelocity().add(vec3d_1.x, entity_1.onGround ? 0.0D : vec3d_1.y, vec3d_1.z));
    }

    public void setVelocity(double double_1, double double_2, double double_3, float float_1, float float_2) {
        Vec3d vec3d_1 = (new Vec3d(double_1, double_2, double_3)).normalize().add(this.random.nextGaussian() * 0.007499999832361937D * (double) float_2, this.random.nextGaussian() * 0.007499999832361937D * (double) float_2, this.random.nextGaussian() * 0.007499999832361937D * (double) float_2).multiply((double) float_1);
        this.setVelocity(vec3d_1);
        float float_3 = MathHelper.sqrt(squaredHorizontalLength(vec3d_1));
        this.yaw = (float) (MathHelper.atan2(vec3d_1.x, vec3d_1.z) * 57.2957763671875D);
        this.pitch = (float) (MathHelper.atan2(vec3d_1.y, (double) float_3) * 57.2957763671875D);
        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}