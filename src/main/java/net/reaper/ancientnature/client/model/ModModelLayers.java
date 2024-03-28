package net.reaper.ancientnature.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.reaper.ancientnature.AncientNature;

@OnlyIn(Dist.CLIENT)
public class ModModelLayers {
    public static final ModelLayerLocation ARANDASPIS_LAYER = new ModelLayerLocation(
            new ResourceLocation(AncientNature.MOD_ID, "arandaspis_layer"), "main");
}
