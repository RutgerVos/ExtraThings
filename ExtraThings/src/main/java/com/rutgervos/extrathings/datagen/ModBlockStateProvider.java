package com.rutgervos.extrathings.datagen;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.block.ModBlocks;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ExtraThings.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockWithItem(ModBlocks.EXTRA_ORE);
        BlockWithItem(ModBlocks.SOUND_BLOCK);
        BlockWithItem(ModBlocks.EXTRA_BLOCK);

        stairsBlock((StairBlock) ModBlocks.EXTRA_STAIRS.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()));
        slabBlock((SlabBlock) ModBlocks.EXTRA_SLAB.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()), blockTexture(ModBlocks.EXTRA_BLOCK.get()));

        buttonBlock((ButtonBlock) ModBlocks.EXTRA_BUTTON.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()));
        pressurePlateBlock((PressurePlateBlock) ModBlocks.EXTRA_PRESSURE_PLATE.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()));

        fenceBlock((FenceBlock) ModBlocks.EXTRA_FENCE.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.EXTRA_FENCE_GATE.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()));
        wallBlock((WallBlock) ModBlocks.EXTRA_WALL.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()));
        doorBlock((DoorBlock) ModBlocks.EXTRA_DOOR.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()), blockTexture(ModBlocks.EXTRA_BLOCK.get()));
        trapdoorBlock((TrapDoorBlock) ModBlocks.EXTRA_TRAPDOOR.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()), false);
    }

    private void BlockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

}
