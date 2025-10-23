package com.rutgervos.extrathings.enchantment.custom;

import com.mojang.serialization.MapCodec;
import com.rutgervos.extrathings.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Custom enchantment effect for Lava Walker, converting nearby Lava into Magma Blocks temporarily.
 * This effect is designed to be applied on the TICK component of the enchantment, running every tick.
 */
public record LavaWalkerEnchantmentEffect() implements EnchantmentEntityEffect {
    
    // Codec for a record with no parameters
    public static final MapCodec<LavaWalkerEnchantmentEffect> CODEC = MapCodec.unit(LavaWalkerEnchantmentEffect::new);

    /**
     * Applies the effect on entity tick.
     */
    @Override
    public void apply(ServerLevel level, int levelValue, EnchantedItemInUse enchantedItem, Entity entity, Vec3 position) {
        // Ensure the entity is a LivingEntity (like a player)
        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        // Only apply the effect if the entity is not flying, is not already fully immersed, and is moving (for performance)
        if (!livingEntity.onGround() && !livingEntity.isInWaterOrBubble() && !livingEntity.isInLava()) {
            return;
        }
        
        // Don't run every tick if the player is standing still (to mimic Frost Walker performance)
        if (livingEntity.tickCount % 20 != 0 && livingEntity.getDeltaMovement().lengthSqr() < 1.0E-3D) {
             return;
        }

        // Calculate the horizontal range based on the enchantment level (similar to Frost Walker)
        int range = 2 + levelValue; // Level I: 3 radius, Level II: 4 radius
        BlockPos entityPos = livingEntity.blockPosition();

        // Stream over a box around the player's feet to find lava blocks
        Set<BlockPos> blocksToConvert = BlockPos.betweenClosedStream(
                entityPos.offset(-range, -1, -range), 
                entityPos.offset(range, -1, range)
            )
            .map(BlockPos::immutable)
            .filter(blockPos -> isLava(level.getBlockState(blockPos))) // Filter for lava blocks
            .collect(Collectors.toSet());

        // Place Magma Blocks on the identified lava positions
        BlockState temporaryMagmaState = ModBlocks.TEMPORARY_MAGMA_BLOCK.get().defaultBlockState();
        
        for (BlockPos blockPos : blocksToConvert) {
            // Check if the block directly above the lava is clear (to prevent suffocation)
            if (level.isEmptyBlock(blockPos.above())) {
                
                // 1. Set the block to Magma Block
                level.setBlock(blockPos, temporaryMagmaState, 3);
                
                // 2. Schedule a tick for the flowing lava block at this position after the delay.
                // This forces the Magma Block to be replaced by flowing lava when the tick runs.
                int delay = 40 - (levelValue * 10); // ~2 to 3 seconds
                
                // We use the Block instance for the Fluid's default state to schedule the tick.
                level.scheduleTick(blockPos, ModBlocks.TEMPORARY_MAGMA_BLOCK.get(), delay); 
            }
        }
    }
    
    /**
     * Checks if a block state represents a lava source or flowing lava.
     */
    private boolean isLava(BlockState state) {
        // Check if the block is considered a lava fluid
        if (state.getFluidState().is(Fluids.LAVA)) {
            return true;
        }
        // Check if it's a LiquidBlock containing lava (e.g., source block)
        return state.getBlock() instanceof LiquidBlock liquidBlock && liquidBlock.getFluid().isSame(Fluids.LAVA);
    }


    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
