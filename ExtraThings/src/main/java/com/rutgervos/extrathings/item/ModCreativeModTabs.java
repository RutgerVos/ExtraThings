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
        pOutput.accept(ModBlocks.EXTRA_BLOCK.get());
        })
    .build());

     public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
