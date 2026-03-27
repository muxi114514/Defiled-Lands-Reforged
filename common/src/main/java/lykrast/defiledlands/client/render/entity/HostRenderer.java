package lykrast.defiledlands.client.render.entity;

import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.entity.monster.HostEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class HostRenderer extends HumanoidMobRenderer<HostEntity, HumanoidModel<HostEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DefiledLands.MOD_ID, "textures/entity/host.png");

    public HostRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE)), 0.5F);

        this.addLayer(new HumanoidArmorLayer<>(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)),
                context.getModelManager()
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(HostEntity entity) {
        return TEXTURES;
    }
}
