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

package io.github.blayyke.fabrictoys.blocks.chest;

import io.github.blayyke.fabrictoys.Constants;
import io.github.blayyke.fabrictoys.FabricToys;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.class_4730;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class FTChestBlock extends ChestBlock implements BlockEntityProvider {
    private Identifier path;
    private Identifier rightPath;
    private Identifier leftPath;
    private class_4730 normal;
    private class_4730 left;
    private class_4730 right;

    public FTChestBlock(Block.Settings settings, String path) {
        super(settings);
        FuelRegistry.INSTANCE.add(this, 300);
        this.path = Constants.of("entity/chest/" + path);
        this.rightPath = Constants.of("entity/chest/" + path + "_right");
        this.leftPath = Constants.of("entity/chest/" + path + "_left");
        FabricToys.LOGGER.debug("Init chest texture: " + path);
    }

    public Identifier getPath() {
        return this.path;
    }

    public Identifier getRightPath() {
        return this.rightPath;
    }

    public Identifier getLeftPath() {
        return this.leftPath;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView_1) {
        return new FTChestBlockEntity();
    }

    public void setNormal(class_4730 normal) {
        this.normal = normal;
    }

    public void setLeft(class_4730 left) {
        this.left = left;
    }

    public void setRight(class_4730 right) {
        this.right = right;
    }

    public class_4730 getNormal() {
        return this.normal;
    }

    public class_4730 getLeft() {
        return left;
    }

    public class_4730 getRight() {
        return right;
    }
}