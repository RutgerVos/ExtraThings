package com.rutgervos.extrathings.block.custom;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;
import com.rutgervos.extrathings.block.entity.ModBlockEntities;
import com.rutgervos.extrathings.block.entity.custom.ExtraFuseBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ExtraFuseBlock extends BaseEntityBlock{

    public ExtraFuseBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
       return new ExtraFuseBlockEntity(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof ExtraFuseBlockEntity ExtraFuseBlockEntity) {
                ((ServerPlayer) player).openMenu(ExtraFuseBlockEntity, pos);
            }
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        throw new UnsupportedOperationException("Unimplemented method 'codec'");
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null; // Don't tick recipe logic on the client rendering thread
        }
        
        // Verifies the block entity matches before starting the ticker loop
        return createTickerHelper(pBlockEntityType, ModBlockEntities.EXTRA_FUSE_BLOCK_BE.get(),
                (level, pos, state, blockEntity) -> ExtraFuseBlockEntity.tick(level, pos, state, blockEntity));
    }

}
