package net.reaper.ancientnature.client.model.entity.thylacine;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.reaper.ancientnature.client.animations.entity.thylacine.ThylacineBabyAnimations;
import net.reaper.ancientnature.client.model.entity.SmartAnimalModel;
import net.reaper.ancientnature.common.entity.ground.ThylacineEntity;

public class ThylacineBabyModel extends SmartAnimalModel<ThylacineEntity> {
	private final ModelPart root;
	public ModelPart thylacine;
	public ModelPart body;
	public ModelPart head;
	public ModelPart tail;

	public ThylacineBabyModel(ModelPart root) {
		this.root = root.getChild("root");
		this.thylacine = this.root.getChild("thylacine");
		this.body = this.thylacine.getChild("body");
		this.head = this.body.getChild("head");
		this.tail = this.body.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition thylacine = root.addOrReplaceChild("thylacine", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = thylacine.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 1.0F));

		PartDefinition belly = body.addOrReplaceChild("belly", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -4.5F, 5.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -0.0858F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(10, 13).addBox(-3.0F, -1.5F, -2.8333F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(16, 23).addBox(2.0F, -2.5F, -1.8333F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 6).addBox(-3.0F, -2.5F, -1.8333F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -4.2525F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(19, 2).addBox(-1.0F, -1.75F, -3.0858F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(15, 19).addBox(-1.0F, 0.25F, -3.0858F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 0.25F, -2.7475F));

		PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(19, 0).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -2.3333F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(22, 20).addBox(-1.0F, 0.0F, -2.9858F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -2.8475F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, -0.2535F, -1.1823F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.7154F, 4.4889F, -0.6981F, 0.0F, 0.0F));

		PartDefinition left_front = thylacine.addOrReplaceChild("left_front", CubeListBuilder.create().texOffs(8, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -4.0F, -2.5858F));

		PartDefinition right_front = thylacine.addOrReplaceChild("right_front", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -4.0F, -2.5858F));

		PartDefinition left_back = thylacine.addOrReplaceChild("left_back", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -4.0F, 4.4142F));

		PartDefinition right_back = thylacine.addOrReplaceChild("right_back", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -4.0F, 4.4142F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public float shadowRadius() {
		return .5f;
	}

	@Override
	public AnimationDefinition getWalkAnim() {
		return ThylacineBabyAnimations.WALK;
	}

	@Override
	public AnimationDefinition getIdleAnim() {
		return ThylacineBabyAnimations.IDLE;
	}

	@Override
	public AnimationDefinition getSitAnim() {
		return ThylacineBabyAnimations.REST;
	}

	@Override
	public AnimationDefinition getSleepAnim() {
		return ThylacineBabyAnimations.SLEEP;
	}

	@Override
	public AnimationDefinition getRunAnim() {
		return ThylacineBabyAnimations.SPRINT;
	}

	@Override
	public AnimationDefinition getEatAnim() {
		return ThylacineBabyAnimations.EAT;
	}

	@Override
	public AnimationDefinition getAttackAnim() {
		return ThylacineBabyAnimations.ATTACK;
	}

	@Override
	public AnimationDefinition getDownAnim() {
		return ThylacineBabyAnimations.SIT;
	}

	@Override
	public AnimationDefinition getFallAsleepAnim() {
		return ThylacineBabyAnimations.FALL_ASLEEP;
	}

	@Override
	public AnimationDefinition getWakeUpAnim() {
		return ThylacineBabyAnimations.WAKE_UP;
	}

	@Override
	public AnimationDefinition getUpAnim() {
		return ThylacineBabyAnimations.UP;
	}

	@Override
	public AnimationDefinition getRoarAnim() {
		return null;
	}

	protected void applyHeadRotation(ThylacineEntity pEntity, float pNetHeadYaw, float pHeadPitch, float ageInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -10.0F, 10.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * 0.017453292F;
		this.head.xRot = pHeadPitch * 0.017453292F;
	}

	@Override
	protected void dynamicTail(ThylacineEntity pEntity) {
		float targetYaw = pEntity.prevTailRot + (pEntity.tailRot - pEntity.prevTailRot) * Minecraft.getInstance().getPartialTick();
		this.tail.yRot = Mth.lerp(0.05F, this.tail.yRot, targetYaw);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}