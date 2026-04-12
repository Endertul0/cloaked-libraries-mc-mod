package io.github.endertul.cloaked.item.custom;

import io.github.endertul.cloaked.block.ModBlocks;
import io.github.endertul.cloaked.entity.custom.EncodedEntity;
import io.github.endertul.cloaked.util.MiscUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SlowCloakerItem extends Item {
	public SlowCloakerItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		if (! (world.getBlockState(pos).isOf(ModBlocks.CLOAK))) {
			return MiscUtils.cloak(context, false, context.getBlockPos());
		}
		return ActionResult.FAIL;
	}
	
	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (! (entity instanceof EncodedEntity)) {
			return MiscUtils.cloakEntity(entity).getActionResult();
		} else {
			return ActionResult.FAIL;
		}
	}
}