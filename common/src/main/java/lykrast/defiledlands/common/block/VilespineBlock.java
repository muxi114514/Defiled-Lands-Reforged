package lykrast.defiledlands.common.block;

import lykrast.defiledlands.common.registry.ModBlocks;
import lykrast.defiledlands.common.util.PlantUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VilespineBlock extends BushBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final float DAMAGE = 3.0F;

    public VilespineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlocks.GRASS_DEFILED.get())
            || state.is(ModBlocks.DIRT_DEFILED.get())
            || state.is(ModBlocks.SAND_DEFILED.get())
            || state.is(ModBlocks.VILESPINE.get());
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        Block blockBelow = below.getBlock();

        if (blockBelow == this) {
            return true;
        }

        return blockBelow == ModBlocks.GRASS_DEFILED.get()
            || blockBelow == ModBlocks.DIRT_DEFILED.get()
            || blockBelow == ModBlocks.SAND_DEFILED.get();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        if (!level.getBlockState(pos.below()).is(this) && !this.canSurvive(state, level, pos)) {
            return;
        }

        if (level.getBlockState(pos.above()).isAir()) {

            int height = 1;
            while (level.getBlockState(pos.below(height)).is(this)) {
                height++;
            }

            if (height < 7) {
                int age = state.getValue(AGE);

                if (age == 15) {

                    level.setBlockAndUpdate(pos.above(), this.defaultBlockState());
                    level.setBlock(pos, state.setValue(AGE, 0), 4);
                } else {

                    level.setBlock(pos, state.setValue(AGE, age + 1), 4);
                }
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {

        if (PlantUtils.vulnerableToVilespine(entity)) {
            entity.hurt(level.damageSources().cactus(), DAMAGE);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

}
