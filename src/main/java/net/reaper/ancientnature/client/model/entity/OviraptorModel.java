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
import net.reaper.ancientnature.client.animations.entity.TuataraAnimation;
import net.reaper.ancientnature.common.entity.ground.OviraptorEntity;
import net.reaper.ancientnature.common.entity.ground.TuataraEntity;
import net.reaper.ancientnature.common.entity.water.Anomalocris;

public class OviraptorModel extends HierarchicalModel<OviraptorEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation OviraptorLayer = new ModelLayerLocation(new ResourceLocation(
            AncientNature.MOD_ID, "oviraptor"), "main");

    private final ModelPart body;

    public OviraptorModel(ModelPart root) {
        this.body = root.getChild("oviraptor");

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition oviraptor = partdefinition.addOrReplaceChild("oviraptor", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leg2 = oviraptor.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 36).addBox(-1.5F, -1.0F, -2.5F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -11.0F, 2.5F));

        PartDefinition knee2 = leg2.addOrReplaceChild("knee2", CubeListBuilder.create().texOffs(2, 24).addBox(-0.5F, 0.0F, -0.125F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 2.625F));

        PartDefinition cube_r1 = knee2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 3.0F, -0.125F, 0.0F, 0.5236F, 0.0F));

        PartDefinition foot2 = knee2.addOrReplaceChild("foot2", CubeListBuilder.create().texOffs(0, 22).addBox(1.0F, 0.0F, -2.875F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 12).addBox(-1.0F, 0.0F, -1.875F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(-2.0F, 0.0F, -2.875F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(9, 0).addBox(-0.5F, 0.0F, -4.875F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.977F, -0.25F));

        PartDefinition leg = oviraptor.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(37, 34).addBox(-1.5F, -1.0F, -2.5F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -11.0F, 2.5F));

        PartDefinition knee = leg.addOrReplaceChild("knee", CubeListBuilder.create().texOffs(0, 22).addBox(-0.5F, 0.0F, -0.125F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 2.625F));

        PartDefinition cube_r2 = knee.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 3.0F, -0.125F, 0.0F, -0.5236F, 0.0F));

        PartDefinition foot = knee.addOrReplaceChild("foot", CubeListBuilder.create().texOffs(22, 5).addBox(-1.0F, 0.0F, -1.875F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(1.0F, 0.0F, -2.875F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(7, 0).addBox(-0.5F, 0.0F, -4.875F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(22, 7).addBox(-2.0F, 0.0F, -2.875F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.977F, -0.25F));

        PartDefinition body = oviraptor.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.75F, -9.25F, 5.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.25F, 1.25F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(40, 41).addBox(0.0F, -11.5F, 0.5F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(34, 18).addBox(-1.0F, -9.5F, -2.5F, 2.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.15F, -7.75F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(39, 0).addBox(-1.5F, -1.6667F, -5.8333F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(11, 37).addBox(-1.5F, 1.3333F, -5.8333F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 39).addBox(-0.5F, -4.6667F, -6.8333F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.9333F, -0.6667F));

        PartDefinition skin = head.addOrReplaceChild("skin", CubeListBuilder.create().texOffs(12, 5).addBox(0.0F, -4.0F, -0.5F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.4333F, -2.3333F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -0.25F, -3.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(20, 27).addBox(-1.5F, 0.75F, -5.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5833F, -0.3333F));

        PartDefinition arm = body.addOrReplaceChild("arm", CubeListBuilder.create().texOffs(39, 0).addBox(-0.6667F, -0.8333F, -1.3333F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(-0.6667F, 3.1667F, -4.3333F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(28, 40).addBox(-0.6667F, 2.1667F, -4.3333F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(15, 22).addBox(-0.6667F, 2.1667F, -7.3333F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 22).addBox(-0.6667F, 4.1667F, -7.3333F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(19, 0).addBox(-0.6667F, 3.1667F, -7.3333F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8333F, 3.0833F, -5.9167F, 0.6981F, 0.0F, 0.0F));

        PartDefinition arm2 = body.addOrReplaceChild("arm2", CubeListBuilder.create().texOffs(23, 39).addBox(-0.3333F, -0.8333F, -1.3333F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(42, 14).addBox(-0.3333F, 2.1667F, -4.3333F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(1.6667F, 3.1667F, -4.3333F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 27).addBox(-1.3333F, 3.1667F, -7.3333F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(23, 0).addBox(-1.3333F, 4.1667F, -7.3333F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(19, 22).addBox(-1.3333F, 2.1667F, -7.3333F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8333F, 3.0833F, -5.9167F, 0.6981F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(24, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.25F, 4.75F));

        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(20, 26).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 9.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 22).addBox(-2.5F, 0.0F, -2.0F, 5.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 7.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(OviraptorEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return body;
    }
}