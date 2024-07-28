package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.10.4
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
import net.reaper.ancientnature.client.animations.entity.DunkleosteusAnimations;
import net.reaper.ancientnature.client.animations.entity.HorseshoeCrabAnimations;
import net.reaper.ancientnature.common.entity.water.DunkleosteusEntity;
import net.reaper.ancientnature.common.entity.water.HorseshoeCrabEntity;

public class HorseshoeCrabModel extends HierarchicalModel<HorseshoeCrabEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation HORSESHOE_LAYER = new ModelLayerLocation(new ResourceLocation(
			AncientNature.MOD_ID, "horseshoecrab"), "main");
	private final ModelPart horseshoe;
	public HorseshoeCrabModel(ModelPart root) {
		this.horseshoe = root.getChild("horseshoe");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition horseshoe = partdefinition.addOrReplaceChild("horseshoe", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = horseshoe.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.375F, -1.425F, -11.875F, 13.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 14).addBox(-5.375F, -3.425F, -10.875F, 11.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(21, 27).addBox(-5.375F, 0.275F, 0.125F, 11.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(-0.375F, -3.425F, -10.875F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.125F, -0.575F, 4.875F));

		PartDefinition tail = horseshoe.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(26, 0).addBox(-0.5F, 0.25F, 0.0F, 1.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(0, 15).addBox(0.0F, -0.75F, 0.0F, 0.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.25F, 5.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		horseshoe.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(HorseshoeCrabEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.horseshoe.getAllParts().forEach(ModelPart::resetPose);
		applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);

		//WALK ANIMATION
		this.animateWalk(HorseshoeCrabAnimations.WALK, limbSwing, limbSwingAmount, 4f, 4.5f);

		//IDLE ANIMATION
		animate(entity.idleAnimationState,HorseshoeCrabAnimations.IDLE, ageInTicks);
	}

	private void applyHeadRotation(HorseshoeCrabEntity pEntity, float pNetHeadYaw, float pHeadPitch, float ageInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, 0.0F, 0.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.horseshoe.yRot = pNetHeadYaw * 0;
		this.horseshoe.xRot = pHeadPitch * 0.017453292F;
	}

	@Override
	public ModelPart root() {
		return horseshoe;
	}
}