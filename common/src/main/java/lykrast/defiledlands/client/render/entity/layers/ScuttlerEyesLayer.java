package lykrast.defiledlands.client.render.entity.layers;

import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.entity.monster.ScuttlerEntity;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class ScuttlerEyesLayer<T extends ScuttlerEntity> extends EyesLayer<T, SpiderModel<T>> {
    private static final RenderType SCUTTLER_EYES = RenderType.eyes(new ResourceLocation(DefiledLands.MOD_ID, "textures/entity/scuttler_eyes.png"));

    public ScuttlerEyesLayer(RenderLayerParent<T, SpiderModel<T>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public RenderType renderType() {
        return SCUTTLER_EYES;
    }
}
