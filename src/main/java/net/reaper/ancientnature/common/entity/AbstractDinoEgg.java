package net.reaper.ancientnature.common.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;
import net.reaper.ancientnature.common.entity.ground.enums.EnumDinoEgg;
import net.reaper.ancientnature.common.entity.multipart.TRexPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class AbstractDinoEgg extends LivingEntity
{
    protected static final EntityDataAccessor<java.util.Optional<UUID>> OWNER_UNIQUE_ID = SynchedEntityData.defineId(AbstractDinoEgg.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> DRAGON_AGE = SynchedEntityData.defineId(AbstractDinoEgg.class, EntityDataSerializers.INT);
    public AbstractDinoEgg(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public static AttributeSupplier.Builder bakeAttributes() {
        return Mob.createMobAttributes()
                //HEALTH
                .add(Attributes.MAX_HEALTH, 10.0D)
                //SPEED
                .add(Attributes.MOVEMENT_SPEED, 0D);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("DragonAge", this.getDragonAge());
        try {
            if (this.getOwnerId() == null) {
                tag.putString("OwnerUUID", "");
            } else {
                tag.putString("OwnerUUID", this.getOwnerId().toString());
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setDragonAge(tag.getInt("DragonAge"));
        String s;

        if (tag.contains("OwnerUUID", 8)) {
            s = tag.getString("OwnerUUID");
        } else {
            String s1 = tag.getString("Owner");
            UUID converedUUID = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s1);
            s = converedUUID == null ? s1 : converedUUID.toString();
        }
        if (!s.isEmpty()) {
            this.setOwnerId(UUID.fromString(s));
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DRAGON_AGE, 0);
        this.getEntityData().define(OWNER_UNIQUE_ID, Optional.empty());
    }

    @Nullable
    public UUID getOwnerId() {
        return this.entityData.get(OWNER_UNIQUE_ID).orElse(null);
    }

    public void setOwnerId(@Nullable UUID p_184754_1_) {
        this.entityData.set(OWNER_UNIQUE_ID, java.util.Optional.ofNullable(p_184754_1_));
    }

    @Override
    public boolean isInvulnerableTo(DamageSource i) {
        return i.getEntity() != null && super.isInvulnerableTo(i);
    }

    public int getDragonAge() {
        return this.getEntityData().get(DRAGON_AGE).intValue();
    }

    public void setDragonAge(int i) {
        this.getEntityData().set(DRAGON_AGE, i);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide()) {
            this.setAirSupply(200);
        }
    }

    @Override
    public SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return null;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return ImmutableList.of();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
}
