package net.reaper.ancientnature.common.entity.ground;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimalPose;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;
import net.reaper.ancientnature.common.entity.util.AnimalDiet;
import net.reaper.ancientnature.common.entity.util.IMouseInput;
import net.reaper.ancientnature.common.entity.util.IShakeScreenOnStep;
import net.reaper.ancientnature.common.messages.NetworkHandler;
import net.reaper.ancientnature.common.messages.packets.MessageServerEntityEvent;
import net.reaper.ancientnature.common.messages.util.LevelEvents;
import net.reaper.ancientnature.common.messages.util.EventData;
import net.reaper.ancientnature.common.network.packet.*;
import net.reaper.ancientnature.common.util.EntityUtils;
import net.reaper.ancientnature.common.util.ScreenShakeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.utils.Log;

import java.util.List;

public class LythronaxEntity extends SmartAnimatedAnimal implements IShakeScreenOnStep, IMouseInput, Saddleable {
    private static final EntityDataAccessor<Integer> DATA_ROAR_TICKS;
    private static final EntityDataAccessor<Integer> DATA_SPEED_BONUS;
    public float tailRot;
    public float prevTailRot;

    public LythronaxEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 95)
                .add(Attributes.MOVEMENT_SPEED, 0.25d)
                .add(Attributes.FOLLOW_RANGE, 64d)
                .add(Attributes.ATTACK_KNOCKBACK, 1d)
                .add(Attributes.ATTACK_DAMAGE, 3.0);

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_ROAR_TICKS, 0);
        this.getEntityData().define(DATA_SPEED_BONUS, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("RoarTicks", this.getRoarTicks());
        pCompound.putInt("SpeedBonus", this.getSpeedBonusTicks());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setRoarTicks(pCompound.getInt("RoarTicks"));
        this.setSpeedBonusTicks(pCompound.getInt("SpeedBonus"));
    }

    @Override
    public void tick() {
        super.tick();
        this.roarAnimationSetup(this.getRoarTicks() > 0);
        if (EntityUtils.isEntityMoving(this, 0.1F)) {
            if (this.isVehicle()) {
                this.setSprinting(EntityUtils.canSprintByPlayer(this));
            }
        } else {
            this.setSprinting(false);
        }
        if (EntityUtils.canAttackByPlayer(this) && this.attackAnimationTimeout <= 0) {
            NetworkHandler.sendMSGToServer(new EntityAttackKeyPacket(this.getId()));
            this.setSmartPose(SmartAnimalPose.ATTACK);
            this.setPoseTicks(this.getAnimationLengthInTicks(SmartAnimalPose.ATTACK));
        }
        if (this.isVehicle() && this.getSmartPose() == SmartAnimalPose.ATTACK) {
            this.setAttacking(this.getPoseTicks() > 0);
        }
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        List<LythronaxEntity> entityList = EntityUtils.getEntitiesAroundSelf(LythronaxEntity.class, this, 15, 4, false);
        if (entityList != null && localPlayer != null && !entityList.isEmpty()) {
            entityList.stream().filter(entity -> entity.getRoarTicks() == Mth.clamp(entity.getRoarTicks(), 9, 55)).forEach(entity -> {
                if (this.getSpeedBonusTicks() <= 0) {
                    this.setSpeedBonusTicks(220);
                }
                float distanceFactor = getShakeDistance() / entity.distanceTo(localPlayer) / 8.0F;
                float shakePower = Math.min(distanceFactor * 0.7F, 0.6F);
                ScreenShakeUtils.shakeScreen(5, shakePower);
            });
        }
        if (!this.level().isClientSide) {
            Log.info(this.getRoarTicks());
            if (this.getRoarTicks() > 0) {
                this.setRoarTicks(this.getRoarTicks() - 1);
            }
        }
        this.setSpeedBonusTicks(Math.max(this.getSpeedBonusTicks() - 1, 0));
        this.handleScreenShake();
        this.prevTailRot = this.tailRot;
        this.tailRot += (-(this.yBodyRot - this.yBodyRotO) - this.tailRot) * 0.15F;
    }

    @Override
    public void defineDiet(AnimalDiet.Builder pDietBuilder) {
        super.defineDiet(pDietBuilder);
        pDietBuilder.addItems(Items.BEEF, Items.PORKCHOP);
    }

    @Override
    public int getAttackAnimationDamageDelay() {
        return 6;
    }

    @Override
    public int getEatAnimationConsumeDelay() {
        return 20;
    }


    @Override
    protected int getAnimationLengthInTicks(SmartAnimalPose smartPose) {
        return switch (smartPose) {
            case IDLE -> (int) (1.92 * 20.0);
            case WALK -> (int) (1.92 * 20.0);
            case RUN -> (int) (.84 * 20.0);
            case EAT -> (int) (2.84 * 20.0);
            case ATTACK -> (int) (.72 * 20.0);
            case ATTACK2 -> 0;
            case ATTACK3 -> 0;
            case ROAR -> (int) (2.64 * 20.0);
            case DOWN -> (int) (.96 * 20.0);
            case FALL_ASLEEP -> (int) (.88 * 20.0);
            case WAKE_UP -> (int) (1.04 * 20.0);
            case UP -> (int) (1.68 * 20.0);
            case SIT -> (int) (1.92 * 20.0);
            case SLEEP -> (int) (1.92 * 20.0);
            case SWIM -> (int) (1.92 * 20.0);
            case COMMUNICATE -> (int) (2.72 * 20.0);
        };
    }

    @Override
    public @NotNull InteractionResult interactAt(@NotNull Player pPlayer, @NotNull Vec3 pVec, @NotNull InteractionHand pHand) {
        pPlayer.startRiding(this);
        return InteractionResult.PASS;
    }

    @Override
    protected float getRiddenSpeed(@NotNull Player pRider) {
        return (this.isSprinting() ? 0.230F : 0.1F) + (this.getSpeedBonusTicks() > 0 ? 0.15F : 0.0F);
    }

    @Override
    protected @NotNull Vec3 getRiddenInput(@NotNull Player pRider, @NotNull Vec3 pTravelVector) {
        float forwardMovementFactor = pRider.zza < 0.0F ? 0.0F : 1.0F;
        boolean flag = this.getSmartPose() != SmartAnimalPose.SIT || this.getSmartPose() != SmartAnimalPose.UP || this.getSmartPose() != SmartAnimalPose.SLEEP || this.getSmartPose() != SmartAnimalPose.DOWN || this.getSmartPose() != SmartAnimalPose.WAKE_UP;
        return new Vec3(pRider.xxa * 0.35F, 0.0, pRider.zza * 0.8F * forwardMovementFactor);
    }

    @Override
    protected void tickRidden(@NotNull Player pRider, @NotNull Vec3 pTravelVector) {
        super.tickRidden(pRider, pTravelVector);
        this.setTarget(null);
        if (pRider.xxa != 0.0F || pRider.zza != 0.0F) {
            EntityUtils.smoothVehicleYawToRider(pRider, this, 0.1F, 0.14F);
        }
    }

    @Override
    public LivingEntity getControllingPassenger() {
        return this.getFirstPassenger() instanceof Player player ? player : null;
    }

    @Override
    protected void positionRider(@NotNull Entity pPassenger, @NotNull MoveFunction pCallback) {
        super.positionRider(pPassenger, pCallback);
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float speed = this.isSprinting() ? 0.7F : 6.0F;
        float f = this.getPose() == Pose.STANDING ? Math.min(pPartialTick * speed, 1.0F) : 0.0F;
        this.walkAnimation.update(f, 0.5F);
    }

    @Override
    public boolean isRidable() {
        return true;
    }

    @Override
    public float getMaxScreenShake() {
        return this.isSprinting() ? 0.6F : 0.3F;
    }

    @Override
    public float getShakePower() {
        return this.isSprinting() ? 0.5F : 0.2F;
    }

    @Override
    public float getShakeFrequency() {
        return this.isSprinting() ? 1.39F : 0.39F;
    }

    @Override
    public float getShakeDistance() {
        return 10;
    }

    @Override
    public boolean canEntityShake(@NotNull LivingEntity pEntity) {
        return IShakeScreenOnStep.super.canEntityShake(this) && EntityUtils.isEntityMoving(this, 0.1F);
    }

    @Override
    public void onMouseClick(int pButton) {
        if (pButton == 0) {
            EntityUtils.attackByRider(this, 1.5F, 6.0F);
        }
        if (pButton == 1) {
            NetworkHandler.sendMSGToServer(new MessageServerEntityEvent(this.blockPosition(), LevelEvents.ROAR.eventId, EventData.valueInteger(this.getId())));
            this.setSmartPose(SmartAnimalPose.ROAR);
            this.setPoseTicks(this.getAnimationLengthInTicks(SmartAnimalPose.ROAR));
        }
    }

    @Override
    public boolean isActionDenied(int pActionId) {
        return pActionId == 0 || pActionId == 1;
    }

    public int getRoarTicks() {
        return this.getEntityData().get(DATA_ROAR_TICKS);
    }

    public void setRoarTicks(int pTicks) {
        this.getEntityData().set(DATA_ROAR_TICKS, pTicks);
    }

    public int getSpeedBonusTicks() {
        return this.getEntityData().get(DATA_ROAR_TICKS);
    }

    public void setSpeedBonusTicks(int pTicks) {
        this.getEntityData().set(DATA_ROAR_TICKS, pTicks);
    }

    static {
        DATA_ROAR_TICKS = SynchedEntityData.defineId(LythronaxEntity.class, EntityDataSerializers.INT);
        DATA_SPEED_BONUS = SynchedEntityData.defineId(LythronaxEntity.class, EntityDataSerializers.INT);
    }

    @Override
    public boolean isSaddled() {
        return true;
    }

    @Override
    public boolean isSaddleable() {
        return true;
    }

    @Override
    public void equipSaddle(@Nullable SoundSource soundSource) {

    }
}
