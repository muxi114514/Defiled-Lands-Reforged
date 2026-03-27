package lykrast.defiledlands.common.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.world.feature.ConjuringAltarFeature;
import lykrast.defiledlands.common.world.feature.CorruptionPostFeature;
import lykrast.defiledlands.common.world.feature.DefiledDungeonFeature;
import lykrast.defiledlands.common.world.feature.TenebraTreeFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(DefiledLands.MOD_ID, Registries.FEATURE);

    public static final RegistrySupplier<Feature<NoneFeatureConfiguration>> DEFILED_DUNGEON = FEATURES.register("defiled_dungeon", () -> new DefiledDungeonFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistrySupplier<Feature<NoneFeatureConfiguration>> CONJURING_ALTAR = FEATURES.register("conjuring_altar", () -> new ConjuringAltarFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistrySupplier<Feature<NoneFeatureConfiguration>> CORRUPTION_POST = FEATURES.register("corruption_post", () -> new CorruptionPostFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistrySupplier<Feature<NoneFeatureConfiguration>> TENEBRA_TREE = FEATURES.register("tenebra_tree", () -> new TenebraTreeFeature(NoneFeatureConfiguration.CODEC));

    public static void register() {
        FEATURES.register();
    }
}
