package com.rutgervos.extrathings.event;

import java.util.List;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.item.ModItems;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.item.ItemStack;

@Mod.EventBusSubscriber(modid = ExtraThings.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event)
    {
        if (event.getType() == VillagerProfession.FARMER) {
            Int2ObjectMap<List<ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(net.minecraft.world.item.Items.MILK_BUCKET, 1), 
                new ItemStack(ModItems.BUTTER.get(), 12),  
                10, 8, 0.02f));
        }
    }

     @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(net.minecraft.world.item.Items.EMERALD, 30),
                new ItemStack(ModItems.EXTRA_PICKAXE.get(), 1),
                3, 2, 0.2f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(net.minecraft.world.item.Items.EMERALD, 24),
                new ItemStack(ModItems.ORE_DETECTOR.get(), 1),
                2, 12, 0.15f));
    }
}
