package com.rutgervos.extrathings.block.entity;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.block.ModBlocks;
import com.rutgervos.extrathings.block.entity.custom.ExtraChamberBlockEntity;
import com.rutgervos.extrathings.block.entity.custom.PedestalBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// New imports for the lambda expressions
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ExtraThings.MODID);

    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BE =
            BLOCK_ENTITIES.register("pedestal_be", () -> BlockEntityType.Builder.<PedestalBlockEntity>of(
                // Corrected lambda expression with explicit generic type
                (BlockPos pos, BlockState state) -> new PedestalBlockEntity(pos, state),
                ModBlocks.PEDESTAL.get()).build(null));

    public static final RegistryObject<BlockEntityType<ExtraChamberBlockEntity>> EXTRA_CHAMBER_BE =
            BLOCK_ENTITIES.register("extra_chamber_be", () -> BlockEntityType.Builder.<ExtraChamberBlockEntity>of(
                // Corrected lambda expression with explicit generic type
                (BlockPos pos, BlockState state) -> new ExtraChamberBlockEntity(pos, state),
                ModBlocks.EXTRA_CHAMBER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
