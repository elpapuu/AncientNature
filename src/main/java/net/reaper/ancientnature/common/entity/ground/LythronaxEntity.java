package net.reaper.ancientnature.common.entity.ground;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidType;
import net.reaper.ancientnature.common.entity.BaseTameableDinoEntity;
import net.reaper.ancientnature.common.entity.goals.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LythronaxEntity extends BaseTameableDinoEntity {
    public static AnimationState idleAnimation = new AnimationState();
    private static AnimationState walkAnimationState = new AnimationState();
    public AnimationState sitAnimation = new AnimationState();
    public AnimationState sleepAnimation = new AnimationState();
    private int idleAnimationTimeout = 0;
    private float tailAngle;
    private float tailSpeed = 0.1f; // animation speed
    private float timer;

    public LythronaxEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 95)
                .add(Attributes.MOVEMENT_SPEED, 0.16d)
                .add(Attributes.FOLLOW_RANGE, 64d)
                .add(Attributes.ATTACK_KNOCKBACK, 1d)
                .add(Attributes.ATTACK_DAMAGE,3.0);

    }
    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new CommunicateGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new MeleeAttackGoal(this, 3.0f, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, OviraptorEntity.class, true));
        this.goalSelector.addGoal(12, new OrderRandomStrollAvoidWater(this, 1.0D, getOrder()));
        this.targetSelector.addGoal(1, new BTD_ProtectBabyTargetGoal(this, Player.class, true));

    }



    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();
        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(pPlayer) || this.isTame() || itemstack.is(Items.BONE) && !this.isTame(); // add !is baby
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else if (itemstack.is(Items.BEEF)){
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                this.tame(pPlayer);
                this.navigation.stop();
                this.setTarget((LivingEntity)null);
                this.setOrderedToSit(true);
                this.level().broadcastEntityEvent(this, (byte)7);
            } else {
                this.level().broadcastEntityEvent(this, (byte)6);
            }

            return InteractionResult.SUCCESS;
        }  if (this.isTame()) {

            if (getOrder() == 1 || getOrder() == 0)  {
                setOrder(2);
                pPlayer.displayClientMessage(Component.nullToEmpty("Lythronax is following you"), true);
                System.out.println("following:");

            } else if (getOrder() == 2) {
                pPlayer.displayClientMessage(Component.nullToEmpty("Lythronax is wandering"), true);
                System.out.println("wandering" );
                setOrder(3);
            }
            else if (getOrder() == 3) {
                pPlayer.displayClientMessage(Component.nullToEmpty("Lythronax is resting"), true);
                System.out.println("sitting");
                setOrder(1);
            }
            System.out.println(getOrder());

             {
                boolean flag = this.isFood(pPlayer.getItemInHand(pHand));
                if (!flag && this.isSaddled() && !this.isVehicle() && !pPlayer.isSecondaryUseActive()) {
                    if (!this.level().isClientSide) {
                        pPlayer.startRiding(this);
                    }

                    return InteractionResult.sidedSuccess(this.level().isClientSide);
                } else {
                    InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
                    if (!interactionresult.consumesAction()) {
                        itemstack = pPlayer.getItemInHand(pHand);
                        return itemstack.is(Items.SADDLE) ? itemstack.interactLivingEntity(pPlayer, this, pHand) : InteractionResult.PASS;
                    } else {
                        return interactionresult;
                    }
                }
            }

        } else {
            return super.mobInteract(pPlayer, pHand);
        }


    }

    private boolean isSaddled() {
        return true;
    }
    @Override
    public boolean isRidable() {
        return true;
    }
    @Override
    public boolean canBeSteered() {
        return true;
    }
    @Override
    protected void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimation.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
        if (this.walkAnimation()) {
            this.walkAnimationState.start(this.tickCount);
            this.walkAnimationState.startIfStopped(this.tickCount);
        } else {
            this.walkAnimationState.stop();
        }
        //sitting
        if (this.getOrder() == 3) {
            if (this.idleAnimation.isStarted())
                this.idleAnimation.stop();
            this.sitAnimation.startIfStopped(this.tickCount);
        } else {
            this.sitAnimation.stop();
        }
    }

    private boolean walkAnimation() {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        return super.getOwner();
    }
@Override
    public LivingEntity self() {
        return super.self();
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return super.getPickedResult(target);
    }

    @Override
    public boolean canRiderInteract() {
        return super.canRiderInteract();
    }

    @Override
    public boolean setRiding(Player pPlayer) {
        pPlayer.setYRot(getYRot());
        pPlayer.setXRot(getXRot());
        pPlayer.startRiding(this);
        return false;
    }
    protected void tickRidden(Player pPlayer, Vec3 pTravelVector) {
        super.tickRidden(pPlayer, pTravelVector);
        this.setRot(pPlayer.getYRot(), pPlayer.getXRot() * 0.5F);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
    }

    protected Vec3 getRiddenInput(Player pPlayer, Vec3 pTravelVector) {
        return new Vec3(0.0, 0.0, 1.0);
    }
    @Override
    public boolean canBeRiddenUnderFluidType(FluidType type, Entity rider) {
        return super.canBeRiddenUnderFluidType(type, rider);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return super.getCapability(cap);
    }
}
