package net.reaper.ancientnature.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.client.model.entity.TRexModel;
import net.reaper.ancientnature.client.model.layer.ModModelLayers;
import net.reaper.ancientnature.common.entity.ground.DodoEntity;
import net.reaper.ancientnature.common.entity.ground.TRexEntity;
import org.jetbrains.annotations.NotNull;

public class TRexRenderer<T extends TRexEntity> extends MobRenderer<TRexEntity, TRexModel<TRexEntity>>
{
    public static final ResourceLocation TYRANOSAUR_MALE = new ResourceLocation(AncientNature.MOD_ID,
            "textures/entity/trex/tyrannosaur_male_red.png");
    public static final ResourceLocation TYRANOSAUR_FEMALE = new ResourceLocation(AncientNature.MOD_ID,
            "textures/entity/trex/tyrannosaur_female_red.png");
    public static final ResourceLocation TYRANOSAUR_MALE_BLUE = new ResourceLocation(AncientNature.MOD_ID,
            "textures/entity/trex/tyrannosaur_male_blue.png");
    public static final ResourceLocation TYRANOSAUR_FEMALE_BLUE = new ResourceLocation(AncientNature.MOD_ID,
            "textures/entity/trex/tyrannosaur_female_blue.png");
    public static final ResourceLocation JURNARO_FEMALE = new ResourceLocation(AncientNature.MOD_ID,
            "textures/entity/trex/jurnaro_female.png");
    public static final ResourceLocation JURNARO_MALE = new ResourceLocation(AncientNature.MOD_ID,
            "textures/entity/trex/jurnaro_male.png");

    public static final ResourceLocation SADDLE_TEXTURE = new ResourceLocation(AncientNature.MOD_ID,
            "textures/entity/trex/tyranosaur_saddle_layer.png");

    public TRexRenderer(EntityRendererProvider.Context pContext) {
        super(pContext,new TRexModel<>(pContext.bakeLayer(ModModelLayers.TREX_LAYER)),2.5f);
        this.addLayer(new SaddleLayer<>(this,new TRexModel<>(pContext.bakeLayer(ModModelLayers.TREX_LAYER)),SADDLE_TEXTURE));
    }
    @Override
    public void render(@NotNull TRexEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        if(pEntity.isBaby())
        {
            pMatrixStack.scale(0.45f, 0.45f, 0.45f);
        }

    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(TRexEntity pEntity)
    {
        int variant = pEntity.getVariant();
        return switch (variant)
        {
            case 0 -> pEntity.getGender() ? TYRANOSAUR_MALE : TYRANOSAUR_FEMALE;
            default -> pEntity.getGender() ? TYRANOSAUR_MALE_BLUE : TYRANOSAUR_FEMALE_BLUE;
            case 1 -> (pEntity.hasCustomName() && pEntity.getName().getString().equals("Jurnaro")) ? JURNARO_MALE : TYRANOSAUR_MALE;
            case 2 -> (pEntity.hasCustomName() && pEntity.getName().getString().equals("Jurnaro")) ? JURNARO_MALE : TYRANOSAUR_MALE_BLUE;
            case 3 -> (pEntity.hasCustomName() && pEntity.getName().getString().equals("Jurnaro")) ? JURNARO_FEMALE : TYRANOSAUR_FEMALE;
            case 4 -> (pEntity.hasCustomName() && pEntity.getName().getString().equals("Jurnaro")) ? JURNARO_FEMALE : TYRANOSAUR_FEMALE_BLUE;

        };

    }
}
