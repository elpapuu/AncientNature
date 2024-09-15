package net.reaper.ancientnature.common.entity.ground;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
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
import net.minecraft.world.level.LevelAccessor;
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
import net.reaper.ancientnature.common.config.AncientNatureConfig;
import net.reaper.ancientnature.common.entity.BaseTameableDinoEntity;
import net.reaper.ancientnature.common.entity.goals.OrderRandomStrollAvoidWater;
import net.reaper.ancientnature.common.entity.goals.PanicSprintingGoal;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimalPose;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;
import net.reaper.ancientnature.common.entity.util.AnimalDiet;
import net.reaper.ancientnature.core.init.ModSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.List;

public class DodoEntity extends SmartAnimatedAnimal {

    public DodoEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new MoveControl(this) {
            @Override
            public void tick() {
                if (!DodoEntity.this.isSleeping()) {
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
                .add(Attributes.ATTACK_DAMAGE, 1);
    }


    @Override
    public void defineDiet(AnimalDiet.Builder pDietBuilder) {
        super.defineDiet(pDietBuilder);
        pDietBuilder.addItems(Items.MELON, Items.GLISTERING_MELON_SLICE, Items.MELON_SLICE, Items.MELON_SEEDS, Items.SWEET_BERRIES, Items.GLOW_BERRIES);
    }

    @Override
    protected int getAnimationLengthInTicks(SmartAnimalPose smartPose) {
        return switch (smartPose){
            case IDLE -> (int)(2.88F*20.0);
            case WALK -> (int)(0.96F*20.0);
            case RUN -> (int)(0.72F*20.0);
            case SWIM -> (int)(1.28F*20.0);
            case COMMUNICATE -> (int)(0.5417F*20.0);
            case EAT -> (int)(2.16F*20.0);
            case ATTACK -> (int)(.72*20.0);
            case ATTACK2 -> 0;
            case ATTACK3 -> 0;
            case ROAR -> 0;
            case DOWN -> (int)(0.96F*20.0);
            case FALL_ASLEEP -> (int)(0.68F*20.0);
            case WAKE_UP -> (int)(0.72F*20.0);
            case UP -> (int)(0.64F*20.0);
            case SIT -> (int)(2.88F*20.0);
            case SLEEP -> (int)(2.88F*20.0);
        };

    }
    @Nullable
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource pDamageSource) {
        return this.random.nextInt(2) == 0 ? ModSounds.DODO_HURT_1.get() : ModSounds.DODO_HURT_2.get();
    }
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return this.random.nextInt(2) == 0 ? ModSounds.DODO_DIYING.get() : ModSounds.DODO_DIYING.get();
    }
    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.random.nextInt(2) == 0 ? ModSounds.DODO_IDLE_1.get() : ModSounds.DODO_IDLE_2.get();
    }

    @Override
    public int getEatAnimationConsumeDelay() {
        return 40;
    }

    @Override
    public boolean canAttack() {
        return false;
    }
}
