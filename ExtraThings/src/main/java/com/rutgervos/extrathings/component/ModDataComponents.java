package com.rutgervos.extrathings.component;

import com.mojang.serialization.Codec;
import com.rutgervos.extrathings.ExtraThings;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ExtraThings.MODID);

    // Boolean component to track if the Pocket Furnace is ON or OFF
    public static final RegistryObject<DataComponentType<Boolean>> IS_ACTIVE = register("is_active", 
            builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    // Integer component to track fuel units remaining
    public static final RegistryObject<DataComponentType<Integer>> FUEL_LEVEL = register("fuel_level", 
            builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT));


    // Helper method to simplify registration
    private static <T> RegistryObject<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return DATA_COMPONENT_TYPES.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}