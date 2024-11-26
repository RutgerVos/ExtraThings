package com.rutgervos.extrathings.item;

import com.rutgervos.extrathings.util.ModTags;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModToolTiers {

     public static final Tier EXTRA = new ForgeTier(1400, 8, 5f, 20,
            ModTags.Blocks.NEEDS_EXTRA_TOOL, () -> Ingredient.of(ModItems.EXTRA_INGOT.get()),
            ModTags.Blocks.INCORRECT_FOR_EXTRA_TOOL);
}
