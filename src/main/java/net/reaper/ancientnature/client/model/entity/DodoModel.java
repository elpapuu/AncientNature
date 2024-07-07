package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.animations.entity.DodoAnimations;
import net.reaper.ancientnature.common.entity.ground.DodoEntity;

public class DodoModel extends HierarchicalModel<DodoEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation DODO_LAYER = new ModelLayerLocation(new ResourceLocation(
			AncientNature.MOD_ID, "dodo"), "main");
	private final ModelPart dodo;
	public ModelPart body;
	public ModelPart neck;
	public ModelPart head;

	public DodoModel(ModelPart root) {
		this.dodo = root.getChild("dodo");
		this.body = this.dodo.getChild("body");
		this.neck = this.body.getChild("neck");
		this.head = this.neck.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition dodo = partdefinition.addOrReplaceChild("dodo", CubeListBuilder.create(), PartPose.offset(0.0F, 23.9F, 0.0F));

		PartDefinition leg1 = dodo.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -4.0F, 3.0F));

		PartDefinition foot1 = leg1.addOrReplaceChild("foot1", CubeListBuilder.create().texOffs(24, 0).addBox(-2.5F, 0.0F, -4.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

		PartDefinition leg2 = dodo.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.5F, -4.0F, 3.0F));

		PartDefinition foot2 = leg2.addOrReplaceChild("foot2", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(-3.5F, 0.0F, -4.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 4.0F, 0.0F));

		PartDefinition body = dodo.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -8.0F, 9.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -4.0F, 3.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(18, 22).addBox(-3.0F, -2.0F, -1.0F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 41).addBox(-2.0F, 5.0F, -1.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(19, 40).addBox(-3.0F, -2.0F, 4.0F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 4.0F));

		PartDefinition wing1 = body.addOrReplaceChild("wing1", CubeListBuilder.create().texOffs(42, 9).addBox(-1.0F, -2.5F, 6.5F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(35, 0).addBox(-1.0F, -2.5F, -0.5F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -4.5F, -6.5F));

		PartDefinition wing2 = body.addOrReplaceChild("wing2", CubeListBuilder.create().texOffs(11, 34).addBox(0.0F, -2.5F, -0.5F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(42, 15).addBox(1.0F, -2.5F, 6.5F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -4.5F, -6.5F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 22).addBox(-2.5F, -8.0F, -2.0F, 4.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -8.0F));

		PartDefinition cube_r1 = neck.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 37).mirror().addBox(-2.5F, 0.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.0F, -2.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r2 = neck.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 37).addBox(-2.5F, 0.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -2.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(33, 29).addBox(-3.0F, -2.0F, -4.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(20, 34).addBox(-2.0F, -4.0F, -3.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 1.0F));

		PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(25, 44).addBox(-3.0F, -1.5F, -1.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -0.5F, -3.0F));

		PartDefinition upjaw = head.addOrReplaceChild("upjaw", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, -7.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.1F))
		.texOffs(33, 22).addBox(-2.0F, -1.0F, -4.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(44, 0).addBox(-2.0F, 2.0F, -6.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, -3.0F));

		PartDefinition downjaw = head.addOrReplaceChild("downjaw", CubeListBuilder.create().texOffs(13, 22).addBox(-2.0F, 0.0F, -5.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 8).addBox(-2.0F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -4.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		dodo.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	@Override
	public void setupAnim(DodoEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.dodo.getAllParts().forEach(ModelPart::resetPose);
		applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
		if (entity.isSprinting())
			this.animateWalk(DodoAnimations.RUN, limbSwing, limbSwingAmount, 3f, 4.5f);
		if (entity.getOrder()==3)
			this.animate(entity.sitAnimation, DodoAnimations.DOWN, ageInTicks, 1.0F);
		if (entity.isSleeping())
			this.animate(entity.sleepAnimation, DodoAnimations.SLEEP, ageInTicks, 1.0F);
		if (entity.isInWater())
			this.animate(entity.swimAnimationState, DodoAnimations.SWIM, ageInTicks, 1.0F);
		this.animateWalk(DodoAnimations.WALK, limbSwing, limbSwingAmount, 4f, 4.5f);
		this.animate(entity.idleAnimation, DodoAnimations.IDLE, ageInTicks, 1.0F);
	}

	private void applyHeadRotation(DodoEntity pEntity, float pNetHeadYaw, float pHeadPitch, float ageInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.body.getChild("neck").yRot = pNetHeadYaw * 0.017453292F;
		this.body.getChild("neck").xRot = pNetHeadYaw * 0.017453292F;
		this.neck.getChild("head").yRot = pHeadPitch * 0.017453292F;
		this.neck.getChild("head").xRot = pHeadPitch * 0.017453292F;
	}
	@Override
	public ModelPart root() {
		return dodo;
	}
}