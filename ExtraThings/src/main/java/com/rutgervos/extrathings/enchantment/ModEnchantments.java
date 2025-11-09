package com.rutgervos.extrathings.enchantment;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.enchantment.custom.LavaWalkerEnchantmentEffect;
import com.rutgervos.extrathings.enchantment.custom.LightningStrikerEnchantmentEffect;
import com.rutgervos.extrathings.enchantment.custom.WitherSlashEnchantmentEffect;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;

public class ModEnchantments {
     public static final ResourceKey<Enchantment> LIGHTNING_STRIKER = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "lightning_striker"));
             public static final ResourceKey<Enchantment> LAVA_WALKER = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "lava_walker"));
      public static final ResourceKey<Enchantment> WITHER_SLASH = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "wither_slash"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);

        register(context, LIGHTNING_STRIKER, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                5,
                2,
                Enchantment.dynamicCost(5, 8),
                Enchantment.dynamicCost(25, 8),
                2,
                EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new LightningStrikerEnchantmentEffect()));
        register(context, LAVA_WALKER, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE), // Item tag for boots
                items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                2, // Max Level (like Frost Walker)
                2, // Rarity (Common)
                Enchantment.dynamicCost(10, 8), // Minimum cost (10 + level * 8)
                Enchantment.dynamicCost(30, 8), // Maximum cost (30 + level * 8)
                2, // Max trade offer
                EquipmentSlotGroup.FEET)) // Slot for boots
                
                // Exclusive with other movement enchantments (like Frost Walker and Depth Strider)
                .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.BOOTS_EXCLUSIVE))
                
                // Apply the LavaWalkerEnchantmentEffect every T_CK (tick)
                .withEffect(EnchantmentEffectComponents.TICK, 
                        new LavaWalkerEnchantmentEffect()));
         register(context, WITHER_SLASH, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                5,
                2,
                Enchantment.dynamicCost(5, 8),
                Enchantment.dynamicCost(25, 8),
                2,
                EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new WitherSlashEnchantmentEffect()));
    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }

}
