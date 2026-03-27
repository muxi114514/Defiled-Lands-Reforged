package lykrast.defiledlands.common;

import lykrast.defiledlands.common.event.EffectEventHandler;
import lykrast.defiledlands.common.registry.ModBlockEntities;
import lykrast.defiledlands.common.registry.ModBlocks;
import lykrast.defiledlands.common.registry.ModCreativeTabs;
import lykrast.defiledlands.common.registry.ModEffects;
import lykrast.defiledlands.common.registry.ModEntityTypes;
import lykrast.defiledlands.common.registry.ModFeatures;
import lykrast.defiledlands.common.registry.ModItems;

public class DefiledLands {
    public static final String MOD_ID = "defiledlands";

    public static void init() {
        ModBlocks.register();
        ModBlockEntities.register();
        ModItems.register();
        ModEntityTypes.register();
        ModEntityTypes.registerAttributes();
        ModFeatures.register();
        ModEffects.register();

        ModCreativeTabs.register();

        EffectEventHandler.register();

    }
}
