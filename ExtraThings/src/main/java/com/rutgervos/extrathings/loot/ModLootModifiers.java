package com.rutgervos.extrathings.loot;


import com.mojang.serialization.MapCodec;
import com.rutgervos.extrathings.ExtraThings;

import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
     public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ExtraThings.MODID);

    public static final RegistryObject<MapCodec<AddItemModifier>> ADD_ITEM =
            LOOT_MODIFIER_SERIALIZERS.register("add_item",  AddItemModifier.CODEC_SUPPLIER);


    public static void register(IEventBus eventBus) {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
