package com.rutgervos.extrathings.item;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.item.custom.FuelItem;
import com.rutgervos.extrathings.item.custom.OreDetectorItem;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, ExtraThings.MODID);

    public static final RegistryObject<Item> EXTRA_INGOT = ITEMS.register("extra_ingot", () -> new Item( new Item.Properties()));
    public static final RegistryObject<Item> ORE_DETECTOR = ITEMS.register("ore_detector", () -> new OreDetectorItem( new Item.Properties()));
    public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new OreDetectorItem( new Item.Properties().food(ModFoods.BUTTER)));
    public static final RegistryObject<Item> EXTRA_FUEL = ITEMS.register("extra_fuel", () -> new FuelItem( new Item.Properties(), 20000));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
