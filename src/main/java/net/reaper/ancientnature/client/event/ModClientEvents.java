package net.reaper.ancientnature.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.entity.smartanimal.SmartAnimatedAnimal;
import net.reaper.ancientnature.common.entity.util.ICustomPlayerRidePos;
import net.reaper.ancientnature.common.entity.util.IMouseInput;
import net.reaper.ancientnature.common.entity.water.Paranogmius;
import net.reaper.ancientnature.common.util.RenderUtil;
import net.reaper.ancientnature.common.util.ScreenShakeUtils;
import net.reaper.ancientnature.common.util.ScreenUtils;

@Mod.EventBusSubscriber(modid = AncientNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {
    public static final ResourceLocation SWIMMING_INSTINCT_BAR = new ResourceLocation(AncientNature.MOD_ID, "textures/gui/swimming_instinct_bar.png");
    public static final ResourceLocation TIME_BAR = new ResourceLocation(AncientNature.MOD_ID, "textures/gui/time_bar.png");
    public static final ResourceLocation PRIMORDIAL_INSTINCT_BAR = new ResourceLocation(AncientNature.MOD_ID, "textures/gui/primordial_instinct_bar.png");
    public static final ResourceLocation HUNGER_BAR = new ResourceLocation(AncientNature.MOD_ID, "textures/gui/hunger_bar.png");
    public static final ResourceLocation STAMINA_BAR = new ResourceLocation(AncientNature.MOD_ID, "textures/gui/stamina_bar.png");

    @SubscribeEvent
    public static void onComputeCameraAngle(ViewportEvent.ComputeCameraAngles pEvent) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            if (ScreenShakeUtils.shakeTicks > 0.0F && !Minecraft.getInstance().isPaused()) {
                double intensity = ScreenShakeUtils.shakePower * Minecraft.getInstance().options.screenEffectScale().get();
                double strength = player.getRandom().nextFloat() * 0.2F * intensity / 2;
                pEvent.getCamera().move(strength, strength, strength);
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent pEvent) {
        if (pEvent.phase == TickEvent.Phase.START) {
            if (!Minecraft.getInstance().isPaused() && ScreenShakeUtils.shakeTicks > 0.0F) {
                ScreenShakeUtils.shakeTicks -= 5.0F;
            } else {
                ScreenShakeUtils.shakePower = 0.0F;
            }
        }
    }

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre pEvent) {
        int screenWidth = pEvent.getWindow().getGuiScaledWidth();
        int screenHeight = pEvent.getWindow().getGuiScaledHeight();
        int guiOffsetY  = (Minecraft.getInstance().gui instanceof ForgeGui forgeGui) ? Math.max(forgeGui.leftHeight, forgeGui.rightHeight) : 0;
        int d0 = screenWidth / 2;
        int d1 = screenHeight - guiOffsetY;
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            Entity vehicle = player.getVehicle();
            if (vehicle instanceof SmartAnimatedAnimal smartAnimal) {
                int lifeProgress = ScreenUtils.getScaledInt((int) smartAnimal.getHealth(), (int) smartAnimal.getMaxHealth(), 167);
                int hungerProgress = ScreenUtils.getScaledInt((int) smartAnimal.getHunger(), 100, 167);
                int staminaProgress = ScreenUtils.getScaledInt((int) smartAnimal.getStamina(), 100, 167);
                ScreenUtils.renderBar(pEvent, PRIMORDIAL_INSTINCT_BAR, d0 + 280, d1 - 40, d0 + 283, d1 - 39, lifeProgress, 30.5F, 180, 24, 27);
                ScreenUtils.renderBar(pEvent, HUNGER_BAR, d0 + 280, d1 - 15, d0 + 283, d1 - 15, hungerProgress, 25.7F, 180, 24, 27);
                ScreenUtils.renderBar(pEvent, STAMINA_BAR, d0 + 280, d1 + 7, d0 + 283, d1 + 9, staminaProgress, 25.7F, 180, 24, 27);
                if (pEvent.getOverlay().id().equals(VanillaGuiOverlay.MOUNT_HEALTH.id())) {
                    pEvent.setCanceled(true);
                }
            }
            if (vehicle instanceof Paranogmius paranogmius) {
                int lifeProgress = ScreenUtils.getScaledInt((int) paranogmius.getHealth(), (int) paranogmius.getMaxHealth(), 164);
                int timerProgress = ScreenUtils.getScaledInt(paranogmius.getRemainingRideTicks(), 12000, 164);
                ScreenUtils.renderBar(pEvent, SWIMMING_INSTINCT_BAR, d0 + 120, d1 + 15, d0 + 126, d1 + 15, lifeProgress, 30.5F, 180, 24, 27);
                ScreenUtils.renderBar(pEvent, TIME_BAR, d0 - 297, d1 + 15, d0 - 291, d1 + 18, timerProgress, 30.5F, 180, 24, 27);
                if (pEvent.getOverlay().id().equals(VanillaGuiOverlay.MOUNT_HEALTH.id())) {
                    pEvent.setCanceled(true);
                }
            }
        }
    }

    private static int getIdForAction(InputEvent.InteractionKeyMappingTriggered pEvent) {
        if (pEvent.isUseItem()) {
            return 0;
        } else if (pEvent.isAttack()) {
            return 1;
        } else {
            return -1;
        }
    }

    @SubscribeEvent
    public static void onInteractionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered pEvent) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null) {
            if (localPlayer.getVehicle() instanceof IMouseInput mouseInput) {
                if (mouseInput.isActionDenied(getIdForAction(pEvent))) {
                    pEvent.setSwingHand(false);
                    pEvent.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMouseButton(InputEvent.MouseButton pEvent) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null && Minecraft.getInstance().screen == null) {
            if (localPlayer.getVehicle() instanceof IMouseInput mouseInput) {
                mouseInput.onMouseClick(pEvent.getButton());
            }
        }
    }

    @SubscribeEvent
    public static <T extends LivingEntity> void onPlayerPose(PlayerPoseEvent<T> pEvent) {
        T entity = pEvent.getEntity();
        if (entity instanceof Player) {
            if (RenderUtil.getEntityRenderer(entity.getVehicle()) instanceof ICustomPlayerRidePos customRidePos) {
                customRidePos.applyRiderPose(pEvent.getHumanoidModel(), entity);
            }
        }
    }

    @SubscribeEvent
    public static <T extends LivingEntity> void onModelRotation(ModelRotationEvent<T> pEvent) {
        T entity = pEvent.getEntity();
        if (entity instanceof Player) {
            pEvent.setCanceled(RenderUtil.getEntityRenderer(entity.getVehicle()) instanceof ICustomPlayerRidePos);
        }
    }

    @SubscribeEvent
    public static <T extends LivingEntity, M extends EntityModel<T>> void onRenderLivingPre(RenderLivingEvent.Pre<T, M> pEvent) {
        LivingEntity entity = pEvent.getEntity();
        RenderLivingEvent.Post<T, M> event = new RenderLivingEvent.Post<>(entity, pEvent.getRenderer(), pEvent.getPartialTick(), pEvent.getPoseStack(), pEvent.getMultiBufferSource(), pEvent.getPackedLight());
        synchronized (RenderUtil.hiddenEntities) {
            if (RenderUtil.hiddenEntities.remove(entity.getUUID()) && RenderUtil.shouldSkipRendering(false, Minecraft.getInstance().getCameraEntity())) {
                MinecraftForge.EVENT_BUS.post(event);
                pEvent.setCanceled(true);
            }
        }
    }
}
