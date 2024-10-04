package com.rutgervos.extrathings.item;

import com.rutgervos.extrathings.ExtraThings;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, ExtraThings.MODID);

    public static final RegistryObject<Item> EXTRA_INGOT = ITEMS.register("extra_ingot", () -> new Item( new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
