package com.rutgervos.extrathings.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import com.rutgervos.extrathings.recipe.ExtraFuseRecipe;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExtraFuseRecipeBuilder implements RecipeBuilder {
    private final ItemStack result;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>(); // FIXED: Criterion<?>
    @Nullable
    private String group;

    private ExtraFuseRecipeBuilder(ItemStack result) {
        this.result = result;
    }

    public static ExtraFuseRecipeBuilder fuseRecipe(ItemStack result) {
        return new ExtraFuseRecipeBuilder(result);
    }

    public static ExtraFuseRecipeBuilder fuseRecipe(ItemLike result, int count) {
        return new ExtraFuseRecipeBuilder(new ItemStack(result, count));
    }

    public ExtraFuseRecipeBuilder requires(Ingredient ingredient) {
        if (this.ingredients.size() >= 3) {
            throw new IllegalStateException("ExtraFuse recipes cannot have more than 3 ingredients!");
        }
        this.ingredients.add(ingredient);
        return this;
    }

    public ExtraFuseRecipeBuilder requires(ItemLike item) {
        return this.requires(Ingredient.of(item));
    }

    @Override
    public ExtraFuseRecipeBuilder unlockedBy(String name, Criterion<?> criterion) { // FIXED: Criterion<?>
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public ExtraFuseRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        if (this.ingredients.isEmpty()) {
            throw new IllegalStateException("An ExtraFuse recipe must have at least one ingredient!");
        }
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("An ExtraFuse recipe must have an unlocking criterion!");
        }

        // 1. Initialize the advancement builder and attach the base recipe unlock trigger
        Advancement.Builder advancementBuilder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id));
        
        // 2. Attach your custom recipe-book unlocks (.unlockedBy)
        this.criteria.forEach(advancementBuilder::addCriterion);
        
        // 3. FIXED: Build a combined set of keys so the strategy validates cleanly
        java.util.Set<String> allCriteriaKeys = new java.util.HashSet<>(this.criteria.keySet());
        allCriteriaKeys.add("has_the_recipe"); // <-- Explicitly include the missing key!

        advancementBuilder.requirements(AdvancementRequirements.Strategy.OR.create(allCriteriaKeys));

        // 4. Finalize and serialize the recipe record
        ExtraFuseRecipe recipe = new ExtraFuseRecipe(this.ingredients, this.result);
        output.accept(id, recipe, advancementBuilder.build(id.withPrefix("recipes/")));
    }
}
