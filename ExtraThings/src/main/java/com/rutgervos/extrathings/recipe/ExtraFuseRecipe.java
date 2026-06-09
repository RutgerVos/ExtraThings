package com.rutgervos.extrathings.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

// FIXED: Removed ResourceLocation id parameter to match 1.21.1 standard specifications
public record ExtraFuseRecipe(NonNullList<Ingredient> ingredients, ItemStack output) implements Recipe<ExtraFuseRecipeInput> {

    @Override
    public boolean matches(ExtraFuseRecipeInput pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;

        // 1. Gather all actual physical items currently placed in the machine's 3 slots
        List<ItemStack> presentItems = new ArrayList<>();
        for (int slot = 0; slot < 3; slot++) {
            ItemStack stack = pContainer.getItem(slot);
            if (!stack.isEmpty()) {
                presentItems.add(stack);
            }
        }

        // 2. Gather all expected ingredients required by this specific recipe JSON
        List<Ingredient> requiredIngredients = new ArrayList<>();
        for (Ingredient ing : this.ingredients) {
            if (ing != null && !ing.isEmpty()) {
                requiredIngredients.add(ing);
            }
        }

        // 3. Quick count check: If item counts don't align perfectly, this isn't our recipe
        if (presentItems.size() != requiredIngredients.size()) {
            return false;
        }

        // 4. Order-Independent Matching Matrix loop
        // We look at each item in the machine and see if any remaining required ingredient accepts it
        for (ItemStack item : presentItems) {
            boolean ingredientMatched = false;
            
            for (int i = 0; i < requiredIngredients.size(); i++) {
                if (requiredIngredients.get(i).test(item)) {
                    requiredIngredients.remove(i); // Consume the ingredient requirement
                    ingredientMatched = true;
                    break; // Move to the next item in the machine
                }
            }
            
            // If any item in the machine doesn't belong to this recipe, it's a total failure
            if (!ingredientMatched) {
                return false;
            }
        }

        // If all required ingredients were successfully matched and consumed, start crafting!
        return requiredIngredients.isEmpty();
    }

    @Override
    public ItemStack assemble(ExtraFuseRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return this.output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.EXTRA_FUSE_BLOCK_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.EXTRA_FUSE_BLOCK_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<ExtraFuseRecipe> {
        // FIXED: Removed the ID field lookup step from the MapCodec completely
        public static final MapCodec<ExtraFuseRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.listOf().xmap(
                        list -> {
                            NonNullList<Ingredient> nonNullList = NonNullList.create();
                            nonNullList.addAll(list);
                            return nonNullList;
                        },
                        ArrayList::new
                ).fieldOf("ingredients").forGetter(ExtraFuseRecipe::ingredients),
                ItemStack.CODEC.fieldOf("result").forGetter(ExtraFuseRecipe::output)
        ).apply(inst, ExtraFuseRecipe::new));

        // FIXED: StreamCodec matches raw parameters without parsing implicit registry identities
        public static final StreamCodec<RegistryFriendlyByteBuf, ExtraFuseRecipe> STREAM_CODEC = StreamCodec.of(
                (buf, recipe) -> {
                    buf.writeVarInt(recipe.ingredients().size());
                    for (Ingredient ingredient : recipe.ingredients()) {
                        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
                    }
                    ItemStack.STREAM_CODEC.encode(buf, recipe.output());
                },
                buf -> {
                    int size = buf.readVarInt();
                    NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
                    for (int i = 0; i < size; i++) {
                        ingredients.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
                    }
                    ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
                    return new ExtraFuseRecipe(ingredients, output);
                }
        );

        @Override public MapCodec<ExtraFuseRecipe> codec() { return CODEC; }
        @Override public StreamCodec<RegistryFriendlyByteBuf, ExtraFuseRecipe> streamCodec() { return STREAM_CODEC; }
    }
}