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

/**
 * 污秽地牢生成器
 * 生成小型地下房间，包含刷怪笼和战利品箱
 */
public class DefiledDungeonFeature extends Feature<NoneFeatureConfiguration> {

    public static final ResourceLocation LOOT_TABLE = new ResourceLocation(DefiledLands.MOD_ID,
            "chests/dungeon_defiled");

    public DefiledDungeonFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        // 配置关闭时跳过地牢生成
        if (!lykrast.defiledlands.common.util.CorruptionHelper.dungeonEnabled)
            return false;

        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();

        // 随机大小
        int sizeX = random.nextInt(2) + 2; // 2-3
        int sizeZ = random.nextInt(2) + 2; // 2-3

        int minX = -sizeX - 1;
        int maxX = sizeX + 1;
        int minZ = -sizeZ - 1;
        int maxZ = sizeZ + 1;

        // 检查是否有足够的空间
        int doorCount = 0;
        for (int x = minX; x <= maxX; x++) {
            for (int y = -1; y <= 4; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos checkPos = pos.offset(x, y, z);
                    BlockState state = level.getBlockState(checkPos);
                    boolean isSolid = state.isSolidRender(level, checkPos);

                    // 地板和天花板必须是固体
                    if (y == -1 && !isSolid)
                        return false;
                    if (y == 4 && !isSolid)
                        return false;

                    // 计算门的数量
                    if ((x == minX || x == maxX || z == minZ || z == maxZ) &&
                            y == 0 &&
                            level.isEmptyBlock(checkPos) &&
                            level.isEmptyBlock(checkPos.above())) {
                        doorCount++;
                    }
                }
            }
        }

        // 必须有 1-5 个门
        if (doorCount < 1 || doorCount > 5) {
            return false;
        }

        // 生成地牢
        BlockState bricks = ModBlocks.STONE_BRICKS_DEFILED.get().defaultBlockState();
        BlockState crackedBricks = ModBlocks.CRACKED_STONE_BRICKS_DEFILED.get().defaultBlockState();

        for (int x = minX; x <= maxX; x++) {
            for (int y = 3; y >= -1; y--) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos placePos = pos.offset(x, y, z);

                    // 内部空间
                    if (x != minX && y != -1 && z != minZ &&
                            x != maxX && y != 4 && z != maxZ) {
                        // 清空内部（除了箱子）
                        if (!(level.getBlockState(placePos).getBlock() instanceof ChestBlock)) {
                            level.setBlock(placePos, Blocks.AIR.defaultBlockState(), 2);
                        }
                    }
                    // 墙壁、地板、天花板
                    else if (placePos.getY() >= 0 &&
                            !level.getBlockState(placePos.below()).isSolidRender(level, placePos.below())) {
                        level.setBlock(placePos, Blocks.AIR.defaultBlockState(), 2);
                    } else if (level.getBlockState(placePos).isSolidRender(level, placePos) &&
                            !(level.getBlockState(placePos).getBlock() instanceof ChestBlock)) {
                        // 25% 概率使用裂纹石砖
                        BlockState blockToPlace = random.nextInt(4) == 0 ? crackedBricks : bricks;
                        level.setBlock(placePos, blockToPlace, 2);
                    }
                }
            }
        }

        // 放置箱子（1-2个）
        for (int i = 0; i < 2; i++) {
            for (int attempt = 0; attempt < 3; attempt++) {
                int chestX = pos.getX() + random.nextInt(sizeX * 2 + 1) - sizeX;
                int chestY = pos.getY();
                int chestZ = pos.getZ() + random.nextInt(sizeZ * 2 + 1) - sizeZ;
                BlockPos chestPos = new BlockPos(chestX, chestY, chestZ);

                if (level.isEmptyBlock(chestPos)) {
                    // 检查是否靠墙
                    int solidSides = 0;
                    Direction chestFacing = Direction.NORTH;

                    for (Direction dir : Direction.Plane.HORIZONTAL) {
                        if (level.getBlockState(chestPos.relative(dir)).isSolidRender(level, chestPos.relative(dir))) {
                            solidSides++;
                            chestFacing = dir;
                        }
                    }

                    // 只有一面靠墙时放置箱子
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

        // 放置刷怪笼
        level.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 2);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof SpawnerBlockEntity spawner) {
            // TODO: 设置刷怪笼生成的怪物
            // spawner.getSpawner().setEntityId(pickMobSpawner(random));
        }

        return true;
    }
}
