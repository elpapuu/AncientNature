package net.reaper.ancientnature.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.reaper.ancientnature.common.entity.ground.AbstractTrexEntity;
import net.reaper.ancientnature.common.entity.ground.TRexEntity;
import net.reaper.ancientnature.common.event.CameraShakeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class TyrantAxe extends Item {
    private static final int MAX_INTIMIDATION_TICKS = 60;
    private int intimatedTicks;
    private static final RandomSource RANDOM = RandomSource.create();

    public TyrantAxe(Properties pProperties) {
        super(pProperties);
        this.intimatedTicks = MAX_INTIMIDATION_TICKS;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        List<LivingEntity> nearbyEntities = player.level().getEntitiesOfClass(LivingEntity.class,
                player.getBoundingBox().inflate(8)
        );
        for (LivingEntity nearbyEntity : nearbyEntities) {
            nearbyEntity.hurt(player.level().damageSources().playerAttack(player), player.getAttackStrengthScale(1));
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        BlockPos clickedPos = pContext.getClickedPos();
        Level level = Objects.requireNonNull(player).level();

        if (!level.isClientSide()) {
            if (level.getBlockState(clickedPos) != null) {
                AABB aabb = new AABB(clickedPos).inflate(8);
                List<LivingEntity> entitiesNearby = level.getEntitiesOfClass(LivingEntity.class, aabb);

                if (!entitiesNearby.isEmpty()) {
                    for (LivingEntity entity : entitiesNearby) {
                        if (!(entity instanceof AbstractTrexEntity)) {
                            applyKnockbackAndDamage(entity, player);
                        } else {
                            handleTrexFear((AbstractTrexEntity) entity);
                        }
                    }

                    if (player instanceof ServerPlayer) {
                        CameraShakeManager.applyCameraShake((ServerPlayer) player, 50, 3.0f);
                    }

                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void applyKnockbackAndDamage(LivingEntity entity, Player player) {
        double deltaX = entity.getX() - player.getX();
        double deltaZ = entity.getZ() - player.getZ();

        double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        if (distance != 0) {
            deltaX /= distance;
            deltaZ /= distance;
        }

        entity.knockback(2.5, deltaX, deltaZ);
        entity.hurt(player.level().damageSources().playerAttack(player), 12);
    }

    private void handleTrexFear(AbstractTrexEntity trexEntity) {
        if (intimatedTicks > 0) {
            fearTrex(trexEntity);
            intimatedTicks--;
        } else {
            unFearTrex(trexEntity);
        }
    }

    private void fearTrex(AbstractTrexEntity entity) {
        entity.setIntimated(true);
    }

    private void unFearTrex(AbstractTrexEntity entity) {
        entity.setIntimated(false);
    }

    public void resetIntimidation() {
        this.intimatedTicks = MAX_INTIMIDATION_TICKS;
    }
}
