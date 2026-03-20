package lykrast.defiledlands.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import lykrast.defiledlands.client.model.DestroyerModel;
import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.entity.boss.DestroyerEntity;
import lykrast.defiledlands.common.registry.ModModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DestroyerRenderer extends HumanoidMobRenderer<DestroyerEntity, DestroyerModel<DestroyerEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(DefiledLands.MOD_ID, "textures/entity/the_destroyer.png");

    public DestroyerRenderer(EntityRendererProvider.Context context) {
        super(context, new DestroyerModel<>(context.bakeLayer(ModModelLayers.DESTROYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(DestroyerEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(DestroyerEntity entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
    }
}
