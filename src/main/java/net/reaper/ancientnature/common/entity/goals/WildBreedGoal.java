package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class WildBreedGoal extends Goal {
    private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();
    protected final Animal animal;
    private final Class<? extends Animal> partnerClass;
    protected final Level level;
    @Nullable
    protected Animal partner;
    protected final Predicate<Animal> predicate;
    int chance;

    public WildBreedGoal(Animal pAnimal, Predicate<Animal> predicate) {
        this(pAnimal, pAnimal.getClass(), predicate, 200);
    }

    public WildBreedGoal(Animal pAnimal, Predicate<Animal> predicate, int chanceToUse) {
        this(pAnimal, pAnimal.getClass(), predicate, chanceToUse);
    }

    public WildBreedGoal(Animal animal, Class<? extends Animal> partnerClass, Predicate<Animal> predicate) {
        this(animal, partnerClass, predicate, 200);
    }

    public WildBreedGoal(Animal animal, Class<? extends Animal> partnerClass, Predicate<Animal> predicate, int chanceToUse) {
        this.animal = animal;
        this.level = animal.level();
        this.partnerClass = partnerClass;
        this.predicate = predicate;
        this.chance = chanceToUse;
    }

    /**
     * Looking for an additional breeding condition? <p>
     * Override "canFallInLove" in your entity class!
     */

    @Override
    public void start() {
        super.start();
        if (this.partner != null) {

            this.animal.setInLove(null);
            this.partner.setInLove(null);
        }
    }

    @Override
    public boolean canUse() {
        if (!predicate.test(this.animal) || !(this.animal.getRandom().nextInt(chance) == 0)) {
            return false;
        } else {
            this.partner = this.getFreePartner();
            return this.partner != null && this.partner.getAge() == 0 && this.animal.getAge() == 0;
        }
    }

    private boolean canMateWith(Animal pAnimal, Animal pOtherAnimal) {
        if (pOtherAnimal == pAnimal) {
            return false;
        } else if (pOtherAnimal.getClass() != pAnimal.getClass()) {
            return false;
        } else {
            return pAnimal.canFallInLove() && pOtherAnimal.canFallInLove();
        }
    }

    // copied since breed class wanted to be a prude . . .
    private Animal getFreePartner() {
        List<? extends Animal> list = this.level.getNearbyEntities(this.partnerClass, PARTNER_TARGETING, this.animal, this.animal.getBoundingBox().inflate(8.0D));
        double d0 = Double.MAX_VALUE;
        Animal animal = null;

        for(Animal target : list) {
            if (this.canMateWith(this.animal, target) && this.animal.distanceToSqr(target) < d0) {
                animal = target;
                d0 = this.animal.distanceToSqr(target);
            }
        }

        return animal;
    }
}
