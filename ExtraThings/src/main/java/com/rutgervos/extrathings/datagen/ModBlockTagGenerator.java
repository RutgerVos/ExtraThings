package com.rutgervos.extrathings.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.block.ModBlocks;
import com.rutgervos.extrathings.util.ModTags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagGenerator extends BlockTagsProvider {

    public ModBlockTagGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ExtraThings.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider pProvider) {
        this.tag(ModTags.Blocks.ORE_DETECTOR_VALUABLES)
        .add(ModBlocks.EXTRA_ORE.get())
        .addTag(Tags.Blocks.ORES);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.EXTRA_ORE.get(),
        ModBlocks.EXTRA_BLOCK.get(),
        ModBlocks.EXTRA_ORE.get(),
        ModBlocks.SOUND_BLOCK.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.EXTRA_BLOCK.get());
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.EXTRA_ORE.get());
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.EXTRA_BLOCK.get());
        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(ModBlocks.EXTRA_BLOCK.get());
        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(ModBlocks.EXTRA_ORE.get());
    }

}
