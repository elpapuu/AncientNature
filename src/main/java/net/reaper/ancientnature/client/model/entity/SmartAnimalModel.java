package net.reaper.ancientnature.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.entity.ground.DodoEntity;
import net.reaper.ancientnature.common.entity.ground.LythronaxEntity;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.html.parser.Entity;

public abstract class SmartAnimalModel<T extends SmartAnimatedAnimal> extends HierarchicalModel<T> {

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }


    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        applyHeadRotation(pEntity, pNetHeadYaw, pLimbSwingAmount, pAgeInTicks);
        dynamicTail(pEntity);

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
        if(getAttackAnim()!=null)
            this.animate(pEntity.attackAnimation, getAttackAnim(), pAgeInTicks, 1);
        if(getDownAnim()!=null)
            this.animate(pEntity.downAnimation, getDownAnim(), pAgeInTicks, 1);
        if(getFallAsleepAnim()!=null)
            this.animate(pEntity.fallAlseepAnimation, getFallAsleepAnim(), pAgeInTicks, 1);
        if(getWakeUpAnim()!=null)
            this.animate(pEntity.wakeUpAnimation, getWakeUpAnim(), pAgeInTicks, 1);
        if(getUpAnim()!=null)
            this.animate(pEntity.upAnimation, getUpAnim(), pAgeInTicks, 1);
    }


    protected abstract void dynamicTail(T pEntity);

    public abstract AnimationDefinition getWalkAnim();
    public abstract AnimationDefinition getIdleAnim();
    public abstract AnimationDefinition getSitAnim();
    public abstract AnimationDefinition getSleepAnim();
    public abstract AnimationDefinition getRunAnim();
    public abstract AnimationDefinition getEatAnim();
    public abstract AnimationDefinition getAttackAnim();
    public abstract AnimationDefinition getDownAnim();
    public abstract AnimationDefinition getFallAsleepAnim();
    public abstract AnimationDefinition getWakeUpAnim();
    public abstract AnimationDefinition getUpAnim();


    protected abstract void applyHeadRotation(T pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks);

    public ResourceLocation getTexture(T pEntity) {

        String name = ForgeRegistries.ENTITY_TYPES.getKey(pEntity.getType()).getPath();

        String path = "textures/entity/" + name
                + "/"+ name
                +(pEntity.isMale()?"_male":"_female")
                +".png";

        return AncientNature.modLoc(path);
    }

    public float shadowRadius() {
        return 1f;
    }
}
