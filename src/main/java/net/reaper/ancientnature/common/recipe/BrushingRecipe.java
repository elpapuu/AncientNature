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
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.core.init.ModRecipes;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BrushingRecipe implements Recipe<Container> {

    public static final Serializer SERIALIZER = new Serializer();

    protected final ResourceLocation id;
    protected final Ingredient input;
    protected final Ingredient brush;
    protected final ItemStack output;
    protected final float probabilityForOutput;
    protected Random cachedRandom;

    public BrushingRecipe(ResourceLocation id, Ingredient input, Ingredient brush, ItemStack output, float probabilityForOutput) {
        this.id = id;
        this.input = input;
        this.brush = brush;
        this.output = output;
        this.probabilityForOutput = probabilityForOutput;
    }


    /**
     * @param pContainer should have the main hand in slot 0 and the offhand in slot 1
     */
    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        if (pContainer.getContainerSize() == 2) {
            if (brush.test(pContainer.getItem(0)) && pContainer.getItem(0).isDamageableItem() && input.test(pContainer.getItem(1)))
                return true;
            if (input.test(pContainer.getItem(0)) && brush.test(pContainer.getItem(1)) && pContainer.getItem(1).isDamageableItem())
                return true;
        }
        return false;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        if (this.cachedRandom == null)
            this.cachedRandom = new Random();
        return this.cachedRandom.nextFloat() <= this.probabilityForOutput ? this.output.copy() : ItemStack.EMPTY;
    }

    public Ingredient getBrush() {
        return brush;
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
        return ModRecipes.BRUSHING_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<BrushingRecipe> {

        @Override
        public BrushingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            float probability = Mth.clamp(GsonHelper.getAsFloat(pSerializedRecipe, "probability", 0.2f), 0f, 1f);
            Ingredient input = Ingredient.fromJson(pSerializedRecipe.get("input"));
            Ingredient brush = Ingredient.fromJson(pSerializedRecipe.get("brush"));
            ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"), true);
            return new BrushingRecipe(pRecipeId, input, brush, output, probability);
        }

        @Override
        public @Nullable BrushingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            float probability = pBuffer.readFloat();
            Ingredient input = Ingredient.fromNetwork(pBuffer);
            Ingredient brush = Ingredient.fromNetwork(pBuffer);
            ItemStack output = pBuffer.readItem();
            return new BrushingRecipe(pRecipeId, input, brush, output, probability);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, BrushingRecipe pRecipe) {
            pBuffer.writeFloat(pRecipe.probabilityForOutput);
            pRecipe.input.toNetwork(pBuffer);
            pRecipe.brush.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
