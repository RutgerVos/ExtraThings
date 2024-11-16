package com.rutgervos.extrathings.block;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.block.custom.SoundBlock;
import com.rutgervos.extrathings.item.ModItems;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
    DeferredRegister.create(ForgeRegistries.BLOCKS, ExtraThings.MODID);

     public static final RegistryObject<Block> EXTRA_BLOCK = registerBlock("extra_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));
            public static final RegistryObject<Block> EXTRA_ORE = registerBlock("extra_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.STONE)));
            public static final RegistryObject<Block> SOUND_BLOCK = registerBlock("sound_block",
            () -> new SoundBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

            public static final RegistryObject<Block> EXTRA_STAIRS = registerBlock("extra_stairs",
            () -> new StairBlock(ModBlocks.EXTRA_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));

            public static final RegistryObject<Block> EXTRA_SLAB = registerBlock("extra_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));

            public static final RegistryObject<Block> EXTRA_BUTTON = registerBlock("extra_button",
            () -> new ButtonBlock(BlockSetType.IRON, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));

            public static final RegistryObject<Block> EXTRA_PRESSURE_PLATE = registerBlock("extra_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.IRON, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));

            public static final RegistryObject<Block> EXTRA_FENCE = registerBlock("extra_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));

            public static final RegistryObject<Block> EXTRA_FENCE_GATE = registerBlock("extra_fence_gate",
            () -> new FenceGateBlock(null, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST), SoundEvents.CHAIN_PLACE, SoundEvents.ANVIL_BREAK));

            public static final RegistryObject<Block> EXTRA_WALL = registerBlock("extra_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));

            public static final RegistryObject<Block> EXTRA_DOOR = registerBlock("extra_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST).noCollission()));

            public static final RegistryObject<Block> EXTRA_TRAPDOOR = registerBlock("extra_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST).noCollission()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

      private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

     public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
