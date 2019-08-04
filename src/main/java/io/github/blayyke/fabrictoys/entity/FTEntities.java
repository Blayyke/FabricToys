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

import io.github.blayyke.fabrictoys.Constants;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

public class FTEntities {
    public static final EntityType<Entity> BULLET =
            Registry.register(
                    Registry.ENTITY_TYPE,
                    Constants.of(Constants.Entities.BULLET),
                    FabricEntityTypeBuilder.create(EntityCategory.MISC, (EntityType.EntityFactory<Entity>) BulletEntity::new).size(EntityDimensions.fixed(1, 1)).build()
            );

    public static void init() {
    }
}