package com.rutgervos.extrathings.datagen;

import java.util.concurrent.CompletableFuture;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.item.ModItems;
import com.rutgervos.extrathings.loot.AddItemModifier;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {

      public ModGlobalLootModifiersProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, ExtraThings.MODID, registries);
    }
    @Override
    protected void start() {

        add("butter_from_cow", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/cow")).build(), 
                LootItemRandomChanceCondition.randomChance(0.65f).build()}, ModItems.BUTTER.get()));
                add("strawberry_seeds_from_grass", new AddItemModifier(new LootItemCondition[] {
                    new LootTableIdCondition.Builder(new ResourceLocation("blocks/short_grass")).build(), 
                    LootItemRandomChanceCondition.randomChance(0.65f).build()}, ModItems.STRAWBERRY_SEEDS.get()));

                add("ore_detector_from_jungle_temples", new AddItemModifier(new LootItemCondition[] {
                    new LootTableIdCondition.Builder(new ResourceLocation("chests/jungle_temple")).build() }, ModItems.ORE_DETECTOR.get()));
    
    }

}
