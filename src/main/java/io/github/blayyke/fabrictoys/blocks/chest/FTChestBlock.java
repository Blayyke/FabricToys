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
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class FTChestBlock extends ChestBlock implements BlockEntityProvider {
    private Identifier texture;
    private Identifier rightTexture;
    private Identifier leftTexture;

    public FTChestBlock(Block.Settings settings, String texture) {
        super(settings);
        FuelRegistry.INSTANCE.add(this, 300);
        this.texture = Constants.of("entity/chest/" + texture);
        this.rightTexture = Constants.of("entity/chest/" + texture + "_right");
        this.leftTexture = Constants.of("entity/chest/" + texture + "_left");
        FabricToys.LOGGER.debug("Init chest texture: " + texture);
    }

    public Identifier getTexture() {
        return this.texture;
    }

    public Identifier getRightTexture() {
        return this.rightTexture;
    }

    public Identifier getLeftTexture() {
        return this.leftTexture;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView_1) {
        return new FTChestBlockEntity();
    }
}