package lykrast.defiledlands.common.block;

import lykrast.defiledlands.common.registry.ModBlocks;
import lykrast.defiledlands.common.registry.ModFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Optional;

/**
 * 阴暗树苗（Tenebra Sapling）
 * - 随机 tick 有概率生长为阴暗树
 * - 支持骨粉催熟（立即尝试生成树）
 * - 只能种植在污秽草地或污秽泥土上
 */
public class TenebraSaplingBlock extends BushBlock implements BonemealableBlock {

    public TenebraSaplingBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlocks.GRASS_DEFILED.get()) || state.is(ModBlocks.DIRT_DEFILED.get());
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return mayPlaceOn(level.getBlockState(below), level, below);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // 光照充足且 1/7 概率触发生长
        if (level.getMaxLocalRawBrightness(pos.above()) >= 9 && random.nextInt(7) == 0) {
            tryGrow(state, level, pos, random);
        }
    }

    private void tryGrow(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // 先移除树苗自身，让树干可以从这个位置开始生长
        level.removeBlock(pos, false);

        boolean success = ModFeatures.TENEBRA_TREE.get().place(
                new FeaturePlaceContext<>(
                        Optional.empty(),
                        level,
                        level.getChunkSource().getGenerator(),
                        random,
                        pos,
                        NoneFeatureConfiguration.INSTANCE
                )
        );

        // 如果树木生长失败，恢复树苗
        if (!success) {
            level.setBlock(pos, state, 3);
        }
    }

    // ---- BonemealableBlock ----

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        tryGrow(state, level, pos, random);
    }
}
