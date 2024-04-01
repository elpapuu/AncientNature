package net.reaper.ancientnature.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.reaper.ancientnature.core.init.ModRecipes;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class WaterWashingRecipe implements Recipe<Container> {

    public static final Serializer SERIALIZER = new Serializer();

    protected final ResourceLocation id;
    protected final Ingredient input;
    protected final ItemStack output;
    protected final float probabilityForOutput;
    protected Random cachedRandom;

    public WaterWashingRecipe(ResourceLocation id, Ingredient input, ItemStack output, float probabilityForOutput) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.probabilityForOutput = probabilityForOutput;
    }


    /**
     * @param pContainer should have the main hand in slot 0 and the offhand in slot 1
     */
    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        if (pContainer.getContainerSize() == 1) {
            return this.input.test(pContainer.getItem(0));
        }
        return false;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        if (this.cachedRandom == null)
            this.cachedRandom = new Random();
        return this.cachedRandom.nextFloat() <= this.probabilityForOutput ? this.output.copy() : ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.WATER_WASHING.get();
    }

    public static class Serializer implements RecipeSerializer<WaterWashingRecipe> {

        @Override
        public WaterWashingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            float probability = Mth.clamp(GsonHelper.getAsFloat(pSerializedRecipe, "probability", 0.2f), 0f, 1f);
            Ingredient input = Ingredient.fromJson(pSerializedRecipe.get("input"));
            ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"), true);
            return new WaterWashingRecipe(pRecipeId, input, output, probability);
        }

        @Override
        public @Nullable WaterWashingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            float probability = pBuffer.readFloat();
            Ingredient input = Ingredient.fromNetwork(pBuffer);
            ItemStack output = pBuffer.readItem();
            return new WaterWashingRecipe(pRecipeId, input, output, probability);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, WaterWashingRecipe pRecipe) {
            pBuffer.writeFloat(pRecipe.probabilityForOutput);
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
