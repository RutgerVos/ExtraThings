package com.rutgervos.extrathings.worldgen;

import com.rutgervos.extrathings.ExtraThings;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {

        public static final ResourceKey<BiomeModifier> ADD_EXTRA_ORE = registerKey("add_extra_ore");
        public static final ResourceKey<BiomeModifier> ADD_NETHER_EXTRA_ORE = registerKey("add_nether_extra_ore");
        public static final ResourceKey<BiomeModifier> ADD_END_EXTRA_ORE = registerKey("add_end_extra_ore");

        public static final ResourceKey<BiomeModifier> ADD_EXTRA_TREE = registerKey("add_extra_walnut");

        public static void bootstrap(BootstrapContext<BiomeModifier> context) {
                var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_EXTRA_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.EXTRA_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        // Individual Biomes
        // context.register(ADD_ALEXANDRITE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
        //         HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.BAMBOO_JUNGLE)),
        //         HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.ALEXANDRITE_ORE_PLACED_KEY)),
        //         GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_NETHER_EXTRA_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.NETHER_EXTRA_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_END_EXTRA_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.END_EXTRA_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_EXTRA_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.SAVANNA)),
                        HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.EXTRA_PLACED_KEY)),
                        GenerationStep.Decoration.VEGETAL_DECORATION));
            }
        
            private static ResourceKey<BiomeModifier> registerKey(String name) {
                return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, name));
            }
}
