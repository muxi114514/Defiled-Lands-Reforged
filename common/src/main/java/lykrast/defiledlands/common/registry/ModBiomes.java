package lykrast.defiledlands.common.registry;

import lykrast.defiledlands.common.DefiledLands;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ModBiomes {
    public static final ResourceKey<Biome> DESERT_DEFILED = register("desert_defiled");
    public static final ResourceKey<Biome> PLAINS_DEFILED = register("plains_defiled");
    public static final ResourceKey<Biome> FOREST_TENEBRA = register("forest_tenebra");
    public static final ResourceKey<Biome> FOREST_VILESPINE = register("forest_vilespine");
    public static final ResourceKey<Biome> HILLS_DEFILED = register("hills_defiled");
    public static final ResourceKey<Biome> SWAMP_DEFILED = register("swamp_defiled");
    public static final ResourceKey<Biome> ICE_PLAINS_DEFILED = register("ice_plains_defiled");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(DefiledLands.MOD_ID, name));
    }
}
