package net.reaper.ancientnature.common.entity.goals;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.Goal;
import net.reaper.ancientnature.common.entity.ground.AbstractDinoAnimal;
import net.reaper.ancientnature.common.messages.NetworkHandler;
import net.reaper.ancientnature.common.messages.packets.CameraShakePacket;

import java.util.EnumSet;
import java.util.List;

public class RoarGoal extends Goal {
    private final AbstractDinoAnimal mob;
    private final double roarRadius;
    private int roarCooldown;

    public RoarGoal(AbstractDinoAnimal mob, double roarRadius, int roarCooldown) {
        this.mob = mob;
        this.roarRadius = roarRadius;
        this.roarCooldown = roarCooldown;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.roarCooldown <= 0 && this.mob.getTarget() != null;
    }

    @Override
    public void start() {
        this.roarCooldown = 100; // Cooldown in ticks (5 seconds)
        this.mob.setRoaring(true);// Custom method to set roaring animation, if needed
        this.mob.roar();
        //roar();
    }

    @Override
    public void tick() {
        if (this.roarCooldown > 0) {
            this.roarCooldown--;
        }
    }

    @Override
    public void stop() {
        this.mob.setRoaring(false);
    }

    private void roar() {
        List<ServerPlayer> players = this.mob.level().getEntitiesOfClass(ServerPlayer.class, this.mob.getBoundingBox().inflate(roarRadius));
        for (ServerPlayer player : players) {
            NetworkHandler.sendMSGToPlayer(new CameraShakePacket(), player);
        }
    }
}
