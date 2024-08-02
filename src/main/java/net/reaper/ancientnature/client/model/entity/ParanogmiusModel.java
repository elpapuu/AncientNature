package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.10.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.animations.entity.ParanogmiusAnimations;
import net.reaper.ancientnature.common.entity.water.Paranogmius;
import org.jetbrains.annotations.NotNull;

public class ParanogmiusModel extends HierarchicalModel<Paranogmius> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation PARANOGMIUS_LAYER = new ModelLayerLocation(new ResourceLocation(
			AncientNature.MOD_ID, "paranogmius"), "main");

	private final ModelPart paranogmius;
	public ModelPart body;
	public ModelPart head;
	public ModelPart jaw;
	public ModelPart finleft;
	public ModelPart finright;
	public ModelPart tailfin;

	public ModelPart endtail;

	public ModelPart tail;
	public ModelPart pelvicright;
	public ModelPart pelvicleft;

	public ParanogmiusModel(ModelPart root) {
        this.paranogmius = root.getChild("paranogmius");
        this.body = this.paranogmius.getChild("body");
		this.tail = this.body.getChild("tail");
		this.endtail = this.tail.getChild("tailend");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition paranogmius = partdefinition.addOrReplaceChild("paranogmius", CubeListBuilder.create(), PartPose.offset(0.0F, 10.5F, -1.25F));

		PartDefinition body = paranogmius.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -9.25F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(15, 0).addBox(-3.0F, -8.0F, -1.5F, 6.0F, 16.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(41, 26).addBox(-3.0F, -8.0F, 1.0F, 6.0F, 16.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 12.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -4.5F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(50, 0).addBox(-2.0F, -6.0F, 38.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(-0.01F))
		.texOffs(65, 8).addBox(-3.0F, -12.0F, 31.0F, 6.0F, 9.0F, 7.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 6.0F, 32.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.0F, 2.25F, -9.0F));

		PartDefinition mouth_r1 = mouth.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(70, 25).addBox(-3.0F, -6.0F, 31.0F, 6.0F, 4.0F, 7.0F, new CubeDeformation(0.02F))
		.texOffs(46, 21).addBox(-2.0F, -6.0F, 38.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 4.75F, 41.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 3.75F, -0.5F));

		PartDefinition jaw_r1 = jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, -4.0F, 38.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.01F))
		.texOffs(0, 76).addBox(-3.0F, -4.0F, 31.0F, 6.0F, 1.0F, 7.0F, new CubeDeformation(-0.01F))
		.texOffs(64, 71).addBox(-3.0F, -3.0F, 31.0F, 6.0F, 3.0F, 7.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 2.25F, 32.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition dorsal = body.addOrReplaceChild("dorsal", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, -2.0F));

		PartDefinition dorsal_r1 = dorsal.addOrReplaceChild("dorsal_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -30.0F, -1.0F, 0.0F, 14.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, 25.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition finleft = body.addOrReplaceChild("finleft", CubeListBuilder.create(), PartPose.offset(3.0F, 2.5F, 1.5F));

		PartDefinition finleft_r1 = finleft.addOrReplaceChild("finleft_r1", CubeListBuilder.create().texOffs(16, 26).addBox(-11.0F, -5.0F, 29.0F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 3.5F, 29.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition finright = body.addOrReplaceChild("finright", CubeListBuilder.create(), PartPose.offset(-3.0F, 2.5F, 1.5F));

		PartDefinition finright_r1 = finright.addOrReplaceChild("finright_r1", CubeListBuilder.create().texOffs(0, 26).addBox(3.0F, -5.0F, 29.0F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 3.5F, 29.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 13.5F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(0, 43).addBox(-3.0F, -8.0F, -18.0F, 6.0F, 16.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition pelvileft = tail.addOrReplaceChild("pelvileft", CubeListBuilder.create(), PartPose.offset(1.0F, 8.5F, -5.5F));

		PartDefinition pelvileft_r1 = pelvileft.addOrReplaceChild("pelvileft_r1", CubeListBuilder.create().texOffs(6, 40).addBox(-1.0F, -2.5F, 6.5F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, 8.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition pelvicright = tail.addOrReplaceChild("pelvicright", CubeListBuilder.create(), PartPose.offset(-1.0F, 8.5F, -5.5F));

		PartDefinition pelvicright_r1 = pelvicright.addOrReplaceChild("pelvicright_r1", CubeListBuilder.create().texOffs(0, 40).addBox(1.0F, -2.5F, 6.5F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, 8.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition anal = tail.addOrReplaceChild("anal", CubeListBuilder.create(), PartPose.offset(0.0F, 7.5F, 3.5F));

		PartDefinition anal_r1 = anal.addOrReplaceChild("anal_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -3.5F, -17.5F, 0.0F, 7.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition endtail = tail.addOrReplaceChild("endtail", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 17.0F));

		PartDefinition endtail_r1 = endtail.addOrReplaceChild("endtail_r1", CubeListBuilder.create().texOffs(64, 59).addBox(-2.0F, -11.0F, -9.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, -3.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition tailfin = endtail.addOrReplaceChild("tailfin", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 4.5F));

		PartDefinition tailfin_r1 = tailfin.addOrReplaceChild("tailfin_r1", CubeListBuilder.create().texOffs(46, 50).addBox(0.0F, -19.0F, -17.0F, 0.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.5F, -8.5F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		paranogmius.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}



	public void setupAnim(Paranogmius entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
		this.dynamicTail(entity);


		this.body.xRot = headPitch * (float) (Math.PI / 180F);
		this.body.yRot = netHeadYaw * (float) (Math.PI / 180F);
		/*
		if (entity.isSwimming() && entity.isInWater()) {
			this.body.zRot = Mth.sin((float) ((entity.tickCount + ageInTicks) * 2 * Math.PI * 0.8125D));
		}

		 */
		if (entity.isInWater()) {
			if (entity.isSprinting()) {
				this.animateWalk(ParanogmiusAnimations.PARANOGMIUS_SPRINT, limbSwing, limbSwingAmount, 1.0F, 1.0F);
			} else {
				this.animateWalk(ParanogmiusAnimations.PARANOGMIUS_SWIM, limbSwing, limbSwingAmount, 2.0F, 2.0F);
			}
		}
		this.animateWalk(ParanogmiusAnimations.PARANOGMIUS_SWIM, limbSwing, limbSwingAmount, 4.0F, 4.0F);
		this.animate(entity.idleAnimation, ParanogmiusAnimations.PARANOGMIUS_IDLE, ageInTicks, 1.0F);
		this.animate(entity.attackAnimationState, ParanogmiusAnimations.PARANOGMIUS_ATTACK, ageInTicks, 1.0F);
	//	this.animate(entity.flopAnimation, ParanogmiusAnimations.PARANOGMIUS_FLOP, ageInTicks, 1.0F);
	}

	private void applyHeadRotation(Paranogmius pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.body.yRot = pNetHeadYaw * 0.017453292F;
		this.body.xRot = pHeadPitch * 0.017453292F;
	}

	private void dynamicTail(@NotNull Paranogmius pEntity) {
		float targetYaw = pEntity.prevTailRot + (pEntity.tailRot - pEntity.prevTailRot) * Minecraft.getInstance().getPartialTick();
		this.tail.yRot = Mth.lerp(0.1F, this.tail.yRot, targetYaw);
		this.endtail.yRot = Mth.lerp(0.05F, this.endtail.yRot, targetYaw);
	}

	public void setCustomPose(@NotNull PoseStack pMatrixStack) {
		this.paranogmius.translateAndRotate(pMatrixStack);
		this.body.translateAndRotate(pMatrixStack);
		this.tail.translateAndRotate(pMatrixStack);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.paranogmius;
	}
}
