package com.rutgervos.extrathings.datagen;

import java.util.concurrent.CompletableFuture;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.block.ModBlocks;
import com.rutgervos.extrathings.item.ModItems;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EXTRA_CHAMBER.get())
                .pattern("SSS")
                .pattern("FRF")
                .pattern("TTT")
                .define('S', ModItems.EXTRA_INGOT.get())
                .define('F', Blocks.FURNACE)
                .define('R', Items.REDSTONE)
                .define('T', Blocks.STONE)
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EXTRA_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.EXTRA_INGOT.get())
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EXTRA_DOOR.get())
                .pattern("SS ")
                .pattern("SS ")
                .pattern("SS ")
                .define('S', ModItems.EXTRA_INGOT.get())
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EXTRA_PRESSURE_PLATE.get())
                .pattern("   ")
                .pattern("   ")
                .pattern("SS ")
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

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EXTRA_AXE.get())
                .pattern(" SS")
                .pattern(" WS")
                .pattern(" W ")
                .define('S', ModItems.EXTRA_INGOT.get())
                .define('W', Items.STICK)
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EXTRA_SHOVEL.get())
                .pattern(" S ")
                .pattern(" W ")
                .pattern(" W ")
                .define('S', ModItems.EXTRA_INGOT.get())
                .define('W', Items.STICK)
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EXTRA_HOE.get())
                .pattern(" SS")
                .pattern(" W ")
                .pattern(" W ")
                .define('S', ModItems.EXTRA_INGOT.get())
                .define('W', Items.STICK)
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EXTRA_SWORD.get())
                .pattern(" S ")
                .pattern(" S ")
                .pattern(" W ")
                .define('S', ModItems.EXTRA_INGOT.get())
                .define('W', Items.STICK)
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EXTRA_HAMMER.get())
                .pattern("SSS")
                .pattern("SWS")
                .pattern(" W ")
                .define('S', ModItems.EXTRA_INGOT.get())
                .define('W', Items.STICK)
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COBBLESTONE_GENERATOR.get())
                .pattern("   ")
                .pattern("WCL")
                .pattern("CCC")
                .define('L', Items.LAVA_BUCKET)
                .define('W', Items.WATER_BUCKET)
                .define('C', Items.COBBLESTONE)
                .unlockedBy(getHasName(Items.LAVA_BUCKET), has(Items.LAVA_BUCKET))
                .save(pWriter);

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PORTABLE_ENDER_CHEST.get())
                .pattern("SSS")
                .pattern("SWS")
                .pattern("SSS")
                .define('S', ModItems.EXTRA_INGOT.get())
                .define('W', Blocks.ENDER_CHEST)
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

                 ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.EXTRA_HELMET.get())
                .pattern("SSS")
                .pattern("S S")
                .pattern("   ")
                .define('S', ModItems.EXTRA_INGOT.get())
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.EXTRA_CHESTPLATE.get())
                .pattern("S S")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.EXTRA_INGOT.get())
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.EXTRA_LEGGINGS.get())
                .pattern("SSS")
                .pattern("S S")
                .pattern("S S")
                .define('S', ModItems.EXTRA_INGOT.get())
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

                ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.EXTRA_BOOTS.get())
                .pattern("   ")
                .pattern("S S")
                .pattern("S S")
                .define('S', ModItems.EXTRA_INGOT.get())
                .unlockedBy(getHasName(ModItems.EXTRA_INGOT.get()), has(ModItems.EXTRA_INGOT.get()))
                .save(pWriter);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DECOOL14_SMITHING_TEMPLATE.get(), 2)
                .requires(ModItems.EXTRA_INGOT.get())
                .requires(ModItems.DECOOL14_SMITHING_TEMPLATE.get())
                .unlockedBy(getHasName(ModBlocks.EXTRA_BLOCK.get()), has(ModBlocks.EXTRA_BLOCK.get()))
                .save(pWriter);

                 ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.EXTRA_INGOT.get(), 9)
                .requires(ModBlocks.EXTRA_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.EXTRA_BLOCK.get()), has(ModBlocks.EXTRA_BLOCK.get()))
                .save(pWriter);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EXTRA_PLANKS.get(), 4)
                .requires(ModBlocks.EXTRA_LOG.get())
                .unlockedBy(getHasName(ModBlocks.EXTRA_LOG.get()), has(ModBlocks.EXTRA_LOG.get()))
                .save(pWriter);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModBlocks.EXTRA_BUTTON.get(), 1)
                .requires(ModItems.EXTRA_INGOT.get())
                .unlockedBy(getHasName(ModBlocks.EXTRA_LOG.get()), has(ModBlocks.EXTRA_LOG.get()))
                .save(pWriter);

                ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.STRAWBERRY_SEEDS.get(), 2)
                .requires(ModItems.STRAWBERRY.get())
                .unlockedBy(getHasName(ModItems.STRAWBERRY.get()), has(ModItems.STRAWBERRY.get()))
                .save(pWriter);

                trimSmithing(pWriter, ModItems.DECOOL14_SMITHING_TEMPLATE.get(), ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "decool14"));
     new ExtraChamberRecipeBuilder(Items.COAL, Items.DIAMOND)
    .unlockedBy("has_coal", has(Items.COAL))
    .save(pWriter, ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "extra_chamber/diamond_from_coal"));
     new ExtraChamberRecipeBuilder(ModItems.EXTRA_SHOVEL.get(), ModItems.ORE_DETECTOR.get())
    .unlockedBy("has_extra_shovel", has(ModItems.EXTRA_SHOVEL.get()))
    .save(pWriter, ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "extra_chamber/ore_detector_from_extra_shovel"));
     new ExtraChamberRecipeBuilder(ModItems.EXTRA_HOE.get(), ModItems.STAFF_OFF_EXTRA.get())
    .unlockedBy("has_extra_hoe", has(ModItems.EXTRA_HOE.get()))
    .save(pWriter, ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "extra_chamber/staff_off_extra_from_extra_hoe"));
     new ExtraChamberRecipeBuilder(Blocks.BOOKSHELF, Items.EXPERIENCE_BOTTLE)
    .unlockedBy("has_bookshelf", has(Blocks.BOOKSHELF))
    .save(pWriter, ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "extra_chamber/experience_bottle_from_bookshelf"));
     new ExtraChamberRecipeBuilder(Blocks.STONE, ModBlocks.PEDESTAL.get())
    .unlockedBy("has_stone", has(Blocks.STONE))
    .save(pWriter, ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "extra_chamber/pedestal_from_stone"));
    }
}
