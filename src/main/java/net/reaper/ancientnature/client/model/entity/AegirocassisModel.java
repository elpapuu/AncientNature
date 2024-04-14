package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.reaper.ancientnature.AncientNature;

public abstract class AegirocassisModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(AncientNature.modLoc( "aegirocassis"), "main");
	private final ModelPart body;

	public AegirocassisModel(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -4.0F, -19.2857F, 10.0F, 2.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(35, 24).addBox(-5.0F, 0.0F, -7.2857F, 10.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 24).addBox(-4.0F, -3.0F, 0.7143F, 8.0F, 8.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(0, 51).addBox(-5.0F, -2.0F, -13.2857F, 10.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(42, 0).addBox(-4.0F, -2.0F, -6.2857F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, -5.7143F));

		PartDefinition mouth = body.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 7).addBox(-4.0F, -0.6667F, -5.6667F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(3.0F, -0.6667F, -5.6667F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(50, 47).addBox(-4.0F, 0.3333F, -5.6667F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.6667F, -6.619F));

		PartDefinition eye = body.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 0.0F, 1.2143F));

		PartDefinition eye2 = body.addOrReplaceChild("eye2", CubeListBuilder.create().texOffs(8, 7).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.0F, 1.2143F));

		PartDefinition fin = body.addOrReplaceChild("fin", CubeListBuilder.create().texOffs(6, 30).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -2.0F, 3.7143F));

		PartDefinition fin2 = body.addOrReplaceChild("fin2", CubeListBuilder.create().texOffs(0, 30).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -2.0F, 6.7143F));

		PartDefinition fin3 = body.addOrReplaceChild("fin3", CubeListBuilder.create().texOffs(6, 28).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -2.0F, 9.7143F));

		PartDefinition fin4 = body.addOrReplaceChild("fin4", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -2.0F, 12.7143F));

		PartDefinition fin5 = body.addOrReplaceChild("fin5", CubeListBuilder.create().texOffs(6, 26).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -2.0F, 15.7143F));

		PartDefinition fin6 = body.addOrReplaceChild("fin6", CubeListBuilder.create().texOffs(0, 26).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -2.0F, 3.7143F));

		PartDefinition fin7 = body.addOrReplaceChild("fin7", CubeListBuilder.create().texOffs(6, 24).addBox(-2.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -2.0F, 6.7143F));

		PartDefinition fin8 = body.addOrReplaceChild("fin8", CubeListBuilder.create().texOffs(0, 24).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -2.0F, 9.7143F));

		PartDefinition fin9 = body.addOrReplaceChild("fin9", CubeListBuilder.create().texOffs(12, 20).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -2.0F, 12.7143F));

		PartDefinition fin10 = body.addOrReplaceChild("fin10", CubeListBuilder.create().texOffs(6, 20).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -2.0F, 15.7143F));

		PartDefinition fin11 = body.addOrReplaceChild("fin11", CubeListBuilder.create().texOffs(0, 20).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 3.0F, 15.7143F));

		PartDefinition fin12 = body.addOrReplaceChild("fin12", CubeListBuilder.create().texOffs(12, 18).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 3.0F, 12.7143F));

		PartDefinition fin13 = body.addOrReplaceChild("fin13", CubeListBuilder.create().texOffs(6, 18).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 3.0F, 9.7143F));

		PartDefinition fin14 = body.addOrReplaceChild("fin14", CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 3.0F, 6.7143F));

		PartDefinition fin15 = body.addOrReplaceChild("fin15", CubeListBuilder.create().texOffs(12, 16).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 3.0F, 3.7143F));

		PartDefinition fin16 = body.addOrReplaceChild("fin16", CubeListBuilder.create().texOffs(6, 16).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 3.0F, 3.7143F));

		PartDefinition fin17 = body.addOrReplaceChild("fin17", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 3.0F, 6.7143F));

		PartDefinition fin18 = body.addOrReplaceChild("fin18", CubeListBuilder.create().texOffs(12, 14).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 3.0F, 9.7143F));

		PartDefinition fin19 = body.addOrReplaceChild("fin19", CubeListBuilder.create().texOffs(6, 14).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 3.0F, 12.7143F));

		PartDefinition fin20 = body.addOrReplaceChild("fin20", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 3.0F, 15.7143F));

		PartDefinition fin21 = body.addOrReplaceChild("fin21", CubeListBuilder.create().texOffs(13, 7).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 3.0F, 18.7143F));

		PartDefinition fin22 = body.addOrReplaceChild("fin22", CubeListBuilder.create().texOffs(13, 0).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -2.0F, 18.7143F));

		PartDefinition fin23 = body.addOrReplaceChild("fin23", CubeListBuilder.create().texOffs(12, 12).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -2.0F, 18.7143F));

		PartDefinition fin24 = body.addOrReplaceChild("fin24", CubeListBuilder.create().texOffs(12, 5).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 3.0F, 18.7143F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}