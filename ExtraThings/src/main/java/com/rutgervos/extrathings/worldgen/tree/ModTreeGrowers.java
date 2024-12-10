package com.rutgervos.extrathings.worldgen.tree;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower EXTRA = new TreeGrower(ExtraThings.MODID + ":extra",
            Optional.empty(), Optional.of(ModConfiguredFeatures.EXTRA_KEY), Optional.empty());

}
