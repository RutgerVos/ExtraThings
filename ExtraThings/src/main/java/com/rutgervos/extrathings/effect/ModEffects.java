package com.rutgervos.extrathings.effect;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.rutgervos.extrathings.ExtraThings;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

      public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ExtraThings.MODID);

    public static final RegistryObject<MobEffect> SLIMEY_EFFECT = MOB_EFFECTS.register("slimey",
            () -> new SlimeyEffect(MobEffectCategory.NEUTRAL, 0x36ebab));
    public static final RegistryObject<MobEffect> MILK = MOB_EFFECTS.register("milk",
            () -> new InstantenousMobEffect(MobEffectCategory.NEUTRAL, 0xFFFFFF) {

                //Called when DRANK
                @Override
                public boolean applyEffectTick(LivingEntity target, int amplifier) {
                    cleanseTarget(target, null);
                    return true;
                }

                //Called when SPLASHED (from splash potion, cloud, or arrow)
                @Override
                public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity target, int amplifier, double healthDelta) {
                    cleanseTarget(target, indirectSource);
                }

                private void cleanseTarget(LivingEntity target, @Nullable Entity thrower) {
                    if (target.level().isClientSide()) return;

                    //Remove all active effects safely
                    List<MobEffectInstance> active = new ArrayList<>(target.getActiveEffects());
                    for (MobEffectInstance instance : active) {
                        target.removeEffect(instance.getEffect());
                    }

                    //Award advancement to player target OR the thrower
                    ServerPlayer playerToAward = null;
                    if (target instanceof ServerPlayer serverPlayer) {
                        playerToAward = serverPlayer;
                    } else if (thrower instanceof ServerPlayer throwerPlayer) {
                        playerToAward = throwerPlayer;
                    }

                    if (playerToAward != null) {
                        AdvancementHolder advancement = playerToAward.getServer().getAdvancements().get(
                                ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "got_milk_potion")
                        );
                        if (advancement != null) {
                            playerToAward.getAdvancements().award(advancement, "cleansed_with_milk");
                        }
                    }
                }
            });

    
    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

}
