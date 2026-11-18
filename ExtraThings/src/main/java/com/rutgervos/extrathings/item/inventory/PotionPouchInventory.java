package com.rutgervos.extrathings.item.inventory;

// import com.rutgervos.extrathings.item.custom.PotionPouchItem;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class PotionPouchInventory extends ItemStackHandler {
    private final ItemStack pouchStack;

    public PotionPouchInventory(ItemStack pouchStack, int size) {
        super(size);
        this.pouchStack = pouchStack;
        //Load saved items if they exist on the stack
        CompoundTag tag = pouchStack.getOrDefault(net.minecraft.core.component.DataComponents.CUSTOM_DATA, 
                net.minecraft.world.item.component.CustomData.EMPTY).copyTag();
        if (tag.contains("Inventory")) {
            deserializeNBT(HolderLookup.Provider.create(java.util.stream.Stream.empty()), tag.getCompound("Inventory"));
        }
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        //Save inventory back to the ItemStack tag whenever contents change
        CompoundTag tag = pouchStack.getOrDefault(net.minecraft.core.component.DataComponents.CUSTOM_DATA, 
                net.minecraft.world.item.component.CustomData.EMPTY).copyTag();
        tag.put("Inventory", serializeNBT(HolderLookup.Provider.create(java.util.stream.Stream.empty())));
        pouchStack.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, 
                net.minecraft.world.item.component.CustomData.of(tag));
    }

    // @Override
    // public boolean isItemValid(int slot, ItemStack stack) {
    //     //Only allow potions, splash potions, and lingering potions inside the pouch!
    //     if (stack.getItem() instanceof PotionPouchItem) {
    //     return false;
    //     }
    //     return stack.is(net.minecraft.world.item.Items.POTION) || 
    //            stack.is(net.minecraft.world.item.Items.SPLASH_POTION) || 
    //            stack.is(net.minecraft.world.item.Items.LINGERING_POTION) ||
    //            stack.getItem() instanceof PotionPouchItem == false; //Prevent nesting pouches inside pouches
    // }
}