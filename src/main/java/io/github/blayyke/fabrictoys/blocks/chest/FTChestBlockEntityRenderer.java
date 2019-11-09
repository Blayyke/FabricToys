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

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class FTChestBlockEntityRenderer extends ChestBlockEntityRenderer<FTChestBlockEntity> {
    public static final Identifier TRAPPED_TEX = new Identifier("entity/chest/trapped");
    public static final Identifier field_21473 = new Identifier("entity/chest/trapped_left");
    public static final Identifier field_21474 = new Identifier("entity/chest/trapped_right");
    public static final Identifier CHRISTMAS_TEX = new Identifier("entity/chest/christmas");
    public static final Identifier field_21475 = new Identifier("entity/chest/christmas_left");
    public static final Identifier field_21476 = new Identifier("entity/chest/christmas_right");
    public static final Identifier NORMAL_TEX = new Identifier("entity/chest/normal");
    public static final Identifier field_21477 = new Identifier("entity/chest/normal_left");
    public static final Identifier field_21478 = new Identifier("entity/chest/normal_right");
    public static final Identifier ENDER_TEX = new Identifier("entity/chest/ender");

    public FTChestBlockEntityRenderer(BlockEntityRenderDispatcher instance) {
        super(instance);
    }

    private Identifier method_23690(ChestType chestType_1, Identifier identifier_1, Identifier identifier_2, Identifier identifier_3) {
        switch (chestType_1) {
            case LEFT:
                return identifier_3;
            case RIGHT:
                return identifier_2;
            case SINGLE:
            default:
                return identifier_1;
        }
    }

    private void method_22749(MatrixStack matrixStack_1, VertexConsumer vertexConsumer_1, ModelPart modelPart_1, ModelPart modelPart_2, ModelPart modelPart_3, float float_1, int int_1, int int_2, Sprite sprite_1) {
        modelPart_1.pitch = -(float_1 * 1.5707964F);
        modelPart_2.pitch = modelPart_1.pitch;
        modelPart_1.render(matrixStack_1, vertexConsumer_1, int_1, int_2, sprite_1);
        modelPart_2.render(matrixStack_1, vertexConsumer_1, int_1, int_2, sprite_1);
        modelPart_3.render(matrixStack_1, vertexConsumer_1, int_1, int_2, sprite_1);
    }

    @Override
    public void render(FTChestBlockEntity blockEntity_1, float float_1, MatrixStack matrixStack_1, VertexConsumerProvider vertexConsumerProvider_1, int int_1, int int_2) {
        BlockState blockState_1 = blockEntity_1.hasWorld() ? blockEntity_1.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType chestType_1 = blockState_1.contains(ChestBlock.CHEST_TYPE) ? blockState_1.get(ChestBlock.CHEST_TYPE) : ChestType.SINGLE;
        boolean boolean_1 = chestType_1 != ChestType.SINGLE;
        Identifier identifier_4 = this.method_23690(chestType_1, NORMAL_TEX, field_21477, field_21478);

        matrixStack_1.push();
        float float_2 = blockState_1.get(ChestBlock.FACING).asRotation();
        matrixStack_1.translate(0.5D, 0.5D, 0.5D);
        matrixStack_1.multiply(Vector3f.POSITIVE_Y.getRotationQuaternion(-float_2));
        matrixStack_1.translate(-0.5D, -0.5D, -0.5D);
        float float_3 = ((ChestAnimationProgress) blockEntity_1).getAnimationProgress(float_1);
        float_3 = 1.0F - float_3;
        float_3 = 1.0F - float_3 * float_3 * float_3;
        Sprite sprite_1 = this.getSprite(identifier_4);
        VertexConsumer vertexConsumer_1;
        if (boolean_1) {
            vertexConsumer_1 = vertexConsumerProvider_1.getBuffer(RenderLayer.getEntityCutout(SpriteAtlasTexture.BLOCK_ATLAS_TEX));
            if (chestType_1 == ChestType.LEFT) {
//                this.method_22749(matrixStack_1, vertexConsumer_1, this.field_21479, this.field_21481, this.field_21480, float_3, int_1, int_2, sprite_1);
            } else {
//                this.method_22749(matrixStack_1, vertexConsumer_1, this.field_20820, this.field_20822, this.field_20821, float_3, int_1, int_2, sprite_1);
            }
        } else {
            vertexConsumer_1 = vertexConsumerProvider_1.getBuffer(RenderLayer.getEntitySolid(SpriteAtlasTexture.BLOCK_ATLAS_TEX));
//            this.method_22749(matrixStack_1, vertexConsumer_1, this.field_20817, this.field_20819, this.field_20818, float_3, int_1, int_2, sprite_1);
        }

        matrixStack_1.pop();
    }
}