package com.rutgervos.extrathings.item.custom;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class InventoryCobbleGenerator extends Item {

    public InventoryCobbleGenerator(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
       ItemStack Cobblestone = new ItemStack(Items.COBBLESTONE, 1);
        player.getInventory().add(Cobblestone);
    }
}
