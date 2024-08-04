package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.reaper.ancientnature.AncientNature;import net.reaper.ancientnature.client.animations.entity.DodoAnimations;
import net.reaper.ancientnature.common.entity.ground.DodoEntity;

public class DodoModel extends SmartAnimalModel<DodoEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation DODO_LAYER = new ModelLayerLocation(new ResourceLocation(
			AncientNature.MOD_ID, "dodo"), "main");

	ResourceLocation PIZZA = new ResourceLocation(AncientNature.MOD_ID, "textures/entity/dodo/dodo_pizza.png");

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

	@Override
	public ResourceLocation getTexture(DodoEntity pEntity) {
		if((pEntity.hasCustomName() && pEntity.getName().getString().equals("Pizza")))
			return PIZZA;
		return super.getTexture(pEntity);
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
	public float shadowRadius() {
		return .5f;
	}

	@Override
	public AnimationDefinition getWalkAnim() {
		return DodoAnimations.WALK;
	}

	@Override
	public AnimationDefinition getIdleAnim() {
		return DodoAnimations.IDLE;
	}

	@Override
	public AnimationDefinition getSitAnim() {
		return DodoAnimations.REST;
	}

	@Override
	public AnimationDefinition getSleepAnim() {
		return DodoAnimations.SLEEP;
	}

	@Override
	public AnimationDefinition getRunAnim() {
		return DodoAnimations.RUN;
	}

	@Override
	public AnimationDefinition getEatAnim() {
		return DodoAnimations.EAT;
	}

	@Override
	public AnimationDefinition getAttackAnim() {
		return null;
	}

	@Override
	public AnimationDefinition getDownAnim() {
		return DodoAnimations.DOWN;
	}

	@Override
	public AnimationDefinition getFallAsleepAnim() {
		return DodoAnimations.FALL_ASLEEP;
	}

	@Override
	public AnimationDefinition getWakeUpAnim() {
		return DodoAnimations.WAKE_UP;
	}

	@Override
	public AnimationDefinition getUpAnim() {
		return DodoAnimations.UP;
	}

	@Override
	public AnimationDefinition getRoarAnim() {
		return null;
	}

	protected void applyHeadRotation(DodoEntity pEntity, float pNetHeadYaw, float pHeadPitch, float ageInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -10.0F, 10.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.body.getChild("neck").yRot = pNetHeadYaw * 0.017453292F;
		this.body.getChild("neck").xRot = pHeadPitch * 0.017453292F;
		this.neck.getChild("head").xRot = pHeadPitch * 0.017453292F;
	}

	@Override
	protected void dynamicTail(DodoEntity pEntity) {
	}

	@Override
	public ModelPart root() {
		return dodo;
	}
}