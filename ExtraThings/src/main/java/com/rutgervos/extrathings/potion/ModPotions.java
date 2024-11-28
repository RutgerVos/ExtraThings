package com.rutgervos.extrathings.potion;

import com.rutgervos.extrathings.ExtraThings;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {

    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, ExtraThings.MODID);

             public static final RegistryObject<Potion> WITHER_POTION = POTIONS.register("wither_potion",
            () -> new Potion(new MobEffectInstance(MobEffects.WITHER, 400, 0)));


        public static void register(IEventBus eventBus) {
            POTIONS.register(eventBus);
        }

}
