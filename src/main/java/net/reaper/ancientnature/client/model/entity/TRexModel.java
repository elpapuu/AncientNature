package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.reaper.ancientnature.client.animations.entity.TRexAnimations;
import net.reaper.ancientnature.client.animations.entity.TRexAnimations;
import net.reaper.ancientnature.common.entity.ground.DodoEntity;
import net.reaper.ancientnature.common.entity.ground.TRexEntity;

public class TRexModel<T extends TRexEntity> extends HierarchicalModel<T> {
	
	private final ModelPart tyrannosaur;
	private final ModelPart head;
	private final ModelPart tail1;
	private final ModelPart tail2;
	private final ModelPart tail3;

	public TRexModel(ModelPart root) {
		this.tyrannosaur = root.getChild("tyrannosaur");
        this.head = this.tyrannosaur.getChild("body").getChild("neck").getChild("head");
        this.tail1 = this.tyrannosaur.getChild("body").getChild("tail1");
        this.tail2 = this.tyrannosaur.getChild("body").getChild("tail1").getChild("tail2");
        this.tail3 = this.tail2.getChild("tail3");
    }

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tyrannosaur = partdefinition.addOrReplaceChild("tyrannosaur", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, 10.0F));

		PartDefinition leg1 = tyrannosaur.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(92, 100).addBox(-8.0F, -3.0F, -14.0F, 16.0F, 34.0F, 28.0F, new CubeDeformation(0.0F))
		.texOffs(0, 343).addBox(-8.0F, -3.0F, -14.0F, 16.0F, 34.0F, 28.0F, new CubeDeformation(0.01F)), PartPose.offset(-12.0F, -52.0F, -11.0F));

		PartDefinition kee1 = leg1.addOrReplaceChild("kee1", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -6.0F, 10.0F, 20.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 28.0F, 10.0F));

		PartDefinition cube_r1 = kee1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -4.0F, 0.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 18.0F, 6.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition foot1 = kee1.addOrReplaceChild("foot1", CubeListBuilder.create().texOffs(0, 34).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.0F, -1.0F, 0.0F, 0.0873F, 0.0F));

		PartDefinition fingers1 = foot1.addOrReplaceChild("fingers1", CubeListBuilder.create().texOffs(64, 100).addBox(-7.0F, -2.0F, -10.0F, 14.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(56, 114).addBox(-7.0F, 2.0F, -10.0F, 14.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(42, 26).addBox(4.0F, -2.0F, -10.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(42, 26).addBox(-2.0F, -2.0F, -10.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(42, 26).addBox(-4.0F, -2.0F, -10.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(42, 26).addBox(2.0F, -2.0F, -10.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -5.0F));

		PartDefinition leg2 = tyrannosaur.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 343).mirror().addBox(-8.0F, -3.0F, -14.0F, 16.0F, 34.0F, 28.0F, new CubeDeformation(0.01F)).mirror(false)
		.texOffs(92, 100).mirror().addBox(-8.0F, -3.0F, -14.0F, 16.0F, 34.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(12.0F, -52.0F, -11.0F));

		PartDefinition kee2 = leg2.addOrReplaceChild("kee2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-5.0F, 0.0F, -6.0F, 10.0F, 20.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 28.0F, 10.0F));

		PartDefinition cube_r2 = kee2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 1).mirror().addBox(-3.0F, -4.0F, 0.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 18.0F, 6.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition foot2 = kee2.addOrReplaceChild("foot2", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(-7.0F, 0.0F, -7.0F, 14.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 20.0F, -1.0F, 0.0F, -0.0873F, 0.0F));

		PartDefinition fingers2 = foot2.addOrReplaceChild("fingers2", CubeListBuilder.create().texOffs(64, 100).mirror().addBox(-7.0F, -2.0F, -10.0F, 14.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(42, 26).mirror().addBox(4.0F, -2.0F, -10.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(42, 26).mirror().addBox(-2.0F, -2.0F, -10.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(56, 114).addBox(-7.0F, 2.0F, -10.0F, 14.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(42, 26).mirror().addBox(-4.0F, -2.0F, -10.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(42, 26).mirror().addBox(2.0F, -2.0F, -10.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, -5.0F));

		PartDefinition body = tyrannosaur.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -54.0F, -10.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(-2, 117).addBox(-7.0F, -29.0F, 13.0F, 14.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(-2, 117).mirror().addBox(-7.0F, -14.0F, 13.0F, 14.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 156).addBox(0.0F, -27.0F, 13.0F, 0.0F, 21.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -33.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition throat = neck.addOrReplaceChild("throat", CubeListBuilder.create().texOffs(0, 100).addBox(-9.0F, -14.0F, -28.0F, 18.0F, 31.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 13.0F));

		PartDefinition saddle_neck = throat.addOrReplaceChild("saddle_neck", CubeListBuilder.create().texOffs(420, 354).addBox(-9.0F, -70.0F, -43.0F, 18.0F, 31.0F, 28.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 56.0F, 15.0F));

		PartDefinition cube_r3 = saddle_neck.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(448, 382).addBox(-9.0F, 0.0F, -0.25F, 18.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -50.0F, -43.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(184, 191).addBox(-10.5F, -11.0F, -14.0F, 21.0F, 12.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(0, 57).addBox(-10.5F, 1.0F, -14.01F, 21.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(32, 0).addBox(-10.5F, -13.0F, -14.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(37, 55).mirror().addBox(6.5F, -15.0F, -14.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(32, 0).mirror().addBox(6.5F, -13.0F, -14.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(37, 55).addBox(-10.5F, -15.0F, -14.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 44).addBox(-7.0F, -12.0F, -24.0F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 44).mirror().addBox(7.0F, -12.0F, -24.0F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(125, 178).addBox(-7.0F, 4.0F, -37.0F, 14.0F, 5.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(0, 221).addBox(-6.0F, 4.0F, -36.0F, 12.0F, 5.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, -5.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(199, 190).addBox(-10.5F, -0.5F, -0.01F, 21.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.5F, -14.0F));

		PartDefinition pharynx = head.addOrReplaceChild("pharynx", CubeListBuilder.create().texOffs(187, 220).addBox(-10.5F, 0.0F, -2.0F, 21.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -9.0F));

		PartDefinition cube_r4 = pharynx.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(142, 207).addBox(-10.5F, 0.0F, 1.0F, 21.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition saddle_head = head.addOrReplaceChild("saddle_head", CubeListBuilder.create().texOffs(506, 423).addBox(7.5F, -51.0F, -47.5F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(440, 319).addBox(-10.5F, -68.0F, -43.0F, 21.0F, 12.0F, 15.0F, new CubeDeformation(0.25F))
		.texOffs(502, 280).addBox(7.0F, -55.0F, -48.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(506, 418).addBox(-7.5F, -51.0F, -47.5F, 0.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(502, 280).addBox(-8.0F, -55.0F, -48.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(378, 203).addBox(-11.5F, -62.0F, -46.0F, 23.0F, 14.0F, 41.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 57.0F, 29.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(188, 0).addBox(-11.0F, 0.0F, -15.0F, 22.0F, 10.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(74, 162).addBox(-7.0F, 3.0F, -38.0F, 14.0F, 7.0F, 23.0F, new CubeDeformation(-0.01F))
		.texOffs(176, 178).addBox(-5.0F, 10.0F, -38.0F, 10.0F, 2.0F, 9.0F, new CubeDeformation(-0.01F))
		.texOffs(0, 195).addBox(-6.0F, 0.0F, -37.0F, 12.0F, 3.0F, 22.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 1.0F, 1.0F));

		PartDefinition goatee = jaw.addOrReplaceChild("goatee", CubeListBuilder.create().texOffs(243, 35).addBox(-2.0F, 0.0F, -9.0F, 4.0F, 4.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, -10.0F));

		PartDefinition tongue = jaw.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(43, 159).addBox(-6.0F, 0.0F, -20.0F, 12.0F, 0.0F, 20.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, -15.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 159).addBox(-7.0F, -13.0F, -12.0F, 14.0F, 13.0F, 23.0F, new CubeDeformation(0.0F))
		.texOffs(64, 99).addBox(0.0F, -16.0F, -12.0F, 0.0F, 4.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -25.0F));

		PartDefinition saddle_nose = nose.addOrReplaceChild("saddle_nose", CubeListBuilder.create().texOffs(438, 271).addBox(-7.0F, -66.0F, -66.0F, 14.0F, 13.0F, 23.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 53.0F, 54.0F));

		PartDefinition slabber = head.addOrReplaceChild("slabber", CubeListBuilder.create().texOffs(85, 206).addBox(-6.5F, 0.0F, -3.0F, 13.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -18.0F));

		PartDefinition saliva = slabber.addOrReplaceChild("saliva", CubeListBuilder.create().texOffs(84, 212).addBox(0.0F, 0.0F, -0.5F, 0.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.5F, -2.0F, -11.5F));

		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(124, 0).addBox(-9.0F, -10.0F, 0.0F, 18.0F, 26.0F, 28.0F, new CubeDeformation(0.0F))
		.texOffs(130, 392).addBox(-9.0F, -10.0F, 0.0F, 18.0F, 26.0F, 28.0F, new CubeDeformation(0.01F))
		.texOffs(124, 27).addBox(0.0F, -13.0F, 0.0F, 0.0F, 3.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 22.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(148, 130).addBox(-6.0F, -7.0F, 0.0F, 12.0F, 16.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 28.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(156, 68).addBox(-3.0F, -5.0F, 0.0F, 6.0F, 8.0F, 32.0F, new CubeDeformation(0.0F))
		.texOffs(156, 79).addBox(0.0F, -9.0F, 0.0F, 0.0F, 4.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 32.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition arm1 = body.addOrReplaceChild("arm1", CubeListBuilder.create().texOffs(0, 100).addBox(-2.0F, -3.0F, -3.0F, 3.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(44, 9).addBox(-2.0F, 2.0F, 3.0F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(42, 40).addBox(-2.0F, 8.0F, 3.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, 28.0F, -35.0F, -0.7854F, -0.3927F, 0.0F));

		PartDefinition arm2 = body.addOrReplaceChild("arm2", CubeListBuilder.create().texOffs(44, 9).mirror().addBox(-1.0F, 2.0F, 3.0F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(42, 40).mirror().addBox(-1.0F, 8.0F, 3.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 100).mirror().addBox(-1.0F, -3.0F, -3.0F, 3.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(15.0F, 28.0F, -35.0F, -0.7854F, 0.3927F, 0.0F));

		PartDefinition belly = body.addOrReplaceChild("belly", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, 0.0F, -32.0F, 30.0F, 36.0F, 64.0F, new CubeDeformation(0.0F))
		.texOffs(0, 412).addBox(-15.0F, 0.0F, -32.0F, 30.0F, 36.0F, 64.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -6.0F, -10.0F));

		PartDefinition feathers = belly.addOrReplaceChild("feathers", CubeListBuilder.create().texOffs(177, 106).addBox(14.0F, -4.0F, -5.0F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(14, 242).addBox(7.0F, -4.0F, -5.0F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(177, 106).addBox(0.0F, -4.0F, -5.0F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 0.0F, -10.0F));

		PartDefinition saddle = belly.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(472, 442).addBox(3.0F, -63.0F, 4.0F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(472, 442).addBox(-11.0F, -63.0F, 4.0F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(324, 412).addBox(-15.0F, -60.0F, -41.0F, 30.0F, 36.0F, 64.0F, new CubeDeformation(0.25F))
		.texOffs(506, 423).addBox(0.0F, -24.0F, -19.5F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 60.0F, 9.0F));

		PartDefinition cube_r5 = saddle.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(314, 397).addBox(0.25F, 0.0F, -15.0F, 0.0F, 10.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, -51.0F, -7.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition cube_r6 = saddle.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(318, 445).addBox(0.0F, 0.0F, -5.0F, 0.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, -34.0F, -31.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r7 = saddle.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(354, 444).addBox(0.0F, 0.0F, -5.0F, 0.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, -34.0F, -31.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r8 = saddle.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(253, 505).addBox(-15.0F, 0.0F, 0.0F, 30.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -34.0F, -41.25F, -0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r9 = saddle.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(350, 400).addBox(-14.0F, -8.0F, 0.0F, 28.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -60.0F, 6.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r10 = saddle.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(498, 439).addBox(0.0F, -12.0F, 0.0F, 0.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(456, 458).addBox(-14.0F, -12.0F, 0.0F, 28.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -60.0F, 7.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r11 = saddle.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(314, 411).addBox(-0.25F, 0.0F, -15.0F, 0.0F, 10.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, -51.0F, -7.0F, 0.0F, 0.0F, 0.3927F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		applyHeadRotation(pEntity, pNetHeadYaw, pLimbSwingAmount, pAgeInTicks);
		applyTailRot(pLimbSwing,pLimbSwingAmount);

		if(pEntity.walkAnimation.isMoving()&&(!pEntity.isSprinting()||getRunAnim()==null)&&getWalkAnim()!=null)
			this.animateWalk(getWalkAnim(), pLimbSwing, pLimbSwingAmount,2f,4f);
		if (pEntity.isSprinting()&&getRunAnim()!=null)
			this.animateWalk(getRunAnim(), pLimbSwing, pLimbSwingAmount, 4f, 8f);
		if(getIdleAnim()!=null)
			this.animate(pEntity.idleAnimation, getIdleAnim(), pAgeInTicks, 1);
		if(getSitAnim()!=null)
			this.animate(pEntity.sitAnimation, getSitAnim(), pAgeInTicks, 1);
		if(getSleepAnim()!=null)
			this.animate(pEntity.sleepAnimation, getSleepAnim(), pAgeInTicks, 1);
		if(getEatAnim()!=null)
			this.animate(pEntity.eatAnimation, getEatAnim(),pAgeInTicks, 1);
		if(getAttack1Anim()!=null)
			this.animate(pEntity.attackAnimation, getAttack1Anim(), pAgeInTicks, 1);
		if(getAttack2Anim()!=null)
			this.animate(pEntity.attack2Animation, getAttack2Anim(), pAgeInTicks, 1);
		if(getAttack3Anim()!=null)
			this.animate(pEntity.attack3Animation, getAttack3Anim(), pAgeInTicks, 1);
		if(this.getRoarAnim() !=null)
		{
			this.animate(pEntity.roarAnimation, getRoarAnim(), pAgeInTicks,1);
		}
		if(getDownAnim()!=null)
			this.animate(pEntity.downAnimation, getDownAnim(), pAgeInTicks, 1);
		if(getFallAsleepAnim()!=null)
			this.animate(pEntity.fallAlseepAnimation, getFallAsleepAnim(), pAgeInTicks, 1);
		if(getWakeUpAnim()!=null)
			this.animate(pEntity.wakeUpAnimation, getWakeUpAnim(), pAgeInTicks, 1);
		if(getUpAnim()!=null)
			this.animate(pEntity.upAnimation, getUpAnim(), pAgeInTicks, 1);
	}

	
	public AnimationDefinition getWalkAnim() {
		return TRexAnimations.WALK;
	}

	
	public AnimationDefinition getIdleAnim() {
		return TRexAnimations.IDLE;
	}

	
	public AnimationDefinition getSitAnim() {
		return TRexAnimations.REST;
	}

	
	public AnimationDefinition getSleepAnim() {
		return TRexAnimations.SLEEP;
	}

	
	public AnimationDefinition getRunAnim() {
		return TRexAnimations.RUN;
	}

	
	public AnimationDefinition getEatAnim() {
		return TRexAnimations.EAT;
	}

	
	public AnimationDefinition getRoarAnim() {
		return TRexAnimations.ROAR;
	}
	public AnimationDefinition getAttack1Anim() {
		return TRexAnimations.ATTACK1;
	}
	public AnimationDefinition getAttack2Anim() {
		return TRexAnimations.ATTACK2;
	}
	public AnimationDefinition getAttack3Anim() {
		return TRexAnimations.ATTACK3;
	}


	
	public AnimationDefinition getDownAnim() {
		return TRexAnimations.DOWN;
	}

	
	public AnimationDefinition getFallAsleepAnim() {
		return TRexAnimations.FALL_ASLEEP;
	}

	
	public AnimationDefinition getWakeUpAnim() {
		return TRexAnimations.WAKE_UP;
	}

	
	public AnimationDefinition getUpAnim() {
		return TRexAnimations.UP;
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		tyrannosaur.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return tyrannosaur;
	}
	private float calculateTailRotation(float limbSwing, float limbSwingAmount) {
		float frequency = 0.6662F;
		return Mth.sin(limbSwing * frequency) * limbSwingAmount;
	}
	protected void applyHeadRotation(TRexEntity pEntity, float pNetHeadYaw, float pHeadPitch, float ageInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -10.0F, 10.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.tyrannosaur.getChild("body").getChild("neck").yRot = pNetHeadYaw * 0.017453292F;
		this.tyrannosaur.getChild("body").getChild("neck").xRot = pHeadPitch * 0.017453292F;
		this.head.xRot = pHeadPitch * 0.017453292F;
	}
	private void applyTailRot(float limbSwing, float limbSwingAmount)
	{

		float tailRotation = calculateTailRotation(limbSwing, limbSwingAmount);
		this.tail1.yRot = tailRotation * 0.2F;
		this.tail2.yRot = tailRotation * 0.4F;
		this.tail3.yRot = tailRotation * 0.6F;
	}

}