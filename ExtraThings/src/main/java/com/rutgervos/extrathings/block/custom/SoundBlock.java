package com.rutgervos.extrathings.block.custom;

import com.mojang.datafixers.types.templates.List;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SoundBlock extends Block {

    public SoundBlock(Properties p_49795_) {
        super(p_49795_);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, java.util.List<Component> pTooltipComponents,
            TooltipFlag pTooltipFlag) {
                pTooltipComponents.add(Component.literal("Cowbell moooooooo!"));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer,
            BlockHitResult pHit) {
                pLevel.playSound(pPlayer, pPos, SoundEvents.NOTE_BLOCK_COW_BELL.get(), SoundSource.BLOCKS, 1f, 1f);
        // return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHit);
        return InteractionResult.SUCCESS;
    }
}
