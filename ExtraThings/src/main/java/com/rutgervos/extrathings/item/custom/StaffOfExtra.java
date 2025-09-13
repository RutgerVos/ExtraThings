package com.rutgervos.extrathings.item.custom;

import java.util.Map;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class StaffOfExtra extends Item {

      private static final Map<Block, Block> STAFF_MAP =
            Map.of(
                    Blocks.WATER, Blocks.ICE,
                    Blocks.ICE, Blocks.WATER,
                    Blocks.COBBLESTONE, Blocks.OBSIDIAN,
                    Blocks.LAVA, Blocks.OBSIDIAN,
                    Blocks.OBSIDIAN, Blocks.LAVA
            );

    public StaffOfExtra(Properties p_41383_) {
        super(p_41383_);
    }

     @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Block clickedBlock = level.getBlockState(pContext.getClickedPos()).getBlock();

        if(STAFF_MAP.containsKey(clickedBlock)) {
            if(!level.isClientSide()) {
                level.setBlockAndUpdate(pContext.getClickedPos(), STAFF_MAP.get(clickedBlock).defaultBlockState());

                pContext.getItemInHand().hurtAndBreak(1, ((ServerPlayer) pContext.getPlayer()),EquipmentSlot.MAINHAND);

                level.playSound(null, pContext.getClickedPos(), SoundEvents.ALLAY_THROW, SoundSource.BLOCKS);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
