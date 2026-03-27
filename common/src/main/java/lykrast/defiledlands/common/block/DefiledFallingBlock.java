package lykrast.defiledlands.common.block;

import lykrast.defiledlands.common.util.CorruptionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DefiledFallingBlock extends FallingBlock {

    public DefiledFallingBlock(Properties properties) {
        super(properties.randomTicks());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        CorruptionHelper.spread(level, pos, state, random);

        super.randomTick(state, level, pos, random);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }
}
