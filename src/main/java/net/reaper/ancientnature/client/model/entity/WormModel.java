package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.11.0
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
import net.reaper.ancientnature.client.animations.entity.WormAnimations;
import net.reaper.ancientnature.client.animations.entity.thylacine.ThylacineAnimations;
import net.reaper.ancientnature.common.entity.ground.ThylacineEntity;
import net.reaper.ancientnature.common.entity.ground.WormEntity;

public class WormModel extends SmartAnimalModel<WormEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation WORM_LAYER = new ModelLayerLocation(new ResourceLocation(
			AncientNature.MOD_ID, "worm"), "main");

	private final ModelPart worm;
	private ModelPart front;
	private ModelPart middle;
	private ModelPart back;

	public WormModel(ModelPart root) {
		this.worm = root.getChild("worm");
		this.front = this.worm.getChild("front");
		this.middle = this.worm.getChild("middle");
		this.back = this.worm.getChild("back");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition worm = partdefinition.addOrReplaceChild("worm", CubeListBuilder.create(), PartPose.offset(-0.5F, 24.0F, 0.0F));

		PartDefinition front = worm.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -1.5F));

		PartDefinition middle = worm.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(5, 1).addBox(-0.5F, -1.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition back = worm.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 1.5F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public float shadowRadius() {
		return .2f;
	}

	@Override
	public AnimationDefinition getWalkAnim() {
		return WormAnimations.WALK;
	}

	@Override
	public AnimationDefinition getIdleAnim() {
		return WormAnimations.IDLE;
	}

	@Override
	public AnimationDefinition getSitAnim() {
		return null;
	}

	@Override
	public AnimationDefinition getSleepAnim() {
		return null;
	}

	@Override
	public AnimationDefinition getRunAnim() {
		return null;
	}

	@Override
	public AnimationDefinition getEatAnim() {
		return null;
	}

	@Override
	public AnimationDefinition getAttackAnim() {
		return null;
	}

	@Override
	public AnimationDefinition getDownAnim() {
		return null;
	}

	@Override
	public AnimationDefinition getFallAsleepAnim() {
		return null;
	}

	@Override
	public AnimationDefinition getWakeUpAnim() {
		return null;
	}

	@Override
	public AnimationDefinition getUpAnim() {
		return null;
	}

	@Override
	public AnimationDefinition getRoarAnim() {
		return null;
	}

	protected void applyHeadRotation(WormEntity pEntity, float pNetHeadYaw, float pHeadPitch, float ageInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -10.0F, 10.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.worm.getChild("front").yRot = pNetHeadYaw * 0F;
		this.worm.getChild("front").xRot = pHeadPitch * 0.017453292F;
	}

	@Override
	protected void dynamicTail(WormEntity pEntity) {
		float targetYaw = pEntity.prevTailRot + (pEntity.tailRot - pEntity.prevTailRot) * Minecraft.getInstance().getPartialTick();
		this.back.yRot = Mth.lerp(0.09F, this.back.yRot, targetYaw);
	}

	@Override
	public ModelPart root() {
		return worm;
	}
}