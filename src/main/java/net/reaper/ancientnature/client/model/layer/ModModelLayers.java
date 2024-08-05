package net.reaper.ancientnature.client.model.layer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.reaper.ancientnature.AncientNature;

public class ModModelLayers {
    public static final ModelLayerLocation TREX_LAYER = new ModelLayerLocation(new ResourceLocation(AncientNature.MOD_ID, "trex"), "main");
    public static final ModelLayerLocation BABY_LYTHRONAX_LAYER = newLayer("baby_lythronax");

    public static ModelLayerLocation newLayer(String pName) {
        return new ModelLayerLocation(new ResourceLocation(AncientNature.MOD_ID, pName + "_layer"), "main");
    }
}
