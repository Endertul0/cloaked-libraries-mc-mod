package io.github.endertul.cloaked.util;

import io.github.endertul.cloaked.Cloaked;
import io.github.endertul.cloaked.entity.ModEntities;
import io.github.endertul.cloaked.entity.custom.EncodedEntity;
import io.github.endertul.cloaked.item.ModItems;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CloakedCallbackSetup {
	public static void setupCallbacks() {
		UseEntityCallback.EVENT.register((PlayerEntity playerEntity, World world, Hand hand, Entity entity, EntityHitResult entityHitResult) -> {
			Cloaked.LOGGER.info("@CloakedCallbackSetup:21 - An entity was right clicked!");
			
			ItemStack stack = playerEntity.getStackInHand(hand);
			
			if (stack.isOf(ModItems.SLOW_CLOAKER)) {
				if (! world.isClient()) {
					convertToEncodedEntity(entity);
					return ActionResult.SUCCESS;
				}
				if (world.isClient()) {
					return ActionResult.SUCCESS;
				}
			}
			
			return ActionResult.PASS;
		});
	}
	
	@Nullable
	public static EncodedEntity convertToEncodedEntity(Entity source) {
		if (source.isRemoved()) {
			Cloaked.LOGGER.error("@CloakedCallbackSetup:42 - Source {}Entity was removed!", source.getType().getName().getString());
			return null;
		} else {
			EncodedEntity encodedEntity = ModEntities.ENCODED_ENTITY.create(source.getWorld());
			if (encodedEntity == null) {
				Cloaked.LOGGER.error("@CloakedCallbackSetup:47 - Could not create EncodedEntity!");
				return null;
			} else {
				NbtCompound sourceNbt = source.writeNbt(new NbtCompound());
				encodedEntity.setStoredNbt(sourceNbt);
				encodedEntity.setStoredEntity(source.getType());
				Cloaked.LOGGER.info(
					"@CloakedCallbackSetup:52 - Successfully created EncodedEntity from {}Entity! (Stored as {}Entity) (With NBT {})",
					source.getType().getName().getString(),
					encodedEntity.getType().getName().getString(),
					sourceNbt
				);
				
				encodedEntity.copyPositionAndRotation(source);
				source.getWorld().spawnEntity(encodedEntity);
				if (source.hasVehicle()) {
					Entity entity = source.getVehicle();
					source.stopRiding();
					encodedEntity.startRiding(entity, true);
				}
				
				source.discard();
				return encodedEntity;
			}
		}
	}
}
