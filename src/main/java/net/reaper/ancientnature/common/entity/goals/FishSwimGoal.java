package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.reaper.ancientnature.common.entity.water.AquaticAnimal;

public class FishSwimGoal extends RandomSwimmingGoal {

    protected final AquaticAnimal fish;
    public FishSwimGoal(AquaticAnimal p_25753_) {
        super(p_25753_, 1.0D, 40);
        this.fish = p_25753_;
    }

    public boolean canUse() {
        return this.fish.isSwimming() && super.canUse();
    }
}
