package net.reaper.ancientnature.common.entity.ground;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimalPose;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;
import org.jetbrains.annotations.Nullable;

public class WormEntity extends SmartAnimatedAnimal {
    public float tailRot;
    public float prevTailRot;
    public WormEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 1;
        this.setTame(false);
        this.moveControl = new MoveControl(this) {
            @Override
            public void tick() {
                if (!WormEntity.this.isSleeping()) {
                    super.tick();
                }
            }
        };
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.02d)
                .add(Attributes.FOLLOW_RANGE, 64d)
                .add(Attributes.ATTACK_DAMAGE, 0);
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
    protected int getAnimationLengthInTicks(SmartAnimalPose smartPose) {
        return switch (smartPose){
            case IDLE -> (int)(2.88F*20.0);
            case WALK -> (int)(1.44F*20.0);
            case RUN -> 0;
            case SWIM -> 0;
            case COMMUNICATE -> 0;
            case EAT -> 0;
            case ATTACK -> 0;
            case ATTACK2 -> 0;
            case ATTACK3 -> 0;
            case ROAR -> 0;
            case DOWN -> 0;
            case FALL_ASLEEP -> 0;
            case WAKE_UP -> 0;
            case SNIFF -> 0;
            case INTIMATED -> 0;
            case UP -> 0;
            case SIT -> 0;
            case SLEEP -> 0;
        };

    }
    public static boolean canSpawn(EntityType<WormEntity> tEntityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType spawnType, BlockPos blockPos, RandomSource randomSource) {
        return Animal.checkAnimalSpawnRules(tEntityType, serverLevelAccessor, spawnType, blockPos, randomSource) && !serverLevelAccessor.getLevelData().isRaining();
    }
}
