package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.animations.entity.TuataraAnimation;
import net.reaper.ancientnature.common.entity.ground.TuataraEntity;

public class TuataraModel extends HierarchicalModel<TuataraEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation TUATARA_LAYER = new ModelLayerLocation(new ResourceLocation(AncientNature.MOD_ID, "tuatara"), "main");
	private final ModelPart root;
	private final ModelPart Tuatara;

	public TuataraModel(ModelPart root) {
		this.root = root;
		this.Tuatara = root.getChild("Tuatara");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Tuatara = partdefinition.addOrReplaceChild("Tuatara", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

		PartDefinition body = Tuatara.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 14).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(10, 13).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(12, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(10, 5).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(4, 9).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(17, 1).addBox(-1.0F, -3.25F, -2.375F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(12, 4).addBox(-0.5F, -3.25F, -3.375F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 6).addBox(-1.0F, -1.25F, -1.375F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.01F))
		.texOffs(0, 0).addBox(0.0F, -4.25F, -1.375F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.25F, -2.625F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 12).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.25F, -2.375F));

		PartDefinition legs2 = Tuatara.addOrReplaceChild("legs2", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 3.0F));

		PartDefinition right2 = legs2.addOrReplaceChild("right2", CubeListBuilder.create().texOffs(6, 18).addBox(0.0F, -0.5F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 9).addBox(0.0F, 1.5F, -2.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 0.5F, -1.0F));

		PartDefinition left2 = legs2.addOrReplaceChild("left2", CubeListBuilder.create().texOffs(18, 17).addBox(-1.0F, -0.5F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 0).addBox(-2.0F, 1.5F, -2.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 0.5F, -1.0F));

		PartDefinition legs1 = Tuatara.addOrReplaceChild("legs1", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -2.0F));

		PartDefinition right = legs1.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 9).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 13).addBox(-2.0F, 1.5F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 0.5F, -0.5F));

		PartDefinition left = legs1.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 15).addBox(-1.0F, 1.5F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 0.5F, -0.5F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(TuataraEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
//		if (entity.isPanicing())
//			this.animateWalk(ArandaspisAnimation.ARANDASPIS_RUN, limbSwing, limbSwingAmount, 4f, 4.5f);
//		else
			this.animateWalk(TuataraAnimation.TUATARA_WALK, limbSwing, limbSwingAmount, 4f, 4.5f);
		this.animate(entity.idleAnimation, TuataraAnimation.TUATARA_IDLE, ageInTicks);
	}

	private void applyHeadRotation(TuataraEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.Tuatara.getChild("body").getChild("head").yRot = pNetHeadYaw * 0.017453292F;
		this.Tuatara.getChild("body").getChild("head").xRot = pHeadPitch * 0.017453292F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Tuatara.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}