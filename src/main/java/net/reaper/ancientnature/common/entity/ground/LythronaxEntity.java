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
import net.reaper.ancientnature.common.messages.util.LevelEventManager;
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
    public int tickControlled;

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

    private float calculateShakeIntensity(float pAnimationSpeed) {
        float animationFactor = 1.0F + pAnimationSpeed * 0.3F;
        return 0.2F * animationFactor;
    }

    @Override
    public void tick() {
        super.tick();
        this.roarAnimationSetup(this.getRoarTicks() > 0);
        if (EntityUtils.isEntityMoving(this, 0.1F)) {
            if (this.isVehicle()) {
                this.setSprinting(EntityUtils.canSprintByPlayer(this) && this.getStamina() > 0.0F);
            }
        } else {
            this.setSprinting(false);
        }
        if (this.isSprinting()) {
            if (this.tickCount % 3 == 0) {
                this.setStamina(this.getStamina() - 1.0F);
            }
        } else {
            if (this.getStamina() < 100.0F && this.getHunger() > 0.0F) {
                if (this.tickCount % 5 == 0) {
                    this.setHunger(this.getHunger() - 0.2F);
                    this.setStamina(Math.min(this.getStamina() + 0.5F, 100.0F));
                }
            }
        }
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        List<LythronaxEntity> entityList = EntityUtils.getEntitiesAroundSelf(LythronaxEntity.class, this, 15, 4, false);
        if (entityList != null && !entityList.isEmpty() && localPlayer != null) {
            for (LythronaxEntity entity : entityList) {
                if (this.canEntityShake(entity) && EntityUtils.isEntityStepping(entity, getShakeFrequency(), calculateShakeIntensity(entity.walkAnimation.speed()))) {
                    float distanceFactor = getShakeDistance() / entity.distanceTo(localPlayer) / 8.0F;
                    float shakePower = Math.min(distanceFactor * 0.7F, 0.6F);
                    ScreenShakeUtils.shakeScreen(7, shakePower);
                }
            }
        }
        if (entityList != null && localPlayer != null && !entityList.isEmpty()) {
            entityList.stream().filter(entity -> entity.getRoarTicks() == Mth.clamp(entity.getRoarTicks(), 19, 55)).forEach(entity -> {
                float distanceFactor = getShakeDistance() / entity.distanceTo(localPlayer) / 8.0F;
                float shakePower = Math.min(distanceFactor * 0.7F, 0.6F);
                ScreenShakeUtils.shakeScreen(5, shakePower);
            });
        }
        if (!this.level().isClientSide) {
            this.setRoarTicks(Math.max(this.getRoarTicks() - 1, 0));
            this.setSpeedBonusTicks(Math.max(this.getSpeedBonusTicks() - 1, 0));
        }
        if (this.getSmartPose() == SmartAnimalPose.ATTACK) {
            this.setAttacking(this.getPoseTicks() > 0);
        }
        if (this.isVehicle()) {
            ++this.tickControlled;
        } else {
            this.tickControlled = 0;
        }
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
    protected int getAnimationLengthInTicks(SmartAnimalPose pSmartPose) {
        return switch (pSmartPose) {
            case IDLE -> (int) ((this.isBaby() ? .96 : 1.92) * 20.0);
            case WALK -> (int) ((this.isBaby() ? .96 : 1.92) * 20.0);
            case RUN -> (int) ((this.isBaby() ? .48 : .84) * 20.0);
            case EAT -> (int) ((this.isBaby() ? 2.04 : 2.84) * 20.0);
            case ATTACK -> (int) ((this.isBaby() ? .44 : .72) * 20.0);
            case ATTACK2 -> 0;
            case ATTACK3 -> 0;
            case ROAR -> (int) ((this.isBaby() ? 1.12 : 2.64) * 20.0);
            case DOWN -> (int) ((this.isBaby() ? .56 : .96) * 20.0);
            case FALL_ASLEEP -> (int) (.88 * 20.0);
            case WAKE_UP -> (int) ((this.isBaby() ? 1.2 : 1.04) * 20.0);
            case SNIFF -> 0;
            case INTIMATED -> 0;
            case UP -> (int) ((this.isBaby() ? 1.12 : 1.68) * 20.0);
            case SIT -> (int) ((this.isBaby() ? 1.44 : 1.92) * 20.0);
            case SLEEP -> (int) ((this.isBaby() ? 1.44 : 1.92) * 20.0);
            case SWIM -> (int) (1.92 * 20.0);
            case COMMUNICATE -> (int) (2.72 * 20.0);
        };
    }

    @Override
    public @NotNull InteractionResult interactAt(@NotNull Player pPlayer, @NotNull Vec3 pVec, @NotNull InteractionHand pHand) {
        if (this.canAddPassenger(pPlayer) && !this.isBaby()) {
            pPlayer.startRiding(this);
        }
        return InteractionResult.PASS;
    }

    @Override
    protected float getRiddenSpeed(@NotNull Player pRider) {
        return (this.isSprinting() ? 0.230F : 0.1F) + (this.getSpeedBonusTicks() > 0 ? (this.isSprinting() ? 0.07F : 0.04F) : 0.0F);
    }

    @Override
    protected @NotNull Vec3 getRiddenInput(@NotNull Player pRider, @NotNull Vec3 pTravelVector) {
        float forwardMovementFactor = pRider.zza < 0.0F ? 0.0F : 1.0F;
        boolean flag = this.getSmartPose() != SmartAnimalPose.SIT || this.getSmartPose() != SmartAnimalPose.UP || this.getSmartPose() != SmartAnimalPose.SLEEP || this.getSmartPose() != SmartAnimalPose.DOWN || this.getSmartPose() != SmartAnimalPose.WAKE_UP;
        return flag ? new Vec3(pRider.xxa * 0.35F, 0.0, pRider.zza * 0.8F * forwardMovementFactor) : Vec3.ZERO;
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
        if (this.isPassengerOfSameVehicle(pPassenger)) {
            pCallback.accept(pPassenger, this.getX(), this.getY() + this.getPassengersRidingOffset() + 0.4, this.getZ());
        } else {
            super.positionRider(pPassenger, pCallback);
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float speed = !this.isBaby() ? (this.isSprinting() ? 0.7F : 6.0F) : 1.3F;
        float f = this.getPose() == Pose.STANDING ? Math.min(pPartialTick * speed, 1.0F) : 0.0F;
        this.walkAnimation.update(f, 1.0F);
    }

    @Override
    public boolean isRidable() {
        return true;
    }

    @Override
    public boolean canSpawnSprintParticle() {
        return false;
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
        return this.isSprinting() ? 1.39F : 0.35F;
    }

    @Override
    public float getShakeDistance() {
        return 10;
    }

    @Override
    public boolean canEntityShake(@NotNull LivingEntity pEntity) {
        return IShakeScreenOnStep.super.canEntityShake(this) && EntityUtils.isEntityMoving(this, 0.1F) && !this.isBaby();
    }

    @Override
    public void onMouseClick(int pButton) {
        if (pButton == 0) {
            if (this.attackAnimationTimeout <= 0 && this.tickControlled >= 5) {
                EventData[] data = new EventData[]{EventData.valueInteger(this.getId()), EventData.valueFloat(1.5F), EventData.valueFloat(8.0F)};
                NetworkHandler.sendMSGToServer(new MessageServerEntityEvent(this.blockPosition(), LevelEventManager.ATTACK.eventId, data));
                this.setSmartPose(SmartAnimalPose.ATTACK);
                this.setPoseTicks(this.getAnimationLengthInTicks(SmartAnimalPose.ATTACK));
            }
        }
        if (pButton == 1) {
            if (this.roarAnimationTimeout <= 0 && this.tickControlled >= 5 && this.getRoarTicks() <= 0) {
                NetworkHandler.sendMSGToServer(new MessageServerEntityEvent(this.blockPosition(), LevelEventManager.ROAR.eventId, EventData.valueInteger(this.getId())));
                this.setSmartPose(SmartAnimalPose.ROAR);
                this.setPoseTicks(this.getAnimationLengthInTicks(SmartAnimalPose.ROAR));
                this.setSpeedBonusTicks(220);
            }
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
        return this.getEntityData().get(DATA_SPEED_BONUS);
    }

    public void setSpeedBonusTicks(int pTicks) {
        this.getEntityData().set(DATA_SPEED_BONUS, pTicks);
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
        return !this.isBaby();
    }

    @Override
    public void equipSaddle(@Nullable SoundSource soundSource) {

    }
}
