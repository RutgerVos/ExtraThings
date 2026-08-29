package com.rutgervos.extrathings.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.List;

public class ExperienceGemItem extends Item {
    public static final String XP_TAG = "StoredXP";
    public static final int MAX_XP_LEVELS = 100; //Limit per gem

    public ExperienceGemItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag tag = customData.copyTag();
            int currentStored = tag.getInt(XP_TAG);

            //SHIFT + RIGHT CLICK: Withdraw 1 Level
            if (player.isShiftKeyDown()) {
                if (currentStored > 0) {
                    tag.putInt(XP_TAG, currentStored - 1);
                    CustomData.set(DataComponents.CUSTOM_DATA, stack, tag);
                    
                    player.giveExperienceLevels(1);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5F, 1.2F);
                    return InteractionResultHolder.success(stack);
                } else {
                    player.displayClientMessage(Component.literal("The Experience Gem is empty!")
                            .withStyle(ChatFormatting.RED), true);
                }
            } 
            //RIGHT CLICK: Store 1 Level
            else {
                if (player.experienceLevel > 0) {
                    if (currentStored < MAX_XP_LEVELS) {
                        tag.putInt(XP_TAG, currentStored + 1);
                        CustomData.set(DataComponents.CUSTOM_DATA, stack, tag);

                        player.giveExperienceLevels(-1);
                        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5F, 0.8F);
                        return InteractionResultHolder.success(stack);
                    } else {
                        player.displayClientMessage(Component.literal("The Experience Gem is full!")
                                .withStyle(ChatFormatting.GOLD), true);
                    }
                } else {
                    player.displayClientMessage(Component.literal("You don't have any levels to store!")
                            .withStyle(ChatFormatting.RED), true);
                }
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        int stored = customData.copyTag().getInt(XP_TAG);

        tooltip.add(Component.literal("Stored Levels: " + stored + " / " + MAX_XP_LEVELS)
                .withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.literal("Right-Click to deposit 1 Level")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Shift + Right-Click to withdraw 1 Level")
                .withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltip, flag);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return customData.copyTag().getInt(XP_TAG) > 0;
    }
}