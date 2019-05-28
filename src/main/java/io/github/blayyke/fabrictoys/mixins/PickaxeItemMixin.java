package io.github.blayyke.fabrictoys.mixins;

import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PickaxeItem.class)
public class PickaxeItemMixin extends ToolItem {
    public PickaxeItemMixin(ToolMaterial toolMaterial_1, Settings item$Settings_1) {
        super(toolMaterial_1, item$Settings_1);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
        return ActionResult.PASS;
    }
}