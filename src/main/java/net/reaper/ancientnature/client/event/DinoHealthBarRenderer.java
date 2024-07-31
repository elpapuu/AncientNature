package net.reaper.ancientnature.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.entity.ground.AbstractDinoAnimal;

@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID, value = Dist.CLIENT)
public class DinoHealthBarRenderer extends Screen
{
    public static final ResourceLocation HEALTH_BAR = new ResourceLocation(AncientNature.MOD_ID,
            "textures/gui/primordialinstinct.png");
    public DinoHealthBarRenderer(Component pTitle) {
        super(pTitle);
    }

    @Override
    protected void init() {
        super.init();
    }

    @SubscribeEvent
    public static void onRenderGameOverlay(ScreenEvent.Render event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player != null && player.getVehicle() instanceof AbstractDinoAnimal) {
            LivingEntity entity = (AbstractDinoAnimal) player.getVehicle();
            renderCustomGuiOverlay(event.getGuiGraphics(), entity);
        }
    }

    public static void renderCustomGuiOverlay(GuiGraphics guiGraphics, LivingEntity entity) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        float health = entity.getHealth();
        float maxHealth = entity.getMaxHealth();
        int barWidth = 100;
        int barHeight = 10;

        int x = (screenWidth - barWidth) / 2;
        int y = screenHeight - 50;

        int healthBarWidth = (int) ((health / maxHealth) * barWidth);

        RenderSystem.setShaderTexture(0, HEALTH_BAR);

        guiGraphics.blit(HEALTH_BAR, x, y, 0, 0, barWidth, barHeight);

        guiGraphics.blit(HEALTH_BAR, x, y, 0, barHeight, healthBarWidth, barHeight);
    }


}
