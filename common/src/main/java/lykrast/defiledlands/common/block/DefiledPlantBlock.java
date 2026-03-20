package lykrast.defiledlands.common.block;

import lykrast.defiledlands.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DefiledPlantBlock extends BushBlock {
    public DefiledPlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlocks.GRASS_DEFILED.get()) || state.is(ModBlocks.DIRT_DEFILED.get()) || state.is(ModBlocks.SAND_DEFILED.get()) || super.mayPlaceOn(state, level, pos);
    }
}
