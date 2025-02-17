package com.rutgervos.extrathings.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties BUTTER = new FoodProperties.Builder().nutrition(3).fast()
    .saturationModifier(0.3f).effect(new MobEffectInstance(MobEffects.SATURATION, 200), 1).build();

        public static final FoodProperties STRAWBERRY = new FoodProperties.Builder().nutrition(2).fast()
                .saturationModifier(0.2f).effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 0.1f).build();
}
