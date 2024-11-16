package com.rutgervos.extrathings.loot;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class AddItemModifier extends LootModifier{


     public static Supplier<MapCodec<AddItemModifier>> CODEC_SUPPLIER = Suppliers.memoize(() -> RecordCodecBuilder
            .mapCodec(instance -> AddItemModifier.codecStart(instance)
            .and(BuiltInRegistries.ITEM.byNameCodec().fieldOf("item")
                    .forGetter(addItemModifierInstance -> addItemModifierInstance.item))
            .apply(instance, AddItemModifier::new)));
      
    private final Item item;

    public AddItemModifier(LootItemCondition[] conditionsIn, Item item) {
        super(conditionsIn);
        this.item = item;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC_SUPPLIER.get();
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
            LootContext context) {
 for(LootItemCondition condition : this.conditions) {
            if(!condition.test(context)) {
                return generatedLoot;
            }
    }
    return generatedLoot;
}  

}
