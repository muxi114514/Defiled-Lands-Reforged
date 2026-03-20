package lykrast.defiledlands.common.util;

import lykrast.defiledlands.common.registry.ModBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 污化扩散工具类
 *
 * 两个配置项由 Forge 端 DefiledLandsConfig.bake() 注入：
 * - spreadEnabled：是否开启自动扩散，默认 false（关闭）
 * - confinedSpread：扩散是否限制在污秽群系内，默认 true
 */
public class CorruptionHelper {

    /**
     * 是否开启污秽方块自动扩散。默认 false（关闭）。
     */
    public static boolean spreadEnabled = false;

    /**
     * 若为 true，污秽方块只在污秽生物群系内才能自动扩散污化。
     * 仅在 spreadEnabled=true 时生效。默认 true。
     */
    public static boolean confinedSpread = true;

    /**
     * 每1点幸运属性对繁殖出金色书虫概率的倍率加成。
     * 公式：实际概率 = 默认概率 × (1 + 幸运值 × goldWyrmPerLuck)
     * 默认 0.2（每点幸运+20%），设为 0 则禁用幸运影响。
     */
    public static double goldWyrmPerLuck = 0.2;

    /**
     * 书虫 MaxLevel（消化阈值）通过繁殖可达到的上限。
     * 默认 30，设为更大值可允许培育出消化能力更强的书虫。
     */
    public static int wyrmMaxLevelCap = 30;

    /**
     * 书虫吐出附魔书时的附魔过滤列表，存储附魔的 ResourceLocation 字符串（如 "minecraft:sharpness"）。
     * 配合 wyrmEnchantListIsWhitelist 决定行为：
     * - false（黑名单）：列表中的附魔不会被吐出
     * - true（白名单）：只有列表中的附魔才会被吐出
     */
    public static java.util.List<String> wyrmEnchantList = new java.util.ArrayList<>();

    /**
     * 若为 true，wyrmEnchantList 作为白名单使用；若为 false，作为黑名单使用。
     * 默认 false（黑名单模式）。
     */
    public static boolean wyrmEnchantListIsWhitelist = false;

    /**
     * TerraBlender 生物群系区域权重。值越大污秽群系越常见，0 表示完全禁用。
     */
    public static int biomeRegionWeight = 5;

    /**
     * 是否生成污秽地牢。默认 true。
     */
    public static boolean dungeonEnabled = true;

    /**
     * 每次 randomTick 中尝试扩散的次数。默认 4。
     */
    public static int spreadAttempts = 4;

    /**
     * 扩散范围半径（each axis offset = rand(range*2+1) - range）。默认 2。
     */
    public static int spreadRange = 2;

    /**
     * 判断指定附魔是否允许被书虫吐出。
     */
    public static boolean isWyrmEnchantAllowed(net.minecraft.world.item.enchantment.Enchantment enchantment) {
        String key = net.minecraft.core.registries.BuiltInRegistries.ENCHANTMENT.getKey(enchantment).toString();
        boolean inList = wyrmEnchantList.contains(key);
        return wyrmEnchantListIsWhitelist ? inList : !inList;
    }

    /**
     * 尝试向周围扩散污化。
     * 在 randomTick 中调用，每次最多尝试扩散 4 格。
     * 若 spreadEnabled=false 则直接跳过。
     */
    public static void spread(Level level, BlockPos pos, BlockState state, RandomSource rand) {
        if (level.isClientSide)
            return;
        if (!spreadEnabled)
            return;

        if (canSpread(level, pos)) {
            int diameter = spreadRange * 2 + 1;
            for (int i = 0; i < spreadAttempts; i++) {
                BlockPos target = pos.offset(
                        rand.nextInt(diameter) - spreadRange,
                        rand.nextInt(diameter) - spreadRange,
                        rand.nextInt(diameter) - spreadRange);

                if (target.getY() < level.getMinBuildHeight() || target.getY() >= level.getMaxBuildHeight()) {
                    continue;
                }

                if (!level.isLoaded(target)) {
                    return;
                }

                corrupt(level, target, level.getBlockState(target));
            }
        }
    }

    /**
     * 判断该位置是否允许扩散。
     * 若 confinedSpread=true，则只有在污秽生物群系内才能扩散。
     */
    public static boolean canSpread(Level level, BlockPos pos) {
        if (!confinedSpread) {
            return true;
        }
        Holder<Biome> biome = level.getBiome(pos);
        return isDefiledBiome(biome);
    }

    /**
     * 判断给定生物群系是否属于污秽生物群系。
     */
    public static boolean isDefiledBiome(Holder<Biome> biome) {
        return biome.is(ModBiomes.DESERT_DEFILED)
                || biome.is(ModBiomes.PLAINS_DEFILED)
                || biome.is(ModBiomes.FOREST_TENEBRA)
                || biome.is(ModBiomes.FOREST_VILESPINE)
                || biome.is(ModBiomes.HILLS_DEFILED)
                || biome.is(ModBiomes.SWAMP_DEFILED)
                || biome.is(ModBiomes.ICE_PLAINS_DEFILED);
    }

    /**
     * 检查给定方块状态是否可以被污化。
     */
    public static boolean isCorruptable(BlockState input) {
        return CorruptionRecipes.getCorrupted(input) != null;
    }

    /**
     * 尝试污化指定位置的方块。
     *
     * @return 是否成功污化
     */
    public static boolean corrupt(Level level, BlockPos pos, BlockState state) {
        BlockState corrupted = CorruptionRecipes.getCorrupted(state);
        if (corrupted == null)
            return false;
        level.setBlock(pos, corrupted, 3);
        return true;
    }
}
