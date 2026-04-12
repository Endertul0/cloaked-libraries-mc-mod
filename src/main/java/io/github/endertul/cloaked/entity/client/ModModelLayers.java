package io.github.endertul.cloaked.entity.client;

import io.github.endertul.cloaked.Cloaked;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
	public static final EntityModelLayer ENCODED_ENTITY =
		new EntityModelLayer(new Identifier(Cloaked.MOD_ID, "encoded_entity"), "main");
}
