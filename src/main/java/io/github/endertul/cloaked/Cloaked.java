package io.github.endertul.cloaked;

import io.github.endertul.cloaked.block.ModBlockEntities;
import io.github.endertul.cloaked.block.ModBlocks;
import io.github.endertul.cloaked.entity.ModEntities;
import io.github.endertul.cloaked.entity.custom.EncodedEntity;
import io.github.endertul.cloaked.item.ModItemGroups;
import io.github.endertul.cloaked.item.ModItems;
import io.github.endertul.cloaked.item.custom.SlowCloakerItem;
import io.github.endertul.cloaked.util.MiscUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cloaked implements ModInitializer {
	public static final String MOD_ID = "cloaked";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	@Override
	public void onInitialize() {
		LOGGER.info("Initialized " + MOD_ID);
		
		ModItemGroups.registerItemGroups();
		
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerModBlockEntities();
		FabricDefaultAttributeRegistry.register(ModEntities.ENCODED_ENTITY, EncodedEntity.createEncodedEntityAttributes());
		
		UseEntityCallback.EVENT.register((PlayerEntity playerEntity, World world, Hand hand, Entity entity, EntityHitResult entityHitResult) -> {
			ItemStack stack = playerEntity.getStackInHand(hand);
			if (stack.isOf(ModItems.SLOW_CLOAKER)) {
				MiscUtils.cloakEntity(entity);
				return ActionResult.SUCCESS;
			}
			
			return ActionResult.PASS;
		});
	}
}