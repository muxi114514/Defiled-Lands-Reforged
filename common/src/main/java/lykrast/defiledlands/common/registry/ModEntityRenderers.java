package lykrast.defiledlands.common.registry;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import lykrast.defiledlands.client.render.entity.DefiledSlimeRenderer;
import lykrast.defiledlands.client.render.entity.HostRenderer;
import lykrast.defiledlands.client.render.entity.ScuttlerRenderer;
import lykrast.defiledlands.client.render.entity.ShamblerRenderer;
import lykrast.defiledlands.client.render.entity.TwistedShamblerRenderer;
import lykrast.defiledlands.common.registry.ModEntityTypes;

public class ModEntityRenderers {
    public static void register() {
        EntityRendererRegistry.register(ModEntityTypes.SHAMBLER, ShamblerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.SHAMBLER_TWISTED, TwistedShamblerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.SCUTTLER, ScuttlerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.HOST, HostRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.SLIME_DEFILED, DefiledSlimeRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.DESTROYER, lykrast.defiledlands.client.render.entity.DestroyerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.MOURNER, lykrast.defiledlands.client.render.entity.MournerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.BOOK_WYRM, lykrast.defiledlands.client.render.entity.BookWyrmRenderer::new);

        dev.architectury.registry.client.level.entity.EntityModelLayerRegistry.register(ModModelLayers.DESTROYER, lykrast.defiledlands.client.model.DestroyerModel::createBodyLayer);
        dev.architectury.registry.client.level.entity.EntityModelLayerRegistry.register(ModModelLayers.MOURNER, lykrast.defiledlands.client.model.MournerModel::createBodyLayer);
        dev.architectury.registry.client.level.entity.EntityModelLayerRegistry.register(ModModelLayers.BOOK_WYRM, lykrast.defiledlands.client.model.BookWyrmModel::createBodyLayer);
    }
}
