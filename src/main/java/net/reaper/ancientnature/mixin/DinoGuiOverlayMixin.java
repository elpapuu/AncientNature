package net.reaper.ancientnature.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.entity.ground.AbstractTrexEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class DinoGuiOverlayMixin {

    @Shadow protected int screenWidth;
    @Shadow protected int screenHeight;

    private static final ResourceLocation HEALTH_BAR = new ResourceLocation(AncientNature.MOD_ID, "textures/gui/primordialinstinct.png");

    @Inject(method = "renderVehicleHealth", at = @At("HEAD"), cancellable = true)
    private void renderDinoHealthBar(GuiGraphics matrices, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player != null && player.getVehicle() instanceof AbstractTrexEntity) {
            LivingEntity entity = (AbstractTrexEntity) player.getVehicle();
            renderCustomGuiOverlay(matrices, entity, screenWidth, screenHeight);

            ci.cancel();
        }
    }

    private void renderCustomGuiOverlay(GuiGraphics matrices, LivingEntity entity, int screenWidth, int screenHeight) {
        float health = entity.getHealth();
        float maxHealth = entity.getMaxHealth();
        int barWidth = 100;
        int barHeight = 10;

        int x = (screenWidth - barWidth) / 2;
        int y = screenHeight - 50;

        int healthBarWidth = (int) ((health / maxHealth) * barWidth);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, HEALTH_BAR);

        // Render the background of the health bar
        matrices.blit(HEALTH_BAR, x, y, 0, 0, barWidth, barHeight);

        // Render the actual health portion of the bar
        matrices.blit(HEALTH_BAR, x, y, 0, barHeight, healthBarWidth, barHeight);
    }
}
