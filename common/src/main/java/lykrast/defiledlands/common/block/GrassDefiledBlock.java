package lykrast.defiledlands.common.block;

import lykrast.defiledlands.common.registry.ModBlocks;
import lykrast.defiledlands.common.util.CorruptionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

public class GrassDefiledBlock extends DefiledBlock {

    public GrassDefiledBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        CorruptionHelper.spread(level, pos, state, random);

        if (level.getBrightness(LightLayer.SKY, pos.above()) < 4
                && level.getBlockState(pos.above()).getLightBlock(level, pos.above()) > 2) {
            level.setBlock(pos, ModBlocks.DIRT_DEFILED.get().defaultBlockState(), 3);
            return;
        }

        if (level.getBrightness(LightLayer.SKY, pos.above()) >= 9) {
            for (int i = 0; i < 4; i++) {
                BlockPos target = pos.offset(
                        random.nextInt(3) - 1,
                        random.nextInt(5) - 3,
                        random.nextInt(3) - 1
                );

                if (target.getY() < level.getMinBuildHeight() || target.getY() >= level.getMaxBuildHeight()) {
                    continue;
                }
                if (!level.isLoaded(target)) return;

                BlockState targetState = level.getBlockState(target);
                BlockState above = level.getBlockState(target.above());

                if (targetState.is(ModBlocks.DIRT_DEFILED.get())
                        && level.getBrightness(LightLayer.SKY, target.above()) >= 4
                        && above.getLightBlock(level, target.above()) <= 2) {
                    level.setBlock(target, ModBlocks.GRASS_DEFILED.get().defaultBlockState(), 3);
                }
            }
        }
    }

}
