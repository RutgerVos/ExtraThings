package com.rutgervos.extrathings.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceLocation;
import com.rutgervos.extrathings.ExtraThings;

import java.util.List;

public class HeartContainerItem extends Item {
    // Unique ID for the health modifier
    private static final ResourceLocation HEALTH_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(ExtraThings.MODID, "extra_heart_modifier");
    private static final int MAX_ADDITIONAL_HEARTS = 10; // Player can gain 10 extra hearts (Total 20)

    public HeartContainerItem(Properties properties) {
        super(properties.stacksTo(16));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide) {
            AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);

            if (healthAttribute != null) {
                double currentModifierValue = 0;
                AttributeModifier existingModifier = healthAttribute.getModifier(HEALTH_MODIFIER_ID);
                
                if (existingModifier != null) {
                    currentModifierValue = existingModifier.amount();
                }

                // Check if player is under the limit (2.0 per heart)
                if (currentModifierValue < (MAX_ADDITIONAL_HEARTS * 2)) {
                    // Remove old modifier to replace it with the upgraded one
                    healthAttribute.removeModifier(HEALTH_MODIFIER_ID);
                    
                    AttributeModifier newModifier = new AttributeModifier(
                            HEALTH_MODIFIER_ID, 
                            currentModifierValue + 2.0, 
                            AttributeModifier.Operation.ADD_VALUE
                    );
                    
                    healthAttribute.addPermanentModifier(newModifier);
                    
                    
                    player.setHealth(player.getMaxHealth());
                    
                    // Consume item
                    stack.shrink(1);
                    
                    player.displayClientMessage(Component.translatable("message.extrathings.heart_used")
                            .withStyle(ChatFormatting.LIGHT_PURPLE), true);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), 
                    net.minecraft.sounds.SoundEvents.PLAYER_LEVELUP, 
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
                    
                    return InteractionResultHolder.success(stack);
                } else {
                    player.displayClientMessage(Component.translatable("message.extrathings.heart_limit")
                            .withStyle(ChatFormatting.RED), true);
                }
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.extrathings.heart_container.desc")
                .withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}
