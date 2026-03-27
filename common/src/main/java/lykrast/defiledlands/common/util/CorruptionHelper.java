package lykrast.defiledlands.common.util;

import lykrast.defiledlands.common.registry.ModBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

public class CorruptionHelper {

    public static boolean spreadEnabled = false;

    public static boolean confinedSpread = true;

    public static double goldWyrmPerLuck = 0.2;

    public static int wyrmMaxLevelCap = 30;

    public static java.util.List<String> wyrmEnchantList = new java.util.ArrayList<>();

    public static boolean wyrmEnchantListIsWhitelist = false;

    public static double wyrmConversionRate = 1.0;

    public static int biomeRegionWeight = 5;

    public static boolean dungeonEnabled = true;

    public static int spreadAttempts = 4;

    public static int spreadRange = 2;

    public static boolean isWyrmEnchantAllowed(net.minecraft.world.item.enchantment.Enchantment enchantment) {
        String key = net.minecraft.core.registries.BuiltInRegistries.ENCHANTMENT.getKey(enchantment).toString();
        boolean inList = wyrmEnchantList.contains(key);
        return wyrmEnchantListIsWhitelist ? inList : !inList;
    }

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

    public static boolean canSpread(Level level, BlockPos pos) {
        if (!confinedSpread) {
            return true;
        }
        Holder<Biome> biome = level.getBiome(pos);
        return isDefiledBiome(biome);
    }

    public static boolean isDefiledBiome(Holder<Biome> biome) {
        return biome.is(ModBiomes.DESERT_DEFILED)
                || biome.is(ModBiomes.PLAINS_DEFILED)
                || biome.is(ModBiomes.FOREST_TENEBRA)
                || biome.is(ModBiomes.FOREST_VILESPINE)
                || biome.is(ModBiomes.HILLS_DEFILED)
                || biome.is(ModBiomes.SWAMP_DEFILED)
                || biome.is(ModBiomes.ICE_PLAINS_DEFILED);
    }

    public static boolean isCorruptable(BlockState input) {
        return CorruptionRecipes.getCorrupted(input) != null;
    }

    public static boolean corrupt(Level level, BlockPos pos, BlockState state) {
        BlockState corrupted = CorruptionRecipes.getCorrupted(state);
        if (corrupted == null)
            return false;
        level.setBlock(pos, corrupted, 3);
        return true;
    }
}
