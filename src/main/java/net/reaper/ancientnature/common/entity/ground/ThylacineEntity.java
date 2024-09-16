package net.reaper.ancientnature.common.entity.ground;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimalPose;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;
import net.reaper.ancientnature.common.entity.util.AnimalDiet;
import net.reaper.ancientnature.core.init.ModItems;
import net.reaper.ancientnature.core.init.ModSounds;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ThylacineEntity extends SmartAnimatedAnimal {
    public float tailRot;
    public float prevTailRot;
    public int tickControlled;
    public ThylacineEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 2;
        this.setTame(false);
        this.moveControl = new MoveControl(this) {
            @Override
            public void tick() {
                if (!ThylacineEntity.this.isSleeping()) {
                    super.tick();
                }
            }
        };
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 15)
                .add(Attributes.MOVEMENT_SPEED, 0.2d)
                .add(Attributes.FOLLOW_RANGE, 64d)
                .add(Attributes.ATTACK_DAMAGE, 2);
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

    @Override
    public void defineDiet(AnimalDiet.Builder pDietBuilder) {
        super.defineDiet(pDietBuilder);
        pDietBuilder.addItems(Items.BEEF, Items.CHICKEN, Items.PORKCHOP, Items.MUTTON, Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_PORKCHOP, Items.COOKED_MUTTON, ModItems.RAW_DODO.get(), ModItems.COOKED_DODO.get());
    }

    @Override
    protected int getAnimationLengthInTicks(SmartAnimalPose smartPose) {
        return switch (smartPose){
            case IDLE -> (int)(1.5F*20.0);
            case WALK -> (int)(1F*20.0);
            case RUN -> (int)(0.42F*20.0);
            case SWIM -> (int)(1F*20.0);
            case COMMUNICATE -> 0;
            case EAT -> (int)(1.75F*20.0);
            case ATTACK -> (int)(0.5*20.0);
            case ATTACK2 -> 0;
            case ATTACK3 -> 0;
            case ROAR -> 0;
            case DOWN -> (int)(1F*20.0);
            case FALL_ASLEEP -> (int)(1F*20.0);
            case WAKE_UP -> (int)(2.08F*20.0);
            case UP -> (int)(1F*20.0);
            case SIT -> (int)(2F*20.0);
            case SLEEP -> (int)(2F*20.0);
        };

    }

    @Override
    public int getEatAnimationConsumeDelay() {
        return 40;
    }

    @Override
    public boolean canAttack() {
        return true;
    }
    public static boolean canSpawn(EntityType<ThylacineEntity> tEntityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType spawnType, BlockPos blockPos, RandomSource randomSource) {
        return Animal.checkAnimalSpawnRules(tEntityType, serverLevelAccessor, spawnType, blockPos, randomSource) && !serverLevelAccessor.getLevelData().isRaining();
    }
}