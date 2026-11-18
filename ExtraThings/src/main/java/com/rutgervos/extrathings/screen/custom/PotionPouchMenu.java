package com.rutgervos.extrathings.screen.custom;

import com.rutgervos.extrathings.item.inventory.PotionPouchInventory;
import com.rutgervos.extrathings.screen.ModMenuTypes;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class PotionPouchMenu extends AbstractContainerMenu {
    private final PotionPouchInventory pouchInventory;
    private final ItemStack pouchStack;

    public PotionPouchMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(containerId, playerInventory, playerInventory.player.getInventory().getItem(buf.readInt()));
    }

    public PotionPouchMenu(int containerId, Inventory inv, ItemStack pouchStack) {
        super(ModMenuTypes.POTION_POUCH.get(), containerId);
        this.pouchStack = pouchStack;
        this.pouchInventory = new PotionPouchInventory(pouchStack, 9);

        //Add 3x3 Grid for Potions inside the Pouch
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(new SlotItemHandler(pouchInventory, j + i * 3, 62 + j * 18, 17 + i * 18));
            }
        }

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    @Override
    public boolean stillValid(Player player) {
        return !pouchStack.isEmpty() && player.getMainHandItem() == pouchStack || player.getOffhandItem() == pouchStack;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        //Standard shift-click quick move logic between player inventory and pouch
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 9) {
                if (!this.moveItemStackTo(itemstack1, 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
