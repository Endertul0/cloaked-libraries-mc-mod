package io.github.endertul.cloaked.util;

import io.github.endertul.cloaked.Cloaked;
import io.github.endertul.cloaked.block.ModBlocks;
import io.github.endertul.cloaked.block.custom.CloakBlockEntity;
import io.github.endertul.cloaked.entity.custom.EncodedEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.ArrayList;
import java.util.List;

public class MiscUtils {
	public static List<BlockPos> getNeighbors(BlockPos pos) {
		List<BlockPos> positions = new ArrayList<BlockPos>();
		positions.add(pos.up().east().north());
		positions.add(pos.up().east().south());
		positions.add(pos.up().west().north());
		positions.add(pos.up().west().south());
		positions.add(pos.up().west());
		positions.add(pos.up().east());
		positions.add(pos.up().south());
		positions.add(pos.up().north());
		positions.add(pos.up());
		positions.add(pos.down().east().north());
		positions.add(pos.down().east().south());
		positions.add(pos.down().west().north());
		positions.add(pos.down().west().south());
		positions.add(pos.down().west());
		positions.add(pos.down().east());
		positions.add(pos.down().south());
		positions.add(pos.down().north());
		positions.add(pos.down());
		positions.add(pos.east().north());
		positions.add(pos.east().south());
		positions.add(pos.west().north());
		positions.add(pos.west().south());
		positions.add(pos.west());
		positions.add(pos.east());
		positions.add(pos.south());
		positions.add(pos.north());
		return positions;
	}
	
	public static ActionResult cloak(World world, boolean expand, BlockPos pos, int currentTally) {
		// BlockState before conversion
		BlockState blockState = world.getBlockState(pos);
		// BlockEntity before conversion
		BlockEntity blockEntity = world.getBlockEntity(pos);
		NbtCompound blockEntityNBT = null;
		if (blockState.hasBlockEntity() && blockEntity != null) {
			blockEntityNBT = blockEntity.createNbtWithIdentifyingData(world.getRegistryManager());
		}
		
		
		// Remove the inventory from the block so as not to
		// cause an explosion of items when it is replaced
		if (blockEntity instanceof Inventory && blockEntityNBT != null) {
			NbtCompound BENbt2 = blockEntityNBT.copy();
			BENbt2.put("Items", new NbtList());
			blockEntity.read(BENbt2, world.getRegistryManager());
		}
		
		
		world.setBlockState(pos, ModBlocks.CLOAK.getDefaultState());
		CloakBlockEntity cloakBlockEntity = (CloakBlockEntity) world.getBlockEntity(pos);
		
		// Set storedBlockState
		cloakBlockEntity.setStoredBlockState(blockState);
		
		// Set storedNbt
		if (blockEntityNBT != null) {
			cloakBlockEntity.setStoredNbt(blockEntityNBT);
		}
		
		// Handle expansion
		if (expand) {
			if (currentTally > 256) {
				return ActionResult.FAIL;
			}
			
			// Get all entities adjacent to the neighboring block
			List<Entity> entities = world.getOtherEntities(
				null,
				new Box(
					pos.up().east().north().toCenterPos(),
					pos.down().west().south().toCenterPos()
				)
			);
			for (Entity entity : entities) {
				if ((! (entity instanceof PlayerEntity)) && (! (entity instanceof EncodedEntity))) {
					EncodedEntity ent = CloakedCallbackSetup.convertToEncodedEntity(entity);
				}
			}
			
			// Get neighbors
			List<BlockPos> neighbors = getNeighbors(pos);
			for (BlockPos listPos : neighbors) {
				if (! world.getBlockState(listPos).isOf(Blocks.AIR)) {
					if (! world.getBlockState(listPos).isOf(ModBlocks.CLOAK)) {
						currentTally++;
						cloak(world, true, listPos, currentTally);
					}
				}
			}
		}
		
		return ActionResult.SUCCESS;
	}
	
	public static ActionResult cloak(ItemUsageContext context, boolean expand, BlockPos pos) {
		return cloak(context.getWorld(), expand, pos, 0);
	}
	
	public static ActionResult decloak(WorldAccess world, BlockPos pos, int currentTally) {
		if (currentTally > 256) {
			return ActionResult.FAIL;
		}
		BlockState cloakBlock = world.getBlockState(pos);
		if (! cloakBlock.isOf(ModBlocks.CLOAK)) {
			return ActionResult.FAIL;
		}
		
		CloakBlockEntity cloakBlockEntity = (CloakBlockEntity) world.getBlockEntity(pos);
		BlockState decodedState = cloakBlockEntity.getStoredBlockState();
		
		if (decodedState == null) {
			Cloaked.LOGGER.error("@MiscUtils:155 - Decoded state is null! @ BlockPos {}!", pos.toShortString());
			return ActionResult.SUCCESS;
		}
		
		world.setBlockState(pos, decodedState, Block.NOTIFY_ALL);
		if (cloakBlock.hasBlockEntity() && decodedState.hasBlockEntity()) {
			world.getBlockEntity(pos).read(cloakBlockEntity.getStoredNbt(), world.getRegistryManager());
			Cloaked.LOGGER.info("@MiscUtils:162 - Decoding with NBT! as " + decodedState.getBlock().getName().toString());
		}
		List<BlockPos> positions = MiscUtils.getNeighbors(pos);
		for (BlockPos listPos : positions) {
			BlockState listBlockState = world.getBlockState(listPos);
			Block listBlock = listBlockState.getBlock();
			if (listBlock == ModBlocks.CLOAK) {
				currentTally++;
				decloak(world, listPos, currentTally);
			}
		}
		
		return ActionResult.SUCCESS;
	}
	
	public static ActionResult decloak(ItemUsageContext context, BlockPos pos) {
		return decloak(context.getWorld(), pos, 0);
	}
}