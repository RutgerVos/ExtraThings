package com.rutgervos.extrathings.effect;

import com.rutgervos.extrathings.ExtraThings;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

      public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ExtraThings.MODID);

    public static final RegistryObject<MobEffect> SLIMEY_EFFECT = MOB_EFFECTS.register("slimey",
            () -> new SlimeyEffect(MobEffectCategory.NEUTRAL, 0x36ebab));

    
    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

}
