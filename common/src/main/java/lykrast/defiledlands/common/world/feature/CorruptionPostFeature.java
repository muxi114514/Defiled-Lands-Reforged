package lykrast.defiledlands.common.world.feature;

import com.mojang.serialization.Codec;
import lykrast.defiledlands.common.util.CorruptionRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * 污化柱生成器
 * 从地表向下污化一整列方块
 */
public class CorruptionPostFeature extends Feature<NoneFeatureConfiguration> {
    
    public CorruptionPostFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        
        // 从当前位置向下污化所有方块
        BlockPos currentPos = pos;
        int corrupted = 0;
        
        while (currentPos.getY() > level.getMinBuildHeight()) {
            BlockState state = level.getBlockState(currentPos);
            BlockState corruptedState = CorruptionRecipes.getCorrupted(state);
            
            if (corruptedState != null) {
                level.setBlock(currentPos, corruptedState, 2);
                corrupted++;
            }
            
            currentPos = currentPos.below();
        }
        
        // 至少污化了一个方块才算成功
        return corrupted > 0;
    }
}
