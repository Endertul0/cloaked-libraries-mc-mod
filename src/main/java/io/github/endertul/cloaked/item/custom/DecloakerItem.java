package io.github.endertul.cloaked.item.custom;

import io.github.endertul.cloaked.block.ModBlocks;
import io.github.endertul.cloaked.util.MiscUtils;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class DecloakerItem extends Item {
	public DecloakerItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());
		if (blockState.isOf(ModBlocks.CLOAK)) {
			MiscUtils.decloak(context, context.getBlockPos());
			return ActionResult.SUCCESS;
		} else {
			return ActionResult.FAIL;
		}
	}
}
