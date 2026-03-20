package lykrast.defiledlands.client.render.entity;

import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.entity.monster.ShamblerEntity;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ShamblerRenderer<T extends ShamblerEntity> extends MobRenderer<T, EndermanModel<T>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DefiledLands.MOD_ID, "textures/entity/shambler.png");

    public ShamblerRenderer(EntityRendererProvider.Context context) {
        super(context, new EndermanModel<>(context.bakeLayer(ModelLayers.ENDERMAN)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURES;
    }
}
