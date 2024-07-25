package net.reaper.ancientnature.common.entity.water;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.reaper.ancientnature.common.config.AncientNatureConfig;

import javax.annotation.Nonnull;

public abstract class AquaticAnimal extends Animal {
    public static final int EGG_LAYING_COOLDOWN = 600;

    int eggLayingCooldown=0;

    protected AquaticAnimal(EntityType<? extends AquaticAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return type != ForgeMod.WATER_TYPE.get();
    }

    @Nonnull
    public MobType getMobType() {
        return MobType.WATER;
    }

    public boolean checkSpawnObstruction(LevelReader pLevel) {
        return pLevel.isUnobstructed(this);
    }

    protected void handleAirSupply(int pAirSupply) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(pAirSupply - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }

    }

    @Override
    public void tick() {
        super.tick();
        if (this.eggLayingCooldown > 0) {
            this.eggLayingCooldown--;
        }
    }

    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();
        this.handleAirSupply(i);
    }

    public boolean canLayEggs() {
        return this.eggLayingCooldown <= 0;
    }

    public boolean isPushedByFluid() {
        return false;
    }

    public boolean canBeLeashed(Player pPlayer) {
        return false;
    }

    public abstract boolean setRiding(Player pPlayer);

    public static boolean checkWaterSurfaceSpawnRules(EntityType<? extends AquaticAnimal> aquatic, LevelAccessor level, MobSpawnType type, BlockPos pos, RandomSource random) {
        int i = level.getSeaLevel();
        int j = i - 13;
        return pos.getY() >= j && pos.getY() <= i && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }

    public abstract boolean isMovementBlocked();

    public abstract boolean isRidable();

    public abstract boolean canBeSteered();

    public static boolean checkSurfaceWaterAnimalSpawnRules(EntityType<? extends AquaticAnimal> pAquaticAnimal, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        int i = pLevel.getSeaLevel();
        int j = i - 13;
        return pPos.getY() >= j && pPos.getY() <= i && pLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pLevel.getBlockState(pPos.above()).is(Blocks.WATER) && AncientNatureConfig.PREHISTORIC_OVERWORLD_SPAWNING.get();
    }
}
