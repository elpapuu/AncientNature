package net.reaper.ancientnature.common.entity.water;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.entity.goals.*;
import net.reaper.ancientnature.common.entity.goals.paranogmius.ParanogmiusJumpGoal;
import net.reaper.ancientnature.common.entity.util.IMouseInput;
import net.reaper.ancientnature.common.messages.NetworkHandler;
import net.reaper.ancientnature.common.messages.packets.MessageHandler;
import net.reaper.ancientnature.common.network.packet.EntityAttackKeyPacket;
import net.reaper.ancientnature.common.util.EntityUtils;
import net.reaper.ancientnature.core.init.ModBlocks;
import net.reaper.ancientnature.core.init.ModEntities;
import net.reaper.ancientnature.core.init.ModTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.utils.Log;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

public class Paranogmius extends AquaticAnimal implements IMouseInput {
    private static final EntityDataAccessor<Integer> DATA_RIDEABLE_FOR;
    private static final EntityDataAccessor<Boolean> DATA_JUMP;
    private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ENTITY_UUID;
    public final AnimationState idleAnimation = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    private int attackAnimationTimeout = 0;
    public final AnimationState flipAnimationState = new AnimationState();
    public int flipAnimationTimeout = 0;
    public float tailRot;
    public float prevTailRot;
    private int exitWaterTicks;
    private int postOutWaterTicks;
    private float jumpStrength = 0.0F;
    private float a = 0.0F;
    boolean hasEggs;
    public int speedBoost;


    public Paranogmius(EntityType<? extends AquaticAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new SmoothSwimmingMoveControl(this, 90, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
        this.navigation = new WaterBoundPathNavigation(this, pLevel);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(4, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(3, new SwimGoal(this, 4f));
        this.goalSelector.addGoal(0, new PanicSprintingGoal(this, 2.0D));
        this.goalSelector.addGoal(1, new AvoidEntitySprinting<>(this, Player.class, 8.0F, 1f, 2f, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(2, new EggBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new ParanogmiusJumpGoal(this, 10));
        this.goalSelector.addGoal(1, new ParanogmiusAvoidPlayerGoal(this));
        this.goalSelector.addGoal(3, new LayEggGoal(this, 1.0D, 16, 16));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && !Paranogmius.this.isVehicle();
            }
        });
        this.goalSelector.addGoal(8, new FollowBoatGoal(this));
        this.goalSelector.addGoal(20, new WildBreedGoal(this, Entity::isInWater, 300));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_RIDEABLE_FOR, 0);
        this.getEntityData().define(DATA_JUMP, false);
        this.getEntityData().define(DATA_TRUSTED_ENTITY_UUID, Optional.empty());
    }

    public int getRemainingRideTicks() {
        return this.getEntityData().get(DATA_RIDEABLE_FOR);
    }

    public void setRemainingRideTicks(int pTicks) {
        this.getEntityData().set(DATA_RIDEABLE_FOR, pTicks);
    }

    public boolean isJump() {
        return this.getEntityData().get(DATA_JUMP);
    }

    public void setJump(boolean pJump) {
        this.getEntityData().set(DATA_JUMP, pJump);
    }

    @Nullable
    public Entity getTrustedEntity() {
        UUID uuid = this.getEntityData().get(DATA_TRUSTED_ENTITY_UUID).orElse(null);
        if (this.level() instanceof ServerLevel serverLevel) {
            return uuid == null ? null : serverLevel.getEntity(uuid);
        }
        return null;
    }

    public void setTrustedEntity(@Nullable UUID pEntityUUID) {
        this.getEntityData().set(DATA_TRUSTED_ENTITY_UUID, Optional.ofNullable(pEntityUUID));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        pCompound.putInt("RideableFor", this.getRemainingRideTicks());
        if (pCompound.hasUUID("TrustedEntity") && this.getTrustedEntity() != null) {
            pCompound.putUUID("TrustedEntity", this.getTrustedEntity().getUUID());
        }
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        this.setRemainingRideTicks(pCompound.getInt("RideableFor"));
        if (pCompound.hasUUID("TrustedEntity")) {
            this.setTrustedEntity(pCompound.getUUID("TrustedEntity"));
        }
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f = this.getPose() == Pose.STANDING ? Math.min(pPartialTick * 6.0F, 1.0F) : 0.0F;
        this.walkAnimation.update(f, 0.5F);
    }

