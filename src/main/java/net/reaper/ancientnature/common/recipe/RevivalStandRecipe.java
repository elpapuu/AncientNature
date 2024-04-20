package net.reaper.ancientnature.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.reaper.ancientnature.common.blockentity.RevivalStandBlockEntity;
import net.reaper.ancientnature.common.util.IngredientCount;
import net.reaper.ancientnature.core.init.ModRecipes;
import org.jetbrains.annotations.Nullable;

import javax.json.JsonException;

public class RevivalStandRecipe implements Recipe<RevivalStandBlockEntity> {

    public static final Serializer SERIALIZER = new Serializer();

    protected final IngredientCount fossil, amber;
    protected final int amberInfusionTime, fossilInfusionTime;
    protected final Ingredient baseRoe;
    protected final ItemStack eggs;
    protected final ResourceLocation id;

    public RevivalStandRecipe(IngredientCount fossil, IngredientCount amber, int amberInfusionTime, int fossilInfusionTime, Ingredient baseRoe, ItemStack eggs, ResourceLocation id) {
        this.fossil = fossil;
        this.amber = amber;
        this.amberInfusionTime = amberInfusionTime;
        this.fossilInfusionTime = fossilInfusionTime;
        this.baseRoe = baseRoe;
        this.eggs = eggs;
        this.id = id;
    }

    @Override
    public boolean matches(RevivalStandBlockEntity pContainer, Level pLevel) {
        if (amber.test(pContainer.getItem(1)) && fossil.test(pContainer.getItem(2))) {
            int counter = 0;
            for (int i = 3; i <= 5; i++) {
                if (this.baseRoe.test(pContainer.getItem(i)))
                    counter++;
            }
            return counter >= this.eggs.getCount();
        }
        return false;
    }

    @Override
    public ItemStack assemble(RevivalStandBlockEntity pContainer, RegistryAccess pRegistryAccess) {
        amber.shrink(pContainer.getItem(1));
        fossil.shrink(pContainer.getItem(2));
        return this.eggs;
    }

    public Ingredient getBaseRoe() {
        return baseRoe;
    }

    public int getAmberInfusionTime() {
        return amberInfusionTime;
    }

    public int getFossilInfusionTime() {
        return fossilInfusionTime;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.eggs;
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
        return ModRecipes.REVIVAL_STAND_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<RevivalStandRecipe> {

        @Override
        public RevivalStandRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            IngredientCount fossil = IngredientCount.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "fossil"));
            IngredientCount amber = IngredientCount.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "amber"));
            Ingredient baseRoe = Ingredient.EMPTY;
            if (pSerializedRecipe.has("base_roe"))
                baseRoe = Ingredient.fromJson(pSerializedRecipe.get("base_roe"));
            int amberInfusionTime = GsonHelper.getAsInt(pSerializedRecipe, "amberInfusionTime", 200);
            int fossilInfusionTime = GsonHelper.getAsInt(pSerializedRecipe, "fossilInfusionTime", 200);
            ItemStack eggs = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pSerializedRecipe, "egg"), true);
            if (eggs.getCount() > 3)
                throw new JsonException(String.format("the count of the eggs for recipe [%s] has to have a count between [1, 3] and not %s", pRecipeId, eggs.getCount()));
            return new RevivalStandRecipe(fossil, amber, amberInfusionTime, fossilInfusionTime, baseRoe, eggs, pRecipeId);
        }

        @Override
        public @Nullable RevivalStandRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            IngredientCount amber = IngredientCount.fromBuffer(pBuffer);
            IngredientCount fossil = IngredientCount.fromBuffer(pBuffer);
            Ingredient baseRoe = Ingredient.fromNetwork(pBuffer);
            ItemStack eggs = pBuffer.readItem();
            int amberInfusion = pBuffer.readInt();
            int fossilInfusion = pBuffer.readInt();
            return new RevivalStandRecipe(fossil, amber, amberInfusion, fossilInfusion, baseRoe, eggs, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, RevivalStandRecipe pRecipe) {
            pRecipe.amber.toNetwork(pBuffer);
            pRecipe.fossil.toNetwork(pBuffer);
            pRecipe.baseRoe.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.eggs);
            pBuffer.writeInt(pRecipe.amberInfusionTime);
            pBuffer.writeInt(pRecipe.fossilInfusionTime);
        }
    }
}
