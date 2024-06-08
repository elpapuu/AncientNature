package net.reaper.ancientnature.common.entity.ground;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.reaper.ancientnature.common.entity.BaseTameableDinoEntity;
import net.reaper.ancientnature.common.entity.goals.*;
import org.jetbrains.annotations.Nullable;

public class OviraptorEntity extends BaseTameableDinoEntity {
    public AnimationState idleAnimation = new AnimationState();
    public AnimationState eatAnimation = new AnimationState();
    private AnimationState swimAnimation = new AnimationState();
    private AnimationState walkAnimationState = new AnimationState();
    private int walkAnimationTimeout = 0;
    private int sitAnimationTimeout = 0;
    private int swimAnimationTimeout = 0;
    private int idleAnimationTimeout = 0;
    private static final EntityDataAccessor<Integer> ITEM_TIMER = SynchedEntityData.defineId(OviraptorEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_DISTRACTED = SynchedEntityData.defineId(OviraptorEntity.class, EntityDataSerializers.BOOLEAN);
    public AnimationState sitAnimation;


    public OviraptorEntity(EntityType<? extends BaseTameableDinoEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.3d)
                .add(Attributes.FOLLOW_RANGE, 25d);

    }




    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isDistracted", isDistracted());
        pCompound.putInt("itemTimer", getItemTimer());

    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setDistracted(pCompound.getBoolean("isDistracted"));
        setItemTimer(pCompound.getInt("itemTimer"));

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ITEM_TIMER, 3000);
        this.entityData.define(IS_DISTRACTED, true);

    }




    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new HerbivorePanic(this, 2.0D, 12)); // might need to be custom
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new CommunicateGoal(this));
        // drop egg goal
        this.goalSelector.addGoal(6, new DropAtClayEggGoal(Blocks.CLAY, this, 1.4, (int) 25D));
        // find egg item goal
        this.goalSelector.addGoal(7, new SearchForItemsFromTagsGoal(this, true, Tags.Items.SEEDS, 25d));
        // break egg block goal
        this.goalSelector.addGoal(8, new RemoveBlocksFromTagGoal(BlockTags.CANDLE_CAKES,this, 1, 25 ));

        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(12, new OrderRandomStrollAvoidWater(this, 1.0D, getOrder()));


        this.targetSelector.addGoal(1, new BTD_ProtectBabyTargetGoal(this, Player.class, true));

    }


    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
                if(!itemStack.isEmpty()) {
                    ItemEntity itemEntity = new ItemEntity(this.level(), this.getX() + 0.5D, this.getY() + 1D, this.getZ() + 0.5D, itemStack);
                    this.level().addFreshEntity(itemEntity);
                    setItemTimer(600);

                }
        return super.hurt(pSource, pAmount);
    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();
        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(pPlayer) || this.isTame() || itemstack.is(Items.BONE) && !this.isTame(); // add !is baby
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else if (itemstack.is(Items.BEETROOT_SEEDS)){
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
                pPlayer.displayClientMessage(Component.nullToEmpty("Oviraptor is following you"), true);
                System.out.println("following:");

            } else if (getOrder() == 2) {
                pPlayer.displayClientMessage(Component.nullToEmpty("Oviraptor is wandering"), true);
                System.out.println("wandering" );
                setOrder(3);
            }
            else if (getOrder() == 3) {
                pPlayer.displayClientMessage(Component.nullToEmpty("Oviraptor is resting"), true);
                System.out.println("sitting");
                setOrder(1);
            }
            System.out.println(getOrder());



            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(pPlayer, pHand);
        }


    }

    public boolean isDistracted(){
        return this.entityData.get(IS_DISTRACTED);
    }
    public void setDistracted(boolean distracted){
        this.entityData.set(IS_DISTRACTED, distracted);
    }

    public int getItemTimer(){
        return this.entityData.get(ITEM_TIMER);
    }
    public void setItemTimer(int timer){
        this.entityData.set(ITEM_TIMER, timer);
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
        if (!this.isSleeping()) {
            if (this.idleAnimation.isStarted())
                this.idleAnimation.stop();
            this.sitAnimation.startIfStopped(this.tickCount);
        } else {
            this.swimAnimation.stop();
        }
        //swim
        if (this.isInWater()) {
            if (this.idleAnimation.isStarted())
                this.idleAnimation.stop();
            this.swimAnimation.startIfStopped(this.tickCount);
        } else {
            this.swimAnimation.stop();
        }
    }

    private boolean walkAnimation() {
        return false;
    }
    private boolean sitAnimation() {
        return false;
    }


    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
    }

    @Override
    public CompoundTag serializeNBT() {
        return super.serializeNBT();
    }
}
