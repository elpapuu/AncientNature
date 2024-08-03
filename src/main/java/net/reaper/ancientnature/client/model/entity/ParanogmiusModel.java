package net.reaper.ancientnature.client.model.entity;


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
	public static final ModelLayerLocation PARANOGMIUS_LAYER = new ModelLayerLocation(new ResourceLocation(AncientNature.MOD_ID, "paranogmius"), "main");
	private final ModelPart Concavotectum;
	public ModelPart body;
	public ModelPart head;
	public ModelPart tail1;
	public ModelPart tail2;
	public ModelPart fin;

	public ParanogmiusModel(ModelPart root) {
		this.Concavotectum = root.getChild("Concavotectum");
		this.body = this.Concavotectum.getChild("body");
		this.tail1 = this.body.getChild("tail1");
		this.tail2 = this.tail1.getChild("tail2");
		this.fin = this.tail2.getChild("fin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Concavotectum = partdefinition.addOrReplaceChild("Concavotectum", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition body = Concavotectum.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 34).addBox(-5.0F, -9.0F, -18.0F, 10.0F, 16.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, 7.0F, -4.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(56, 51).addBox(0.0F, -20.0F, -15.0F, 0.0F, 11.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(38, 0).addBox(-3.0F, -1.0F, -11.0F, 6.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 68).addBox(-3.0F, -8.0F, -6.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -18.0F));

		PartDefinition upperjaw = head.addOrReplaceChild("upperjaw", CubeListBuilder.create().texOffs(38, 4).mirror().addBox(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(38, 4).addBox(-6.0F, 0.0F, -0.5F, 0.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 3.0F, -10.5F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(56, 15).addBox(-3.0F, 0.0F, -11.0F, 6.0F, 2.0F, 11.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition fin1 = body.addOrReplaceChild("fin1", CubeListBuilder.create().texOffs(38, 33).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 4.0F, -12.0F, 0.0F, -0.0436F, 0.0F));

		PartDefinition fin2 = body.addOrReplaceChild("fin2", CubeListBuilder.create().texOffs(38, 33).mirror().addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 4.0F, -12.0F, 0.0F, 0.0436F, 0.0F));

		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(38, 16).addBox(0.0F, -15.0F, 0.0F, 0.0F, 6.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(38, 22).addBox(0.0F, 7.0F, 0.0F, 0.0F, 6.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -9.0F, 0.0F, 10.0F, 16.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(56, 46).addBox(-2.0F, -5.0F, 0.0F, 4.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 18.0F));

		PartDefinition fin = tail2.addOrReplaceChild("fin", CubeListBuilder.create().texOffs(0, 74).addBox(0.0F, -10.0F, 0.0F, 0.0F, 20.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Concavotectum.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
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
		this.tail1.yRot = Mth.lerp(0.1F, this.tail1.yRot, targetYaw);
		this.tail2.yRot = Mth.lerp(0.05F, this.tail2.yRot, targetYaw);
		this.fin.yRot = Mth.lerp(0.025F, this.fin.yRot, targetYaw);
	}

	public void setCustomPose(@NotNull PoseStack pMatrixStack) {
		this.Concavotectum.translateAndRotate(pMatrixStack);
		this.body.translateAndRotate(pMatrixStack);
		this.tail1.translateAndRotate(pMatrixStack);
	}

	@Override
	public ModelPart root() {
		return Concavotectum;
	}
}