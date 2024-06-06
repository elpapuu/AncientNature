package net.reaper.ancientnature.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseTameableDinoEntity extends TamableAnimal {
    protected BaseTameableDinoEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    private static final EntityDataAccessor<Boolean> IS_MALE = SynchedEntityData.defineId(BaseTameableDinoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_SLEEPING = SynchedEntityData.defineId(BaseTameableDinoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_BABY = SynchedEntityData.defineId(BaseTameableDinoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ORDER = SynchedEntityData.defineId(BaseTameableDinoEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_HERBIVORE = SynchedEntityData.defineId(BaseTameableDinoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> BEFORE_SLEEP_TIMER = SynchedEntityData.defineId(BaseTameableDinoEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> EAT_TIMER = SynchedEntityData.defineId(BaseTameableDinoEntity.class, EntityDataSerializers.INT);



    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isMale", isMale());
        pCompound.putBoolean("isSleeping", isSleeping());
        pCompound.putInt("Order", getOrder());
        pCompound.putBoolean("isBaby", isBaby());
        pCompound.putBoolean("isHerbivore", isHerbivore());
        pCompound.putInt("SleepTimer", getSleepTimer());
        pCompound.putInt("EatTimer", getEatTimer());

    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setMale(pCompound.getBoolean("isMale"));
        setSleeping(pCompound.getBoolean("isSleeping"));
        setOrder(pCompound.getInt("Order"));
        setBaby(pCompound.getBoolean("isBaby"));
        setHerbivore(pCompound.getBoolean("isHerbivore"));
        setSleepTimer(pCompound.getInt("SleepTimer"));
        setEatTimer(pCompound.getInt("EatTimer"));


    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_MALE, true);
        this.entityData.define(IS_SLEEPING, false);
        this.entityData.define(ORDER, 3);
        this.entityData.define(IS_BABY, false);
        this.entityData.define(IS_HERBIVORE, false);
        this.entityData.define(BEFORE_SLEEP_TIMER, 0);
        this.entityData.define(EAT_TIMER, 0);
    }

    public boolean isMale(){
        return this.entityData.get(IS_MALE);
    }
    public void setMale(boolean male){
        this.entityData.set(IS_MALE, male);
    }



    public boolean isHerbivore(){
        return this.entityData.get(IS_HERBIVORE);
    }
    public void setHerbivore(boolean herbivore){
        this.entityData.set(IS_HERBIVORE, herbivore);
    }

    public boolean returnHerbivore(){
        return true;
    }


    public boolean isSleeping(){
        return this.entityData.get(IS_SLEEPING);
    }
    public void setSleeping(boolean sleeping){
        this.entityData.set(IS_SLEEPING, sleeping);
    }
    public boolean isBaby(){
        return this.entityData.get(IS_BABY);
    }
    public void setBaby(boolean baby){
        this.entityData.set(IS_BABY, baby);
    }
    public int getOrder(){
        return this.entityData.get(ORDER);
    }
    public void setOrder(int order){
        this.entityData.set(ORDER, order);
    }
    public int getSleepTimer(){
        return this.entityData.get(BEFORE_SLEEP_TIMER);
    }
    public void setSleepTimer(int timer){
        this.entityData.set(BEFORE_SLEEP_TIMER, timer);
    }

    public int getEatTimer(){
        return this.entityData.get(EAT_TIMER);
    }
    public void setEatTimer(int timer){
        this.entityData.set(EAT_TIMER, timer);
    }

    public boolean canBeBaby() {
        return false;
    }

    public boolean additionalSleepConditions() {
        return true;
    }



    public boolean timeForSleepConditions() {
        return this.level().isNight();
    }

    public int detectRadiusSize() {
        return 12;
    }


    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        setMale(random.nextBoolean());
        setOrder(3);
        setBaby(canBeBaby());
        setHerbivore(returnHerbivore());


        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if(this.getSleepTimer() > 0) {this.setSleepTimer(getSleepTimer()-1); System.out.println("sleepTimer: " + getSleepTimer());}
            if(isSleeping()) {this.getNavigation().stop(); System.out.println("is sleeping");}

            if (timeForSleepConditions() && getSleepTimer() >= 0 &&
                    this.getLastHurtByMob() == null && getOrder() != 2 && additionalSleepConditions()) {
              //  System.out.println("conditions have been met for sleep");
                if (tickCount % 10 == 0) {
                   // System.out.println("SLEEPING!!!!");
                    this.setSleeping(true);
                }
            } else if (this.isSleeping()) {
                this.setSleeping(false);
            }

        }
    }
}
