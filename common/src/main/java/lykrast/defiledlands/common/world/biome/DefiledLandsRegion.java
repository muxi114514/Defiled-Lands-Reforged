package lykrast.defiledlands.common.world.biome;

import com.mojang.datafixers.util.Pair;
import lykrast.defiledlands.common.DefiledLands;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class DefiledLandsRegion extends Region {
    public static final ResourceLocation LOCATION = new ResourceLocation(DefiledLands.MOD_ID, "overworld_defiled");

    public DefiledLandsRegion(int weight) {
        super(LOCATION, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {

        this.addModifiedVanillaOverworldBiomes(mapper, modifiedBiome -> {
            modifiedBiome.replaceBiome(net.minecraft.world.level.biome.Biomes.DESERT, lykrast.defiledlands.common.registry.ModBiomes.DESERT_DEFILED);
            modifiedBiome.replaceBiome(net.minecraft.world.level.biome.Biomes.PLAINS, lykrast.defiledlands.common.registry.ModBiomes.PLAINS_DEFILED);
            modifiedBiome.replaceBiome(net.minecraft.world.level.biome.Biomes.FOREST, lykrast.defiledlands.common.registry.ModBiomes.FOREST_TENEBRA);
            modifiedBiome.replaceBiome(net.minecraft.world.level.biome.Biomes.BIRCH_FOREST, lykrast.defiledlands.common.registry.ModBiomes.FOREST_VILESPINE);
            modifiedBiome.replaceBiome(net.minecraft.world.level.biome.Biomes.WINDSWEPT_HILLS, lykrast.defiledlands.common.registry.ModBiomes.HILLS_DEFILED);
            modifiedBiome.replaceBiome(net.minecraft.world.level.biome.Biomes.SWAMP, lykrast.defiledlands.common.registry.ModBiomes.SWAMP_DEFILED);
            modifiedBiome.replaceBiome(net.minecraft.world.level.biome.Biomes.SNOWY_PLAINS, lykrast.defiledlands.common.registry.ModBiomes.ICE_PLAINS_DEFILED);
        });
    }
}
