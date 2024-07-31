package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.10.1
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
import net.reaper.ancientnature.client.animations.entity.LythronaxAnimations;
import net.reaper.ancientnature.common.entity.ground.LythronaxEntity;

public class LythronaxModel extends HierarchicalModel<LythronaxEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LYTHRONAX_LAYER = new ModelLayerLocation(new ResourceLocation(AncientNature.MOD_ID, "lythronax"), "main");
	private final ModelPart Lythronax;
	private final ModelPart body;

	public LythronaxModel(ModelPart root) {
		this.body = root;
		this.Lythronax = root.getChild("Lythronax");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Lythronax = partdefinition.addOrReplaceChild("Lythronax", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition body = Lythronax.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -9.0F, -22.0F, 14.0F, 18.0F, 29.0F, new CubeDeformation(0.0F))
		.texOffs(57, 0).addBox(-4.0F, -10.0F, -21.0F, 8.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 1.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(66, 47).addBox(-4.0F, -16.0F, -7.5F, 8.0F, 21.0F, 11.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.0F, -3.0F, -19.5F, 0.3927F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(57, 19).addBox(-3.5F, -3.5F, -8.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.01F))
		.texOffs(20, 0).addBox(2.5F, -3.5F, -8.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.01F))
		.texOffs(86, 33).addBox(-3.5F, -2.5F, -6.5F, 7.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(34, 85).addBox(-2.5F, -1.5F, -17.5F, 5.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(57, 19).addBox(-1.5F, -2.5F, -14.5F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(49, 37).addBox(0.0F, -3.5F, -16.5F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(102, 93).addBox(-2.5F, 2.5F, -17.5F, 5.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.5F, -4.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(53, 49).addBox(-3.5F, -0.5F, -1.0F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -1.0F, -5.5F));

		PartDefinition upperlips = head.addOrReplaceChild("upperlips", CubeListBuilder.create().texOffs(0, 104).addBox(-2.5F, 0.0F, -5.5F, 5.0F, 2.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 2.5F, -12.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(104, 47).addBox(-2.5F, 2.0F, -18.0F, 5.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(102, 68).addBox(-2.5F, 0.0F, -18.0F, 5.0F, 2.0F, 11.0F, new CubeDeformation(-0.01F))
		.texOffs(86, 19).addBox(-3.5F, 0.0F, -7.0F, 7.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 0.5F));

		PartDefinition pharnyx = jaw.addOrReplaceChild("pharnyx", CubeListBuilder.create().texOffs(91, 0).addBox(-3.5F, -2.0F, -7.0F, 7.0F, 4.0F, 9.0F, new CubeDeformation(-0.01F))
		.texOffs(25, 100).addBox(-3.5F, -2.0F, -7.0F, 7.0F, 4.0F, 9.0F, new CubeDeformation(-0.02F))
		.texOffs(0, 0).addBox(-3.5F, -2.0F, -4.0F, 7.0F, 4.0F, 6.0F, new CubeDeformation(-0.03F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition lowerlips = jaw.addOrReplaceChild("lowerlips", CubeListBuilder.create().texOffs(55, 93).addBox(-2.5F, -2.0F, -5.5F, 5.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -12.5F));

		PartDefinition bulb = neck.addOrReplaceChild("bulb", CubeListBuilder.create().texOffs(93, 47).addBox(-3.0F, -3.5F, -2.0F, 6.0F, 7.0F, 4.0F, new CubeDeformation(-0.6F)), PartPose.offset(0.0F, -4.5F, -7.5F));

		PartDefinition armleft = body.addOrReplaceChild("armleft", CubeListBuilder.create().texOffs(57, 0).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(17, 10).addBox(-1.0F, 5.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 8.0F, -18.5F));

		PartDefinition armright = body.addOrReplaceChild("armright", CubeListBuilder.create().texOffs(57, 9).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(17, 19).addBox(-1.0F, 5.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 8.0F, -18.5F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(32, 106).addBox(-4.0F, -2.5F, -0.5F, 8.0F, 11.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.5F, 7.5F));

		PartDefinition tailend = tail.addOrReplaceChild("tailend", CubeListBuilder.create().texOffs(34, 55).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 24.5F));

		PartDefinition saddle = body.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(128, -1).addBox(-5.0F, -4.0F, 6.0F, 10.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(130, 12).addBox(-3.0F, -3.0F, -6.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -10.0F));

		PartDefinition legleft = Lythronax.addOrReplaceChild("legleft", CubeListBuilder.create().texOffs(0, 79).addBox(-3.0F, -3.0F, -5.5F, 6.0F, 14.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 2.0F, 0.5F));

		PartDefinition kneeleft = legleft.addOrReplaceChild("kneeleft", CubeListBuilder.create().texOffs(0, 47).addBox(-0.7835F, 0.25F, -3.125F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.2165F, 10.75F, 4.625F));

		PartDefinition cube_r1 = kneeleft.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7835F, 9.25F, 0.875F, 0.0F, 0.5236F, 0.0F));

		PartDefinition footleft = kneeleft.addOrReplaceChild("footleft", CubeListBuilder.create().texOffs(0, 10).addBox(-2.5F, -1.0F, -5.75F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(64, 6).addBox(-2.5F, -1.0F, -8.75F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(50, 61).addBox(-0.5F, -1.0F, -8.75F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(13, 61).addBox(1.5F, -1.0F, -8.75F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.2165F, 13.25F, -1.375F));

		PartDefinition legright = Lythronax.addOrReplaceChild("legright", CubeListBuilder.create().texOffs(79, 79).addBox(-3.0F, -3.0F, -5.5F, 6.0F, 14.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 2.0F, 0.5F));

		PartDefinition kneeright = legright.addOrReplaceChild("kneeright", CubeListBuilder.create().texOffs(37, 47).addBox(-3.2165F, 0.25F, -2.625F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.2165F, 10.75F, 4.125F));

		PartDefinition cube_r2 = kneeright.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(2, 0).addBox(0.0F, -2.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7835F, 9.25F, 0.375F, 0.0F, -0.5236F, 0.0F));

		PartDefinition footright = kneeright.addOrReplaceChild("footright", CubeListBuilder.create().texOffs(0, 19).addBox(-2.5F, -1.0F, -5.75F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(66, 52).addBox(1.5F, -1.0F, -8.75F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(67, 11).addBox(-0.5F, -1.0F, -8.75F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(67, 0).addBox(-2.5F, -1.0F, -8.75F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.2165F, 13.25F, -0.875F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Lythronax.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(LythronaxEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.Lythronax.getAllParts().forEach(ModelPart::resetPose);
		applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
		if (entity.isSprinting())
			this.animateWalk(LythronaxAnimations.LYTHRONAX_SPRINT, limbSwing, limbSwingAmount, 4f, 4.5f);
		else
		if (entity.getOrder()==3)
			this.animate(entity.sitAnimation, LythronaxAnimations.LYTHRONAX_SIT, ageInTicks, 1.0F);
		else
			if (entity.isSleeping())
			this.animate(entity.sleepAnimation, LythronaxAnimations.LYTHRONAX_SLEEP, ageInTicks, 1.0F);
		else
			this.animateWalk(LythronaxAnimations.LYTHRONAX_WALK, limbSwing, limbSwingAmount, 4f, 4.5f);
		this.animateWalk(LythronaxAnimations.LYTHRONAX_COMMUNICATION, limbSwing, limbSwingAmount, 4f, 4.5f);
		this.animate(entity.idleAnimation, LythronaxAnimations.LYTHRONAX_IDLE, ageInTicks, 1.0F);
	}

	private void applyHeadRotation(LythronaxEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.Lythronax.getChild("body").yRot = pNetHeadYaw * 0.017453292F;
		this.Lythronax.getChild("body").xRot = pHeadPitch * 0.017453292F;
	}
	@Override
	public ModelPart root() {return Lythronax; }
}