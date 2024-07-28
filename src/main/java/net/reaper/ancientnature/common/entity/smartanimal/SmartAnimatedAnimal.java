package net.reaper.ancientnature.common.entity.smartanimal;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.reaper.ancientnature.common.entity.smartanimal.ai.goal.*;
import net.reaper.ancientnature.common.entity.smartanimal.ai.goal.LookAtPlayerGoal;
import net.reaper.ancientnature.common.entity.smartanimal.ai.goal.RandomLookAroundGoal;
import net.reaper.ancientnature.common.entity.util.AnimalDiet;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public abstract class SmartAnimatedAnimal extends TamableAnimal {

    private static final EntityDataAccessor<Boolean> IS_MALE = SynchedEntityData.defineId(SmartAnimatedAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_ATTACKING = SynchedEntityData.defineId(SmartAnimatedAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_EATING = SynchedEntityData.defineId(SmartAnimatedAnimal.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> IS_BABY = SynchedEntityData.defineId(SmartAnimatedAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ORDER = SynchedEntityData.defineId(SmartAnimatedAnimal.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(SmartAnimatedAnimal.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BEDTIME_VARIANCE = SynchedEntityData.defineId(SmartAnimatedAnimal.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SMART_ANIMAL_POSE = SynchedEntityData.defineId(SmartAnimatedAnimal.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Long> DAY_TIME = SynchedEntityData.defineId(SmartAnimatedAnimal.class, EntityDataSerializers.LONG);
    private static final EntityDataAccessor<Integer> POSE_TICK = SynchedEntityData.defineId(SmartAnimatedAnimal.class, EntityDataSerializers.INT);

    public AnimationState idleAnimation = new AnimationState();
    public AnimationState sitAnimation = new AnimationState();
    public AnimationState sleepAnimation = new AnimationState();
    public AnimationState eatAnimation = new AnimationState();
    public AnimationState upAnimation = new AnimationState();
    public AnimationState downAnimation = new AnimationState();
    public AnimationState fallAlseepAnimation = new AnimationState();
    public AnimationState wakeUpAnimation = new AnimationState();
    public AnimationState attackAnimation = new AnimationState();


    public int attackAnimationTimeout;
    public int eatAnimationTimeout;

    public int poseTicks;
    public SmartAnimalPose currentPose=SmartAnimalPose.IDLE;
    AnimalDiet diet;
    int sleepVariationCounter;

    protected SmartAnimatedAnimal(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        AnimalDiet.Builder dietBuilder = new AnimalDiet.Builder();
        defineDiet(dietBuilder);
        diet = dietBuilder.build();
    }

    @Override
    public void tick() {
        super.tick();
        updateDayTime();
        setupAnimationStates();
        if(poseTicks>0){
            poseTicks--;
        }
    }

    private void updateDayTime() {
        if(level().isClientSide()){
            return;
        }
        this.entityData.set(DAY_TIME, level().getDayTime());

    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if(!level().isClientSide()&&pHand==InteractionHand.MAIN_HAND){
            setOrder(getOrder() == TameableOrders.SIT ? TameableOrders.FOLLOW : TameableOrders.SIT);
            pPlayer.sendSystemMessage(Component.literal("Order: " + getOrder().name()));
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(pPlayer, pHand);
    }

    public boolean canAttack() {
        return true;
    }

    public void defineDiet(AnimalDiet.Builder pDietBuilder){}

    public boolean shouldSleep() {
        return this.entityData.get(DAY_TIME) > 13000+getBedtimeVariance() || this.entityData.get(DAY_TIME) < 1000+getBedtimeVariance();
    }



    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        if(canAttack()) {
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
            this.goalSelector.addGoal(1, new AttackGoal(this, 1.0D, true,getAttackAnimationDamageDelay(),getAnimationLengthInTicks(SmartAnimalPose.ATTACK)));
        }

        this.goalSelector.addGoal(2, new ConsumeItemFromGroundGoal(this, 1.2D,true,getEatAnimationConsumeDelay(),getAnimationLengthInTicks(SmartAnimalPose.EAT)));
        this.goalSelector.addGoal(2, new SmartAnimalTemptGoal(this, 1.2D,false));

        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));

        this.goalSelector.addGoal(4, new RandomWanderStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this,3f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));


    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isMale", isMale());
        pCompound.putInt("order", getOrder().ordinal());
        pCompound.putBoolean("isBaby", isBaby());
        pCompound.putInt("age", getAge());
        pCompound.putBoolean("isAttacking", isAttacking());
        pCompound.putInt("bedtimeVariance", getBedtimeVariance());
        pCompound.putInt("poseTicks", getPoseTicks());

    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setMale(pCompound.getBoolean("isMale"));
        setOrder(TameableOrders.values()[pCompound.getInt("order")]);
        setBaby(pCompound.getBoolean("isBaby"));
        setAge(pCompound.getInt("age"));
        setAttacking(pCompound.getBoolean("isAttacking"));
        setBedtimeVariance(pCompound.getInt("bedtimeVariance"));
        setPoseTicks(pCompound.getInt("poseTicks"));
    }

    private void setBedtimeVariance(int bedtimeVariance) {
        this.entityData.set(BEDTIME_VARIANCE, bedtimeVariance);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_MALE, false);
        this.entityData.define(IS_BABY, false);
        this.entityData.define(ORDER, TameableOrders.IDLE.ordinal());
        this.entityData.define(AGE, 100);
        this.entityData.define(IS_ATTACKING, false);
        this.entityData.define(BEDTIME_VARIANCE, 0);
        this.entityData.define(SMART_ANIMAL_POSE, SmartAnimalPose.IDLE.ordinal());
        this.entityData.define(DAY_TIME, level().getDayTime());
        this.entityData.define(POSE_TICK, 0);
        this.entityData.define(IS_EATING, false);
    }

    public int getPoseTicks() {
        return poseTicks;
    }

    public void setPoseTicks(int poseTicks) {
        this.poseTicks = Math.max(0,poseTicks);
    }
    public SmartAnimalPose getSmartPose() {
        return SmartAnimalPose.values()[currentPose.ordinal()];
    }

    public void setSmartPose(SmartAnimalPose pPose) {
        this.currentPose = pPose;
    }

    public int getBedtimeVariance() {
        return this.entityData.get(BEDTIME_VARIANCE);
    }

    public boolean isMale() {
        return this.entityData.get(IS_MALE);
    }

    public void setMale(boolean pIsMale) {
        this.entityData.set(IS_MALE, pIsMale);
    }

    public boolean isSleeping() {
        return getSmartPose()==SmartAnimalPose.SLEEP;
    }


    public boolean isBaby() {
        return this.entityData.get(IS_BABY);
    }

    public void setBaby(boolean pIsBaby) {
        this.entityData.set(IS_BABY, pIsBaby);
    }

    public TameableOrders getOrder() {
        return TameableOrders.values()[this.entityData.get(ORDER)];
    }

    public void setOrder(TameableOrders pOrder) {
        this.entityData.set(ORDER, pOrder.ordinal());
    }

    public int getAge() {
        return this.entityData.get(AGE);
    }

    public void setAge(int pAge) {
        this.entityData.set(AGE, pAge);
    }

    public boolean isAttacking() {
        return this.entityData.get(IS_ATTACKING);
    }

    public void setAttacking(boolean pIsAttacking) {
        this.entityData.set(IS_ATTACKING, pIsAttacking);
    }

    public AnimalDiet getDiet() {
        return diet;
    }

    public boolean isEating() {
        return this.entityData.get(IS_EATING);
    }

    public void setEating(boolean pIsEating) {
        this.entityData.set(IS_EATING, pIsEating);
    }

    public boolean isRidable(){
        return false;
    }

    public boolean canBeSteered(){
        return isRidable();
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

        if (getOrder() == TameableOrders.SIT && getSmartPose() != SmartAnimalPose.SIT&&!walkAnimation.isMoving()) {
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

        if (shouldSleep() && getSmartPose() != SmartAnimalPose.SLEEP &&!walkAnimation.isMoving()) {
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
        if(getSmartPose()==SmartAnimalPose.UP&&getPoseTicks()==0) {
            upAnimation.stop();
            idleAnimation.startIfStopped(this.tickCount);
            setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.IDLE));
            setSmartPose(SmartAnimalPose.IDLE);
        }
        if(shouldSleep()&&getSmartPose()==SmartAnimalPose.SLEEP&&getPoseTicks()==0){
            sleepAnimation.stop();
            setPoseTicks(getAnimationLengthInTicks(SmartAnimalPose.SLEEP));
            setSmartPose(SmartAnimalPose.SLEEP);
            sleepAnimation.start(this.tickCount);
        }
    }

    private void attackAnimationSetup() {
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

    public boolean isSaddled() {
        return false;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        Random random = new Random();
        setMale(random.nextBoolean());
        setBedtimeVariance(random.nextInt(200));
        setSmartPose(SmartAnimalPose.IDLE);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    public boolean isSitting() {
        return getSmartPose()==SmartAnimalPose.SIT;
    }

    public int getAttackAnimationDamageDelay(){
        return 0;
    }

    public int getEatAnimationConsumeDelay(){
        return 0;
    }

    protected abstract int getAnimationLengthInTicks(SmartAnimalPose smartPose);


}
