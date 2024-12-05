package com.rutgervos.extrathings.trim;

import java.util.Map;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.item.ModItems;

import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;

public class ModTrimMaterials {

      public static final ResourceKey<TrimMaterial> EXTRA_INGOT =
            ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "extra_ingot"));

    public static void bootstrap(BootstrapContext<TrimMaterial> context) {
        registerTrim(context, EXTRA_INGOT, ModItems.EXTRA_INGOT.get(), Style.EMPTY.withColor(TextColor.parseColor("#031cfc").getOrThrow()), 0.8F);
    }

    private static void registerTrim(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Item item,
                                 Style style, float itemModelIndex) {
        TrimMaterial trimmaterial = TrimMaterial.create(trimKey.location().getPath(), item, itemModelIndex,
                Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(style), Map.of());
        context.register(trimKey, trimmaterial);
    }

}
