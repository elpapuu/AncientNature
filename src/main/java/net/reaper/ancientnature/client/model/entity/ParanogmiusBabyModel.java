package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ParanogmiusBabyModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "paranogmius_baby"), "main");
	private final ModelPart Concavotectum;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart fin1;
	private final ModelPart fin2;
	private final ModelPart tail1;
	private final ModelPart tail2;
	private final ModelPart fin;

	public ParanogmiusBabyModel(ModelPart root) {
		this.Concavotectum = root.getChild("Concavotectum");
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.jaw = root.getChild("jaw");
		this.fin1 = root.getChild("fin1");
		this.fin2 = root.getChild("fin2");
		this.tail1 = root.getChild("tail1");
		this.tail2 = root.getChild("tail2");
		this.fin = root.getChild("fin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Concavotectum = partdefinition.addOrReplaceChild("Concavotectum", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));

		PartDefinition body = Concavotectum.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, -5.0F, -8.0F, 6.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(34, 0).addBox(-1.0F, 4.0F, -4.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(20, 9).addBox(0.0F, -10.0F, -8.0F, 0.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 3.0F, -4.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(28, 6).addBox(2.0F, 3.0F, -5.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(28, 6).mirror().addBox(-2.0F, 3.0F, -5.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(20, 0).addBox(-2.0F, 1.0F, -6.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(32, 32).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -8.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(28, 25).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition fin1 = body.addOrReplaceChild("fin1", CubeListBuilder.create().texOffs(31, 3).addBox(0.0F, -2.0F, 0.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 2.0F, -5.0F, 0.0F, -0.0436F, 0.0F));

		PartDefinition fin2 = body.addOrReplaceChild("fin2", CubeListBuilder.create().texOffs(19, 29).addBox(0.0F, -2.0F, 0.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.0F, -5.0F, 0.0F, 0.0436F, 0.0F));

		PartDefinition tail1 = Concavotectum.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -8.0F, 0.0F, 0.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(20, 14).addBox(0.0F, 4.0F, 0.0F, 0.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-3.0F, -5.0F, 0.0F, 6.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(36, 11).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 8.0F));

		PartDefinition fin = tail2.addOrReplaceChild("fin", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, -5.0F, 0.0F, 0.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Concavotectum.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}