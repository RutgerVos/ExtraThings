package com.rutgervos.extrathings.datagen;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.block.ModBlocks;
import com.rutgervos.extrathings.item.ModItems;

import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

public class ModAdvancementProvider implements ForgeAdvancementProvider.AdvancementGenerator {

    @Override
public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
    
    //Root Tab Advancement
    AdvancementHolder rootAdvancement = Advancement.Builder.advancement().display(
        ModItems.EXTRA_INGOT.get(),
        Component.literal("Extra start"), 
        Component.literal("Starting EXTRA WITH THINGS"),
         ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/stone.png"), 
         AdvancementType.TASK, 
         true, 
         true, 
         false)
         .addCriterion("has_extra_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.EXTRA_INGOT.get()))
         .save(saver, ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "root"));

    //Corrosive Powder Advancement (No variable needed since no other advancement depends on it)
    Advancement.Builder.advancement()
            .parent(rootAdvancement)
            .display(
                    ModItems.CORROSIVE_POWDER.get(),
                    Component.literal("Strange Stuff?"),
                    Component.literal("Obtain Corrosive Powder from fusing materials"),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false
            )
            .addCriterion("has_corrosive_powder", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CORROSIVE_POWDER.get()))
            .save(saver, ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "got_corrosive_powder"));
         Advancement.Builder.advancement()
         .parent(rootAdvancement)
        .display(
                    ModBlocks.EXTRA_FUSE_BLOCK.get(),
                    Component.literal("Extra Fuse"),
                    Component.literal("Start extra Fusion"),
                    ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/stone.png"),
                    AdvancementType.TASK,
                    true,
                    true,
                    false
            )
            .addCriterion("has_fuse_block", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.EXTRA_FUSE_BLOCK.get()))
            .save(saver, ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "got_fuse_block"));
}
}