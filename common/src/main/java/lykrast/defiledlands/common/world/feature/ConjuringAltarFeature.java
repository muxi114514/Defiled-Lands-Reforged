package lykrast.defiledlands.common.world.feature;

import com.mojang.serialization.Codec;
import lykrast.defiledlands.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * 召唤台生成器
 * 在地表生成召唤台结构
 */
public class ConjuringAltarFeature extends Feature<NoneFeatureConfiguration> {
    
    public ConjuringAltarFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        
        // 寻找地面
        while (level.isEmptyBlock(pos) && pos.getY() > level.getMinBuildHeight() + 2) {
            pos = pos.below();
        }
        
        // 检查地面是否合适（2x2 区域）
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos checkPos = pos.offset(x, -1, z);
                if (level.isEmptyBlock(checkPos) && level.isEmptyBlock(checkPos.below())) {
                    return false;
                }
            }
        }
        
        BlockState bricks = ModBlocks.STONE_BRICKS_DEFILED.get().defaultBlockState();
        BlockState crackedBricks = ModBlocks.CRACKED_STONE_BRICKS_DEFILED.get().defaultBlockState();
        BlockState sandstone = ModBlocks.SANDSTONE_CORRUPTED.get().defaultBlockState();
        BlockState altar = ModBlocks.CONJURING_ALTAR.get().defaultBlockState();
        
        // 建造地面（-1 层）
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                level.setBlock(pos.offset(x, -1, z), getRandomBrick(random, bricks, crackedBricks), 2);
                
                // 中心 3x3 区域
                if (x >= -1 && x <= 1 && z >= -1 && z <= 1) {
                    level.setBlock(pos.offset(x, 0, z), sandstone, 2);
                } else {
                    level.setBlock(pos.offset(x, 0, z), getRandomBrick(random, bricks, crackedBricks), 2);
                }
            }
        }
        
        // 建造柱子（1-3 层）
        for (int y = 1; y <= 3; y++) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    // 四个角落是柱子
                    if ((x == -2 || x == 2) && (z == -2 || z == 2)) {
                        level.setBlock(pos.offset(x, y, z), getRandomBrick(random, bricks, crackedBricks), 2);
                    } else {
                        // 其他位置清空
                        level.removeBlock(pos.offset(x, y, z), false);
                    }
                }
            }
        }
        
        // 放置召唤台
        level.setBlock(pos.offset(0, 1, 0), altar, 2);
        
        return true;
    }
    
    /**
     * 随机选择普通石砖或裂纹石砖
     */
    private BlockState getRandomBrick(RandomSource random, BlockState normal, BlockState cracked) {
        return random.nextInt(4) == 0 ? cracked : normal;
    }
}
