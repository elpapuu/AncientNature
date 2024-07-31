package net.reaper.ancientnature.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.entity.ground.AbstractDinoAnimal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class DinoGuiOverlayMixin
{
    private final ResourceLocation DINO_HEALTH = new ResourceLocation(AncientNature.MOD_ID,
            "textures/gui/primordialinstinct.png");
    @Unique
    Minecraft ancientNature$mc = Minecraft.getInstance();
    @Unique
    Entity ancientNature$entity;

    {
        assert ancientNature$mc.player != null;
        ancientNature$entity = ancientNature$mc.player.getVehicle();
    }

    @Inject(method = "render",at = @At("HEAD"), cancellable = true)
    private void render(GuiGraphics pGuiGraphics, float pPartialTick,CallbackInfo ci)
    {
        this.renderVehicleHealth(pGuiGraphics,ci);
        ci.cancel();
    }

    @Inject(method = "renderVehicleHealth", at = @At("HEAD"), cancellable = true)
    private void renderVehicleHealth(GuiGraphics pGuiGraphics, CallbackInfo ci)
    {

        if (ancientNature$mc.player != null && ancientNature$entity instanceof AbstractDinoAnimal animal)
        {
            int vehicleHealth = (int) Math.ceil(animal.getHealth());
            int maxVehicleHealth = (int) Math.ceil(animal.getMaxHealth());

            int screenWidth = ancientNature$mc.getWindow().getGuiScaledWidth();
            int screenHeight = ancientNature$mc.getWindow().getGuiScaledHeight();

            int healthBarWidth = 91;
            int healthBarHeight = 9;
            int healthBarX = screenWidth / 2 - 91 / 2;
            int healthBarY = screenHeight - 39;

            RenderSystem.setShaderTexture(0, DINO_HEALTH);
            pGuiGraphics.blit(DINO_HEALTH, healthBarX, healthBarY, 0, 45, healthBarWidth, healthBarHeight);

            int healthBarFill = (int) ((double) vehicleHealth / maxVehicleHealth * (healthBarWidth - 1));
            pGuiGraphics.blit(DINO_HEALTH, healthBarX + 1, healthBarY + 1, 0, 54, healthBarFill, healthBarHeight - 1);

//            RenderSystem.setShaderTexture(0, DINOSAUR_ICON);
//            pGuiGraphics.blit(DINOSAUR_ICON, healthBarX - 10, healthBarY - 1, 0, 0, 9, 9, 9, 9);

            Font font = ancientNature$mc.font;
            Component healthText = Component.literal(vehicleHealth + " / " + maxVehicleHealth);
            int textWidth = font.width(healthText);
            int textX = healthBarX + (healthBarWidth / 2) - (textWidth / 2);
            int textY = healthBarY - 10;

            ci.cancel();
        }
    }
}
