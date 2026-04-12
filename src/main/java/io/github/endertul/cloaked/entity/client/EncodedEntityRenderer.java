package io.github.endertul.cloaked.entity.client;

import io.github.endertul.cloaked.Cloaked;
import io.github.endertul.cloaked.entity.custom.EncodedEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class EncodedEntityRenderer extends MobEntityRenderer<EncodedEntity, EncodedEntityModel<EncodedEntity>> {
	private static final Identifier TEXTURE = new Identifier(Cloaked.MOD_ID, "textures/entity/encoded_entity.png");
	
	public EncodedEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new EncodedEntityModel<>(context.getPart(ModModelLayers.ENCODED_ENTITY)), 0.6f);
	}
	
	@Override
	public Identifier getTexture(EncodedEntity entity) {
		return TEXTURE;
	}
	
	@Override
	public void render(EncodedEntity mobEntity, float f, float g, MatrixStack matrixStack,
					   VertexConsumerProvider vertexConsumerProvider, int i) {
		if (mobEntity.isBaby()) {
			matrixStack.scale(0.5f, 0.5f, 0.5f);
		} else {
			matrixStack.scale(1f, 1f, 1f);
		}
		
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}
}
