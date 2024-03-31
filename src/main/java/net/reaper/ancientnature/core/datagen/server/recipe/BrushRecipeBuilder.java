package net.reaper.ancientnature.core.datagen.server.recipe;

import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.recipe.BrushingRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BrushRecipeBuilder {

    public static BrushRecipeBuilder builder(ItemLike output) {
        return builder(output, 1);
    }
    /**
     * look at {@link BrushRecipeBuilder#builder(ItemStack)}
     */
    public static BrushRecipeBuilder builder(ItemLike output, int count) {
        return builder(new ItemStack(output, count));
    }

    /**
     * @param output the item that might drop out of this recipe
     */
    public static BrushRecipeBuilder builder(ItemStack output) {
        return new BrushRecipeBuilder(output);
    }

    protected float probability = .2f;
    protected Ingredient brush = Ingredient.of(Items.BRUSH);
    protected Ingredient input;
    protected final ItemStack output;

    public BrushRecipeBuilder(ItemStack output) {
        this.output = output;
    }

    /**
     * @param probability the probability that this item will drop with the given brushing recipe
     *                    this must be in the range of [0, 1]
     * @return
     */
    public BrushRecipeBuilder probability(float probability) {
        if (this.probability != probability)
            this.probability = probability;
        return this;
    }

    /**
     * @param brush a tag of all the items that can be brushes, note that all of these items must be damageable
     * @return
     */
    public BrushRecipeBuilder brush(TagKey<Item> brush) {
        return brush(Ingredient.of(brush));
    }

    /**
     * @param brushes all items here must be damageable otherwise stuff will crash
     * @return
     */
    public BrushRecipeBuilder brush(ItemLike... brushes) {
        return brush(Ingredient.of(brushes));
    }

    /**
     * @param ing sets the brush to an ingredient, meaning all items listed here can be used as brush, not that all of these items must be damageable
     * @return
     */
    public BrushRecipeBuilder brush(Ingredient ing) {
        this.brush = ing;
        return this;
    }

    /**
     * @param inputs all possible inputs but with a tag which is better for compatibility reasons
     */
    public BrushRecipeBuilder input(TagKey<Item> inputs) {
        return input(Ingredient.of(inputs));
    }

    /**
     * @param inputs these can all be possible inputs for this recipe
     */
    public BrushRecipeBuilder input(ItemLike... inputs) {
        return input(Ingredient.of(inputs));
    }

    /**
     * @param ing this is the input that is used together with the brush
     */
    public BrushRecipeBuilder input(Ingredient ing) {
        this.input = ing;
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumer){
        build(consumer, ForgeRegistries.ITEMS.getKey(this.output.getItem()));
    }

    public void build(Consumer<FinishedRecipe> consumer, String id){
        build(consumer, AncientNature.modLoc(id));
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id){
        if (input == null)
            throw new IllegalArgumentException("input sint defines for recipe: " + id);
        consumer.accept(new Result(id, brush, input, probability, output));
    }


    public static class Result implements FinishedRecipe {

        protected final ResourceLocation id;
        protected final Ingredient brush, input;
        protected final float probability;
        protected final ItemStack output;

        public Result(ResourceLocation id, Ingredient brush, Ingredient input, float probability, ItemStack output) {
            this.id = id;
            this.brush = brush;
            this.input = input;
            this.probability = probability;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            if (probability != .2f)
                pJson.addProperty("probability", probability);
            pJson.add("brush", brush.toJson());
            pJson.add("input", input.toJson());
            JsonObject obj = new JsonObject();
            obj.addProperty("item", ForgeRegistries.ITEMS.getKey(output.getItem()).toString());
            if (output.getCount() > 1){
                obj.addProperty("count", output.getCount());
            }
            pJson.add("output", obj);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return BrushingRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
