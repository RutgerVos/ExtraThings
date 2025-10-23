package com.rutgervos.extrathings.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TemporaryMagmaBlock extends MagmaBlock {

    public TemporaryMagmaBlock(Properties properties) {
        super(properties);
        // Ensure the block schedules a tick when placed
        // (though we schedule it manually in the effect)
    }

    /**
     * This method is called when the scheduled tick runs.
     * This is where the block reverts to lava.
     */
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Check if the block is still our temporary magma block.
        if (level.getBlockState(pos).is(this)) {
            // Revert to lava source block or flowing lava.
            // Since we know this block replaced LAVA, we revert to flowing lava.
            // This is the same trick Frost Walker uses to revert to water.
            level.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState()); 
            
            // Optional: Also schedule a tick on the new lava block to ensure it flows correctly
            level.scheduleTick(pos, Blocks.LAVA, 1);
        }
    }
    
    // You must also override the getTickingBlockForWaterLogging() method for fluid interactions
    // if you want full fidelity, but the above is the core of the reversion logic.
}
