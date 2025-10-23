package com.rutgervos.extrathings.enchantment;

import com.mojang.serialization.MapCodec;
import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.enchantment.custom.LavaWalkerEnchantmentEffect;
import com.rutgervos.extrathings.enchantment.custom.LightningStrikerEnchantmentEffect;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantmentEffects {
     public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, ExtraThings.MODID);

    public static final RegistryObject<MapCodec<? extends EnchantmentEntityEffect>> LIGHTNING_STRIKER =
            ENTITY_ENCHANTMENT_EFFECTS.register("lightning_striker", () -> LightningStrikerEnchantmentEffect.CODEC);
     public static final RegistryObject<MapCodec<? extends EnchantmentEntityEffect>> LAVA_WALKER =
            ENTITY_ENCHANTMENT_EFFECTS.register("lava_walker", () -> LavaWalkerEnchantmentEffect.CODEC);


    public static void register(IEventBus eventBus) {
        ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
    }

}
