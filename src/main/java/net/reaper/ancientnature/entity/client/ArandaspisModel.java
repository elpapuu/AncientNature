package net.reaper.ancientnature.entity.client;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.WaterAnimal;

public class ArandaspisModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart body;
	public ArandaspisModel(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(23, 3).addBox(-3.0F, -3.5F, -5.5F, 6.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -1.5F, -5.5F, 6.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 14).addBox(-3.0F, 1.5F, -5.5F, 6.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.5F, -1.5F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(26, 19).addBox(-1.0F, -2.5F, 0.0F, 2.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.5F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 17).addBox(0.0F, -3.5F, -1.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return body;
	}

}