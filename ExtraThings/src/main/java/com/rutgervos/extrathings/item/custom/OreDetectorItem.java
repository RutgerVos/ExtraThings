package com.rutgervos.extrathings.item.custom;

import java.util.List;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class OreDetectorItem extends Item {

    public OreDetectorItem(Properties p_41383_) {
        super(p_41383_);
        //TODO Auto-generated constructor stub
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide()) {
            BlockPos postionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            boolean foundBlock = false;

            for(int i = 0; i<= postionClicked.getY() + 64; i++)
            {
                BlockState state = pContext.getLevel().getBlockState(postionClicked.below(i));
                if (isValuableBlock(state)) {
                    outputValuableCoordinates(postionClicked.below(i), player, state.getBlock());
                    foundBlock = true;
                    break;
                }
            }
            if (!foundBlock) {
                player.sendSystemMessage(Component.literal("Found no ores!"));
            }

        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents,
            TooltipFlag pTooltipFlag) {
                pTooltipComponents.add(Component.translatable("tooltip.extrathings.ore_detector.tooltip"));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    private void outputValuableCoordinates(BlockPos blockPos, Player player, Block block) {
        player.sendSystemMessage(Component.literal("Found " + I18n.get(block.getDescriptionId()) + " at " + "(" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")"));
    }

    private boolean isValuableBlock(BlockState state) {
        return state.is(Blocks.IRON_ORE) || state.is(Blocks.EMERALD_ORE) || 
        state.is(Blocks.DIAMOND_ORE) || state.is(Blocks.GOLD_ORE) || 
        state.is(Blocks.REDSTONE_ORE) || state.is(Blocks.COPPER_ORE) || 
        state.is(Blocks.COAL_ORE) || state.is(Blocks.NETHER_QUARTZ_ORE)|| state.is(Blocks.ANCIENT_DEBRIS);
    }
}
