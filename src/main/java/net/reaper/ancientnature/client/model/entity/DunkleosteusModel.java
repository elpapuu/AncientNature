package net.reaper.ancientnature.client.model.entity;


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
import net.reaper.ancientnature.common.entity.water.DunkleosteusEntity;

public class DunkleosteusModel extends HierarchicalModel<DunkleosteusEntity> {
	public static final ModelLayerLocation DUNKLEOSTEUS_LAYER = new ModelLayerLocation(new ResourceLocation(
			AncientNature.MOD_ID, "dunkleosteus"), "main");
	private final ModelPart Dunkleosteus;
	public ModelPart body;
	public ModelPart head;

	public DunkleosteusModel(ModelPart root) {
		this.Dunkleosteus = root.getChild("Dunkleosteus");
		this.body = this.Dunkleosteus.getChild("body");
		this.head = this.body.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Dunkleosteus = partdefinition.addOrReplaceChild("Dunkleosteus", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 6.0F));

		PartDefinition body = Dunkleosteus.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -11.375F, -22.125F, 24.0F, 24.0F, 34.0F, new CubeDeformation(0.0F))
				.texOffs(0, 58).addBox(-12.0F, -11.375F, -22.125F, 24.0F, 24.0F, 17.0F, new CubeDeformation(0.2F))
				.texOffs(81, 135).addBox(-1.0F, -16.375F, 2.875F, 2.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.0F, -21.375F, 3.875F, 0.0F, 15.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.625F, 4.125F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 100).addBox(-10.0F, -11.1667F, -16.0F, 20.0F, 11.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(100, 42).addBox(-10.0F, -2.1667F, -16.0F, 20.0F, 7.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(82, 0).addBox(-10.0F, -11.1667F, -16.0F, 20.0F, 11.0F, 16.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 3.7917F, -22.125F));

		PartDefinition connection = head.addOrReplaceChild("connection", CubeListBuilder.create().texOffs(113, 102).addBox(-10.0F, -2.0F, -1.5F, 20.0F, 9.0F, 14.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, -0.1667F, -12.5F, 0.0F, 0.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(57, 116).addBox(-10.0F, 1.875F, -14.125F, 20.0F, 3.0F, 15.0F, new CubeDeformation(-0.01F))
				.texOffs(129, 125).addBox(-10.0F, -2.125F, -14.125F, 20.0F, 4.0F, 8.0F, new CubeDeformation(-0.01F))
				.texOffs(0, 127).addBox(-10.0F, -4.125F, -5.125F, 20.0F, 6.0F, 8.0F, new CubeDeformation(-0.01F))
				.texOffs(106, 65).addBox(-10.0F, 1.875F, -14.125F, 20.0F, 3.0F, 17.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 2.9583F, -0.875F));

		PartDefinition right_front = body.addOrReplaceChild("right_front", CubeListBuilder.create().texOffs(116, 27).addBox(-16.0F, -1.0F, -4.0F, 16.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, 10.625F, -16.125F));

		PartDefinition right_back = body.addOrReplaceChild("right_back", CubeListBuilder.create().texOffs(47, 134).addBox(-12.5F, -1.0F, -3.5F, 13.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.5F, 10.625F, 9.375F));

		PartDefinition left_front = body.addOrReplaceChild("left_front", CubeListBuilder.create().texOffs(106, 85).addBox(0.0F, -1.0F, -4.0F, 16.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(12.0F, 10.625F, -16.125F));

		PartDefinition left_back = body.addOrReplaceChild("left_back", CubeListBuilder.create().texOffs(65, 58).addBox(-0.5F, -1.0F, -3.5F, 13.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(10.5F, 10.625F, 9.375F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(61, 78).addBox(-6.0F, -7.25F, -0.25F, 12.0F, 17.0F, 21.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.0F, 9.75F, 3.75F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.125F, 12.125F));

		PartDefinition fin = tail.addOrReplaceChild("fin", CubeListBuilder.create().texOffs(109, 125).addBox(-0.5F, -14.0F, -1.0F, 1.0F, 28.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.25F, 17.75F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Dunkleosteus.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(DunkleosteusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.Dunkleosteus.getAllParts().forEach(ModelPart::resetPose);
		applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);

		//SPRINTING ANIMATION
		if (entity.isSprinting() && entity.isAggressive())
			this.animateWalk(DunkleosteusAnimations.SPRINT, limbSwing, limbSwingAmount, 3f, 4.5f);

		//SWIM ANIMATION
		if (entity.isSwimming())
			this.animateWalk(DunkleosteusAnimations.SWIM, limbSwing, limbSwingAmount, 4f, 4.5f);

		//IDLE ANIMATION
		if (entity.isInWaterOrBubble() || !entity.isSwimming())
			animate(entity.idleAnimationState,DunkleosteusAnimations.IDLE, ageInTicks);

		//ATTACK ANIMATION
		if (entity.isAttacking())
			animate(entity.attackAnimationState,DunkleosteusAnimations.BITE, ageInTicks);

		//FLOP ANIMATION
		if (!entity.isInWaterOrBubble())
			animate(entity.flopAnimation,DunkleosteusAnimations.FLOP, ageInTicks);
	}

	private void applyHeadRotation(DunkleosteusEntity pEntity, float pNetHeadYaw, float pHeadPitch, float ageInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -10.0F, 10.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		if (!pEntity.isInWaterOrBubble()) {
			this.Dunkleosteus.getChild("body").yRot = pNetHeadYaw * 0.0F;
			this.Dunkleosteus.getChild("body").xRot = pHeadPitch * 0.01F;
			this.body.getChild("head").yRot = pNetHeadYaw * 0.01F;
			this.body.getChild("head").xRot = pHeadPitch * 0.01F;
		}
		
		this.Dunkleosteus.getChild("body").yRot = pNetHeadYaw * 0.017453292F;
		this.Dunkleosteus.getChild("body").xRot = pHeadPitch * 0.017453292F;
		this.body.getChild("head").yRot = pNetHeadYaw * 0.01253292F;
		this.body.getChild("head").xRot = pHeadPitch * 0.017453292F;
	}

	@Override
	public ModelPart root() {
		return Dunkleosteus;
	}
}