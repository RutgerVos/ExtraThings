package com.rutgervos.extrathings.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RecallStoneItem extends Item {
    public RecallStoneItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            // 1. Find the dimension and position of the spawn point
            ServerLevel respawnLevel = serverPlayer.server.getLevel(serverPlayer.getRespawnDimension());
            BlockPos respawnPos = serverPlayer.getRespawnPosition();

            if (respawnLevel != null && respawnPos != null) {
                // 2. Manual calculation (The Failsafe)
                // We add 0.5 to X/Z to center the player on the block
                // We add 1.0 to Y so the player stands ON TOP of the bed/anchor
                double tpX = respawnPos.getX() + 0.5;
                double tpY = respawnPos.getY() + 1.0; 
                double tpZ = respawnPos.getZ() + 0.5;

                // 3. Perform the Teleport
                serverPlayer.teleportTo(
                    respawnLevel, 
                    tpX, tpY, tpZ, 
                    serverPlayer.getRespawnAngle(), 0.0F
                );

                // 4. Effects and Cooldown
                level.playSound(null, player.getX(), player.getY(), player.getZ(), 
                    SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                
                player.getCooldowns().addCooldown(this, 100); // 5-second cooldown
                
                stack.hurtAndBreak(1, serverPlayer, Player.getSlotForHand(hand));
                return InteractionResultHolder.success(stack);
            }
            
            // If no spawn point is set
            player.displayClientMessage(Component.literal("No valid respawn point found!"), true);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public boolean isFoil(ItemStack stack) {
    return true; // This gives it the purple enchantment glint
    }
}