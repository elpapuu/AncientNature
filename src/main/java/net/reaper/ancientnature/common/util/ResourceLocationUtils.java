package net.reaper.ancientnature.common.util;

import net.minecraft.resources.ResourceLocation;

public class ResourceLocationUtils {

    public static ResourceLocation prepend(ResourceLocation location, String prependation){
        return new ResourceLocation(location.getNamespace(), prependation + location.getPath());
    }

    public static ResourceLocation extend(ResourceLocation location, String extend){
        return new ResourceLocation(location.getNamespace(), location.getPath() + extend);
    }
}
