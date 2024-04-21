package net.reaper.ancientnature.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.animations.entity.ArandaspisAnimation;
import net.reaper.ancientnature.common.entity.water.ArandaspisEntity;

public class ArandaspisBabyModel extends HierarchicalModel<ArandaspisEntity> {

    public static final ModelLayerLocation ARANDASPIS_BABY_LAYER = new ModelLayerLocation(AncientNature.modLoc("arandaspis_baby_layer"), "main");
    protected final ModelPart root, body;

    public ArandaspisBabyModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 21.0F, -1.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(10, 12).addBox(-1.0F, -1.035F, -0.8058F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, 2.9544F));

        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 4).addBox(0.0F, -2.0F, -0.5071F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.035F, 2.6942F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }
    @Override
    public void setupAnim(ArandaspisEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
        if (entity.isPanicing())
            this.animateWalk(ArandaspisAnimation.Baby.SPRINT, limbSwing, limbSwingAmount, 4f, 4.5f);
        else
            this.animateWalk(ArandaspisAnimation.Baby.SWIM, limbSwing, limbSwingAmount, 4f, 4.5f);
        this.animate(entity.idleAnimation, ArandaspisAnimation.Baby.IDLE, ageInTicks);
        this.animate(entity.flopAnimation, ArandaspisAnimation.Baby.FLOP, ageInTicks, 1.5f);
    }

    private void applyHeadRotation(ArandaspisEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
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
        return this.root;
    }
}
