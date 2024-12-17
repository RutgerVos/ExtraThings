package com.rutgervos.extrathings.event;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rutgervos.extrathings.ExtraThings;
import com.rutgervos.extrathings.block.ModBlocks;
import com.rutgervos.extrathings.item.ModItems;
import com.rutgervos.extrathings.item.custom.HammerItem;
import com.rutgervos.extrathings.potion.ModPotions;
import com.rutgervos.extrathings.villager.ModVillagers;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;

@Mod.EventBusSubscriber(modid = ExtraThings.MODID)
public class ModEvents {
     private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

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

        if (event.getType() == ModVillagers.SOUND_MASTER.get()) {
            Int2ObjectMap<List<ItemListing>> trades = event.getTrades();
            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(net.minecraft.world.item.Items.REDSTONE, 1), 
                new ItemStack(net.minecraft.world.item.Items.NOTE_BLOCK, 1),  
                10, 8, 0.02f));

                trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(net.minecraft.world.item.Items.NOTE_BLOCK, 1), 
                    new ItemStack(ModBlocks.SOUND_BLOCK.get(), 1),  
                    10, 8, 0.02f));

                    trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                        new ItemCost(ModBlocks.SOUND_BLOCK.get(), 1), 
                        new ItemStack(net.minecraft.world.item.Items.JUKEBOX, 1),  
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

     @SubscribeEvent
    public static void onBrewingRecipeRegister(BrewingRecipeRegisterEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.WITHER_SKELETON_SKULL, ModPotions.WITHER_POTION.getHolder().get());
        builder.addMix(Potions.AWKWARD, Items.SLIME_BALL, ModPotions.SLIMEY_POTION.getHolder().get());
    }

     @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if(pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }
}
