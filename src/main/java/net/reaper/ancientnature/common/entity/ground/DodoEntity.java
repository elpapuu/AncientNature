package net.reaper.ancientnature.common.entity.ground;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidType;
import net.reaper.ancientnature.common.entity.BaseTameableDinoEntity;
import net.reaper.ancientnature.common.entity.goals.OrderRandomStrollAvoidWater;
import net.reaper.ancientnature.common.entity.goals.PanicSprintingGoal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class DodoEntity extends BaseTameableDinoEntity {
    public static AnimationState idleAnimation = new AnimationState();
    private static AnimationState walkAnimationState = new AnimationState();
    public static AnimationState screamAnimationState = new AnimationState();
    private static AnimationState panicAnimationState = new AnimationState();
    public AnimationState sitAnimation = new AnimationState();
    public AnimationState sleepAnimation = new AnimationState();
    public AnimationState swimAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private static Ingredient FOOD_ITEMS;
    private int ticksSinceEaten;


    public DodoEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
        if (level().isClientSide()) {
            this.idleAnimation.animateWhen(!isInWaterOrBubble() && !this.walkAnimation.isMoving(), this.tickCount);
        }
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 15)
                .add(Attributes.MOVEMENT_SPEED, 0.2d)
                .add(Attributes.FOLLOW_RANGE, 64d)
                .add(Attributes.ATTACK_DAMAGE, 1);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new DodoEatBerriesGoal(1.0D, 4, 4));
        this.goalSelector.addGoal(0, new DodoSearchForItemsGoal());
        this.goalSelector.addGoal(0, new PanicSprintingGoal(this, 1.5));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0, 5.0F, 1.0F, true));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, FOOD_ITEMS, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new OrderRandomStrollAvoidWater(this, 1.0D, getOrder()));

    }


    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();
        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(pPlayer) || this.isTame() || itemstack.is(Items.BONE) && !this.isTame(); // add !is baby
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else if (itemstack.is(Items.MELON_SLICE)) {
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

            if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                this.tame(pPlayer);
                this.navigation.stop();
                this.setTarget((LivingEntity) null);
                this.setOrderedToSit(true);
                this.level().broadcastEntityEvent(this, (byte) 7);
            } else {
                this.level().broadcastEntityEvent(this, (byte) 6);
            }

            return InteractionResult.SUCCESS;
        }
        if (this.isTame()) {

            } else if (getOrder() == 1) {
                pPlayer.displayClientMessage(Component.nullToEmpty("Dodo is wandering"), true);
                System.out.println("wandering");
                setOrder(3);
            } else if (getOrder() == 2) {
                pPlayer.displayClientMessage(Component.nullToEmpty("Dodo is resting"), true);
                System.out.println("resting");
                setOrder(1);
            }
            System.out.println(getOrder());

        {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    @Override
    public boolean isRidable() {
        return false;
    }

    @Override
    public boolean canBeSteered() {
        return true;
    }


    @Override
    protected void setupAnimationStates() {
        //Idle Animation
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimation.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
        // panic or running
        if (this.panicAnimation()) {
            this.panicAnimationState.start(this.tickCount);
            this.panicAnimationState.startIfStopped(this.tickCount);
        } else {
            this.panicAnimationState.stop();
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide && this.isAlive() && this.isEffectiveAi()) {
            ++this.ticksSinceEaten;
            ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (this.canEat(itemstack)) {
                if (this.ticksSinceEaten > 600) {
                    ItemStack itemstack1 = itemstack.finishUsingItem(this.level(), this);
                    if (!itemstack1.isEmpty()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, itemstack1);
                    }

                    this.ticksSinceEaten = 0;
                } else if (this.ticksSinceEaten > 560 && this.random.nextFloat() < 0.1F) {
                    this.playSound(this.getEatingSound(itemstack), 1.0F, 1.0F);
                    this.level().broadcastEntityEvent(this, (byte) 45);
                }
            }
        }
    }
    private boolean canEat(ItemStack pStack) {
        return pStack.getItem().isEdible() && this.getTarget() == null && this.onGround() && !this.isSleeping();
    }
    public boolean canTakeItem (ItemStack pItemstack){
        EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(pItemstack);
        if (!this.getItemBySlot(equipmentslot).isEmpty()) {
            return false;
        } else {
            return equipmentslot == EquipmentSlot.MAINHAND && super.canTakeItem(pItemstack);
            }
        }

    public boolean canHoldItem (ItemStack pStack){
        Item item = pStack.getItem();
        ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            return itemstack.isEmpty() || this.ticksSinceEaten > 0 && item.isEdible() && !itemstack.getItem().isEdible();
        }
    protected void pickUpItem(ItemEntity pItemEntity) {
        ItemStack itemstack = pItemEntity.getItem();
        if (this.canHoldItem(itemstack)) {
            int i = itemstack.getCount();
            ItemStack itemstack1 = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemstack1.isEmpty()) {
                this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(i));
            } else if (itemstack1.getCount() < itemstack1.getMaxStackSize()) {
                itemstack1.grow(i);
            }

            this.onItemPickup(pItemEntity);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
            this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
            this.take(pItemEntity, itemstack.getCount());
            pItemEntity.discard();
            this.ticksSinceEaten = 0;
        }

    }

    private boolean panicAnimation() {
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
    public boolean setRiding(Player pPlayer) {
        pPlayer.setYRot(getYRot());
        pPlayer.setXRot(getXRot());
        pPlayer.startRiding(this);
        return false;
    }
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return this.isBaby() ? pSize.height * 0.85F : pSize.height * 0.92F;
    }
    public boolean isFood(ItemStack pStack) {
        return FOOD_ITEMS.test(pStack);
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
    static {
        FOOD_ITEMS = Ingredient.of(new ItemLike[]{Items.MELON, Items.GLISTERING_MELON_SLICE, Items.MELON_SLICE, Items.MELON_SEEDS, Items.SWEET_BERRIES, Items.GLOW_BERRIES});
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return super.getCapability(cap);
    }
    public class DodoEatBerriesGoal extends MoveToBlockGoal {
        private static final int WAIT_TICKS = 40;
        protected int ticksWaited;

        public DodoEatBerriesGoal(double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
            super(DodoEntity.this, pSpeedModifier, pSearchRange, pVerticalSearchRange);
        }

        public double acceptedDistance() {
            return 2.0;
        }

        public boolean shouldRecalculatePath() {
            return this.tryTicks % 100 == 0;
        }

        protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
            BlockState blockstate = pLevel.getBlockState(pPos);
            return blockstate.is(Blocks.SWEET_BERRY_BUSH) && (Integer)blockstate.getValue(SweetBerryBushBlock.AGE) >= 2 || CaveVines.hasGlowBerries(blockstate);
        }

        public void tick() {
            if (this.isReachedTarget()) {
                if (this.ticksWaited >= 40) {
                    this.onReachedTarget();
                } else {
                    ++this.ticksWaited;
                }
            } else if (!this.isReachedTarget() && DodoEntity.this.random.nextFloat() < 0.05F) {
                DodoEntity.this.playSound(SoundEvents.FOX_SNIFF, 1.0F, 1.0F);
            }

            super.tick();
        }

        protected void onReachedTarget() {
            if (ForgeEventFactory.getMobGriefingEvent(DodoEntity.this.level(), DodoEntity.this)) {
                BlockState blockstate = DodoEntity.this.level().getBlockState(this.blockPos);
                if (blockstate.is(Blocks.SWEET_BERRY_BUSH)) {
                    this.pickSweetBerries(blockstate);
                } else if (CaveVines.hasGlowBerries(blockstate)) {
                    this.pickGlowBerry(blockstate);
                }
            }

        }

        private void pickGlowBerry(BlockState pState) {
            CaveVines.use(DodoEntity.this, pState, DodoEntity.this.level(), this.blockPos);
        }

        private void pickSweetBerries(BlockState pState) {
            int i = (Integer)pState.getValue(SweetBerryBushBlock.AGE);
            pState.setValue(SweetBerryBushBlock.AGE, 1);
            int j = 1 + DodoEntity.this.level().random.nextInt(2) + (i == 3 ? 1 : 0);
            ItemStack itemstack = DodoEntity.this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemstack.isEmpty()) {
                DodoEntity.this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.SWEET_BERRIES));
                --j;
            }

            if (j > 0) {
                Block.popResource(DodoEntity.this.level(), this.blockPos, new ItemStack(Items.SWEET_BERRIES, j));
            }

            DodoEntity.this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
            DodoEntity.this.level().setBlock(this.blockPos, (BlockState)pState.setValue(SweetBerryBushBlock.AGE, 1), 2);
        }

        public boolean canUse() {
            return super.canUse();
        }

        public void start() {
            this.ticksWaited = 0;
            super.start();
        }
    }
    class DodoSearchForItemsGoal extends Goal {
        public DodoSearchForItemsGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (!DodoEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                return false;
            } else if (DodoEntity.this.getTarget() == null && DodoEntity.this.getLastHurtByMob() == null) {
                if (!DodoEntity.this.walkAnimation.isMoving()) {
                    return false;
                } else if (DodoEntity.this.getRandom().nextInt(reducedTickDelay(10)) != 0) {
                    return false;
                } else {
                    List<ItemEntity> list = DodoEntity.this.level().getEntitiesOfClass(ItemEntity.class, DodoEntity.this.getBoundingBox().inflate(8.0, 8.0, 8.0));
                    return !list.isEmpty() && DodoEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
                }
            } else {
                return false;
            }
        }

        public void tick() {
            List<ItemEntity> list = DodoEntity.this.level().getEntitiesOfClass(ItemEntity.class, DodoEntity.this.getBoundingBox().inflate(8.0, 8.0, 8.0));
            ItemStack itemstack = DodoEntity.this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (itemstack.isEmpty() && !list.isEmpty()) {
                DodoEntity.this.getNavigation().moveTo((Entity)list.get(0), 1.2000000476837158);
            }

        }

        public void start() {
            List<ItemEntity> list = DodoEntity.this.level().getEntitiesOfClass(ItemEntity.class, DodoEntity.this.getBoundingBox().inflate(8.0, 8.0, 8.0));
            if (!list.isEmpty()) {
                DodoEntity.this.getNavigation().moveTo((Entity)list.get(0), 1.2000000476837158);
            }

        }
    }
}
