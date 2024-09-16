package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.animations.entity.ThylacineAnimations;
import net.reaper.ancientnature.common.entity.ground.ThylacineEntity;

public class ThylacineModel extends SmartAnimalModel<ThylacineEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation THYLACINE_LAYER = new ModelLayerLocation(new ResourceLocation(
			AncientNature.MOD_ID, "thylacine"), "main");

	ResourceLocation BENJAMIN = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/thylacine/thylacine_benjamin.png");
	private final ModelPart thylacine;
	public ModelPart body;
	public ModelPart head;
	public ModelPart tail;

	public ThylacineModel(ModelPart root) {
		this.thylacine = root.getChild("thylacine");
		this.body = this.thylacine.getChild("body");
		this.head = this.body.getChild("head");
		this.tail = this.body.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition thylacine = partdefinition.addOrReplaceChild("thylacine", CubeListBuilder.create(), PartPose.offset(0.0F, 13.0F, -3.0F));

		PartDefinition body = thylacine.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 3.0F));

		PartDefinition belly = body.addOrReplaceChild("belly", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.5F, -7.0F, 5.0F, 7.0F, 14.0F, new CubeDeformation(0.03F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(18, 21).addBox(-3.0F, -1.5F, -4.5F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(8, 4).addBox(-3.0F, -2.5F, -2.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 7).addBox(2.0F, -2.5F, -2.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -7.5F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(35, 24).addBox(-1.5F, -1.5F, -2.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(38, 32).addBox(-1.5F, 1.5F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(-0.02F)), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition halo = head.addOrReplaceChild("halo", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -2.0F));

		PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(0, 37).addBox(-3.0F, -0.5F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -3.5F));

		PartDefinition pharynx = head.addOrReplaceChild("pharynx", CubeListBuilder.create().texOffs(35, 17).addBox(-3.0F, -1.0F, -0.6667F, 6.0F, 2.0F, 4.0F, new CubeDeformation(-0.04F))
		.texOffs(24, 0).addBox(-3.0F, -1.0F, -2.6667F, 6.0F, 2.0F, 5.0F, new CubeDeformation(-0.02F))
		.texOffs(24, 7).addBox(-3.0F, -1.0F, -2.6667F, 6.0F, 2.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 1.5F, -1.8333F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(18, 29).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(27, 35).addBox(-1.5F, 0.0F, -9.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, -0.5F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 21).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 7.0F, -0.6981F, 0.0F, 0.0F));

		PartDefinition left_front = thylacine.addOrReplaceChild("left_front", CubeListBuilder.create().texOffs(6, 27).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 5.0F, -3.0F));

		PartDefinition right_front = thylacine.addOrReplaceChild("right_front", CubeListBuilder.create().texOffs(16, 37).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 5.0F, -3.0F));

		PartDefinition left_back = thylacine.addOrReplaceChild("left_back", CubeListBuilder.create().texOffs(0, 21).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 5.0F, 9.0F));

		PartDefinition right_back = thylacine.addOrReplaceChild("right_back", CubeListBuilder.create().texOffs(0, 4).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 5.0F, 9.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public float shadowRadius() {
		return .5f;
	}

	@Override
	public AnimationDefinition getWalkAnim() {
		return ThylacineAnimations.WALK;
	}

	@Override
	public AnimationDefinition getIdleAnim() {
		return ThylacineAnimations.IDLE;
	}

	@Override
	public AnimationDefinition getSitAnim() {
		return ThylacineAnimations.REST;
	}

	@Override
	public AnimationDefinition getSleepAnim() {
		return ThylacineAnimations.SLEEP;
	}

	@Override
	public AnimationDefinition getRunAnim() {
		return ThylacineAnimations.SPRINT;
	}

	@Override
	public AnimationDefinition getEatAnim() {
		return ThylacineAnimations.EAT;
	}

	@Override
	public AnimationDefinition getAttackAnim() {
		return ThylacineAnimations.ATTACK;
	}

	@Override
	public AnimationDefinition getDownAnim() {
		return ThylacineAnimations.SIT;
	}

	@Override
	public AnimationDefinition getFallAsleepAnim() {
		return ThylacineAnimations.FALL_ASLEEP;
	}

	@Override
	public AnimationDefinition getWakeUpAnim() {
		return ThylacineAnimations.WAKE_UP;
	}

	@Override
	public AnimationDefinition getUpAnim() {
		return ThylacineAnimations.UP;
	}

	@Override
	public AnimationDefinition getRoarAnim() {
		return null;
	}

	protected void applyHeadRotation(ThylacineEntity pEntity, float pNetHeadYaw, float pHeadPitch, float ageInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -10.0F, 10.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.body.getChild("head").yRot = pNetHeadYaw * 0.017453292F;
		this.body.getChild("head").xRot = pHeadPitch * 0.017453292F;
		this.head.xRot = pHeadPitch * 0.017453292F;
	}

	@Override
	protected void dynamicTail(ThylacineEntity pEntity) {
		float targetYaw = pEntity.prevTailRot + (pEntity.tailRot - pEntity.prevTailRot) * Minecraft.getInstance().getPartialTick();
		this.tail.yRot = Mth.lerp(0.05F, this.tail.yRot, targetYaw);
	}

	@Override
	public ModelPart root() {
		return thylacine;
	}
}