package lykrast.defiledlands;

import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.util.CorruptionRecipes;
import lykrast.defiledlands.forge.config.DefiledLandsConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DefiledLandsMod.MOD_ID)
public class DefiledLandsMod {
    public static final String MOD_ID = "defiledlands";

    public DefiledLandsMod() {
        System.out.println("Defiled Lands Mod (Forge) initializing...");

        // 注册配置
        DefiledLandsConfig.register();

        EventBuses.registerModEventBus(MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        DefiledLands.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onConfigLoad);

        net.minecraftforge.fml.DistExecutor.unsafeRunWhenOn(net.minecraftforge.api.distmarker.Dist.CLIENT,
                () -> lykrast.defiledlands.common.registry.ModEntityRenderers::register);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // 初始化污化配方（必须在此处调用，方块注册完成后才能安全访问 Registry 对象）
            CorruptionRecipes.init();

            // 权重为 0 时不注册 Region，完全禁用污秽群系生成
            int weight = lykrast.defiledlands.common.util.CorruptionHelper.biomeRegionWeight;
            if (weight > 0) {
                terrablender.api.Regions
                        .register(new lykrast.defiledlands.common.world.biome.DefiledLandsRegion(weight));
            }
            terrablender.api.SurfaceRuleManager.addSurfaceRules(
                    terrablender.api.SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID,
                    lykrast.defiledlands.common.world.biome.DefiledLandsSurfaceRuleData.makeRules());
        });
    }

    private void onConfigLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == DefiledLandsConfig.COMMON_SPEC) {
            DefiledLandsConfig.bake();
        }
    }
}
