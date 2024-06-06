package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.entity.water.Anomalocris;

@OnlyIn(Dist.CLIENT)
public class AnomalocrisModel extends HierarchicalModel<Anomalocris> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation AnomalocarisLayer = new ModelLayerLocation(new ResourceLocation(
			AncientNature.MOD_ID, "anomalocaris"), "main");

	private final ModelPart body;

	public AnomalocrisModel(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.8889F, -1.6111F, -10.3333F, 6.0F, 3.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(-1.8889F, -0.6111F, 7.6667F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(30, 6).addBox(-5.8889F, -2.6111F, -10.3333F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(30, 0).addBox(3.1111F, -2.6111F, -10.3333F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1111F, 23.6111F, 0.3333F));

		PartDefinition mouth = body.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(2.1111F, -0.6111F, -10.3333F));

		PartDefinition mouth2 = body.addOrReplaceChild("mouth2", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.8889F, -0.6111F, -10.3333F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 21).addBox(-6.0F, -0.5F, 0.6667F, 12.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(26, 35).addBox(-6.0F, -0.5F, -1.3333F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(36, 36).addBox(2.0F, -0.5F, -1.3333F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1111F, -0.1111F, 11.0F));

		PartDefinition finn = body.addOrReplaceChild("finn", CubeListBuilder.create().texOffs(39, 6).addBox(0.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.1111F, -0.1111F, -5.3333F));

		PartDefinition finn2 = body.addOrReplaceChild("finn2", CubeListBuilder.create().texOffs(16, 34).addBox(0.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.1111F, -0.1111F, -3.3333F));

		PartDefinition finn3 = body.addOrReplaceChild("finn3", CubeListBuilder.create().texOffs(30, 24).addBox(0.0F, -0.5F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.1111F, -0.1111F, -1.3333F));

		PartDefinition finn4 = body.addOrReplaceChild("finn4", CubeListBuilder.create().texOffs(26, 28).addBox(0.0F, -0.5F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.1111F, -0.1111F, 0.6667F));

		PartDefinition finn5 = body.addOrReplaceChild("finn5", CubeListBuilder.create().texOffs(30, 21).addBox(0.0F, -0.5F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.1111F, -0.1111F, 2.6667F));

		PartDefinition finn6 = body.addOrReplaceChild("finn6", CubeListBuilder.create().texOffs(32, 32).addBox(0.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.1111F, -0.1111F, 4.6667F));

		PartDefinition finn7 = body.addOrReplaceChild("finn7", CubeListBuilder.create().texOffs(39, 0).addBox(0.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.1111F, -0.1111F, 6.6667F));

		PartDefinition finn8 = body.addOrReplaceChild("finn8", CubeListBuilder.create().texOffs(24, 38).addBox(-3.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.8889F, -0.1111F, -5.3333F));

		PartDefinition finn9 = body.addOrReplaceChild("finn9", CubeListBuilder.create().texOffs(22, 31).addBox(-4.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.8889F, -0.1111F, -3.3333F));

		PartDefinition finn10 = body.addOrReplaceChild("finn10", CubeListBuilder.create().texOffs(30, 15).addBox(-5.0F, -0.5F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.8889F, -0.1111F, -1.3333F));

		PartDefinition finn11 = body.addOrReplaceChild("finn11", CubeListBuilder.create().texOffs(10, 28).addBox(-6.0F, -0.5F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.8889F, -0.1111F, 0.6667F));

		PartDefinition finn12 = body.addOrReplaceChild("finn12", CubeListBuilder.create().texOffs(30, 12).addBox(-5.0F, -0.5F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.8889F, -0.1111F, 2.6667F));

		PartDefinition finn13 = body.addOrReplaceChild("finn13", CubeListBuilder.create().texOffs(10, 31).addBox(-4.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.8889F, -0.1111F, 4.6667F));

		PartDefinition finn14 = body.addOrReplaceChild("finn14", CubeListBuilder.create().texOffs(16, 37).addBox(-3.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.8889F, -0.1111F, 6.6667F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Anomalocris entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

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