package net.reaper.ancientnature.common.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RenderUtil  {
    public static final Set<UUID> hiddenEntities  = Collections.synchronizedSet(new HashSet<>());

    public static <T extends Entity> void renderEntity(T pEntity, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super T> renderer = entityRenderDispatcher.getRenderer(pEntity);
        try {
            renderer.render(pEntity, 0.0F, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        } catch (Throwable throwable) {
            throw new ReportedException(CrashReport.forThrowable(throwable, "Error rendering entity"));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static <T extends Entity> boolean shouldSkipRendering(boolean pFirstPerson, T pEntity) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        boolean isFirstPerson = Minecraft.getInstance().options.getCameraType().isFirstPerson();
        return pFirstPerson ? isFirstPerson : !isFirstPerson && pEntity == localPlayer;
    }

    @OnlyIn(Dist.CLIENT)
    public static <T extends Entity> EntityRenderer<? super T> getEntityRenderer(@Nullable T pEntity) {
        if (pEntity == null) {
            return null;
        }
        return Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(pEntity);
    }
}
