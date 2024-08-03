package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.10.1
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
import net.reaper.ancientnature.client.animations.entity.LythronaxAnimations;
import net.reaper.ancientnature.common.entity.ground.LythronaxEntity;
import org.jetbrains.annotations.NotNull;

public class LythronaxModel extends SmartAnimalModel<LythronaxEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LYTHRONAX_LAYER = new ModelLayerLocation(new ResourceLocation(AncientNature.MOD_ID, "lythronax"), "main");
	private final ModelPart root;
	public ModelPart body;
	public ModelPart neck;
	public ModelPart head;
	public ModelPart tail1;
	public ModelPart tail2;
	private final ModelPart saddle;

	public LythronaxModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("Lythronax").getChild("body");
		this.neck = this.body.getChild("neck");
		this.head = this.neck.getChild("head");
		this.tail1 = this.body.getChild("tail1");
		this.tail2 = this.body.getChild("tail2");
		this.saddle = this.body.getChild("belly").getChild("saddle");
	}



	private ResourceLocation getSaddleTexture(LythronaxEntity pEntity) {
		return AncientNature.modLoc("textures/entity/lythronax/saddle_0.png");
	}


	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Lythronax = partdefinition.addOrReplaceChild("Lythronax", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition leg1 = Lythronax.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 87).addBox(-3.0F, -4.0F, -8.5F, 7.0F, 16.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(150, 205).addBox(-3.0F, -4.0F, -8.5F, 7.0F, 16.0F, 15.0F, new CubeDeformation(0.25F)), PartPose.offset(6.0F, 2.0F, 0.5F));

		PartDefinition kee1 = leg1.addOrReplaceChild("kee1", CubeListBuilder.create().texOffs(2, 0).addBox(-0.7835F, -0.75F, -2.125F, 5.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.2165F, 10.75F, 4.625F));

		PartDefinition kee1_r1 = kee1.addOrReplaceChild("kee1_r1", CubeListBuilder.create().texOffs(26, 17).addBox(-3.0F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7835F, 9.25F, 3.875F, 0.0F, 0.9599F, 0.0F));

		PartDefinition foo1 = kee1.addOrReplaceChild("foo1", CubeListBuilder.create().texOffs(1, 119).addBox(-3.0F, 0.0F, -5.75F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(15, 20).addBox(0.0F, 0.0F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(15, 20).addBox(3.0F, 0.0F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(66, 8).addBox(-3.0F, 0.0F, -9.75F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(74, 8).addBox(0.0F, 0.0F, -9.75F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(66, 8).addBox(3.0F, 0.0F, -9.75F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(15, 20).addBox(-3.0F, 0.0F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.2165F, 10.25F, 1.625F));

		PartDefinition leg2 = Lythronax.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 87).addBox(-4.0F, -4.0F, -9.5F, 7.0F, 16.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(150, 205).addBox(-4.0F, -4.0F, -9.5F, 7.0F, 16.0F, 15.0F, new CubeDeformation(0.25F)), PartPose.offset(-6.0F, 2.0F, 0.5F));

		PartDefinition kee2 = leg2.addOrReplaceChild("kee2", CubeListBuilder.create().texOffs(2, 0).addBox(-4.2165F, -0.75F, -1.625F, 5.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(1.2165F, 10.75F, 4.125F));

		PartDefinition kee2_r1 = kee2.addOrReplaceChild("kee2_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7835F, 9.25F, 4.375F, 0.0F, -0.9599F, 0.0F));

		PartDefinition foot2 = kee2.addOrReplaceChild("foot2", CubeListBuilder.create().texOffs(1, 119).addBox(-4.0F, 0.0F, -5.75F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(2.0F, 0.0F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(-1.0F, 0.0F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(-4.0F, 0.0F, -8.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(74, 8).addBox(-1.0F, 0.0F, -9.75F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(66, 8).addBox(-4.0F, 0.0F, -9.75F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(66, 8).addBox(2.0F, 0.0F, -9.75F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.2165F, 10.25F, 2.125F));

		PartDefinition body = Lythronax.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 1.0F));

		PartDefinition belly = body.addOrReplaceChild("belly", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition belly_r1 = belly.addOrReplaceChild("belly_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -10.0F, -23.0F, 14.0F, 20.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition saddle = belly.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(209, 124).addBox(-9.0F, -4.9128F, 11.9981F, 18.0F, 5.0F, 5.0F, new CubeDeformation(0.25F))
				.texOffs(144, 123).addBox(-7.0F, 0.0F, -15.0F, 14.0F, 21.0F, 36.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -10.0F, -10.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition grass1 = saddle.addOrReplaceChild("grass1", CubeListBuilder.create(), PartPose.offset(9.0F, -0.1F, 18.5F));

		PartDefinition saddle_r1 = grass1.addOrReplaceChild("saddle_r1", CubeListBuilder.create().texOffs(201, 148).addBox(-6.5371F, 0.4987F, -3.6391F, 12.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2245F, 0.2968F, 0.6007F));

		PartDefinition grass2 = saddle.addOrReplaceChild("grass2", CubeListBuilder.create(), PartPose.offset(9.0F, -0.1F, 18.5F));

		PartDefinition saddle_r2 = grass2.addOrReplaceChild("saddle_r2", CubeListBuilder.create().texOffs(157, 183).addBox(-7.5F, -5.2856F, 1.5321F, 14.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, -2.0F, -1.5F, -0.7418F, 0.0F, 0.0F));

		PartDefinition grass3 = saddle.addOrReplaceChild("grass3", CubeListBuilder.create(), PartPose.offset(9.0F, -0.1F, 18.5F));

		PartDefinition saddle_r3 = grass3.addOrReplaceChild("saddle_r3", CubeListBuilder.create().texOffs(201, 148).addBox(-5.4629F, 0.4987F, -3.6391F, 12.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.0F, 0.1F, 0.0F, 0.2245F, -0.2968F, -0.6007F));

		PartDefinition carpet1 = saddle.addOrReplaceChild("carpet1", CubeListBuilder.create(), PartPose.offset(-7.5014F, 8.0074F, 7.0F));

		PartDefinition carpet1_r1 = carpet1.addOrReplaceChild("carpet1_r1", CubeListBuilder.create().texOffs(220, 164).addBox(0.0F, -2.0F, -6.0F, 0.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3986F, 1.8926F, -3.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition carpet2 = saddle.addOrReplaceChild("carpet2", CubeListBuilder.create(), PartPose.offset(0.0F, -0.1401F, -1.962F));

		PartDefinition carpet2_r1 = carpet2.addOrReplaceChild("carpet2_r1", CubeListBuilder.create().texOffs(157, 194).addBox(-7.0F, -4.8F, 0.0F, 14.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.0599F, -0.038F, 0.48F, 0.0F, 0.0F));

		PartDefinition carpet3 = saddle.addOrReplaceChild("carpet3", CubeListBuilder.create(), PartPose.offset(7.4014F, 7.9074F, 7.0F));

		PartDefinition saddle_r4 = carpet3.addOrReplaceChild("saddle_r4", CubeListBuilder.create().texOffs(220, 164).addBox(0.0F, -2.0F, -6.0F, 0.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4986F, 1.9926F, -3.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition flag = saddle.addOrReplaceChild("flag", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.3F, 0.0F, 15.8F, -0.1084F, -0.5877F, -0.0772F));

		PartDefinition fag1 = flag.addOrReplaceChild("fag1", CubeListBuilder.create().texOffs(152, 110).addBox(0.0F, 0.0F, -6.5F, 0.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(1.1022F, -27.0938F, 9.1662F));

		PartDefinition flag2 = fag1.addOrReplaceChild("flag2", CubeListBuilder.create().texOffs(152, 123).addBox(0.0F, 0.0F, -6.5F, 0.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.0F));

		PartDefinition stick = flag.addOrReplaceChild("stick", CubeListBuilder.create().texOffs(167, 180).addBox(0.1022F, -29.0938F, -3.3338F, 2.0F, 2.0F, 21.0F, new CubeDeformation(0.01F))
				.texOffs(248, 137).addBox(0.1022F, -31.0938F, 0.6662F, 2.0F, 31.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(34, 56).addBox(-4.0F, -2.5F, 3.0F, 8.0F, 13.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.5F, 7.5F, -0.0873F, 0.0F, 0.0F));

		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(64, 0).addBox(-2.6822F, -2.7221F, -2.7457F, 4.0F, 8.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, 24.5F));

		PartDefinition arm1 = body.addOrReplaceChild("arm1", CubeListBuilder.create().texOffs(100, 0).addBox(-0.6387F, -1.7574F, -1.5511F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(99, 15).addBox(-0.6387F, 4.2426F, -1.5511F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.6944F, 9.5382F, -20.4129F, 0.0F, 0.0F, 0.1745F));

		PartDefinition arm2 = body.addOrReplaceChild("arm2", CubeListBuilder.create().texOffs(105, 35).addBox(-1.6946F, -1.0608F, -1.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(99, 21).addBox(-1.6946F, 4.9392F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4791F, 9.9544F, -20.5F, 0.0F, 0.0F, -0.1745F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, -19.5F, 0.3927F, 0.0F, 0.0F));

		PartDefinition throat = neck.addOrReplaceChild("throat", CubeListBuilder.create().texOffs(45, 88).addBox(-3.5F, -16.1481F, -4.7716F, 7.0F, 21.0F, 13.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.0F, -1.0F, -5.5F, 0.1396F, 0.0F, 0.0F));

		PartDefinition bulb = throat.addOrReplaceChild("bulb", CubeListBuilder.create().texOffs(100, 44).addBox(-3.0F, -8.0F, -7.5F, 6.0F, 7.0F, 4.0F, new CubeDeformation(-0.6F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(80, 81).addBox(-0.8652F, -1.7062F, -6.1452F, 7.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(21, 61).addBox(0.1348F, 4.2938F, -17.1452F, 5.0F, 2.0F, 11.0F, new CubeDeformation(-0.02F))
				.texOffs(0, 56).addBox(0.1348F, 4.2938F, -17.1452F, 5.0F, 2.0F, 11.0F, new CubeDeformation(0.01F))
				.texOffs(156, 237).addBox(-0.8652F, -1.8062F, -6.1452F, 7.0F, 5.0F, 9.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-2.6348F, -12.8836F, -8.1405F, -0.3927F, 0.0F, 0.0F));

		PartDefinition brow_ridges = head.addOrReplaceChild("brow_ridges", CubeListBuilder.create().texOffs(26, 1).addBox(2.5F, -3.4F, -12.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.01F))
				.texOffs(65, 15).addBox(-1.5F, -1.4F, -18.5F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(106, 75).addBox(0.0F, -2.4F, -20.5F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(26, 9).addBox(-3.5F, -3.4F, -12.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(2.6348F, -0.3062F, 4.3548F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(87, 96).addBox(-2.5F, -0.4F, -21.5F, 5.0F, 5.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offset(2.6348F, -0.3062F, 4.3548F));

		PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(0, 20).addBox(-3.5F, -0.6F, -1.0F, 7.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(2.6348F, -0.1062F, -5.1452F));

		PartDefinition pharnyx = head.addOrReplaceChild("pharnyx", CubeListBuilder.create().texOffs(71, 60).addBox(-3.5F, -0.9F, -10.0F, 7.0F, 6.0F, 8.0F, new CubeDeformation(-0.05F))
				.texOffs(102, 60).addBox(-3.5F, -0.9F, -10.0F, 7.0F, 6.0F, 8.0F, new CubeDeformation(-0.06F)), PartPose.offset(2.6348F, 3.1938F, 4.8548F));

		PartDefinition pharnyx_r1 = pharnyx.addOrReplaceChild("pharnyx_r1", CubeListBuilder.create().texOffs(65, 1).addBox(-3.5F, -4.2817F, -0.5977F, 7.0F, 6.0F, 0.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, 3.0F, -4.6F, 0.6981F, 0.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 70).addBox(-2.5F, 3.1F, -20.0F, 5.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(110, 90).addBox(-3.5F, 1.1F, -9.0F, 7.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(100, 0).addBox(-2.5F, 1.1F, -20.0F, 5.0F, 2.0F, 11.0F, new CubeDeformation(-0.01F)), PartPose.offset(2.6348F, 2.1938F, 2.8548F));

		PartDefinition harness = head.addOrReplaceChild("harness", CubeListBuilder.create().texOffs(194, 181).addBox(-2.5F, -4.6F, -39.5F, 5.0F, 6.0F, 11.0F, new CubeDeformation(0.05F))
				.texOffs(234, 144).addBox(-3.5F, -1.0F, -32.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(234, 136).addBox(2.5F, -1.0F, -32.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(220, 192).addBox(-1.5F, -5.5F, -36.5F, 3.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(2.6348F, 3.7938F, 22.3548F));

		PartDefinition rains1 = harness.addOrReplaceChild("rains1", CubeListBuilder.create().texOffs(196, 175).addBox(7.6736F, -6.0F, -28.9848F, 0.0F, 24.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.1745F, -0.5236F));

		PartDefinition rains2 = harness.addOrReplaceChild("rains2", CubeListBuilder.create().texOffs(196, 200).addBox(-7.6736F, -6.0F, -29.9848F, 0.0F, 24.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.1745F, 0.5236F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}


	protected void applyHeadRotation(LythronaxEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.body.getChild("neck").yRot = pNetHeadYaw * 0.017453292F;
		this.body.getChild("neck").xRot = pHeadPitch * 0.017453292F;
		this.neck.getChild("head").yRot = pNetHeadYaw * 0.017453292F;
		this.neck.getChild("head").xRot = pHeadPitch * 0.03F;
	}


	@Override
	protected void dynamicTail(@NotNull LythronaxEntity pEntity) {
		float targetYaw = pEntity.prevTailRot + (pEntity.tailRot - pEntity.prevTailRot) * Minecraft.getInstance().getPartialTick();
		this.tail1.yRot = Mth.lerp(0.05F, this.tail1.yRot, targetYaw);
		this.tail2.yRot = Mth.lerp(0.07F, this.tail2.yRot, targetYaw);
	}

	@Override
	public ModelPart root() {
		return root;
	}


	@Override
	public AnimationDefinition getWalkAnim() {
		return LythronaxAnimations.WALK;
	}

	@Override
	public AnimationDefinition getIdleAnim() {
		return LythronaxAnimations.IDLE;
	}

	@Override
	public AnimationDefinition getSitAnim() {
		return LythronaxAnimations.REST;
	}

	@Override
	public AnimationDefinition getSleepAnim() {
		return LythronaxAnimations.SLEEP;
	}

	@Override
	public AnimationDefinition getRunAnim() {
		return LythronaxAnimations.RUN;
	}

	@Override
	public AnimationDefinition getEatAnim() {
		return LythronaxAnimations.EAT;
	}

	@Override
	public AnimationDefinition getAttackAnim() {
		return LythronaxAnimations.ATTACK;
	}

	@Override
	public AnimationDefinition getDownAnim() {
		return LythronaxAnimations.DOWN;
	}

	@Override
	public AnimationDefinition getFallAsleepAnim() {
		return LythronaxAnimations.FALL_ASLEEP;
	}

	@Override
	public AnimationDefinition getWakeUpAnim() {
		return LythronaxAnimations.WAKE_UP;
	}

	@Override
	public AnimationDefinition getUpAnim() {
		return LythronaxAnimations.UP;
	}
}