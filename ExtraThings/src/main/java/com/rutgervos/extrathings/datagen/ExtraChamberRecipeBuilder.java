package com.rutgervos.extrathings.datagen;


import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.recipe.ExtraChamberRecipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExtraChamberRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final Item result;
    @Nullable
    private String group;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public ExtraChamberRecipeBuilder(ItemLike pIngredient, ItemLike pResult) {
        this.ingredient = Ingredient.of(pIngredient);
        this.result = pResult.asItem();
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, Criterion<?> pCriterion) {
        this.criteria.put(pCriterionName, pCriterion);
        return this;
    }

  @Override
public void save(RecipeOutput pWriter, ResourceLocation pRecipeId) {
    this.ensureValid(pRecipeId);
    Advancement.Builder advancementBuilder = pWriter.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId))
            .rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(pRecipeId))
            .requirements(AdvancementRequirements.Strategy.OR);
    this.criteria.forEach(advancementBuilder::addCriterion);

    // Create an instance of your custom recipe
    ExtraChamberRecipe recipe = new ExtraChamberRecipe(pRecipeId, this.ingredient, this.result);

    // Create and build the advancement holder
    AdvancementHolder advancementHolder = advancementBuilder.build(ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "recipes/" + pRecipeId.getPath()));

    // Pass the custom recipe and its advancement to the accept method
    pWriter.accept(pRecipeId, recipe, advancementHolder);
}

    private void ensureValid(ResourceLocation pRecipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("Recipe " + pRecipeId + " has no criteria defined!");
        }
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroup) {
        this.group = pGroup;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }
}