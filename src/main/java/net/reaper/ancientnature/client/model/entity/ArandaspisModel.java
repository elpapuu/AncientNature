package net.reaper.ancientnature.client.model.entity;


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
import net.reaper.ancientnature.common.entity.water.ArandaspisEntity;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ArandaspisModel extends HierarchicalModel<ArandaspisEntity> {

    public static final ModelLayerLocation ARANDASPIS_LAYER = new ModelLayerLocation(AncientNature.modLoc("arandaspis_layer"), "main");
    private final ModelPart body, root;

    public ArandaspisModel(@NotNull ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(23, 3).addBox(-3.0F, -3.5F, -5.5F, 6.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -1.5F, -5.5F, 6.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-3.0F, 1.5F, -5.5F, 6.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.5F, -1.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(26, 19).addBox(-1.0F, -2.5F, 0.0F, 2.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.5F));

        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 17).addBox(0.0F, -3.5F, -1.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(ArandaspisEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
        if (entity.isPanicing())
            this.animateWalk(ArandaspisAnimation.ARANDASPIS_RUN, limbSwing, limbSwingAmount, 4f, 4.5f);
        else
            this.animateWalk(ArandaspisAnimation.ARANDASPIS_SWIM, limbSwing, limbSwingAmount, 4f, 4.5f);
        this.animate(entity.idleAnimation, ArandaspisAnimation.ARANDASPIS_IDLE, ageInTicks);
        this.animate(entity.flopAnimation, ArandaspisAnimation.ARANDASPIS_FLOP, ageInTicks, 1.5f);

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
        return root;
    }

}