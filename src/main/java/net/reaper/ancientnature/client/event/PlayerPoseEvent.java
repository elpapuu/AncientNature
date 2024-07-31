package net.reaper.ancientnature.client.event;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
@OnlyIn(Dist.CLIENT)
public class PlayerPoseEvent<T extends LivingEntity> extends Event {
    private final T entity;
    private final HumanoidModel<T> humanoidModel;

    public PlayerPoseEvent(T pEntity, HumanoidModel<T> pHumanoidModel) {
        this.entity = pEntity;
        this.humanoidModel = pHumanoidModel;
    }

    public T getEntity() {
        return this.entity;
    }

    public HumanoidModel<T> getHumanoidModel() {
        return this.humanoidModel;
    }
}