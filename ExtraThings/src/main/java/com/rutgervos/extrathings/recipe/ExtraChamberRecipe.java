package com.rutgervos.extrathings.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceLocation;

public record ExtraChamberRecipe(ResourceLocation id, Ingredient inputItem, ItemStack output) implements Recipe<ExtraChamberRecipeInput> {

    public ExtraChamberRecipe(ResourceLocation pRecipeId, Ingredient ingredient, Item result) {
        this(pRecipeId, ingredient, new ItemStack(result));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    public boolean matches(ExtraChamberRecipeInput pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        return inputItem.test(pContainer.getItem(0));
    }

    public ItemStack assemble(ExtraChamberRecipeInput pContainer, HolderLookup.Provider pRegistries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.EXTRA_CHAMBER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.EXTRA_CHAMBER_TYPE.get();
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public static class Serializer implements RecipeSerializer<ExtraChamberRecipe> {
        public static final MapCodec<ExtraChamberRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                ResourceLocation.CODEC.fieldOf("id").forGetter(ExtraChamberRecipe::id),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(ExtraChamberRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(ExtraChamberRecipe::output)
        ).apply(inst, ExtraChamberRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ExtraChamberRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        ResourceLocation.STREAM_CODEC, ExtraChamberRecipe::id,
                        Ingredient.CONTENTS_STREAM_CODEC, ExtraChamberRecipe::inputItem,
                        ItemStack.STREAM_CODEC, ExtraChamberRecipe::output,
                        ExtraChamberRecipe::new);

        @Override
        public MapCodec<ExtraChamberRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ExtraChamberRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}