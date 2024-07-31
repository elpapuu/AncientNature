package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.Vec3;
import net.reaper.ancientnature.common.entity.water.Paranogmius;
import net.reaper.ancientnature.core.init.ModTags;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class ParanogmiusAvoidPlayerGoal extends Goal {
    private final Paranogmius paranogmius;

    public ParanogmiusAvoidPlayerGoal(Paranogmius pParanogmius) {
        this.paranogmius = pParanogmius;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return false;
    }
}