    protected void setupAnimationStates() {
        if (this.level().isClientSide) {
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = this.random.nextInt(40) + 80;
                this.idleAnimation.start(this.tickCount);
            } else {
                --this.idleAnimationTimeout;
            }
            if (this.attackAnimationTimeout == 10) {
                this.attackAnimationState.start(this.tickCount);
            }
            if (this.attackAnimationTimeout > 0) {
                --this.attackAnimationTimeout;
            } else {
                this.attackAnimationState.stop();
            }
            /*
            if (this.flipAnimationTimeout <= 0) {
                this.flipAnimationTimeout = 10;
                this.flipAnimationState.start(this.tickCount);
            } else {
                --this.flipAnimationTimeout;
            }

             */
        }
    }


    @Override
    public void aiStep() {
        if (!this.isInWater()) {
            if (this.onGround() || this.verticalCollision) {
                this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.2F, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
                this.hasImpulse = true;
                this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
            }
        }
        super.aiStep();
    }

    @Override
    public void tick() {
        super.tick();
        if (EntityUtils.isEntityMoving(this, 0.1F)) {
            if (this.isInWater()) {
                if (this.isSprinting()) {
                    this.bubbleTrail(2, 1.2F, 1.2F, 1.2F);
                }
                if (this.isVehicle()) {
                    this.setSprinting(EntityUtils.canSprintByPlayer(this));
                }
            }
        } else {
            this.setSprinting(false);
        }
        if (this.isInWater()) {
            this.a = this.isSprinting() ? 0.73F : 0.33F;
            this.jumpStrength = this.isSprinting() ? 0.43F : 0.2F;
        }
        if (this.getFirstPassenger() != null) {
            this.setRemainingRideTicks(Math.max(this.getRemainingRideTicks() - 1, 0));
        }
        if (EntityUtils.canAttackByPlayer(this) && this.attackAnimationTimeout <= 0) {
            NetworkHandler.sendMSGToServer(new EntityAttackKeyPacket(this.getId()));
            this.attackAnimationTimeout = 10;
        }
        this.setupAnimationStates();
        this.speedBoost = Math.max(--this.speedBoost, 0);
        this.prevTailRot = this.tailRot;
        this.tailRot += (-(this.yBodyRot - this.yBodyRotO) - this.tailRot) * 0.15F;
    }

    @Override
    public boolean setRiding(Player pPlayer) {
        return false;
    }

    public void positionRider(Entity passenger, MoveFunction moveFunction) {
        super.positionRider(passenger, moveFunction);
    }

    @Override
    public @NotNull InteractionResult interactAt(@NotNull Player pPlayer, @NotNull Vec3 pVec, @NotNull InteractionHand pHand) {
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);
        if (!this.isBaby()) {
            if (!super.mobInteract(pPlayer, pHand).consumesAction() && itemInHand.is(Items.SALMON) && this.getTrustedEntity() == null) {
                if (!this.level().isClientSide) {
                    this.usePlayerItem(pPlayer, pHand, itemInHand);
                    this.setTrustedEntity(pPlayer.getUUID());
                    this.setRemainingRideTicks(12000);
                }
                return InteractionResult.SUCCESS;
            } else if (!super.mobInteract(pPlayer, pHand).consumesAction() && this.canAddPassenger(this) && this.getTrustedEntity() != null && this.getTrustedEntity() == pPlayer) {
                pPlayer.startRiding(this);
                this.setTrustedEntity(null);
            }
        }
        return super.interactAt(pPlayer, pVec, pHand);
    }

    @Nonnull
    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createLivingAttributes().add(Attributes.MAX_HEALTH, 25.0D).add(Attributes.MOVEMENT_SPEED, 0.23D).add(Attributes.FOLLOW_RANGE, 16.0D).add(Attributes.KNOCKBACK_RESISTANCE, 2.0D);
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return this.getFirstPassenger() instanceof Player player ? player : null;
    }

    @Override
    protected @NotNull Vec3 getRiddenInput(@NotNull Player pRider, @NotNull Vec3 pTravelVector) {
        float forwardMovementFactor = pRider.zza < 0.0F ? 0.0F : 1.0F;
        return this.isInWater() ? new Vec3(pRider.xxa * 0.35F, 0.0, pRider.zza * 0.8F * forwardMovementFactor) : Vec3.ZERO;
    }

    @Override
    public void travel(@NotNull Vec3 pTravelVector) {
        if (this.isInWater() && this.isVehicle()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9F));
            this.calculateEntityAnimation(false);
            this.move(MoverType.SELF, this.getDeltaMovement());
        } else {
            super.travel(pTravelVector);
        }
    }

    @Override
    protected void tickRidden(@NotNull Player pRider, @NotNull Vec3 pVec3) {
        super.tickRidden(pRider, pVec3);
        boolean isMovingByRider = pRider.xxa != 0.0F || pRider.zza != 0.0F;
        Vec3 delta = this.getDeltaMovement();
        float riderXRot = pRider.getXRot(), selfXRot = this.getXRot();
        float targetPitch = Mth.rotLerp(0.25F, selfXRot, riderXRot);
        double yDirection = Vec3.directionFromRotation(targetPitch / 1.7F, selfXRot).y;
        if (isMovingByRider) {
            EntityUtils.smoothVehicleYawToRider(pRider, this, 0.17F, 0.13F);
        }
        this.setXRot(this.exitWaterTicks < 25 ? Mth.rotLerp(0.06F, selfXRot, 90.0F) : selfXRot);
        if (this.isInWater()) {
            this.postOutWaterTicks = Math.max(--this.postOutWaterTicks, 0);
            this.exitWaterTicks = 25;
            if (!isMovingByRider) {
                this.setDeltaMovement(delta.add(0.0F, 0.01F, 0.0F));
            }
            if (Mth.clamp(this.postOutWaterTicks, 1, 8) == this.postOutWaterTicks) {
                this.setXRot(Mth.rotLerp(0.16F, selfXRot, riderXRot));
            }
            if (isMovingByRider) {
                this.setXRot(targetPitch);
                if (this.postOutWaterTicks <= 0) {
                    this.setDeltaMovement(delta.x, yDirection, delta.z);
                }
            }
        } else {
            this.postOutWaterTicks = 10;
            if (this.exitWaterTicks > 15) {
                --this.exitWaterTicks;
                Vec3 viewVector = this.getViewVector(0.0F).scale(this.a);
                this.setDeltaMovement(viewVector.x, this.jumpStrength, viewVector.z);
            }
            if (this.onGround() || this.verticalCollision) {
                pRider.stopRiding();
            }
        }
    }

    @Override
    protected float getRiddenSpeed(@NotNull Player pRider) {
        return 0.05F + (this.isSprinting() ? 0.05F : 0.0F) + (this.speedBoost > 0 ? 0.25F : 0.0F);
    }

    @Override
    protected void doWaterSplashEffect() {
        if (this.isJump()) {
            this.speedBoost = 100;
            this.setJump(false);
        }
        super.doWaterSplashEffect();
    }

    protected boolean canRide(@NotNull Entity pEntity) {
        return this.getRemainingRideTicks() > 0 && this.getTrustedEntity() != null;
    }

    @Override
    public void onMouseClick(int pButton) {
        if (pButton == 0) {
            EntityUtils.attackByRider(this, 1.5F, 15.0F);
        }
    }

    @Override
    public boolean isActionDenied(int pActionId) {
        return pActionId == 0 || pActionId == 1;
    }

    static class MoveHelperController extends MoveControl {
        private final Paranogmius dolphin;

        public MoveHelperController(Paranogmius dolphinIn) {
            super(dolphinIn);
            this.dolphin = dolphinIn;
        }

        public void tick() {
            if (this.dolphin.isInWater()) {
                this.dolphin.setDeltaMovement(this.dolphin.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            if (this.operation == MoveControl.Operation.MOVE_TO && !this.dolphin.getNavigation().isDone()) {
                double d0 = this.wantedX - this.dolphin.getX();
                double d1 = this.wantedY - this.dolphin.getY();
                double d2 = this.wantedZ - this.dolphin.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double) 2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                    this.dolphin.setYRot(this.rotlerp(this.dolphin.getYRot(), f, 10.0F));
                    this.dolphin.yBodyRot = this.dolphin.getYRot();
                    this.dolphin.yHeadRot = this.dolphin.getYRot();
                    float f1 = (float) (this.speedModifier * this.dolphin.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.dolphin.isInWater()) {
                        this.dolphin.setSpeed(f1 * 0.02F);
                        float f2 = -((float) (Mth.atan2(d1, Mth.sqrt((float) (d0 * d0 + d2 * d2))) * (double) (180F / (float) Math.PI)));
                        f2 = Mth.clamp(Mth.wrapDegrees(f2), -85.0F, 85.0F);
                        this.dolphin.setXRot(this.rotlerp(this.dolphin.getXRot(), f2, 5.0F));
                        float f3 = Mth.cos(this.dolphin.getXRot() * ((float) Math.PI / 180F));
                        float f4 = Mth.sin(this.dolphin.getXRot() * ((float) Math.PI / 180F));
                        this.dolphin.zza = f3 * f1;
                        this.dolphin.yya = -f4 * f1;
                    } else {
                        this.dolphin.setSpeed(f1 * 0.1F);
                    }

                }
            } else {
                this.dolphin.setSpeed(0.0F);
                this.dolphin.setXxa(0.0F);
                this.dolphin.setYya(0.0F);
                this.dolphin.setZza(0.0F);
            }
        }
    }


    public boolean doesHaveEggs() {
        return hasEggs;
    }

    public boolean canBeSteered() {
        return true;
    }

    public void setHasEggs(boolean hasEggs) {
        this.hasEggs = hasEggs;
    }

    @Override
    public boolean isMovementBlocked() {
        return false;
    }

    @Override
    public boolean isRidable() {
        return true;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.PARANOGMIUS.get().create(pLevel);
    }

    @Nonnull
    protected SoundEvent getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }

    public static boolean checkWaterSurfaceSpawnRules(EntityType<? extends AquaticAnimal> aquatic, LevelAccessor level, MobSpawnType type, BlockPos pos, RandomSource random) {
        int i = level.getSeaLevel();
        int j = i - 13;
        return pos.getY() >= j && pos.getY() <= i && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }

    @Override
    public boolean canFallInLove() {
        return super.canFallInLove() && !this.isPassenger();
    }

    static class SwimGoal extends RandomSwimmingGoal {
        private final Paranogmius entity;

        public SwimGoal(PathfinderMob mob, double speedModifier) {
            super(mob, speedModifier, 3);
            this.entity = (Paranogmius) mob;
        }

        @Override
        public boolean canUse() {
            return this.entity.getTarget() == null && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return this.entity.getTarget() == null && super.canContinueToUse();
        }
    }

    static class EggBreedGoal extends BreedGoal {
        private final Paranogmius entity;

        public EggBreedGoal(Paranogmius pAnimal, double pSpeedModifier) {
            super(pAnimal, pSpeedModifier);
            this.entity = pAnimal;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !this.entity.doesHaveEggs();
        }

        @Override
        protected void breed() {
            this.entity.setHasEggs(true);
            //  this.entity.setAge(6000);
            this.animal.resetLove();
            if (this.partner != null) this.partner.setAge(6000);
            if (this.partner != null) this.partner.resetLove();

            RandomSource randomsource = this.animal.getRandom();
            if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), randomsource.nextInt(7) + 1));
            }
        }
    }

    static class LayEggGoal extends MoveToBlockGoal {
        private final Paranogmius entity;

        public LayEggGoal(Paranogmius pMob, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
            super(pMob, pSpeedModifier, pSearchRange, pVerticalSearchRange);
            this.entity = pMob;
        }

        @Override
        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            BlockState state = pLevel.getBlockState(pPos);
            BlockState below = pLevel.getBlockState(pPos.below());
            return !pLevel.canSeeSkyFromBelowWater(pPos) && state.is(Blocks.WATER) && below.isSolid() && !below.is(ModBlocks.ARANDASPIS_ROE.get());
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.entity.doesHaveEggs();
        }

        public void tick() {
            super.tick();
            BlockPos entitypos = this.entity.blockPosition();
            if (this.entity.isInWater() && this.isReachedTarget()) {
                Level level = this.entity.level();
                BlockState blockstate = ModBlocks.ARANDASPIS_ROE.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN);
                level.setBlock(blockPos, blockstate, 3);
                level.gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(this.entity, blockstate));
                this.entity.setHasEggs(false);
            }
        }
    }

    static {
        DATA_RIDEABLE_FOR = SynchedEntityData.defineId(Paranogmius.class, EntityDataSerializers.INT);
        DATA_TRUSTED_ENTITY_UUID = SynchedEntityData.defineId(Paranogmius.class, EntityDataSerializers.OPTIONAL_UUID);
        DATA_JUMP = SynchedEntityData.defineId(Paranogmius.class, EntityDataSerializers.BOOLEAN);
    }
}