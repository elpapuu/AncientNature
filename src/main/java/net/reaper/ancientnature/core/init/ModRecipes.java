package net.reaper.ancientnature.core.init;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.block.RevivalStand;
import net.reaper.ancientnature.common.recipe.BrushingRecipe;
import net.reaper.ancientnature.common.recipe.RevivalStandRecipe;
import net.reaper.ancientnature.common.recipe.WaterWashingRecipe;

import java.util.function.Supplier;

public class ModRecipes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, AncientNature.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AncientNature.MOD_ID);

    public static final RegistryObject<RecipeType<BrushingRecipe>> BRUSHING_RECIPE = register("brushing_recipe", () -> BrushingRecipe.SERIALIZER);
    public static final RegistryObject<RecipeType<WaterWashingRecipe>> WATER_WASHING = register("water_washing", () -> WaterWashingRecipe.SERIALIZER);
    public static final RegistryObject<RecipeType<RevivalStandRecipe>> REVIVAL_STAND_RECIPE = register("revival_stand_recipe", () -> RevivalStandRecipe.SERIALIZER);


    public static void register(IEventBus bus) {
        RECIPE_TYPES.register(bus);
        RECIPE_SERIALIZERS.register(bus);
    }

    public static <T extends Recipe<?>> RegistryObject<RecipeType<T>> register(String name, Supplier<RecipeSerializer<T>> serializer) {
        RegistryObject<RecipeType<T>> object = RECIPE_TYPES.register(name, () -> RecipeType.simple(AncientNature.modLoc(name)));
        RECIPE_SERIALIZERS.register(name, serializer);
        return object;
    }
}
