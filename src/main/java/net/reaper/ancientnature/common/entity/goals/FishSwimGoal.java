package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.animal.AbstractFish;

public class FishSwimGoal extends RandomSwimmingGoal {

    protected final AbstractFish fish;
    public FishSwimGoal(AbstractFish p_25753_) {
        super(p_25753_, 1.0D, 40);
        this.fish = p_25753_;
    }

    public boolean canUse() {
        return this.fish.canRandomSwim() && super.canUse();
    }
}
