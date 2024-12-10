package com.rutgervos.extrathings.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.block.ModBlocks;
import com.rutgervos.extrathings.item.ModItems;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagGenerator extends ItemTagsProvider {

    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<Provider> p_275729_,
            CompletableFuture<TagLookup<Block>> p_275322_,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, ExtraThings.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider p_256380_) {


         this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.EXTRA_LOG.get().asItem())
                .add(ModBlocks.EXTRA_WOOD.get().asItem())
                .add(ModBlocks.EXTRA_SAPLING.get().asItem())
                .add(ModBlocks.STRIPPED_EXTRA_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_EXTRA_WOOD.get().asItem());

        this.tag(ItemTags.PLANKS)
                .add(ModBlocks.EXTRA_PLANKS.get().asItem());
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.EXTRA_HELMET.get())
                .add(ModItems.EXTRA_CHESTPLATE.get())
                .add(ModItems.EXTRA_LEGGINGS.get())
                .add(ModItems.EXTRA_BOOTS.get()); 
                
                this.tag(ItemTags.TRIM_MATERIALS)
                .add(ModItems.EXTRA_INGOT.get());

        this.tag(ItemTags.TRIM_TEMPLATES)
                .add(ModItems.DECOOL14_SMITHING_TEMPLATE.get());
    }

}
