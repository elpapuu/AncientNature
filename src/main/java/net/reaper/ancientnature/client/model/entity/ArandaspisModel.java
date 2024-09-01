package net.reaper.ancientnature.client.model.entity;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.animations.entity.ArandaspisAnimation;
import net.reaper.ancientnature.common.entity.water.Arandaspis;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ArandaspisModel extends HierarchicalModel<Arandaspis> {

	public static final ModelLayerLocation ARANDASPIS_LAYER = new ModelLayerLocation(AncientNature.modLoc("arandaspis_layer"), "main");
	private final ModelPart body, root;

	public ArandaspisModel(@NotNull ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -3.5F, 5.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, -2.5F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(10, 11).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.5F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 4).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 3.5F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Arandaspis entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
		if (entity.isPanicking())
			this.animateWalk(ArandaspisAnimation.SWIM, limbSwing, limbSwingAmount, 5f, 4.5f);
		else
			this.animateWalk(ArandaspisAnimation.SWIM, limbSwing, limbSwingAmount, 4f, 4.5f);
		this.animate(entity.idleAnimation, ArandaspisAnimation.IDLE, ageInTicks);
		this.animate(entity.flopAnimation, ArandaspisAnimation.FLOP, ageInTicks, 1.5f);

	}

	private void applyHeadRotation(Arandaspis pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.body.yRot = pNetHeadYaw * 0.017453292F;
		this.body.xRot = pHeadPitch * 0.017453292F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}