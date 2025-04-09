package com.rutgervos.extrathings.datagen;

import java.util.function.Function;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.block.ModBlocks;
import com.rutgervos.extrathings.block.custom.StrawberryCropBlock;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
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
        BlockWithItem(ModBlocks.EXTRA_CHAMBER);

        stairsBlock((StairBlock) ModBlocks.EXTRA_STAIRS.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()));
        slabBlock((SlabBlock) ModBlocks.EXTRA_SLAB.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()), blockTexture(ModBlocks.EXTRA_BLOCK.get()));

        buttonBlock((ButtonBlock) ModBlocks.EXTRA_BUTTON.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()));
        pressurePlateBlock((PressurePlateBlock) ModBlocks.EXTRA_PRESSURE_PLATE.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()));

        fenceBlock((FenceBlock) ModBlocks.EXTRA_FENCE.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.EXTRA_FENCE_GATE.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()));
        wallBlock((WallBlock) ModBlocks.EXTRA_WALL.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()));
        doorBlockWithRenderType(((DoorBlock) ModBlocks.EXTRA_DOOR.get()), modLoc("block/extra_door_bottom"), modLoc("block/extra_door_top"), "cutout");
        trapdoorBlock((TrapDoorBlock) ModBlocks.EXTRA_TRAPDOOR.get(), blockTexture(ModBlocks.EXTRA_BLOCK.get()), false);

        logBlock(((RotatedPillarBlock) ModBlocks.EXTRA_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.EXTRA_WOOD.get()), blockTexture(ModBlocks.EXTRA_LOG.get()), blockTexture(ModBlocks.EXTRA_LOG.get()));
        axisBlock((RotatedPillarBlock) (ModBlocks.STRIPPED_EXTRA_LOG.get()), blockTexture(ModBlocks.STRIPPED_EXTRA_LOG.get()),
                new ResourceLocation(ExtraThings.MODID, "block/stripped_extra_log_top"));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_EXTRA_WOOD.get()), blockTexture(ModBlocks.STRIPPED_EXTRA_LOG.get()),
                blockTexture(ModBlocks.STRIPPED_EXTRA_LOG.get()));
                saplingBlock(ModBlocks.EXTRA_SAPLING);

                blockItem(ModBlocks.EXTRA_LOG);
                blockItem(ModBlocks.EXTRA_WOOD);
                blockItem(ModBlocks.STRIPPED_EXTRA_LOG);
                blockItem(ModBlocks.STRIPPED_EXTRA_WOOD);
        
                BlockWithItem(ModBlocks.EXTRA_PLANKS);
        
                leavesBlock(ModBlocks.EXTRA_LEAVES);

                simpleBlockWithItem(ModBlocks.BLUE_POPPY.get(), models().cross(blockTexture(ModBlocks.BLUE_POPPY.get()).getPath(),
                blockTexture(ModBlocks.BLUE_POPPY.get())).renderType("cutout"));
        simpleBlockWithItem(ModBlocks.POTTED_BLUE_POPPY.get(), models().singleTexture("potted_blue_poppy", new ResourceLocation("flower_pot_cross"), "plant",
                blockTexture(ModBlocks.BLUE_POPPY.get())).renderType("cutout"));
                makeStrawberryCrop((CropBlock) ModBlocks.STRAWBERRY_CROP.get(), "strawberry_stage", "strawberry_stage");

    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    public void makeStrawberryCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> strawberryStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] strawberryStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((StrawberryCropBlock) block).getAgeProperty()),
                new ResourceLocation(ExtraThings.MODID, "block/" + textureName + state.getValue(((StrawberryCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

     private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(ExtraThings.MODID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void BlockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

}
