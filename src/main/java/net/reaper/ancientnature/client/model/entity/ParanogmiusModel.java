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
import net.reaper.ancientnature.common.entity.water.Paranogmius;

public class ParanogmiusModel extends HierarchicalModel<Paranogmius> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation PARANOGMIUS_LAYER = new ModelLayerLocation(new ResourceLocation("modid", "paranogmius"), "main");
	private final ModelPart paranogmius;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart mouth;
	private final ModelPart jaw;
	private final ModelPart dorsal;
	private final ModelPart finleft;
	private final ModelPart finright;
	private final ModelPart tail;
	private final ModelPart pelvileft;
	private final ModelPart pelvicright;
	private final ModelPart anal;
	private final ModelPart endtail;
	private final ModelPart tailfin;
    private Paranogmius entity;
    private float limbSwing;
    private float limbSwingAmount;
    private float ageInTicks;
    private float netHeadYaw;
    private float headPitch;


    public ParanogmiusModel(ModelPart root) {
		this.paranogmius = root.getChild("paranogmius");
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.mouth = root.getChild("mouth");
		this.jaw = root.getChild("jaw");
		this.dorsal = root.getChild("dorsal");
		this.finleft = root.getChild("finleft");
		this.finright = root.getChild("finright");
		this.tail = root.getChild("tail");
		this.pelvileft = root.getChild("pelvileft");
		this.pelvicright = root.getChild("pelvicright");
		this.anal = root.getChild("anal");
		this.endtail = root.getChild("endtail");
		this.tailfin = root.getChild("tailfin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition paranogmius = partdefinition.addOrReplaceChild("paranogmius", CubeListBuilder.create(), PartPose.offset(0.0F, 10.5F, -1.25F));

		PartDefinition body = paranogmius.addOrReplaceChild("body", CubeListBuilder.create().texOffs(41, 26).addBox(-3.0F, -8.0F, -8.5F, 6.0F, 16.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 8.75F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(65, 8).addBox(-3.0F, -7.0F, -2.0F, 6.0F, 10.0F, 7.0F, new CubeDeformation(-0.01F))
		.texOffs(50, 0).addBox(-2.0F, 1.0F, 5.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 1.0F, 8.5F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(46, 21).addBox(-2.0F, -0.25F, -3.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.02F))
		.texOffs(70, 25).addBox(-3.0F, -1.25F, -10.0F, 6.0F, 4.0F, 7.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 2.25F, 8.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(64, 71).addBox(-3.0F, -0.75F, 0.5F, 6.0F, 3.0F, 7.0F, new CubeDeformation(-0.01F))
		.texOffs(0, 76).addBox(-3.0F, -1.75F, 0.5F, 6.0F, 1.0F, 7.0F, new CubeDeformation(-0.01F))
		.texOffs(0, 48).addBox(-2.0F, -0.75F, 7.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 3.75F, -2.5F));

		PartDefinition dorsal = body.addOrReplaceChild("dorsal", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -14.0F, -26.5F, 0.0F, 14.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 1.0F));

		PartDefinition finleft = body.addOrReplaceChild("finleft", CubeListBuilder.create().texOffs(16, 26).addBox(-8.0F, -1.5F, 0.0F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 4.5F, 4.5F));

		PartDefinition finright = body.addOrReplaceChild("finright", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -1.5F, 0.0F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 4.5F, 4.5F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 43).addBox(-3.0F, -8.0F, -17.0F, 6.0F, 16.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -8.5F));

		PartDefinition pelvileft = tail.addOrReplaceChild("pelvileft", CubeListBuilder.create().texOffs(6, 40).addBox(0.0F, -0.5F, -1.5F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 8.5F, 4.5F));

		PartDefinition pelvicright = tail.addOrReplaceChild("pelvicright", CubeListBuilder.create().texOffs(0, 40).addBox(0.0F, -0.5F, -1.5F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 8.5F, 4.5F));

		PartDefinition anal = tail.addOrReplaceChild("anal", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -0.5F, -17.5F, 0.0F, 7.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.5F, -0.5F));

		PartDefinition endtail = tail.addOrReplaceChild("endtail", CubeListBuilder.create().texOffs(64, 59).addBox(-2.0F, -3.0F, -6.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -17.0F));

		PartDefinition tailfin = endtail.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(46, 50).addBox(0.0F, -10.5F, -8.5F, 0.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -5.5F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		paranogmius.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}


	@Override
	public void setupAnim(Paranogmius paranogmius, float v, float v1, float v2, float v3, float v4) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

	}
	@Override
	public ModelPart root() {
		return paranogmius;
	}
}