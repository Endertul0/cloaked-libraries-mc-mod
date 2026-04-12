package io.github.endertul.cloaked.block;

import com.mojang.datafixers.types.Type;
import io.github.endertul.cloaked.Cloaked;
import io.github.endertul.cloaked.block.custom.CloakBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Util;

public class ModBlockEntities {
	private static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType.Builder<T> builder) {
		Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, builder.build(type));
	}
	
	public static void registerModBlockEntities() {
		Cloaked.LOGGER.info("Registering mod block entities for " + Cloaked.MOD_ID);
	}
	
	public static final BlockEntityType<?> CLOAK_BLOCK_ENTITY = create("cloak_block_entity", BlockEntityType.Builder.create(CloakBlockEntity::new, ModBlocks.CLOAK));
	
	
}
