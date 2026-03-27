package lykrast.defiledlands.common.world.biome;

import lykrast.defiledlands.common.registry.ModBiomes;
import lykrast.defiledlands.common.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class DefiledLandsSurfaceRuleData {
    private static final SurfaceRules.RuleSource DIRT_DEFILED = makeStateRule(ModBlocks.DIRT_DEFILED.get());
    private static final SurfaceRules.RuleSource GRASS_DEFILED = makeStateRule(ModBlocks.GRASS_DEFILED.get());
    private static final SurfaceRules.RuleSource STONE_DEFILED = makeStateRule(ModBlocks.STONE_DEFILED.get());
    private static final SurfaceRules.RuleSource SAND_DEFILED = makeStateRule(ModBlocks.SAND_DEFILED.get());
    private static final SurfaceRules.RuleSource SANDSTONE_DEFILED = makeStateRule(ModBlocks.SANDSTONE_CORRUPTED.get());
    private static final SurfaceRules.RuleSource GRAVEL_DEFILED = makeStateRule(ModBlocks.GRAVEL_DEFILED.get());

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource defiledSurface = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_DEFILED),
                                DIRT_DEFILED
                        )
                ),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT_DEFILED),
                SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(0), 0), STONE_DEFILED)
        );

        SurfaceRules.RuleSource defiledDesertSurface = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SAND_DEFILED),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SAND_DEFILED),
                SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(0), 0), SANDSTONE_DEFILED)
        );

        SurfaceRules.RuleSource defiledHillsSurface = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(90), 0), STONE_DEFILED),
                                SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_DEFILED),
                                DIRT_DEFILED
                        )
                ),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT_DEFILED),
                SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(0), 0), STONE_DEFILED)
        );

        SurfaceRules.RuleSource defiledSwampSurface = defiledSurface;

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.DESERT_DEFILED), defiledDesertSurface),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.HILLS_DEFILED), defiledHillsSurface),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.SWAMP_DEFILED), defiledSwampSurface),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.FOREST_TENEBRA), defiledSurface),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.FOREST_VILESPINE), defiledSurface),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ICE_PLAINS_DEFILED), defiledSurface),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.PLAINS_DEFILED), defiledSurface)
        );
    }
}
