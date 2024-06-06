package net.reaper.ancientnature.core.datagen.server.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import net.reaper.ancientnature.common.recipe.RevivalStandRecipe;
import net.reaper.ancientnature.common.util.IngredientCount;
import net.reaper.ancientnature.common.util.ResourceLocationUtils;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class RevivalStandRecipeBuilder {

    public static RevivalStandRecipeBuilder builder(ItemLike egg){
        return builder(egg, 3);
    }
    public static RevivalStandRecipeBuilder builder(ItemLike egg, int count){
        return builder(new ItemStack(egg, Mth.clamp(count, 1, 3)));
    }

    public static RevivalStandRecipeBuilder builder(ItemStack egg){
        return new RevivalStandRecipeBuilder(egg);
    }

    protected final ItemStack eggs;
    protected int amberInfusionTime, fossilInfusionTime;
    protected Ingredient baseRoe;
    protected IngredientCount fossil, amber;

    public RevivalStandRecipeBuilder(ItemStack eggs) {
        this.eggs = eggs;
        this.eggs.setCount(Mth.clamp(this.eggs.getCount(), 1, 3));
    }


    public RevivalStandRecipeBuilder fossil(TagKey<Item> fossils){
        return fossil(fossils, 1);
    }

    public RevivalStandRecipeBuilder fossil(ItemLike... fossils){
        return fossil(1, fossils);
    }

    public RevivalStandRecipeBuilder fossil(Ingredient fossil){
       return fossil(fossil, 1);
    }

    public RevivalStandRecipeBuilder fossil(TagKey<Item> fossils, int count){
        return fossil(Ingredient.of(fossils), count);
    }

    public RevivalStandRecipeBuilder fossil(int count, ItemLike... fossils){
        return fossil(Ingredient.of(fossils), count);
    }

    public RevivalStandRecipeBuilder fossil(Ingredient fossil, int count){
        this.fossil = new IngredientCount(fossil, count);
        return this;
    }

    public RevivalStandRecipeBuilder amber(TagKey<Item> ambers){
        return amber(ambers, 1);
    }

    public RevivalStandRecipeBuilder amber(ItemLike... ambers){
        return amber(1, ambers);
    }

    public RevivalStandRecipeBuilder amber(Ingredient amber){
        return amber(amber, 1);
    }

    public RevivalStandRecipeBuilder amber(TagKey<Item> ambers, int count){
        return amber(Ingredient.of(ambers), count);
    }

    public RevivalStandRecipeBuilder amber(int count, ItemLike... ambers){
        return amber(Ingredient.of(ambers), count);
    }

    public RevivalStandRecipeBuilder amber(Ingredient amber, int count){
        this.amber = new IngredientCount(amber, count);
        return this;
    }

    /**
     * the time in ticks that is needed for the horizontal arrow to be full
     */
    public RevivalStandRecipeBuilder amberInfusionTime(int amberInfusionTime){
        this.amberInfusionTime = Math.max(1, amberInfusionTime);
        return this;
    }

    /**
     * the time in ticks the vertical arrow need to be finished
     * @param fossilInfusionTime
     * @return
     */
    public RevivalStandRecipeBuilder fossilInfusionTime(int fossilInfusionTime){
        this.fossilInfusionTime = Math.max(1, fossilInfusionTime);
        return this;
    }


    public RevivalStandRecipeBuilder baseRoe(TagKey<Item> baseRoe){
        return baseRoe(Ingredient.of(baseRoe));
    }

    public RevivalStandRecipeBuilder baseRoe(ItemLike... baseRoe){
       return baseRoe(Ingredient.of(baseRoe));
    }

    /**
     *
     * @param baseRoe the row that needs to be placed on the bottom slots in order for that to work
     * @return
     */
    public RevivalStandRecipeBuilder baseRoe(Ingredient baseRoe){
        this.baseRoe = baseRoe;
        return this;
    }

    /**
     * will create a recipe named exactly after the eggs
     */
    public void build(Consumer<FinishedRecipe> consumer){
        build(consumer, ForgeRegistries.ITEMS.getKey(this.eggs.getItem()));
    }

    /**
     * will create a recipe that is named as the eggs but u can add a suffix here
     */
    public void build(Consumer<FinishedRecipe> consumer, String suffix){
        build(consumer, ResourceLocationUtils.extend(ForgeRegistries.ITEMS.getKey(this.eggs.getItem()), suffix));
    }

    /**
     * will create a recipe with the given ResourceLocation
     */
    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id){
        this.validate();
        consumer.accept(new Result(ResourceLocationUtils.prepend(id, "revival_stand/"), fossil, amber, this.baseRoe, amberInfusionTime, fossilInfusionTime, eggs));
    }

    protected void validate(){
        if (this.fossil == null){
            throw new IllegalStateException("the fossil must not be null, every recipe has to have a fossil");
        }
        if (this.amber == null){
            throw new IllegalStateException("the amber must not be null, every recipe has to have a amber");
        }
        if (this.amberInfusionTime <= 0){
            throw new IllegalStateException("the amber infusion time has to be set to a higher value then 0");
        }
        if (this.fossilInfusionTime <= 0){
            throw new IllegalStateException("the fossil infusion time has to be set to a higher value then 0");
        }
    }

    protected static class Result implements FinishedRecipe{

        protected final ResourceLocation id;
        protected final IngredientCount fossil, amber;
        protected final Ingredient basRoe;
        protected final int amberInfusionTime, fossilInfusionTime;
        protected final ItemStack eggs;

        public Result(ResourceLocation id, IngredientCount fossil, IngredientCount amber, Ingredient basRoe, int amberInfusionTime, int fossilInfusionTime, ItemStack eggs) {
            this.id = id;
            this.fossil = fossil;
            this.amber = amber;
            this.basRoe = basRoe;
            this.amberInfusionTime = amberInfusionTime;
            this.fossilInfusionTime = fossilInfusionTime;
            this.eggs = eggs;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonElement fossilJson = this.fossil.toJson();
            pJson.add("fossil", fossilJson);
            JsonElement amberJson = this.amber.toJson();
            pJson.add("amber", amberJson);
            if (amberInfusionTime != 200){
                pJson.addProperty("amberInfusionTime", this.amberInfusionTime);
            }
            if (this.fossilInfusionTime != 200){
                pJson.addProperty("fossilInfusionTime", this.fossilInfusionTime);
            }
            if (this.basRoe != null){
                JsonElement baseEl = this.basRoe.toJson();
                pJson.add("base_roe", baseEl);
            }
            JsonObject eggObject = new JsonObject();
            eggObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.eggs.getItem()).toString());
            if (this.eggs.getCount() != 1){
                eggObject.addProperty("count", this.eggs.getCount());
            }
            pJson.add("egg", eggObject);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RevivalStandRecipe.SERIALIZER;
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
