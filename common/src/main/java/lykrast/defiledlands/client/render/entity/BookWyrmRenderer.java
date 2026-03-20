package lykrast.defiledlands.client.render.entity;

import lykrast.defiledlands.client.model.BookWyrmModel;
import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.entity.passive.BookWyrmEntity;
import lykrast.defiledlands.common.registry.ModModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BookWyrmRenderer extends MobRenderer<BookWyrmEntity, BookWyrmModel<BookWyrmEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DefiledLands.MOD_ID, "textures/entity/book_wyrm.png");
    private static final ResourceLocation TEXTURES_GOLDEN = new ResourceLocation(DefiledLands.MOD_ID, "textures/entity/book_wyrm_golden.png");

    public BookWyrmRenderer(EntityRendererProvider.Context context) {
        super(context, new BookWyrmModel<>(context.bakeLayer(ModModelLayers.BOOK_WYRM)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(BookWyrmEntity entity) {
        return entity.isGolden() ? TEXTURES_GOLDEN : TEXTURES;
    }
}
