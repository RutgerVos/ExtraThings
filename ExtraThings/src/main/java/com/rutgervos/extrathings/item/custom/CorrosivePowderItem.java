package com.rutgervos.extrathings.item.custom;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CorrosivePowderItem extends Item {
    public CorrosivePowderItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        //Check for Nether Wart growth behavior
        if (state.is(Blocks.NETHER_WART)) {
            int age = state.getValue(NetherWartBlock.AGE);
            if (age < NetherWartBlock.MAX_AGE) {
                if (!level.isClientSide()) {
                    //Instantly set to fully grown age (3)
                    level.setBlockAndUpdate(pos, state.setValue(NetherWartBlock.AGE, NetherWartBlock.MAX_AGE));
                    
                    //Spawn nether-themed particles
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.WITCH, 
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 
                            15, 0.2, 0.2, 0.2, 0.05);
                    }
                }
                
                //Play a sound effect
                level.playSound(context.getPlayer(), pos, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 1.0F, 1.0F);
                
                //Shrink the item stack in the player's hand
                if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild) {
                    context.getItemInHand().shrink(1);
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        //Check for dissolving plants/grass into dirt behavior
        if (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT_PATH) || state.is(Blocks.DIRT) || state.is(Blocks.MUDDY_MANGROVE_ROOTS)) {
            if (!level.isClientSide()) {
                level.setBlockAndUpdate(pos, Blocks.SAND.defaultBlockState());
                
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.ASH, 
                        pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, 
                        10, 0.3, 0.1, 0.3, 0.02);
                }
            }
            
            level.playSound(context.getPlayer(), pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 0.8F);
            
            if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        if (state.is(Blocks.SHORT_GRASS) || state.is(Blocks.TALL_GRASS) || state.is(Blocks.DANDELION) || state.is(Blocks.POPPY)) {
            if (!level.isClientSide()) {
                level.setBlockAndUpdate(pos, Blocks.DEAD_BUSH.defaultBlockState());
                
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.ASH, 
                        pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, 
                        10, 0.3, 0.1, 0.3, 0.02);
                }
            }
            
            level.playSound(context.getPlayer(), pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 0.8F);
            
            if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        if (state.is(Blocks.SAND) || state.is(Blocks.RED_SAND)) {
            if (!level.isClientSide()) {
                level.setBlockAndUpdate(pos, Blocks.SOUL_SAND.defaultBlockState());
                
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.ASH, 
                        pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, 
                        10, 0.3, 0.1, 0.3, 0.02);
                }
            }
            
            level.playSound(context.getPlayer(), pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 0.8F);
            
            if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        if (state.is(Blocks.STONE_BRICKS) || state.is(Blocks.CRACKED_STONE_BRICKS)) {
            if (!level.isClientSide()) {
                level.setBlockAndUpdate(pos, Blocks.NETHER_BRICKS.defaultBlockState());
                
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.ASH, 
                        pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, 
                        10, 0.3, 0.1, 0.3, 0.02);
                }
            }
            
            level.playSound(context.getPlayer(), pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 0.8F);
            
            if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.extrathings.corrosive_powder.desc")
                .withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltip, flag);
    }
}