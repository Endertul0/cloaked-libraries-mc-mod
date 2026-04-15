package io.github.endertul.cloaked.util;

import io.github.endertul.cloaked.item.ModItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CloakingDispenserBehaviors {
	public static void addBehaviors() {
		DispenserBlock.registerBehavior(ModItems.QUICK_CLOAKER, new ItemDispenserBehavior() {
			@Override
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				World world = pointer.world();
				BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
				MiscUtils.cloak(world, true, blockPos, 0);
				return stack;
			}
		});
		DispenserBlock.registerBehavior(ModItems.SLOW_CLOAKER, new ItemDispenserBehavior() {
			@Override
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				World world = pointer.world();
				BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
				MiscUtils.cloak(world, false, blockPos, 0);
				return stack;
			}
		});
		DispenserBlock.registerBehavior(ModItems.DECLOAKER, new ItemDispenserBehavior() {
			@Override
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				World world = pointer.world();
				BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
				MiscUtils.decloak(world, blockPos, 0);
				return stack;
			}
		});
	}
}
