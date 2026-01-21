package com.rutgervos.extrathings.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import java.util.List;
import java.util.Optional;

import com.rutgervos.extrathings.component.ModDataComponents;
import com.rutgervos.extrathings.item.ModItems;

public class PocketFurnaceItem extends Item {

    public PocketFurnaceItem(Properties properties) {
        // We set default component values directly in properties
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Toggle ON/OFF when Crouching + Right Click
        if (player.isShiftKeyDown()) {
            boolean currentState = stack.getOrDefault(ModDataComponents.IS_ACTIVE.get(), false);
            stack.set(ModDataComponents.IS_ACTIVE.get(), !currentState);

            if (!level.isClientSide) {
                Component status = !currentState ? 
                    Component.translatable("tooltip.extrathings.pocket_furnace.active").withStyle(ChatFormatting.GREEN) : 
                    Component.translatable("tooltip.extrathings.pocket_furnace.inactive").withStyle(ChatFormatting.RED);
                
                player.displayClientMessage(Component.literal("Pocket Furnace: ").append(status), true);
            }
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level.isClientSide || !(entity instanceof Player player)) return;

        // Only process once per second (20 ticks) to prevent lag
        if (level.getGameTime() % 20 != 0) return;

        // Check if enabled
        if (!stack.getOrDefault(ModDataComponents.IS_ACTIVE.get(), false)) return;

        int fuelLevel = stack.getOrDefault(ModDataComponents.FUEL_LEVEL.get(), 0);

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack targetStack = player.getInventory().getItem(i);
            if (targetStack.isEmpty() || targetStack == stack) continue;

            // Use the global RecipeManager to find Smelting Recipes
            Optional<RecipeHolder<SmeltingRecipe>> recipe = level.getRecipeManager()
                    .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(targetStack), level);

            if (recipe.isPresent()) {
                // 1. Check Fuel logic
                if (fuelLevel <= 0) {
                    if (consumeFuelFromInventory(player)) {
                        fuelLevel = 200; // One Extra Fuel provides 200 "units"
                        stack.set(ModDataComponents.FUEL_LEVEL.get(), fuelLevel);
                    } else {
                        return; // Out of fuel, stop searching
                    }
                }

                // 2. Process Smelt
                ItemStack result = recipe.get().value().getResultItem(level.registryAccess());
                if (!result.isEmpty()) {
                    smeltItem(player, i, targetStack, result.copy());
                    
                    // Reduce fuel and save state
                    fuelLevel--;
                    stack.set(ModDataComponents.FUEL_LEVEL.get(), fuelLevel);
                    
                    // Smelt one item per second
                    break; 
                }
            }
        }
    }

    private boolean consumeFuelFromInventory(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack s = player.getInventory().getItem(i);
            if (s.is(ModItems.EXTRA_FUEL.get())) {
                s.shrink(1);
                return true;
            }
        }
        return false;
    }

    private void smeltItem(Player player, int slot, ItemStack raw, ItemStack ingot) {
        raw.shrink(1);
        if (!player.getInventory().add(ingot)) {
            player.drop(ingot, false);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        boolean isActive = stack.getOrDefault(ModDataComponents.IS_ACTIVE.get(), false);
        int fuel = stack.getOrDefault(ModDataComponents.FUEL_LEVEL.get(), 0);

        tooltip.add(Component.translatable("tooltip.extrathings.pocket_furnace.status")
                .append(isActive ? 
                    Component.translatable("tooltip.extrathings.pocket_furnace.active").withStyle(ChatFormatting.GREEN) : 
                    Component.translatable("tooltip.extrathings.pocket_furnace.inactive").withStyle(ChatFormatting.RED)));

        tooltip.add(Component.translatable("tooltip.extrathings.pocket_furnace.fuel", fuel)
                .withStyle(ChatFormatting.GOLD));

        tooltip.add(Component.translatable("tooltip.extrathings.pocket_furnace.instruction")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("tooltip.extrathings.pocket_furnace.tooltip")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    // Makes the fuel level visible as a durability bar
    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.FUEL_LEVEL.get(), 0) > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        float fuel = stack.getOrDefault(ModDataComponents.FUEL_LEVEL.get(), 0);
        return Math.round(fuel * 13.0f / 200.0f); // 200 is the max fuel from one Extra Fuel
    }
}
