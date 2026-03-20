package lykrast.defiledlands.client.render.entity.layers;

import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.entity.monster.TwistedShamblerEntity;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class TwistedShamblerEyesLayer<T extends TwistedShamblerEntity> extends EyesLayer<T, EndermanModel<T>> {
    private static final RenderType SHAMBLER_TWISTED_EYES = RenderType.eyes(new ResourceLocation(DefiledLands.MOD_ID, "textures/entity/shambler_twisted_eyes.png"));

    public TwistedShamblerEyesLayer(RenderLayerParent<T, EndermanModel<T>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public RenderType renderType() {
        return SHAMBLER_TWISTED_EYES;
    }
}
