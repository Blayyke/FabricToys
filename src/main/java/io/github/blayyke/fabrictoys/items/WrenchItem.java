package io.github.blayyke.fabrictoys.items;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;

public class WrenchItem extends Item {
    public WrenchItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        BlockState block = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (block.getProperties().contains(Properties.HORIZONTAL_FACING)) {
            BlockState state = block.with(Properties.HORIZONTAL_FACING, block.get(Properties.HORIZONTAL_FACING).rotateYClockwise());
            ctx.getWorld().setBlockState(ctx.getBlockPos(), state);
            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(ctx);
    }
}