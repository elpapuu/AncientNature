package net.reaper.ancientnature.common.entity.water;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.reaper.ancientnature.common.entity.goals.CooldownNearestAttackableTraget;
import net.reaper.ancientnature.common.entity.goals.FishRandomSwimmingGoal;
import net.reaper.ancientnature.common.entity.goals.KillerGoal;
import net.reaper.ancientnature.common.entity.util.FishRandomSwimWeights;
import net.reaper.ancientnature.common.entity.util.KillerEntity;

public class Anomalocris extends WaterAnimal implements KillerEntity {

    public static final int HUNT_COOLDOWN = 7*60*20, ATTACK_COOLDOWN = 20;

    public static AttributeSupplier.Builder createAttributes() {
        return WaterAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 18)
                .add(Attributes.MOVEMENT_SPEED, 0.75d)
                .add(Attributes.FOLLOW_RANGE, 25d)
                .add(Attributes.ATTACK_DAMAGE, 4d);

    }

    protected int huntCooldown = 0, attackCooldown;

    public Anomalocris(EntityType<? extends WaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));

        //this.goalSelector.addGoal(1, new KillerGoal<>(this, 1.2d, true));


        //this.goalSelector.addGoal(5, new CooldownNearestAttackableTraget<>(this, AbstractFish.class, true));

        this.goalSelector.addGoal(6, new FishRandomSwimmingGoal(this, 1.0d, new FishRandomSwimWeights(1.1d, 1.4d, 2.4d, 1.1d)));

    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()){
            if (this.huntCooldown > 0)
                this.huntCooldown--;
            if (this.isPassenger()){
                if (this.attackCooldown <= 0){
                    this.doHurtTarget(this.getVehicle());
                    this.attackCooldown = this.getAttackCooldown();
                }else {
                    this.attackCooldown--;
                }
            }
        }
    }

    public boolean canCatch(Entity entity){
        EntityDimensions dim = entity.getType().getDimensions();
        return dim.width <= 0.7F && dim.height <= .4f;
    }

    @Override
    public boolean canHunt() {
        if (this.isPassenger())
            return false;
        return this.huntCooldown <= 0;
    }

    @Override
    public void onAttack(LivingEntity prey) {
        if (this.canCatch(prey)){
            this.startRiding(prey, true);
        }else {
            this.attack(this, prey);
        }
    }

    @Override
    protected void positionRider(Entity pPassenger, MoveFunction pCallback) {
        if (this.hasPassenger(pPassenger)) {
            double d0 = this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset();
            Vec3 offset = new Vec3(getXPreyOffset(), 0, 0f);
            offset = offset.yRot(-this.getYRot() * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
            pCallback.accept(pPassenger, this.getX() + offset.x, d0, this.getZ() + offset.z);
            pPassenger.setYRot(pPassenger.getYRot() + getRotationAnglePreyOffset());
            pPassenger.setYHeadRot(pPassenger.getYHeadRot() + getRotationAnglePreyOffset());
        }
    }

    protected float getXPreyOffset(){
        return .2f;
    }

    protected int getRotationAnglePreyOffset(){
        return 90;
    }

    @Override
    public int getAttackCooldown() {
        return ATTACK_COOLDOWN;
    }
}
