package com.rutgervos.extrathings.enchantment.custom;

import com.mojang.serialization.MapCodec;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record PoisonTouchEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<PoisonTouchEnchantmentEffect> CODEC = MapCodec.unit(PoisonTouchEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel pLevel, int pEnchantmentLevel, EnchantedItemInUse pItem, Entity pEntity, Vec3 pOrigin) {
        if (pEntity instanceof LivingEntity livingEntity) {
            
            // --- Effect Logic ---
            
            // Duration calculation: 2 seconds (40 ticks) per enchantment level.
            // Level I: 80 ticks (4s)
            // Level II: 160 ticks (6s)
            // Level II: 240 ticks (8s)
            int duration = 80 * pEnchantmentLevel;

            // Amplifier calculation: Mob effect amplifiers are 0-indexed.
            // Level I enchantment -> Amplifier 0 (Wither I)
            // Level II enchantment -> Amplifier 1 (Wither II)
            int amplifier = pEnchantmentLevel - 1;

            // Apply the Poison effect
            livingEntity.addEffect(new MobEffectInstance(
                MobEffects.POISON,
                duration,
                amplifier,
                false,
                true 
            ));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}
