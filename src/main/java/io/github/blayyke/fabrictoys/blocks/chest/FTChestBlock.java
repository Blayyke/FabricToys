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
        this.texture = Constants.of("textures/entity/chest/" + texture + ".png");
        this.rightTexture = Constants.of("textures/entity/chest/" + texture + "_right.png");
        this.leftTexture = Constants.of("textures/entity/chest/" + texture + "_left.png");
        System.out.println("Init chest : " + texture);
    }

    public Identifier asTexture() {
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