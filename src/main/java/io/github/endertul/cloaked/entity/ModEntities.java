package io.github.endertul.cloaked.entity;

import io.github.endertul.cloaked.Cloaked;
import io.github.endertul.cloaked.entity.custom.EncodedEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
	public static final EntityType<EncodedEntity> ENCODED_ENTITY = Registry.register(Registries.ENTITY_TYPE,
		new Identifier(Cloaked.MOD_ID, "encoded_entity"),
		FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EncodedEntity::new)
			.dimensions(EntityDimensions.fixed(1f, 1f)).build());
}
