package net.reaper.ancientnature.common.entity.water;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.reaper.ancientnature.common.entity.goals.EnsnareGoal;
import net.reaper.ancientnature.common.entity.goals.AnomalocarisMeleeGoal;
import net.reaper.ancientnature.common.entity.goals.SmallerEntityTargetGoal;

public class Anomalocaris extends WaterAnimal {
    private static final EntityDataAccessor<Boolean> DIGESTING_ID = SynchedEntityData.defineId(Anomalocaris.class, EntityDataSerializers.BOOLEAN);
    public static final String DIGESTING_TAG = "DigestingTime";
    private final int TICKS_TO_DIGEST = 400; // 20 seconds
    private int ticksEnsnaring;
    private int ticksDigesting;

    int HUNGER_COOLDOWN = 9600; // 8 minutes

    public Anomalocaris(EntityType<? extends WaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AnomalocarisPanicGoal(2.0D));
        this.goalSelector.addGoal(2, new EnsnareGoal(this, 0.4F));
        this.goalSelector.addGoal(3, new AnomalocarisMeleeGoal(this, 1.5D, true));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Player.class)));
        this.targetSelector.addGoal(2, (new SmallerEntityTargetGoal<>(this, WaterAnimal.class, false, Entity::isInWater)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 4.0D);
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.9F;
    }

    /**
     * GOALS
     */
    class AnomalocarisPanicGoal extends PanicGoal {
        public AnomalocarisPanicGoal(double modifier) {
            super(Anomalocaris.this, modifier);
        }

        protected boolean shouldPanic() {
            return this.mob.isFreezing() || this.mob.isOnFire();
        }
    }

    @Override
    public void tick() {
        if (this.isAlive() && !this.isNoAi() && !this.level().isClientSide) {
            if (this.isVehicle()) {
                if (this.isDigesting()) {
                    --this.ticksDigesting;
                    if (this.ticksDigesting < 0) {
                        this.digest();
                    } else if (this.ticksDigesting == 100 || this.ticksDigesting == 200 || this.ticksDigesting == 300) {

                        this.level().playSound(null, this.getOnPos(), SoundEvents.PLAYER_BURP, SoundSource.NEUTRAL, 1.0F, 1.2F);
                    }
                } else {
                    ++this.ticksEnsnaring;
                    if (this.ticksEnsnaring >= 140) {
                        this.startDigesting(TICKS_TO_DIGEST);
                    }
                }
            } else {
                this.ticksEnsnaring = -1;
                this.setDigesting(false);
            }
        }
        super.tick();
    }

    /** Target Digestion NBT */
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DIGESTING_ID, false);
    }

    public boolean isDigesting() {
        return this.getEntityData().get(DIGESTING_ID);
    }

    public void setDigesting(boolean isDigesting) {
        this.entityData.set(DIGESTING_ID, isDigesting);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt(DIGESTING_TAG, this.isDigesting() ? this.ticksDigesting : -1);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains(DIGESTING_TAG, 99) && pCompound.getInt(DIGESTING_TAG) > -1) {
            this.startDigesting(pCompound.getInt(DIGESTING_TAG));
        }

    }

    private void startDigesting(int digestionTime) {
        this.ticksDigesting = digestionTime;
        this.setDigesting(true);
    }

    protected void digest() {
        if (this.getFirstPassenger() != null) this.getFirstPassenger().discard();

        if (!this.isSilent()) {

            this.level().playSound(null, this.getOnPos(), SoundEvents.PLAYER_BURP, SoundSource.NEUTRAL, 1.0F, 1.8F);
        }

    }
}
