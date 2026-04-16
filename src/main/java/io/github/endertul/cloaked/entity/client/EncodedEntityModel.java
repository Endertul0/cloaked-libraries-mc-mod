package io.github.endertul.cloaked.entity.client;

import io.github.endertul.cloaked.entity.custom.EncodedEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class EncodedEntityModel<T extends EncodedEntity> extends SinglePartEntityModel<T> {
	private final ModelPart encoded_entity;
	
	public EncodedEntityModel(ModelPart root) {
		this.encoded_entity = root.getChild("bb_main");
	}
	
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(- 8.0F, - 16.0F, - 8.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	
	@Override
	public void setAngles(EncodedEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		encoded_entity.render(matrices, vertices, light, overlay, color);
	}
	
	@Override
	public ModelPart getPart() {
		return encoded_entity;
	}
}