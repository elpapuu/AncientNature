package net.reaper.ancientnature.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;

@Cancelable
@OnlyIn(Dist.CLIENT)
public class ModelRotationEvent<T extends LivingEntity> extends Event {
    private final T entity;
    private final EntityModel<T> entityModel;
    private final PoseStack matrixStack;

    public ModelRotationEvent(T pEntity, EntityModel<T> pEntityModel, PoseStack pMatrixStack) {
        this.entity = pEntity;
        this.entityModel = pEntityModel;
        this.matrixStack = pMatrixStack;
    }

    public T getEntity() {
        return this.entity;
    }

    public EntityModel<T> getEntityModel() {
        return this.entityModel;
    }

    public PoseStack getMatrixStack() {
        return this.matrixStack;
    }
}
