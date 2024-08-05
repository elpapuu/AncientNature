package net.reaper.ancientnature.client.model.entity.lythronax;

import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.animations.entity.lythronax.LythronaxBabyAnimations;
import net.reaper.ancientnature.client.model.entity.SmartAnimalModel;
import net.reaper.ancientnature.common.entity.ground.LythronaxEntity;
import org.jetbrains.annotations.NotNull;

public class LythronaxBabyModel extends SmartAnimalModel<LythronaxEntity> {
    private final ModelPart root;
    private final ModelPart lythronax;
    private final ModelPart body;
    private final ModelPart tail1;
    private final ModelPart tail2;

    public LythronaxBabyModel(ModelPart root) {
        this.root = root.getChild("root");
        this.lythronax = this.root.getChild("lythronax");
        this.body = lythronax.getChild("body");
        this.tail1 = body.getChild("tail1");
        this.tail2 = tail1.getChild("tail2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition lythronax = root.addOrReplaceChild("lythronax", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition body = lythronax.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -6.0F, 5.0F, 8.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 0.0F));
        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 19).addBox(-2.0F, -8.0F, -4.0F, 3.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -4.0F, 0.3927F, 0.0F, 0.0F));
        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(29, 14).addBox(-2.5F, -3.0F, -5.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 5).addBox(-2.5F, -4.0F, -5.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 5).addBox(1.5F, -4.0F, -5.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(31, 30).addBox(-2.0F, -3.0F, -10.0F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(12, 19).addBox(-1.5F, 1.0F, -10.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -2.0F, -0.3927F, 0.0F, 0.0F));
        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(14, 37).addBox(-2.5F, -0.5F, -1.5F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -1.5F, -3.5F));
        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(12, 19).addBox(-1.5F, 0.0F, -10.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(13, 30).addBox(-2.5F, 0.0F, -5.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(33, 0).addBox(-2.5F, -1.75F, -4.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)).texOffs(29, 22).addBox(-2.0F, 1.0F, -10.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(21, 0).addBox(-2.0F, -2.0F, 0.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 5.0F));
        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(18, 19).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 6.0F));
        PartDefinition arm1 = body.addOrReplaceChild("arm1", CubeListBuilder.create().texOffs(6, 0).addBox(-1.0F, 3.0F, -0.5F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(6, 0).addBox(-1.0F, 3.0F, 0.5F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 19).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 3.0F, -4.0F, 0.0F, 0.0F, 0.3927F));
        PartDefinition arm2 = body.addOrReplaceChild("arm2", CubeListBuilder.create().texOffs(6, 0).mirror().addBox(-1.0F, 3.0F, -0.5F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(6, 0).mirror().addBox(-1.0F, 3.0F, 0.5F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 19).mirror().addBox(0.0F, -1.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 3.0F, -4.0F, 0.0F, 0.0F, -0.3927F));
        PartDefinition leg1 = lythronax.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 33).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(35, 7).addBox(-1.5F, 9.0F, -1.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-1.0F, 4.0F, 1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -10.0F, 2.0F));
        PartDefinition leg2 = lythronax.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 33).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(35, 7).addBox(-1.5F, 9.0F, -1.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-1.0F, 4.0F, 1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -10.0F, 2.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    protected void dynamicTail(@NotNull LythronaxEntity pEntity) {
        float targetYaw = pEntity.prevTailRot + (pEntity.tailRot - pEntity.prevTailRot) * Minecraft.getInstance().getPartialTick();
        this.tail1.yRot = Mth.lerp(0.05F, this.tail1.yRot, targetYaw);
        this.tail2.yRot = Mth.lerp(0.07F, this.tail2.yRot, targetYaw);
    }

    @Override
    public AnimationDefinition getWalkAnim() {
        return LythronaxBabyAnimations.WALK;
    }

    @Override
    public AnimationDefinition getIdleAnim() {
        return LythronaxBabyAnimations.IDLE;
    }

    @Override
    public AnimationDefinition getSitAnim() {
        return LythronaxBabyAnimations.REST;
    }

    @Override
    public AnimationDefinition getSleepAnim() {
        return LythronaxBabyAnimations.SLEEP;
    }

    @Override
    public AnimationDefinition getRunAnim() {
        return LythronaxBabyAnimations.SPRINT;
    }

    @Override
    public AnimationDefinition getEatAnim() {
        return LythronaxBabyAnimations.EAT;
    }

    @Override
    public AnimationDefinition getAttackAnim() {
        return LythronaxBabyAnimations.ATTACK;
    }

    @Override
    public AnimationDefinition getDownAnim() {
        return LythronaxBabyAnimations.DOWN;
    }

    @Override
    public AnimationDefinition getFallAsleepAnim() {
        return LythronaxBabyAnimations.FALL_ASLEEP;
    }

    @Override
    public AnimationDefinition getWakeUpAnim() {
        return LythronaxBabyAnimations.WAKE_UP;
    }

    @Override
    public AnimationDefinition getUpAnim() {
        return LythronaxBabyAnimations.UP;
    }

    @Override
    public AnimationDefinition getRoarAnim() {
        return LythronaxBabyAnimations.ROAR;
    }

    @Override
    protected void applyHeadRotation(LythronaxEntity pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {

    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}