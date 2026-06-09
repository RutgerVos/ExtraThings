package com.rutgervos.extrathings.compat;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.block.ModBlocks;
import com.rutgervos.extrathings.recipe.ExtraFuseRecipe;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class ExtraFuseRecipeCategory implements IRecipeCategory<ExtraFuseRecipe> {
     public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "extra_fuse_block");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID,
            "textures/gui/extra_fuse_gui.png");

    public static final RecipeType<ExtraFuseRecipe> EXTRA_FUSE_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, ExtraFuseRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public ExtraFuseRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.EXTRA_FUSE_BLOCK.get()));
    }

    @Override
    public RecipeType<ExtraFuseRecipe> getRecipeType() {
        return EXTRA_FUSE_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.extrathings.extra_fuse_block");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Nullable
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ExtraFuseRecipe recipe, IFocusGroup focuses) {
        // 1. Fetch your ingredients list safely
    List<Ingredient> ingredients = recipe.ingredients();

    // 2. Map Slot 0 (First Input)
    builder.addSlot(RecipeIngredientRole.INPUT, 6, 34) // Replace coordinates with your actual GUI slot X, Y
           .addIngredients(ingredients.size() > 0 ? ingredients.get(0) : Ingredient.EMPTY);

    // 3. Map Slot 1 (Second Input)
    builder.addSlot(RecipeIngredientRole.INPUT, 29, 34) // Replace coordinates with your actual GUI slot X, Y
           .addIngredients(ingredients.size() > 1 ? ingredients.get(1) : Ingredient.EMPTY);

    // 4. Map Slot 2 (Third Input - Handles 2-item vs 3-item dynamically!)
    builder.addSlot(RecipeIngredientRole.INPUT, 54, 34) // Replace coordinates with your actual GUI slot X, Y
           .addIngredients(ingredients.size() > 2 ? ingredients.get(2) : Ingredient.EMPTY);

    // 5. Map Slot 3 (Output Product)
    builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 35) // Replace coordinates with your actual GUI slot X, Y
           .addItemStack(recipe.output());
    }

}
