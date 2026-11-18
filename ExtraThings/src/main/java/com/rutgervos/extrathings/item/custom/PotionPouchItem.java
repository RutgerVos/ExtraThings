package com.rutgervos.extrathings.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import java.util.List;

import com.rutgervos.extrathings.screen.custom.PotionPouchMenu;

public class PotionPouchItem extends Item {
    public PotionPouchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.translatable("screen.extrathings.potion_pouch");
                }

                @Override
                public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player pPlayer) {
                    return new PotionPouchMenu(containerId, playerInventory, stack);
                }
            }, buf -> buf.writeInt(serverPlayer.getInventory().selected));
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
    tooltip.add(Component.translatable("tooltip.extrathings.potion_pouch"));

    //Secret Shift easter egg
    if (Screen.hasShiftDown()) {
        tooltip.add(Component.translatable("tooltip.extrathings.potion_pouch.secret")
                .withStyle(style -> style.withColor(0xAAAAAA).withItalic(true)));
    } else {
        tooltip.add(Component.translatable("tooltip.extrathings.potion_pouch.shift_hint")
                .withStyle(style -> style.withColor(0x555555)));
    }

    super.appendHoverText(stack, context, tooltip, flag);
}
}