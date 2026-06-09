package com.rutgervos.extrathings.screen.custom;

import com.rutgervos.extrathings.block.ModBlocks;
import com.rutgervos.extrathings.block.entity.custom.ExtraFuseBlockEntity;
import com.rutgervos.extrathings.screen.ModMenuTypes;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class ExtraFuseBlockMenu extends AbstractContainerMenu {
    private final ExtraFuseBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
   public ExtraFuseBlockMenu(int id, Inventory inv, FriendlyByteBuf data) {
        this(id, inv, (ExtraFuseBlockEntity) inv.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(4));
    }

   public ExtraFuseBlockMenu(int id, Inventory inv, ExtraFuseBlockEntity blockEntity, ContainerData data) {
    super(ModMenuTypes.EXTRA_FUSE_BLOCK_MENU.get(), id);
    this.blockEntity = (ExtraFuseBlockEntity) blockEntity;
    this.level = inv.player.level();
    this.data = data;

    // 1. ADD TILE ENTITY SLOTS FIRST (Indices 0 - 3)
    this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
        // Keeping your fine-tuned custom coordinates intact
        this.addSlot(new SlotItemHandler(handler, 0, 6, 34));
        this.addSlot(new SlotItemHandler(handler, 1, 29, 34));
        this.addSlot(new SlotItemHandler(handler, 2, 54, 34));
        
        this.addSlot(new SlotItemHandler(handler, 3, 104, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false; 
            }
        });
    });

    // 2. ADD VANILLA INVENTORY SLOTS SECOND (Indices 4 - 39)
    addPlayerInventory(inv);
    addPlayerHotbar(inv);

    addDataSlots(data);
}

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 4;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

   @Override
public boolean stillValid(Player pPlayer) {
    return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
            pPlayer, ModBlocks.EXTRA_FUSE_BLOCK.get());
}

     public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int arrowPixelSize = 24;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
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
