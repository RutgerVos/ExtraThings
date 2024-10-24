package com.rutgervos.extrathings.item;

import com.rutgervos.extrathings.ExtraThings;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import com.rutgervos.extrathings.block.ModBlocks;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = 
    DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExtraThings.MODID);

    public static final RegistryObject<CreativeModeTab> EXTRATHINGS_TAB = 
    CREATIVE_MODE_TABS.register("extrathings_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.EXTRA_INGOT.get()))
    .title(Component.translatable("creativetab.extrathings_tab"))
    .displayItems((pParameters, pOutput) -> {
        pOutput.accept(ModItems.EXTRA_INGOT.get());
        pOutput.accept(ModItems.ORE_DETECTOR.get());
        pOutput.accept(ModItems.BUTTER.get());
        pOutput.accept(ModItems.EXTRA_FUEL.get());
        pOutput.accept(ModItems.EXTRA_PICKAXE.get());
        pOutput.accept(ModBlocks.EXTRA_BLOCK.get());
        pOutput.accept(ModBlocks.EXTRA_ORE.get());

        pOutput.accept(ModBlocks.EXTRA_STAIRS.get());
        pOutput.accept(ModBlocks.EXTRA_SLAB.get());
        pOutput.accept(ModBlocks.EXTRA_BUTTON.get());
        pOutput.accept(ModBlocks.EXTRA_PRESSURE_PLATE.get());

        pOutput.accept(ModBlocks.EXTRA_FENCE.get());
        pOutput.accept(ModBlocks.EXTRA_FENCE_GATE.get());
        pOutput.accept(ModBlocks.EXTRA_WALL.get());

        pOutput.accept(ModBlocks.EXTRA_DOOR.get());
        pOutput.accept(ModBlocks.EXTRA_TRAPDOOR.get());

        pOutput.accept(ModBlocks.SOUND_BLOCK.get());
        })
    .build());

     public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
