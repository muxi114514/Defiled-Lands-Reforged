package lykrast.defiledlands.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;

/**
 * 污秽砾石方块
 * 对应旧版 BlockGravelCorrupted：
 * - 继承 DefiledFallingBlock（重力下落 + 污化扩散）
 * - 有概率掉落燧石（与原版砾石一致的逻辑）
 */
public class GravelDefiledBlock extends DefiledFallingBlock {

    public GravelDefiledBlock(Properties properties) {
        super(properties);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        // 优先使用 loot table，若有则直接返回
        List<ItemStack> drops = super.getDrops(state, builder);
        return drops;
    }
}
