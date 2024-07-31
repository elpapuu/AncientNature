package net.reaper.ancientnature.common.entity.multipart;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;
import net.reaper.ancientnature.common.entity.ground.TRexEntity;
import net.reaper.ancientnature.core.init.ModEntities;

public class TRexPart extends EntityMultiPart {
    private TRexEntity dino;

    public TRexPart(EntityType<?> t, Level world) {
        super(t, world);
    }

    public TRexPart(PlayMessages.SpawnEntity spawnEntity, Level worldIn) {
        this(ModEntities.TREX_MULTIPART.get(), worldIn);
    }

    public TRexPart(EntityType<?> type, TRexEntity dino, float radius, float angleYaw, float offsetY,
                    float sizeX, float sizeY, float damageMultiplier) {
        super(type, dino, radius, angleYaw, offsetY, sizeX, sizeY, damageMultiplier);
        this.dino = dino;
    }

    public TRexPart(TRexEntity parent, float radius, float angleYaw, float offsetY, float sizeX, float sizeY, float damageMultiplier) {
        super(ModEntities.TREX_MULTIPART.get(), parent, radius, angleYaw, offsetY, sizeX, sizeY,
                damageMultiplier);
        this.dino = parent;
    }

    @Override
    public void collideWithNearbyEntities() {
    }
}
