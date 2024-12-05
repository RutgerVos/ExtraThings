package com.rutgervos.extrathings.datagen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.trim.ModTrimMaterials;
import com.rutgervos.extrathings.trim.ModTrimPatterns;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

public class ModDatapackEntries extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.TRIM_MATERIAL, ModTrimMaterials::bootstrap)
            .add(Registries.TRIM_PATTERN, ModTrimPatterns::bootstrap);

    public ModDatapackEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ExtraThings.MODID));
    }

    @Override
    public CompletableFuture<HolderLookup.Provider> getFullRegistries()
    {
        return null;
    }

}
