package com.rutgervos.extrathings.item;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.block.ModBlocks;
import com.rutgervos.extrathings.item.custom.FuelItem;
import com.rutgervos.extrathings.item.custom.ModArmorItem;
import com.rutgervos.extrathings.item.custom.OreDetectorItem;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, ExtraThings.MODID);

    public static final RegistryObject<Item> EXTRA_INGOT = ITEMS.register("extra_ingot", () -> new Item( new Item.Properties()));
    public static final RegistryObject<Item> ORE_DETECTOR = ITEMS.register("ore_detector", () -> new OreDetectorItem( new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new Item( new Item.Properties().food(ModFoods.BUTTER)));
    public static final RegistryObject<Item> EXTRA_FUEL = ITEMS.register("extra_fuel", () -> new FuelItem( new Item.Properties(), 20000));
    public static final RegistryObject<Item> EXTRA_PICKAXE = ITEMS.register("extra_pickaxe", () -> new PickaxeItem(Tiers.NETHERITE, new Item.Properties().durability(1000000).fireResistant().stacksTo(1)));
    public static final RegistryObject<Item> STRAWBERRY_SEEDS = ITEMS.register("strawberry_seeds", () -> new ItemNameBlockItem(ModBlocks.STRAWBERRY_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry",() -> new Item(new Item.Properties().food(ModFoods.STRAWBERRY)));
     public static final RegistryObject<Item> EXTRA_HELMET = ITEMS.register("extra_helmet",
            () -> new ModArmorItem(ModArmorMaterials.EXTRA_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))));
    public static final RegistryObject<Item> EXTRA_CHESTPLATE = ITEMS.register("extra_chestplate",
            () -> new ArmorItem(ModArmorMaterials.EXTRA_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(18))));
    public static final RegistryObject<Item> EXTRA_LEGGINGS = ITEMS.register("extra_leggings",
            () -> new ArmorItem(ModArmorMaterials.EXTRA_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(18))));
    public static final RegistryObject<Item> EXTRA_BOOTS = ITEMS.register("extra_boots",
            () -> new ArmorItem(ModArmorMaterials.EXTRA_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(18))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
