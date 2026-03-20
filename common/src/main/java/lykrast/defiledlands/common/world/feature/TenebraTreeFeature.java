package lykrast.defiledlands.common.world.feature;

import com.mojang.serialization.Codec;
import lykrast.defiledlands.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TenebraTreeFeature extends Feature<NoneFeatureConfiguration> {

    public TenebraTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos position = context.origin();
        RandomSource rand = context.random();

        int height = rand.nextInt(6) + rand.nextInt(6) + 3;
        boolean canGrow = true;

        if (position.getY() >= 1 && position.getY() + height + 1 <= world.getMaxBuildHeight()) {
            // Check space
            for (int j = position.getY(); j <= position.getY() + 1 + height; ++j) {
                int k = 1;
                if (j == position.getY()) k = 0;
                if (j >= position.getY() + 1 + height - 2) k = 2;

                BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

                for (int l = position.getX() - k; l <= position.getX() + k && canGrow; ++l) {
                    for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && canGrow; ++i1) {
                        if (j >= world.getMinBuildHeight() && j < world.getMaxBuildHeight()) {
                            if (!world.isEmptyBlock(mutable.set(l, j, i1)) && !world.getBlockState(mutable).canBeReplaced()) {
                                canGrow = false;
                            }
                        } else {
                            canGrow = false;
                        }
                    }
                }
            }

            if (!canGrow) return false;

            BlockState soil = world.getBlockState(position.below());
            if ((soil.is(ModBlocks.GRASS_DEFILED.get()) || soil.is(ModBlocks.DIRT_DEFILED.get())) && position.getY() < world.getMaxBuildHeight() - height - 1) {
                world.setBlock(position.below(), ModBlocks.DIRT_DEFILED.get().defaultBlockState(), 3);

                BlockPos.MutableBlockPos trunkPos = position.mutable();
                placeLog(world, trunkPos);

                for (int j = 1; j <= height; j++) {
                    trunkPos.move(Direction.UP);
                    if (rand.nextInt(3) == 0) {
                        trunkPos.move(Direction.Plane.HORIZONTAL.getRandomDirection(rand));
                    }
                    if (!world.isEmptyBlock(trunkPos) && !world.getBlockState(trunkPos).canBeReplaced()) {
                        break;
                    }

                    placeLog(world, trunkPos);

                    for (Direction dir : Direction.Plane.HORIZONTAL) {
                        if (rand.nextInt(6) == 0) {
                            placeLeaf(world, trunkPos.relative(dir));
                        }
                    }
                }
                if (rand.nextInt(6) == 0) {
                    placeLeaf(world, trunkPos.above());
                }

                return true;
            }
        }
        return false;
    }

    private void placeLog(WorldGenLevel world, BlockPos pos) {
        world.setBlock(pos, ModBlocks.TENEBRA_LOG.get().defaultBlockState(), 3);
    }

    private void placeLeaf(WorldGenLevel world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.isAir() || state.canBeReplaced()) {
            world.setBlock(pos, ModBlocks.TENEBRA_LEAVES.get().defaultBlockState(), 3);
        }
    }
}
