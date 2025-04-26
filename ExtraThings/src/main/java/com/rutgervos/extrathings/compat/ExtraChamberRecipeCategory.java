package com.rutgervos.extrathings.compat;

import org.jetbrains.annotations.Nullable;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.block.ModBlocks;
import com.rutgervos.extrathings.recipe.ExtraChamberRecipe;

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

public class ExtraChamberRecipeCategory implements IRecipeCategory<ExtraChamberRecipe> {
     public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "extra_chamber");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID,
            "textures/gui/extra_chamber/extra_chamber_gui.png");

    public static final RecipeType<ExtraChamberRecipe> EXTRA_CHAMBER_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, ExtraChamberRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public ExtraChamberRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.EXTRA_CHAMBER.get()));
    }

    @Override
    public RecipeType<ExtraChamberRecipe> getRecipeType() {
        return EXTRA_CHAMBER_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.extrathings.extra_chamber");
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
    public void setRecipe(IRecipeLayoutBuilder builder, ExtraChamberRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 34).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 34).addItemStack(recipe.getResultItem(null));
    }

}
