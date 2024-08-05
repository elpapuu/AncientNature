package net.reaper.ancientnature.common.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.reaper.ancientnature.common.entity.water.Paranogmius;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class EntityUtils {
    @Nullable
    public static <T extends Entity> List<T> getEntitiesAroundSelf(Class<T> pEntityClass, @Nullable Entity pSelf, float pHorizontalRadius, float pVerticalRadius, boolean pMustSee) {
        if (pSelf == null) {
            return null;
        }
        AABB aabb = AABB.ofSize(pSelf.position(), pHorizontalRadius, pVerticalRadius, pHorizontalRadius);
        List<T> entityList = pSelf.level().getEntitiesOfClass(pEntityClass, aabb, EntitySelector.ENTITY_STILL_ALIVE);
        if (!entityList.isEmpty()) {
            if (pMustSee) {
                for (T entity : entityList) {
                    if (pSelf instanceof LivingEntity living && living.hasLineOfSight(entity)) {
                        return entityList;
                    }
                }
            }
            return entityList;
        }
        return null;
    }

    public static boolean isEntityStepping(LivingEntity pEntity, float pAnimationSpeedFactor, float pScale) {
        float stepOffset = (float) Math.tan(pEntity.walkAnimation.position() * pAnimationSpeedFactor - 0.2F);
        return stepOffset * stepOffset < pScale * pScale;
    }

    public static boolean isEntityMoving(@Nullable Entity pEntity, float pMinChange) {
        if (pEntity == null) {
            return false;
        }
        Vec3 delta = pEntity.getDeltaMovement();
        return !(delta.length() > -pMinChange && delta.length() < pMinChange);
    }

    public static void attackByRider(@Nullable LivingEntity pEntity, float pAttackDistance, float pAttackDamage) {
        if (pEntity != null) {
            LivingEntity passenger = pEntity.getControllingPassenger();
            if (passenger != null) {
                Vec3 startPos = passenger.getEyePosition();
                Vec3 endPos = startPos.add(passenger.getViewVector(2.3F).scale(pAttackDistance * pAttackDistance));
                AABB aabb = new AABB(startPos, endPos).inflate(3.0);
                EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(passenger.level(), passenger, startPos, endPos, aabb, entity -> entity instanceof LivingEntity living && pEntity.canAttack(living), 1.0F);
                if (hitResult != null) {
                    Entity target = hitResult.getEntity();
                    if (target != pEntity) {
                        pEntity.setLastHurtMob(target);
                        target.hurt(pEntity.damageSources().mobAttack(pEntity), pAttackDamage);
                    }
                }
            }
        }
    }

    @Contract("null->false")
    public static boolean isPlayerPassengerOf(@Nullable LivingEntity pEntity) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        return localPlayer != null && pEntity != null && localPlayer.getVehicle() != null && localPlayer.getVehicle() == pEntity;
    }

    public static void smoothVehicleYawToRider(@NotNull Player pRider, @NotNull LivingEntity pVehicle, float pSmoothFactor, float pRotationSpeed) {
        float interpolatedRotation  = Mth.rotLerp(pSmoothFactor, pVehicle.yRotO, pRider.getYHeadRot());
        pVehicle.yRotO = interpolatedRotation ;
        pVehicle.yBodyRot = interpolatedRotation ;
        pVehicle.yHeadRot = interpolatedRotation ;
        pVehicle.setYRot(Mth.rotLerp(pRotationSpeed, pVehicle.getYRot(), pRider.getYRot()));
    }

    public static void removeRider(@NotNull LivingEntity pVehicle, @Nullable Player pRider) {
        if (pRider == null) {
            return;
        }
        Vec3 vec3;
        if (pRider.isRemoved()) {
            vec3 = pRider.position();
        } else if (!pVehicle.isRemoved() && !pVehicle.level().getBlockState(pVehicle.blockPosition()).is(BlockTags.PORTALS)) {
            vec3 = pVehicle.getDismountLocationForPassenger(pVehicle);
        } else {
            vec3 = new Vec3(pRider.getX(), Math.max(pRider.getY(), pVehicle.getY()), pRider.getZ());
        }
        pRider.dismountTo(vec3.x, vec3.y, vec3.z);
    }

    public static boolean canSprintByPlayer(@NotNull LivingEntity pEntity) {
        return Minecraft.getInstance().options.keySprint.isDown() && isPlayerPassengerOf(pEntity);
    }

    public static boolean canAttackByPlayer(@NotNull LivingEntity pEntity) {
        return Minecraft.getInstance().options.keyAttack.isDown() && isPlayerPassengerOf(pEntity);
    }

    public static boolean canRoarByPlayer(@NotNull LivingEntity pEntity) {
        return Minecraft.getInstance().options.keyUse.isDown() && isPlayerPassengerOf(pEntity);
    }

    public static void removeRider(Paranogmius paranogmius, Player pRider) {
        if (pRider.getVehicle() == paranogmius) {
            paranogmius.stopRiding();
        }
    }
}
