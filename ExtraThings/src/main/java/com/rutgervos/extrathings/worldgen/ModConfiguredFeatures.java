package com.rutgervos.extrathings.worldgen;

import java.util.List;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.block.ModBlocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class ModConfiguredFeatures {
      public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_EXTRA_ORE_KEY = registerKey("extra_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_EXTRA_ORE_KEY = registerKey("nether_extra_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_EXTRA_ORE_KEY = registerKey("end_extra_ore");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplacables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworldExtraOres = List.of(OreConfiguration.target(stoneReplaceable,
                ModBlocks.EXTRA_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.EXTRA_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_EXTRA_ORE_KEY, Feature.ORE, new OreConfiguration(overworldExtraOres, 9));
        register(context, NETHER_EXTRA_ORE_KEY, Feature.ORE, new OreConfiguration(netherrackReplacables,
                ModBlocks.EXTRA_ORE.get().defaultBlockState(), 9));
        register(context, END_EXTRA_ORE_KEY, Feature.ORE, new OreConfiguration(endReplaceables,
                ModBlocks.EXTRA_ORE.get().defaultBlockState(), 9));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ExtraThings.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
