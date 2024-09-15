package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.animations.entity.TuataraAnimation;
import net.reaper.ancientnature.common.entity.ground.TuataraEntity;
import org.jetbrains.annotations.NotNull;

public class TutuaraModel extends SmartAnimalModel<TuataraEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation TUATARA_LAYER = new ModelLayerLocation(new ResourceLocation(AncientNature.MOD_ID, "tuatara"), "main");
	public final ModelPart tutuara;
	public final ModelPart tail1;
	public final ModelPart tail_end;
	public final ModelPart body;
	public final ModelPart top;
	public final ModelPart head;

	public TutuaraModel(ModelPart root) {
		this.tutuara = root.getChild("tutuara");
		this.top = this.tutuara.getChild("top");
		this.body = this.top.getChild("body");
		this.head = this.top.getChild("head");
		this.tail1 = this.top.getChild("tail1");
		this.tail_end = this.tail1.getChild("tail_end");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tutuara = partdefinition.addOrReplaceChild("tutuara", CubeListBuilder.create(), PartPose.offset(-0.5F, 21.0F, -5.0F));

		PartDefinition front_right = tutuara.addOrReplaceChild("front_right", CubeListBuilder.create().texOffs(30, 28).addBox(-3.0F, 2.9F, -4.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(27, 20).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 0.0F, 1.0F));

		PartDefinition front_left = tutuara.addOrReplaceChild("front_left", CubeListBuilder.create().texOffs(30, 28).mirror().addBox(-2.0F, 2.9F, -4.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(27, 20).mirror().addBox(-1.0F, 0.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.5F, 0.0F, 1.0F));

		PartDefinition right_back = tutuara.addOrReplaceChild("right_back", CubeListBuilder.create().texOffs(24, 13).addBox(-2.0F, 0.0F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(14, 41).addBox(-4.0F, 2.9F, -2.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 0.0F, 8.0F));

		PartDefinition back_left = tutuara.addOrReplaceChild("back_left", CubeListBuilder.create().texOffs(24, 13).mirror().addBox(-1.0F, 0.0F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(14, 41).mirror().addBox(-1.0F, 2.9F, -2.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.5F, 0.0F, 8.0F));

		PartDefinition top = tutuara.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 5.0F));

		PartDefinition tail1 = top.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 30).addBox(0.0F, -3.0F, 0.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(14, 13).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, 4.0F));

		PartDefinition tail_end = tail1.addOrReplaceChild("tail_end", CubeListBuilder.create().texOffs(0, 19).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));

		PartDefinition body = top.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(54, 0).addBox(-4.0F, -3.0F, -5.0F, 1.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(54, 0).addBox(3.0F, -3.0F, -5.0F, 1.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(1, 32).addBox(0.0F, -5.0F, -1.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 1.0F, 0.0F));

		PartDefinition head = top.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 19).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(-0.01F))
		.texOffs(54, 9).addBox(0.0F, -7.0F, -1.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -1.0F, -5.0F));

		PartDefinition upper_jaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(29, 49).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(1.0F, -3.0F, -4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 4).addBox(-2.0F, -3.0F, -4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 43).addBox(-1.5F, -0.25F, -4.75F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition eyes = upper_jaw.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(0, 61).addBox(-2.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -1.5F, -3.0F));

		PartDefinition lowe_jaw = head.addOrReplaceChild("lowe_jaw", CubeListBuilder.create().texOffs(0, 50).addBox(-2.0F, 0.0F, -5.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(29, 3).addBox(0.0F, 1.0F, -5.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(18, 59).addBox(-1.5F, -0.75F, -5.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.08F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void setupAnim(TuataraEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
	}

	protected void applyHeadRotation(TuataraEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);
		
		this.top.getChild("head").yRot = pNetHeadYaw * 0.000000372F;
		this.top.getChild("head").xRot = pHeadPitch * 0.017453292F;
		this.head.yRot = pNetHeadYaw * 0.017453292F;
		this.head.xRot = pHeadPitch * 0.017453292F;

		if (pEntity.isSleeping()) {
			this.top.getChild("head").yRot = 0.0F;
			this.top.getChild("head").xRot = 0.0F;
			this.head.yRot = 0.0F;
		}
	}

	@Override
	protected void dynamicTail(@NotNull TuataraEntity pEntity) {
		float targetYaw = pEntity.prevTailRot + (pEntity.tailRot - pEntity.prevTailRot) * Minecraft.getInstance().getPartialTick();
		this.tail1.yRot = Mth.lerp(0.05F, this.tail1.yRot, targetYaw);
		this.tail_end.yRot = Mth.lerp(0.07F, this.tail_end.yRot, targetYaw);
	}

	public void setMatrixStack(@NotNull PoseStack pMatrixStack) {
		this.tutuara.translateAndRotate(pMatrixStack);
		this.body.translateAndRotate(pMatrixStack);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.tutuara;
	}


	@Override
	public AnimationDefinition getWalkAnim() {
		return TuataraAnimation.WALK;
	}

	@Override
	public AnimationDefinition getIdleAnim() {
		return TuataraAnimation.IDLE;
	}

	@Override
	public AnimationDefinition getSitAnim() {
		return null;
	}


	@Override
	public AnimationDefinition getSleepAnim() {
		return TuataraAnimation.SLEEP;
	}

	@Override
	public AnimationDefinition getRunAnim() {
		return TuataraAnimation.SPRINT;
	}

	@Override
	public AnimationDefinition getEatAnim() {
		return TuataraAnimation.EAT;
	}

	@Override
	public AnimationDefinition getAttackAnim() {return TuataraAnimation.ATTACK;
	}

	@Override
	public AnimationDefinition getDownAnim() {
		return null;
	}

	@Override
	public AnimationDefinition getFallAsleepAnim() {
		return TuataraAnimation.SIT;
	}

	@Override
	public AnimationDefinition getWakeUpAnim() {
		return TuataraAnimation.UP;
	}

	@Override
	public AnimationDefinition getUpAnim() {
		return null;
	}

	@Override
	public AnimationDefinition getRoarAnim() {
		return null;
	}
}