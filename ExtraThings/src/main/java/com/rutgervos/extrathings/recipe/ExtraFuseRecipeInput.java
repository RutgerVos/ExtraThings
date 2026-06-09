package com.rutgervos.extrathings.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import java.util.List;

public record ExtraFuseRecipeInput(List<ItemStack> inputs) implements RecipeInput {
    @Override
    public ItemStack getItem(int pIndex) {
        if (pIndex < 0 || pIndex >= this.inputs.size()) {
            return ItemStack.EMPTY;
        }
        return this.inputs.get(pIndex);
    }

    @Override
    public int size() {
        return 3; // MUST BE FIXED AT 3 ALWAYS!
    }
}