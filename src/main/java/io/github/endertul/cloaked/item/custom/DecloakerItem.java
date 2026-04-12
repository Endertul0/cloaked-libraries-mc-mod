package io.github.endertul.cloaked.item.custom;

import io.github.endertul.cloaked.block.ModBlocks;
import io.github.endertul.cloaked.entity.custom.EncodedEntity;
import io.github.endertul.cloaked.util.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class DecloakerItem extends Item {
	public DecloakerItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());
		Block block = blockState.getBlock();
		if (blockState.isOf(ModBlocks.CLOAK)) {
			MiscUtils.decloak(context.getWorld(), context.getBlockPos(), blockState);
			return ActionResult.SUCCESS;
		} else {
			return ActionResult.FAIL;
		}
	}
	
	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (entity instanceof EncodedEntity) {
			return MiscUtils.decloakEntity((EncodedEntity) entity);
		} else {
			return ActionResult.FAIL;
		}
	}
}
