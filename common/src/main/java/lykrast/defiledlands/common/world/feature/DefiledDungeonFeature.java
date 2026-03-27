package lykrast.defiledlands.common.world.feature;

import com.mojang.serialization.Codec;
import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DefiledDungeonFeature extends Feature<NoneFeatureConfiguration> {

    public static final ResourceLocation LOOT_TABLE = new ResourceLocation(DefiledLands.MOD_ID,
            "chests/dungeon_defiled");

    public DefiledDungeonFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        if (!lykrast.defiledlands.common.util.CorruptionHelper.dungeonEnabled)
            return false;

        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();

        int sizeX = random.nextInt(2) + 2;
        int sizeZ = random.nextInt(2) + 2;

        int minX = -sizeX - 1;
        int maxX = sizeX + 1;
        int minZ = -sizeZ - 1;
        int maxZ = sizeZ + 1;

        int doorCount = 0;
        for (int x = minX; x <= maxX; x++) {
            for (int y = -1; y <= 4; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos checkPos = pos.offset(x, y, z);
                    BlockState state = level.getBlockState(checkPos);
                    boolean isSolid = state.isSolidRender(level, checkPos);

                    if (y == -1 && !isSolid)
                        return false;
                    if (y == 4 && !isSolid)
                        return false;

                    if ((x == minX || x == maxX || z == minZ || z == maxZ) &&
                            y == 0 &&
                            level.isEmptyBlock(checkPos) &&
                            level.isEmptyBlock(checkPos.above())) {
                        doorCount++;
                    }
                }
            }
        }

        if (doorCount < 1 || doorCount > 5) {
            return false;
        }

        BlockState bricks = ModBlocks.STONE_BRICKS_DEFILED.get().defaultBlockState();
        BlockState crackedBricks = ModBlocks.CRACKED_STONE_BRICKS_DEFILED.get().defaultBlockState();

        for (int x = minX; x <= maxX; x++) {
            for (int y = 3; y >= -1; y--) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos placePos = pos.offset(x, y, z);

                    if (x != minX && y != -1 && z != minZ &&
                            x != maxX && y != 4 && z != maxZ) {

                        if (!(level.getBlockState(placePos).getBlock() instanceof ChestBlock)) {
                            level.setBlock(placePos, Blocks.AIR.defaultBlockState(), 2);
                        }
                    }

                    else if (placePos.getY() >= 0 &&
                            !level.getBlockState(placePos.below()).isSolidRender(level, placePos.below())) {
                        level.setBlock(placePos, Blocks.AIR.defaultBlockState(), 2);
                    } else if (level.getBlockState(placePos).isSolidRender(level, placePos) &&
                            !(level.getBlockState(placePos).getBlock() instanceof ChestBlock)) {

                        BlockState blockToPlace = random.nextInt(4) == 0 ? crackedBricks : bricks;
                        level.setBlock(placePos, blockToPlace, 2);
                    }
                }
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int attempt = 0; attempt < 3; attempt++) {
                int chestX = pos.getX() + random.nextInt(sizeX * 2 + 1) - sizeX;
                int chestY = pos.getY();
                int chestZ = pos.getZ() + random.nextInt(sizeZ * 2 + 1) - sizeZ;
                BlockPos chestPos = new BlockPos(chestX, chestY, chestZ);

                if (level.isEmptyBlock(chestPos)) {

                    int solidSides = 0;
                    Direction chestFacing = Direction.NORTH;

                    for (Direction dir : Direction.Plane.HORIZONTAL) {
                        if (level.getBlockState(chestPos.relative(dir)).isSolidRender(level, chestPos.relative(dir))) {
                            solidSides++;
                            chestFacing = dir;
                        }
                    }

                    if (solidSides == 1) {
                        BlockState chestState = Blocks.CHEST.defaultBlockState()
                                .setValue(ChestBlock.FACING, chestFacing.getOpposite());
                        level.setBlock(chestPos, chestState, 2);

                        BlockEntity blockEntity = level.getBlockEntity(chestPos);
                        if (blockEntity instanceof ChestBlockEntity chest) {
                            chest.setLootTable(LOOT_TABLE, random.nextLong());
                        }
                        break;
                    }
                }
            }
        }

        level.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 2);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof SpawnerBlockEntity spawner) {

        }

        return true;
    }
}
