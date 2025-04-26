package com.rutgervos.extrathings.recipe;

import com.rutgervos.extrathings.ExtraThings;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ExtraThings.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ExtraThings.MODID);

    public static final RegistryObject<RecipeSerializer<ExtraChamberRecipe>> EXTRA_CHAMBER_SERIALIZER =
            SERIALIZERS.register("growth_chamber", ExtraChamberRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<ExtraChamberRecipe>> EXTRA_CHAMBER_TYPE =
            TYPES.register("extra_chamber", () -> new RecipeType<ExtraChamberRecipe>() {
                @Override
                public String toString() {
                    return "extra_chamber";
                }
            });


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
