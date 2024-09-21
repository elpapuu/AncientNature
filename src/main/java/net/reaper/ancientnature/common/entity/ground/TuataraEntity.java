package net.reaper.ancientnature.common.entity.ground;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.reaper.ancientnature.common.entity.goals.PanicSprintingGoal;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimalPose;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;
import net.reaper.ancientnature.common.entity.util.AnimalDiet;
import net.reaper.ancientnature.common.entity.util.IShakeScreenOnStep;
import net.reaper.ancientnature.common.messages.NetworkHandler;
import net.reaper.ancientnature.common.messages.packets.MessageServerEntityEvent;
import net.reaper.ancientnature.common.messages.util.EventData;
import net.reaper.ancientnature.common.messages.util.LevelEventManager;
import net.reaper.ancientnature.common.util.EntityUtils;
import net.reaper.ancientnature.common.util.ScreenShakeUtils;
import net.reaper.ancientnature.core.init.ModEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TuataraEntity extends SmartAnimatedAnimal {
    public float tailRot;
    public float prevTailRot;
    public int tickControlled;

    public TuataraEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 1;
        this.setTame(false);
        this.moveControl = new MoveControl(this) {
            @Override
            public void tick() {
                if (!TuataraEntity.this.isSleeping()) {
                    super.tick();
                }
            }
        };
    }
    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicSprintingGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.SPIDER_EYE), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        setMale(random.nextBoolean());
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }
    @Override
    public void tick() {
        super.tick();
        this.prevTailRot = this.tailRot;
        this.tailRot += (-(this.yBodyRot - this.yBodyRotO) - this.tailRot) * 0.15F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 6)
                .add(Attributes.MOVEMENT_SPEED, 0.125d)
                .add(Attributes.FOLLOW_RANGE, 25d);

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.TUATARA.get().create(level());
    }
    @Override
    public void defineDiet(AnimalDiet.Builder pDietBuilder) {
        super.defineDiet(pDietBuilder);
        pDietBuilder.addItems(Items.SPIDER_EYE, Items.FERMENTED_SPIDER_EYE);
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
            case IDLE -> (int) ((this.isBaby() ? .96 : 1.25) * 20.0);
            case WALK -> (int) ((this.isBaby() ? .96 : 1) * 40.0);
            case RUN -> (int) ((this.isBaby() ? .48 : .51) * 20.0);
            case EAT -> (int) ((this.isBaby() ? .48 : 1.46) * 20.0);
            case ATTACK -> (int) ((this.isBaby() ? .44 : .5) * 20.0);
            case ATTACK2 -> 0;
            case ATTACK3 -> 0;
            case ROAR -> 0;
            case DOWN -> 0;
            case FALL_ASLEEP -> (int) (.88 * 1);
            case WAKE_UP -> (int) ((this.isBaby() ? 1.2 : 1) * 20.0);
            case UP -> 0;
            case SIT -> 0;
            case SLEEP -> (int) ((this.isBaby() ? 1.44 : 2.8) * 20.0);
            case SWIM -> (int) (1 * 20.0);
            case COMMUNICATE -> 0;
        };
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float speed = !this.isBaby() ? (this.isSprinting() ? 0.7F : 6.0F) : 1.3F;
        float f = this.getPose() == Pose.STANDING ? Math.min(pPartialTick * speed, 1.0F) : 0.0F;
        this.walkAnimation.update(f, 1.0F);
    }

    @Override
    public boolean canSpawnSprintParticle() {
        return false;
    }
    public static boolean canSpawn(EntityType<TuataraEntity> tEntityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType spawnType, BlockPos blockPos, RandomSource randomSource) {
    return Animal.checkAnimalSpawnRules(tEntityType, serverLevelAccessor, spawnType, blockPos, randomSource) && !serverLevelAccessor.getLevelData().isRaining();
    }
}
