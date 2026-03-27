package lykrast.defiledlands.common.block;

import lykrast.defiledlands.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.core.registries.Registries;

public class TenebraSaplingBlock extends SaplingBlock {

    private static final ResourceKey<ConfiguredFeature<?, ?>> TENEBRA_TREE_FEATURE = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            new ResourceLocation("defiledlands", "tenebra_tree"));

    private static final AbstractTreeGrower TENEBRA_GROWER = new AbstractTreeGrower() {
        @Override
        protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
            return TENEBRA_TREE_FEATURE;
        }
    };

    public TenebraSaplingBlock(Properties properties) {
        super(TENEBRA_GROWER, properties);
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
}
