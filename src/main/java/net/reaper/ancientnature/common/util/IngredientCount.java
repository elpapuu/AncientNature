package net.reaper.ancientnature.common.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientCount {

    public static IngredientCount fromBuffer(FriendlyByteBuf buf) {
        Ingredient ing = Ingredient.fromNetwork(buf);
        int count = buf.readInt();
        return new IngredientCount(ing, count);
    }

    public static IngredientCount fromJson(JsonObject obj) {
        int count = Math.max(1, GsonHelper.getAsInt(obj, "count", 1));
        JsonElement ingElement = obj.get("ingredient");
        Ingredient ing = Ingredient.fromJson(ingElement);
        return new IngredientCount(ing, count);
    }

    protected final Ingredient ing;
    protected final int count;

    public IngredientCount(Ingredient ing, int count) {
        this.ing = ing;
        this.count = Math.max(1, count);
    }

    public void toNetwork(FriendlyByteBuf buf){
        this.ing.toNetwork(buf);
        buf.writeInt(this.count);
    }

    /**
     * this should shrink an itemStack that is matching with this given count, not that this will do nothing if it isnt matching
     * @return whether this was shrinked or not
     */
    public boolean shrink(ItemStack stack){
        if (this.test(stack)){
            stack.shrink(this.count);
            return true;
        }
        return false;
    }

    public JsonElement toJson(){
        JsonObject obj = new JsonObject();
        if (this.count != 1){
            obj.addProperty("count", this.count);
        }
        obj.add("ingredient", this.ing.toJson());
        return obj;
    }

    /**
     * tests this stack, returns true if this stacks item matches the ingredient and if the count is less or equal to this count
     * @param stack
     */
    public boolean test(ItemStack stack){
        if (this.ing.test(stack)){
            return this.count <= stack.getCount();
        }
        return false;
    }

    public Ingredient getIngredient() {
        return ing;
    }

    public int getCount() {
        return count;
    }
}
