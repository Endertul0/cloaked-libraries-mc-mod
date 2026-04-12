package io.github.endertul.cloaked;

import io.github.endertul.cloaked.entity.ModEntities;
import io.github.endertul.cloaked.entity.client.EncodedEntityModel;
import io.github.endertul.cloaked.entity.client.EncodedEntityRenderer;
import io.github.endertul.cloaked.entity.client.ModModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class CloakedClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.ENCODED_ENTITY, EncodedEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ENCODED_ENTITY, EncodedEntityModel::getTexturedModelData);
	}
}
