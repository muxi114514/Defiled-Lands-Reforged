package lykrast.defiledlands.forge.config;

import lykrast.defiledlands.common.util.CorruptionHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 污秽之地模组配置（Forge 端）
 * 配置值通过 bake() 注入到 common 模块的 CorruptionHelper。
 */
public class DefiledLandsConfig {

    public static final ForgeConfigSpec COMMON_SPEC;
    private static final Common COMMON;

    static {
        Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON = specPair.getLeft();
        COMMON_SPEC = specPair.getRight();
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
    }

    public static void bake() {
        CorruptionHelper.spreadEnabled = COMMON.spreadEnabled.get();
        CorruptionHelper.confinedSpread = COMMON.confinedSpread.get();
        CorruptionHelper.spreadAttempts = COMMON.spreadAttempts.get();
        CorruptionHelper.spreadRange = COMMON.spreadRange.get();
        CorruptionHelper.goldWyrmPerLuck = COMMON.goldWyrmPerLuck.get();
        CorruptionHelper.wyrmMaxLevelCap = COMMON.wyrmMaxLevelCap.get();
        CorruptionHelper.wyrmEnchantList = new java.util.ArrayList<>(COMMON.wyrmEnchantList.get());
        CorruptionHelper.wyrmEnchantListIsWhitelist = COMMON.wyrmEnchantListIsWhitelist.get();
        CorruptionHelper.biomeRegionWeight = COMMON.biomeRegionWeight.get();
        CorruptionHelper.dungeonEnabled = COMMON.dungeonEnabled.get();
    }

    private static class Common {
        final ForgeConfigSpec.BooleanValue spreadEnabled;
        final ForgeConfigSpec.BooleanValue confinedSpread;
        final ForgeConfigSpec.IntValue spreadAttempts;
        final ForgeConfigSpec.IntValue spreadRange;
        final ForgeConfigSpec.DoubleValue goldWyrmPerLuck;
        final ForgeConfigSpec.IntValue wyrmMaxLevelCap;
        final ForgeConfigSpec.ConfigValue<java.util.List<? extends String>> wyrmEnchantList;
        final ForgeConfigSpec.BooleanValue wyrmEnchantListIsWhitelist;
        final ForgeConfigSpec.IntValue biomeRegionWeight;
        final ForgeConfigSpec.BooleanValue dungeonEnabled;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("General configuration").push("general");

            spreadEnabled = builder
                    .comment(
                            "If true, defiled blocks will automatically spread corruption to adjacent blocks.",
                            "Default: false (spreading is disabled by default).")
                    .define("spreadEnabled", false);

            confinedSpread = builder
                    .comment(
                            "If true, defiled blocks only spread corruption when inside defiled biomes.",
                            "Has no effect when spreadEnabled is false.",
                            "Default: true.")
                    .define("confinedSpread", true);

            spreadAttempts = builder
                    .comment(
                            "Number of spread attempts per random tick.",
                            "Higher values make corruption spread faster.",
                            "Default: 4.")
                    .defineInRange("spreadAttempts", 4, 1, 20);

            spreadRange = builder
                    .comment(
                            "Spread radius in blocks per attempt.",
                            "Higher values make corruption spread over a wider area.",
                            "Default: 2.")
                    .defineInRange("spreadRange", 2, 1, 8);

            builder.pop();

            builder.comment("World generation configuration").push("worldgen");

            biomeRegionWeight = builder
                    .comment(
                            "TerraBlender region weight for defiled biomes.",
                            "Higher values make defiled biomes more common.",
                            "Set to 0 to completely disable defiled biome generation.",
                            "Requires game restart to take effect.",
                            "Default: 5.")
                    .defineInRange("biomeRegionWeight", 5, 0, 50);

            dungeonEnabled = builder
                    .comment(
                            "If true, defiled dungeons will generate underground in defiled biomes.",
                            "Default: true.")
                    .define("dungeonEnabled", true);

            builder.pop();

            builder.comment("Book Wyrm configuration").push("bookwyrm");

            goldWyrmPerLuck = builder
                    .comment(
                            "Each point of Luck attribute multiplies the default chance to breed a golden Book Wyrm by this factor.",
                            "Formula: actual_chance = base_chance * (1 + luck * goldWyrmPerLuck)",
                            "Example: with 5 Luck and factor 0.2, chances are multiplied by 2 (5 * 0.2 = 1.0, so +100%).",
                            "Base chances: purple+purple=1%, gold+purple=4%, gold+gold=10%.",
                            "Set to 0.0 to disable luck influence. Default: 0.2.")
                    .defineInRange("goldWyrmPerLuck", 0.2, 0.0, 10.0);

            wyrmMaxLevelCap = builder
                    .comment(
                            "Maximum value that a Book Wyrm's MaxLevel (digest threshold) can reach through breeding.",
                            "Higher values allow breeding wyrms with larger digest capacity.",
                            "Default: 30.")
                    .defineInRange("wyrmMaxLevelCap", 30, 1, 10000);

            wyrmEnchantListIsWhitelist = builder
                    .comment(
                            "If false, wyrmEnchantList is a BLACKLIST: listed enchantments will never be produced by Book Wyrms.",
                            "If true, wyrmEnchantList is a WHITELIST: only listed enchantments can be produced.",
                            "Default: false (blacklist mode).")
                    .define("wyrmEnchantListIsWhitelist", false);

            wyrmEnchantList = builder
                    .comment(
                            "List of enchantment IDs to filter when a Book Wyrm produces enchanted books.",
                            "Format: \"namespace:enchantment_id\", e.g. \"minecraft:mending\", \"minecraft:soul_speed\".",
                            "Behavior depends on wyrmEnchantListIsWhitelist.",
                            "Default: empty (no filtering).")
                    .defineList("wyrmEnchantList", java.util.Collections.emptyList(),
                            e -> e instanceof String s && s.contains(":"));

            builder.pop();
        }
    }
}
