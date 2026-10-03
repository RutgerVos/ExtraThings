package com.rutgervos.extrathings.block.entity.custom;

import javax.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import com.rutgervos.extrathings.block.entity.ModBlockEntities;
import com.rutgervos.extrathings.recipe.ExtraFuseRecipe;
import com.rutgervos.extrathings.recipe.ExtraFuseRecipeInput;
import com.rutgervos.extrathings.recipe.ModRecipes;
import com.rutgervos.extrathings.screen.custom.ExtraFuseBlockMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class ExtraFuseBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;

    // FIXED: Fields are now properly declared at the class level so 'tick()' can read them!
    private int progress = 0;
    private int maxProgress = 200; 

    public ExtraFuseBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EXTRA_FUSE_BLOCK_BE.get(), pos, state);
        
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ExtraFuseBlockEntity.this.progress;
                    case 1 -> ExtraFuseBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ExtraFuseBlockEntity.this.progress = value;
                    case 1 -> ExtraFuseBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 4; 
            }
        };
    }

   // --- RECIPE CRAFTING TICK LOGIC ---

    public static void tick(Level level, BlockPos pos, BlockState state, ExtraFuseBlockEntity blockEntity) {
        if (level.isClientSide()) return;

        List<ItemStack> inventorySnapshot = java.util.Arrays.asList(
                blockEntity.itemHandler.getStackInSlot(0).copy(),
                blockEntity.itemHandler.getStackInSlot(1).copy(),
                blockEntity.itemHandler.getStackInSlot(2).copy()
        );
        ExtraFuseRecipeInput recipeInput = new ExtraFuseRecipeInput(inventorySnapshot);

        java.util.Optional<RecipeHolder<ExtraFuseRecipe>> currentRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.EXTRA_FUSE_BLOCK_TYPE.get(), recipeInput, level);

        if (currentRecipe.isPresent()) {
            ExtraFuseRecipe recipe = currentRecipe.get().value();
            ItemStack expectedOutput = recipe.getResultItem(level.registryAccess());

            // DIAGNOSTIC LOG 1: The recipe matched!
            // System.out.println("ExtraThings DEBUG: Recipe found! Output item is: " + expectedOutput.getItem().toString() + " x" + expectedOutput.getCount());

            if (blockEntity.canInsertItemIntoOutput(expectedOutput)) {
                blockEntity.progress++;
                setChanged(level, pos, state); 

                if (blockEntity.progress >= blockEntity.maxProgress) {
                    blockEntity.craftItem(recipe, recipeInput, level.registryAccess());
                    blockEntity.progress = 0; 
                    setChanged(level, pos, state);
                    // System.out.println("ExtraThings DEBUG: Crafting complete!");
                }
            } else {
                // DIAGNOSTIC LOG 2: Jammed output slot
                System.out.println("ExtraThings DEBUG: Recipe matched, but canInsertItemIntoOutput returned false! Output slot contents: " + blockEntity.itemHandler.getStackInSlot(3).getItem().toString());
                blockEntity.progress = 0; 
            }
        } else {
            // DIAGNOSTIC LOG 3: Recipe completely skipped
            // To prevent spamming, only print this if there are actually items inside the machine
            if (!inventorySnapshot.get(0).isEmpty() || !inventorySnapshot.get(1).isEmpty() || !inventorySnapshot.get(2).isEmpty()) {
                // System.out.println("ExtraThings DEBUG: Items are in the grid, but RecipeManager returned Optional.empty()!");
            }
            blockEntity.progress = 0; 
        }
    }

    private boolean canInsertItemIntoOutput(ItemStack result) {
        ItemStack outputSlot = this.itemHandler.getStackInSlot(3);
        if (outputSlot.isEmpty()) return true;
        if (!ItemStack.isSameItemSameComponents(outputSlot, result)) return false;
        return outputSlot.getCount() + result.getCount() <= outputSlot.getMaxStackSize();
    }

    private void craftItem(ExtraFuseRecipe recipe, ExtraFuseRecipeInput input, HolderLookup.Provider registries) {
        // Extract exactly 1 item from each active input slot
        for (int slot = 0; slot < 3; slot++) {
            if (!this.itemHandler.getStackInSlot(slot).isEmpty()) {
                this.itemHandler.extractItem(slot, 1, false);
            }
        }

        // Assemble the final product out of the structured data configuration
        ItemStack resultStack = recipe.assemble(input, registries);
        this.itemHandler.insertItem(3, resultStack, false);
    }

    // --- STANDARD OVERRIDES ---

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.extrathings.extra_fuse_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new ExtraFuseBlockMenu(id, playerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("extra_fuse.progress", this.progress);
        tag.putInt("extra_fuse.max_progress", this.maxProgress);
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        this.progress = tag.getInt("extra_fuse.progress");
        this.maxProgress = tag.getInt("extra_fuse.max_progress");
    }

   public void drops() {
    if (this.level != null && !this.level.isClientSide) {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
}
}