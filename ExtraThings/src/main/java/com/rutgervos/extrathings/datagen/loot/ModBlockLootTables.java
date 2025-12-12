package com.rutgervos.extrathings.datagen.loot;

import java.util.Set;

import com.rutgervos.extrathings.block.ModBlocks;
import com.rutgervos.extrathings.block.custom.StrawberryCropBlock;
import com.rutgervos.extrathings.item.ModItems;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLootSubProvider {
        
        protected ModBlockLootTables(HolderLookup.Provider pRegistries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
        }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.EXTRA_ORE.get());
        this.dropSelf(ModBlocks.EXTRA_BLOCK.get());
        this.dropSelf(ModBlocks.SOUND_BLOCK.get());

        this.dropSelf(ModBlocks.EXTRA_STAIRS.get());
        this.dropSelf(ModBlocks.EXTRA_BUTTON.get());
        this.dropSelf(ModBlocks.EXTRA_PRESSURE_PLATE.get());
        this.dropSelf(ModBlocks.EXTRA_TRAPDOOR.get());
        this.dropSelf(ModBlocks.EXTRA_FENCE.get());
        this.dropSelf(ModBlocks.EXTRA_FENCE_GATE.get());
        this.dropSelf(ModBlocks.EXTRA_WALL.get());
        this.dropSelf(ModBlocks.EXTRA_DOOR.get());
        this.dropSelf(ModBlocks.EXTRA_TRAPDOOR.get());
        this.dropSelf(ModBlocks.EXTRA_PLANKS.get());
        this.dropSelf(ModBlocks.EXTRA_LOG.get());
        this.dropSelf(ModBlocks.EXTRA_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_EXTRA_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_EXTRA_WOOD.get());
        this.dropSelf(ModBlocks.EXTRA_SAPLING.get());
        this.dropSelf(ModBlocks.PEDESTAL.get());
        this.dropSelf(ModBlocks.EXTRA_CHAMBER.get());
        this.dropSelf(ModBlocks.GOLD_SLAB.get());
        this.dropSelf(ModBlocks.GOLD_STAIRS.get());

        this.add(ModBlocks.EXTRA_LEAVES.get(), block ->
        createLeavesDrops(block, ModBlocks.EXTRA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.add(ModBlocks.EXTRA_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.EXTRA_SLAB.get()));
        this.add(ModBlocks.EXTRA_DOOR.get(),
                block -> createDoorTable(ModBlocks.EXTRA_DOOR.get()));

        this.dropSelf(ModBlocks.BLUE_POPPY.get());
        this.add(ModBlocks.POTTED_BLUE_POPPY.get(), createPotFlowerItemTable(ModBlocks.BLUE_POPPY.get()));

        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.STRAWBERRY_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StrawberryCropBlock.AGE, 5));
        this.add(ModBlocks.STRAWBERRY_CROP.get(), createCropDrops(ModBlocks.STRAWBERRY_CROP.get(), ModItems.STRAWBERRY.get(),
                ModItems.STRAWBERRY_SEEDS.get(), lootitemcondition$builder));
    }

     protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(null))));
    }

     @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
