package lykrast.defiledlands.client.render.entity;

import lykrast.defiledlands.client.render.entity.layers.ScuttlerEyesLayer;
import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.entity.monster.ScuttlerEntity;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ScuttlerRenderer extends MobRenderer<ScuttlerEntity, SpiderModel<ScuttlerEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DefiledLands.MOD_ID, "textures/entity/scuttler.png");

    public ScuttlerRenderer(EntityRendererProvider.Context context) {
        super(context, new SpiderModel<>(context.bakeLayer(ModelLayers.SPIDER)), 1.0F);
        this.addLayer(new ScuttlerEyesLayer<>(this));
    }

    @Override
    protected float getFlipDegrees(ScuttlerEntity spider) {
        // Flipping 180 on death
        return 180.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(ScuttlerEntity entity) {
        return TEXTURES;
    }
}
