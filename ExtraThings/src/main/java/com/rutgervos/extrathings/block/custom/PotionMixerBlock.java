package com.rutgervos.extrathings.block.custom;

import com.mojang.serialization.MapCodec;
import com.rutgervos.extrathings.block.entity.ModBlockEntities;
import com.rutgervos.extrathings.block.entity.custom.PotionMixerBlockEntity;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

import org.jetbrains.annotations.Nullable;

public class PotionMixerBlock extends BaseEntityBlock {

    public static final MapCodec<PotionMixerBlock> CODEC = simpleCodec(PotionMixerBlock::new);
    
    public PotionMixerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PotionMixerBlockEntity(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof PotionMixerBlockEntity mixerBE && player instanceof ServerPlayer serverPlayer) {
                serverPlayer.openMenu(mixerBE, pos);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.POTION_MIXER.get(), PotionMixerBlockEntity::tick);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("tooltip.extrathings.potion_mixer.tooltip").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
    if (!state.is(newState.getBlock())) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof PotionMixerBlockEntity potionBE) {
            potionBE.drops();
            level.updateNeighbourForOutputSignal(pos, this);
        }
        super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}