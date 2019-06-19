package io.github.blayyke.fabrictoys.mixins;

import io.github.blayyke.fabrictoys.blocks.FTBlocks;
import net.minecraft.item.EggItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EggItem.class)
public abstract class EggItemMixin extends Item {
    public EggItemMixin(Settings item$Settings_1) {
        super(item$Settings_1);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        BlockPos blockPos = ctx.getBlockPos().up();
        if (ctx.getWorld().isAir(blockPos)) {
            ctx.getWorld().setBlockState(blockPos, FTBlocks.EGG.getDefaultState());
            return ActionResult.SUCCESS;
        }
        return super.useOnBlock(ctx);
    }
}