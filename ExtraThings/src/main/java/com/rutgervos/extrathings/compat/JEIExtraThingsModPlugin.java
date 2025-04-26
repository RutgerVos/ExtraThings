package com.rutgervos.extrathings.compat;

import java.util.List;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.block.ModBlocks;
import com.rutgervos.extrathings.recipe.ExtraChamberRecipe;
import com.rutgervos.extrathings.recipe.ModRecipes;
import com.rutgervos.extrathings.screen.custom.ExtraChamberScreen;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

@JeiPlugin
public class JEIExtraThingsModPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IModPlugin.super.registerCategories(registration);
        registration.addRecipeCategories(new ExtraChamberRecipeCategory(
            registration.getJeiHelpers().getGuiHelper()));
    }

     @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<ExtraChamberRecipe> extraChamberRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.EXTRA_CHAMBER_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(ExtraChamberRecipeCategory.EXTRA_CHAMBER_RECIPE_RECIPE_TYPE, extraChamberRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        IModPlugin.super.registerGuiHandlers(registration);
        registration.addRecipeClickArea(ExtraChamberScreen.class, 70, 30, 25, 20,
        ExtraChamberRecipeCategory.EXTRA_CHAMBER_RECIPE_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.EXTRA_CHAMBER.get().asItem()),
        ExtraChamberRecipeCategory.EXTRA_CHAMBER_RECIPE_RECIPE_TYPE);
    }
}
