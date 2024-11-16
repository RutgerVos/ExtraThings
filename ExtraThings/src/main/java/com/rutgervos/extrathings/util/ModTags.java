package com.rutgervos.extrathings.util;

import com.rutgervos.extrathings.ExtraThings;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> ORE_DETECTOR_VALUABLES = tag("ore_detector_valuables");
        public static final TagKey<Block> NEEDS_EXTRA_TOOL = tag("needs_extra_tool");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(ExtraThings.MODID, name));
        }
    }

    public static class Items {

        @SuppressWarnings("unused")
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(ExtraThings.MODID, name));
        }
    }
}
