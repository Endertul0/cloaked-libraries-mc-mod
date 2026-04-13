package io.github.endertul.cloaked.util;

import io.github.endertul.cloaked.Cloaked;
import io.github.endertul.cloaked.block.ModBlocks;
import io.github.endertul.cloaked.block.custom.CloakBlockEntity;
import io.github.endertul.cloaked.entity.ModEntities;
import io.github.endertul.cloaked.entity.custom.EncodedEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
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
	
	private static ActionResult cloak(ItemUsageContext context, boolean expand, BlockPos pos, int currentTally) {
		World world = context.getWorld();
		// BlockState before conversion
		BlockState blockState = world.getBlockState(pos);
		// BlockEntity before conversion
		BlockEntity blockEntity = world.getBlockEntity(pos);
		NbtCompound blockEntityNBT = null;
		if (blockState.hasBlockEntity() && blockEntity != null) {
			blockEntityNBT = blockEntity.createNbtWithIdentifyingData();
		}
		
		
		// Remove the inventory from the block so as not to
		// cause an explosion of items when it is replaced
		if (blockEntity instanceof Inventory && blockEntityNBT != null) {
			NbtCompound BENbt2 = blockEntityNBT.copy();
			BENbt2.put("Items", new NbtList());
			blockEntity.readNbt(BENbt2);
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
			List<Entity> entities = world.getOtherEntities(null, new Box(pos.up().east().north(), pos.down().west().south()));
			for (Entity entity : entities) {
				if ((! (entity instanceof PlayerEntity)) && (! (entity instanceof EncodedEntity))) {
					EncodedEntity ent = cloakEntity(entity).getEntity();
				}
			}
			
			// Get neighbors
			List<BlockPos> neighbors = getNeighbors(pos);
			for (BlockPos listPos : neighbors) {
				if (! world.getBlockState(listPos).isOf(Blocks.AIR)) {
					if (! world.getBlockState(listPos).isOf(ModBlocks.CLOAK)) {
						currentTally++;
						cloak(context, true, listPos, currentTally);
					}
				}
			}
		}
		
		return ActionResult.SUCCESS;
	}
	
	public static ActionResult cloak(ItemUsageContext context, boolean expand, BlockPos pos) {
		return cloak(context, expand, pos, 0);
	}
	
	public static CloakEntityResult cloakEntity(Entity entity) {
		NbtCompound entityNBT = entity.writeNbt(new NbtCompound()).copy();
		World world = entity.getWorld();
		
		EncodedEntity encodedEntity = new EncodedEntity(ModEntities.ENCODED_ENTITY, world);
		world.spawnEntity(encodedEntity);
		encodedEntity.setPos(entity.getX(), entity.getY(), entity.getZ());
		encodedEntity.setStoredNbt(entityNBT.toString());
		encodedEntity.setStoredEntity(entity.getType());
		
		entity.kill();
		entity.discard();
		return new CloakEntityResult(ActionResult.SUCCESS, encodedEntity);
	}
	
	public static ActionResult decloak(WorldAccess world, BlockPos pos, BlockState cloakBlock, int currentTally) {
		if (currentTally > 256) {
			return ActionResult.FAIL;
		}
		CloakBlockEntity cloakBlockEntity = (CloakBlockEntity) world.getBlockEntity(pos);
		BlockState decodedState = cloakBlockEntity.getStoredBlockState();
		
		if (decodedState == null) {
			Cloaked.LOGGER.error("Decoded state is null! @ BlockPos {}!", pos.toShortString());
			return ActionResult.SUCCESS;
		}
		
		world.setBlockState(pos, decodedState, Block.NOTIFY_ALL);
		if (cloakBlock.hasBlockEntity() && decodedState.hasBlockEntity()) {
			world.getBlockEntity(pos).readNbt(cloakBlockEntity.getStoredNbt());
			Cloaked.LOGGER.info("decoding with NBT! as " + decodedState.getBlock().getName().toString());
		}
		List<BlockPos> positions = MiscUtils.getNeighbors(pos);
		for (BlockPos listPos : positions) {
			BlockState listBlockState = world.getBlockState(listPos);
			Block listBlock = listBlockState.getBlock();
			if (listBlock == ModBlocks.CLOAK) {
				currentTally++;
				decloak(world, listPos, listBlockState, currentTally);
			}
		}
		
		return ActionResult.SUCCESS;
	}
	
	public static ActionResult decloak(WorldAccess world, BlockPos pos, BlockState state) {
		return decloak(world, pos, state, 0);
	}
	
	public static ActionResult decloakEntity(EncodedEntity encodedEntity) {
		World world = encodedEntity.getWorld();
		EntityType<?> eType = encodedEntity.getStoredEntity();
		Entity entity = eType.create(world);
		
		entity.readNbt(encodedEntity.getStoredNbt());
		entity.setPos(encodedEntity.getX(), encodedEntity.getY(), encodedEntity.getZ());
		
		encodedEntity.kill();
		encodedEntity.discard();
		return ActionResult.SUCCESS;
	}
	
	public static final class CloakEntityResult {
		private final ActionResult actionResult;
		private final EncodedEntity entity;
		
		public CloakEntityResult(ActionResult actionResult, EncodedEntity entity) {
			this.actionResult = actionResult;
			this.entity = entity;
		}
		
		public ActionResult getActionResult() {
			return actionResult;
		}
		
		public EncodedEntity getEntity() {
			return entity;
		}
	}
}