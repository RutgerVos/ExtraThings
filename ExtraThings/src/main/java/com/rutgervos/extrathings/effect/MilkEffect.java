package com.rutgervos.extrathings.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class MilkEffect extends MobEffect {

        public MilkEffect() {
        // NEUTRAL category, custom color (pure white milk: 0xFFFFFF)
        super(MobEffectCategory.NEUTRAL, 0xFFFFFF);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide()) {
            //Clears ALL active potion effects (buffs and debuffs)
            entity.removeAllEffects();
        }
        return true;
    }

    public boolean shouldApplyEffectTickThisFrame(int duration, int amplifier) {
        //Run immediately on the first tick (Instant effect like Instant Health/Harm)
        return true;
    }

}
