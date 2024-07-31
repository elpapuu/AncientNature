package net.reaper.ancientnature.common.entity.ground;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.reaper.ancientnature.common.entity.ground.enums.TRexEnum;
import net.reaper.ancientnature.common.entity.multipart.TRexPart;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimalPose;
import net.reaper.ancientnature.common.entity.smartanimal.TameableOrders;
import net.reaper.ancientnature.core.init.ModEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TRexEntity extends AbstractDinoAnimal
{
    
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(
            TRexEntity.class, EntityDataSerializers.INT
    );
    private static final EntityDataAccessor<Long> DAY_TIME = SynchedEntityData.defineId(TRexEntity.class, EntityDataSerializers.LONG);
    private static final EntityDataAccessor<Integer> BEDTIME_VARIANCE = SynchedEntityData.defineId(TRexEntity.class, EntityDataSerializers.INT);


    public AnimationState idleAnimation = new AnimationState();
    public AnimationState sitAnimation = new AnimationState();
    public AnimationState sleepAnimation = new AnimationState();
    public AnimationState eatAnimation = new AnimationState();
    public AnimationState upAnimation = new AnimationState();
    public AnimationState downAnimation = new AnimationState();
    public AnimationState fallAlseepAnimation = new AnimationState();
    public AnimationState wakeUpAnimation = new AnimationState();
    public AnimationState attackAnimation = new AnimationState();
    public AnimationState attack2Animation = new AnimationState();
    public AnimationState attack3Animation = new AnimationState();
    public AnimationState roarAnimation = new AnimationState();
    public AnimationState runAnimation = new AnimationState();

    public int attackAnimationTimeout;
    public int attack2AnimationTimeout;
    public int attack3AnimationTimeout;
    public int eatAnimationTimeout;

    private TRexPart headPart;
    private TRexPart body;
    private TRexPart tail1Part;
    private TRexPart tail2Part;
    private TRexPart tail3Part;

    public TRexEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Override
    protected int getAnimationLengthInTicks(SmartAnimalPose smartPose) {
        return switch (smartPose){
            case IDLE -> (int)(2.5F*20.0);
            case WALK -> (int)(2F*20.0);
            case RUN -> (int)(0.8343334f*20.0);
            case SWIM -> (int)(1.28F*20.0);
            case COMMUNICATE -> (int)(0.5417F*20.0);
            case EAT -> (int)(3.3433335f*20.0);
            case ATTACK -> (int)(0.8343334f*20.0);
            case ATTACK2 -> (int)(0.9583434f*20.0);
            case ATTACK3 -> (int)(0.9167666f*20.0);
            case DOWN -> (int)(1.5834333f*20.0);
            case FALL_ASLEEP -> (int)(2.75F*20.0);
            case WAKE_UP -> (int)(4F*20.0);
            case UP -> (int)(1.875f*20.0);
            case SIT -> (int)(2.5f*20.0);
            case ROAR -> (int)(5.5f*200);
            case SLEEP -> (int)(2.88F*20.0);
        };
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return ModEntities.TREX.get().create(level());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT,0);
        this.entityData.define(BEDTIME_VARIANCE, 0);
        this.entityData.define(DAY_TIME, level().getDayTime());
    }

    public int getVariant()
    {
        return this.entityData.get(VARIANT);
    }
    public void setVariant(int variant)
    {
        this.entityData.set(VARIANT,variant);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant",getVariant());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setVariant(compound.getInt("Variant"));
    }

    @Override
    public void tick() {
        super.tick();
        if(level().isClientSide)
        {
            setupAnimationStates();
        }
        updateDayTime();
    }
    private void updateDayTime() {
        if(level().isClientSide()){
            return;
        }
        this.entityData.set(DAY_TIME, level().getDayTime());

    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.walkAnimation.update(f, 0.2F);
    }

    protected void setupAnimationStates() {
        attackAnimationSetup();
        eatingAnimationSetup();

        this.idleAnimation.animateWhen(!this.walkAnimation.isMoving(),getPoseTicks());


        if (this.isSprinting()) {
            if (getSmartPose() != SmartAnimalPose.RUN) {
                runAnimation.startIfStopped(this.tickCount);
                setSmartPose(SmartAnimalPose.RUN);
                setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.RUN));
            }
        }

        if (getOrder() == TameableOrders.SIT && getSmartPose() != SmartAnimalPose.SIT && !walkAnimation.isMoving()) {
            if (getSmartPose() == SmartAnimalPose.IDLE) {
                downAnimation.startIfStopped(this.tickCount);
                setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.DOWN));
                setSmartPose(SmartAnimalPose.DOWN);
            }
            if (getSmartPose() == SmartAnimalPose.DOWN && getPoseTicks() == 0) {
                downAnimation.stop();
                sitAnimation.start(this.tickCount);
                setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.SIT));
                setSmartPose(SmartAnimalPose.SIT);
            }
        }

        if (getOrder() != TameableOrders.SIT && getSmartPose() != SmartAnimalPose.IDLE) {
            if (getSmartPose() == SmartAnimalPose.SIT) {
                sitAnimation.stop();
                upAnimation.startIfStopped(this.tickCount);
                setSmartPose(SmartAnimalPose.UP);
                setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.UP));
            }
            if (getSmartPose() == SmartAnimalPose.UP && getPoseTicks() == 0) {
                upAnimation.stop();
                setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.IDLE));
                setSmartPose(SmartAnimalPose.IDLE);
            }
        }

        if (shouldSleep() && getSmartPose() != SmartAnimalPose.SLEEP && !walkAnimation.isMoving()) {
            if (getSmartPose() == SmartAnimalPose.IDLE) {
                downAnimation.startIfStopped(this.tickCount);
                setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.DOWN));
                setSmartPose(SmartAnimalPose.DOWN);
            }
            if ((getSmartPose() == SmartAnimalPose.DOWN || getSmartPose() == SmartAnimalPose.SIT) && getPoseTicks() == 0) {
                downAnimation.stop();
                sitAnimation.stop();
                fallAlseepAnimation.start(this.tickCount);
                setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.FALL_ASLEEP));
                setSmartPose(SmartAnimalPose.FALL_ASLEEP);
            }
            if (getSmartPose() == SmartAnimalPose.FALL_ASLEEP && getPoseTicks() == 0) {
                fallAlseepAnimation.stop();
                sleepAnimation.start(this.tickCount);
                setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.SLEEP));
                setSmartPose(SmartAnimalPose.SLEEP);
            }
        }

        if (!shouldSleep() && getSmartPose() == SmartAnimalPose.SLEEP) {
            sleepAnimation.stop();
            wakeUpAnimation.start(this.tickCount);
            setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.WAKE_UP));
            setSmartPose(SmartAnimalPose.WAKE_UP);
        }
        if (getSmartPose() == SmartAnimalPose.WAKE_UP && getPoseTicks() == 0) {
            wakeUpAnimation.stop();
            if (getOrder() == TameableOrders.SIT) {
                sitAnimation.start(this.tickCount);
                setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.SIT));
                setSmartPose(SmartAnimalPose.SIT);
            } else {
                upAnimation.start(this.tickCount);
                setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.UP));
                setSmartPose(SmartAnimalPose.UP);
            }
        }

        if (getSmartPose() == SmartAnimalPose.UP && getPoseTicks() == 0) {
            upAnimation.stop();
            idleAnimation.startIfStopped(this.tickCount);
            setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.IDLE));
            setSmartPose(SmartAnimalPose.IDLE);
        }

        if (shouldSleep() && getSmartPose() == SmartAnimalPose.SLEEP && getPoseTicks() == 0) {
            sleepAnimation.stop();
            setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.SLEEP));
            setSmartPose(SmartAnimalPose.SLEEP);
            sleepAnimation.start(this.tickCount);
        }

        if (isRoaring() && getSmartPose() != SmartAnimalPose.ROAR) {
            roarAnimation.stop();
            setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.ROAR));
            setSmartPose(SmartAnimalPose.ROAR);
            roarAnimation.start(this.tickCount);
        }
    }

    private boolean shouldSleep()
    {
        return this.entityData.get(DAY_TIME) > 13000+getBedtimeVariance() || this.entityData.get(DAY_TIME) < 1000+getBedtimeVariance();
    }

    public int getBedtimeVariance() {
        return this.entityData.get(BEDTIME_VARIANCE);
    }
    private void attackAnimationSetup() {
        int attackDes = this.random.nextIntBetweenInclusive(1,3);
        if(attackDes == 1)
        {
            if (this.isAttacking() && attackAnimationTimeout <= 0) {
                attackAnimationTimeout = getAnimationLengthInTicks(SmartAnimalPose.ATTACK);
                attackAnimation.start(this.tickCount);
            } else {
                --this.attackAnimationTimeout;
            }

            if (!this.isAttacking()) {
                attackAnimation.stop();
            }
        }
        else if(attackDes == 2)
        {
            if (this.isAttacking() && attack2AnimationTimeout <= 0) {
                attack2AnimationTimeout = getAnimationLengthInTicks(SmartAnimalPose.ATTACK2);
                attack2Animation.start(this.tickCount);
            } else {
                --this.attack2AnimationTimeout;
            }

            if (!this.isAttacking()) {
                attack2Animation.stop();
            }
        }
        else if(attackDes == 3)
        {
            if (this.isAttacking() && attack3AnimationTimeout <= 0) {
                attack3AnimationTimeout = getAnimationLengthInTicks(SmartAnimalPose.ATTACK3);
                attack2Animation.start(this.tickCount);
            } else {
                --this.attack3AnimationTimeout;
            }

            if (!this.isAttacking()) {
                attack3Animation.stop();
            }
        }

    }
    private void eatingAnimationSetup() {
        if (this.isEating() && eatAnimationTimeout <= 0) {
            eatAnimationTimeout = getAnimationLengthInTicks(SmartAnimalPose.EAT);
            eatAnimation.start(this.tickCount);
        } else {
            --this.eatAnimationTimeout;
        }

        if (!this.isEating()||eatAnimationTimeout==1) {
            setEating(false);
            eatAnimation.stop();
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor pLevel, @NotNull DifficultyInstance pDifficulty, @NotNull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag)
    {
        this.setVariant(this.getRandom().nextInt(2));
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }
    public TRexEnum getEnum() {
        switch (this.getVariant()) {
            default:
                return TRexEnum.BROWN;
            case 1:
                return TRexEnum.NORMAL;
        }
    }
}
