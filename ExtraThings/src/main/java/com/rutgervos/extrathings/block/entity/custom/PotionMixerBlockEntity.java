package com.rutgervos.extrathings.block.entity.custom;


import com.rutgervos.extrathings.block.entity.ModBlockEntities;
import com.rutgervos.extrathings.screen.custom.PotionMixerMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PotionMixerBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
        @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return switch (slot) {
            case 0, 1 -> stack.is(Items.POTION) || stack.is(Items.SPLASH_POTION) || stack.is(Items.LINGERING_POTION)/* add your custom items/ingredients here */;
            case 2 -> false; // Output slot: player cannot place items directly into it
            default -> super.isItemValid(slot, stack);
        };
    }
    };

    private final LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.of(() -> itemHandler);

    private int progress = 0;
    private int maxProgress = 100;

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> PotionMixerBlockEntity.this.progress;
                case 1 -> PotionMixerBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> PotionMixerBlockEntity.this.progress = value;
                case 1 -> PotionMixerBlockEntity.this.maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public PotionMixerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.POTION_MIXER.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PotionMixerBlockEntity entity) {
        if (level.isClientSide()) return;

        if (entity.hasValidRecipe()) {
            entity.progress++;
            setChanged(level, pos, state);

            if (entity.progress >= entity.maxProgress) {
                entity.mixPotions();
                entity.progress = 0;
            }
        } else {
            entity.progress = 0;
            setChanged(level, pos, state);
        }
    }

    private boolean hasValidRecipe() {
        ItemStack slot0 = itemHandler.getStackInSlot(0);
        ItemStack slot1 = itemHandler.getStackInSlot(1);
        ItemStack output = itemHandler.getStackInSlot(2);

        if (!isPotionItem(slot0) || !isPotionItem(slot1)) return false;
        return output.isEmpty();
    }

    private boolean isPotionItem(ItemStack stack) {
        return stack.is(Items.POTION) || stack.is(Items.SPLASH_POTION) || stack.is(Items.LINGERING_POTION);
    }

    private void mixPotions() {
       ItemStack input1 = itemHandler.getStackInSlot(0);
        ItemStack input2 = itemHandler.getStackInSlot(1);

        PotionContents contents1 = input1.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        PotionContents contents2 = input2.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);

        //Use a map or list to collect and merge identical effects
        List<MobEffectInstance> rawEffects = new ArrayList<>();
        contents1.customEffects().forEach(rawEffects::add);
        contents2.customEffects().forEach(rawEffects::add);

        if (contents1.potion().isPresent()) {
            for (MobEffectInstance instance : contents1.potion().get().value().getEffects()) {
                rawEffects.add(new MobEffectInstance(instance));
            }
        }
        if (contents2.potion().isPresent()) {
            for (MobEffectInstance instance : contents2.potion().get().value().getEffects()) {
                rawEffects.add(new MobEffectInstance(instance));
            }
        }

        //MERGE DUPLICATE EFFECTS
        List<MobEffectInstance> combinedEffects = new ArrayList<>();
        for (MobEffectInstance newEffect : rawEffects) {
            boolean merged = false;
            for (MobEffectInstance existingEffect : combinedEffects) {
                if (existingEffect.getEffect().equals(newEffect.getEffect())) {
                    //Same effect found! Combine durations and amplifiers cleanly.
                    int combinedDuration = existingEffect.getDuration() + newEffect.getDuration();
                    //If amplifiers match, increase amplifier by 1, otherwise take the max amplifier
                    int combinedAmplifier = Math.max(existingEffect.getAmplifier(), newEffect.getAmplifier());
                    
                    //Replace existing with the upgraded stack
                    combinedEffects.remove(existingEffect);
                    combinedEffects.add(new MobEffectInstance(newEffect.getEffect(), combinedDuration, combinedAmplifier));
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                combinedEffects.add(new MobEffectInstance(newEffect));
            }
        }

        ItemStack mixedPotion = new ItemStack(input1.is(Items.SPLASH_POTION) || input2.is(Items.SPLASH_POTION)
                ? Items.SPLASH_POTION : Items.POTION);

        PotionContents newContents = new PotionContents(
                Optional.empty(),
                Optional.of(0x9900FF),
                combinedEffects
        );

        mixedPotion.set(DataComponents.POTION_CONTENTS, newContents);

        Component rainbowName = Component.literal("M")
                .withStyle(style -> style.withColor(0xFF5555).withItalic(false))
                .append(Component.literal("i").withStyle(style -> style.withColor(0xFFAAA6).withItalic(false)))
                .append(Component.literal("x").withStyle(style -> style.withColor(0xFFFFAA).withItalic(false)))
                .append(Component.literal("e").withStyle(style -> style.withColor(0x55FF55).withItalic(false)))
                .append(Component.literal("d").withStyle(style -> style.withColor(0x55FFFF).withItalic(false)))
                .append(Component.literal(" ").withStyle(style -> style.withItalic(false)))
                .append(Component.literal("P").withStyle(style -> style.withColor(0x5555FF).withItalic(false)))
                .append(Component.literal("o").withStyle(style -> style.withColor(0xAA55FF).withItalic(false)))
                .append(Component.literal("t").withStyle(style -> style.withColor(0xFF55FF).withItalic(false)))
                .append(Component.literal("i").withStyle(style -> style.withColor(0xFF8888).withItalic(false)))
                .append(Component.literal("o").withStyle(style -> style.withColor(0xFFAAFF).withItalic(false)))
                .append(Component.literal("n").withStyle(style -> style.withColor(0xFF5555).withItalic(false)));

        mixedPotion.set(DataComponents.CUSTOM_NAME, rainbowName);

        input1.shrink(1);
        input2.shrink(1);
        itemHandler.setStackInSlot(2, mixedPotion);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.extrathings.potion_mixer_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new PotionMixerMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
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