package io.github.endertul.cloaked;

import io.github.endertul.cloaked.block.ModBlockEntities;
import io.github.endertul.cloaked.block.ModBlocks;
import io.github.endertul.cloaked.entity.ModEntities;
import io.github.endertul.cloaked.entity.custom.EncodedEntity;
import io.github.endertul.cloaked.item.ModItemGroups;
import io.github.endertul.cloaked.item.ModItems;
import io.github.endertul.cloaked.util.CloakedCallbackSetup;
import io.github.endertul.cloaked.util.CloakingDispenserBehaviors;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cloaked implements ModInitializer {
	public static final String MOD_ID = "cloaked";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	@Override
	public void onInitialize() {
		LOGGER.info("Initialized {}!", MOD_ID);
		
		ModItemGroups.registerItemGroups();
		
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerModBlockEntities();
		FabricDefaultAttributeRegistry.register(ModEntities.ENCODED_ENTITY, EncodedEntity.createEncodedEntityAttributes());
		
		CloakedCallbackSetup.setupCallbacks();
		
		CloakingDispenserBehaviors.addBehaviors();
	}
}