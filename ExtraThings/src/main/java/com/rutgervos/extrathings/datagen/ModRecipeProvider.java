package com.rutgervos.extrathings.datagen;

import java.util.concurrent.CompletableFuture;

import com.rutgervos.extrathings.block.ModBlocks;
import com.rutgervos.extrathings.item.ModItems;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraft.world.item.Items;

import java.util.List;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public static final List<ItemLike> EXTRA_SMELTABLES = List.of(ModBlocks.EXTRA_ORE.get());

    public ModRecipeProvider(PackOutput p_248933_, CompletableFuture<Provider> p_333797_) {
        super(p_248933_, p_333797_);
    }

    @Override
    protected void buildRecipes(RecipeOutput pWriter) {

        oreSmelting(pWriter, EXTRA_SMELTABLES, RecipeCategory.MISC, ModItems.EXTRA_INGOT.get(), 0.25f, 200, "extra_ingot");
        oreBlasting(pWriter, EXTRA_SMELTABLES, RecipeCategory.MISC, ModItems.EXTRA_INGOT.get(), 0.25f, 100, "extra_ingot");

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EXTRA_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.EXTRA_INGOT.get())
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EXTRA_PICKAXE.get())
                .pattern("SSS")
                .pattern(" W ")
                .pattern(" W ")
                .define('S', ModItems.EXTRA_INGOT.get())
                .define('W', Items.STICK)
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

                 ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.EXTRA_INGOT.get(), 9)
                .requires(ModBlocks.EXTRA_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.EXTRA_BLOCK.get()), has(ModBlocks.EXTRA_BLOCK.get()))
                .save(pWriter);
    }

}
