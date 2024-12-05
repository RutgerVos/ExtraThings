package com.rutgervos.extrathings.trim;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.item.ModItems;

import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTrimPatterns {

      public static final ResourceKey<TrimPattern> DECOOL14 = ResourceKey.create(Registries.TRIM_PATTERN,
            ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "decool14"));

    public static void bootstrap(BootstrapContext<TrimPattern> context) {
        register(context, ModItems.DECOOL14_SMITHING_TEMPLATE.get(), DECOOL14);
    }

    private static void register(BootstrapContext<TrimPattern> context, Item item, ResourceKey<TrimPattern> key) {
        TrimPattern trimPattern = new TrimPattern(key.location(), ForgeRegistries.ITEMS.getHolder(item).get(),
                Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())), false);
        context.register(key, trimPattern);
    }

}
