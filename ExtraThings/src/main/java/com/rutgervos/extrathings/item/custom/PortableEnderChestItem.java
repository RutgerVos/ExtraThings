package com.rutgervos.extrathings.item.custom;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder; // Import InteractionResultHolder
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.network.chat.Component;

public class PortableEnderChestItem extends Item {

    public PortableEnderChestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide) {
            SimpleMenuProvider menuProvider = new SimpleMenuProvider(
                (containerId, inventory, player) -> ChestMenu.threeRows(containerId, inventory, player.getEnderChestInventory()),
                Component.translatable("container.enderchest")
            );

            pPlayer.openMenu(menuProvider);

            return InteractionResultHolder.success(pPlayer.getItemInHand(pHand));
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pHand));
    }

    // The useOn method can still return InteractionResult
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        // We call the 'use' method and get the InteractionResult from the InteractionResultHolder
        return use(pContext.getLevel(), pContext.getPlayer(), pContext.getHand()).getResult();
    }
}