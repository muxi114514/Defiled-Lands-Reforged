package lykrast.defiledlands.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import lykrast.defiledlands.client.model.MournerModel;
import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.entity.boss.MournerEntity;
import lykrast.defiledlands.common.registry.ModModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MournerRenderer extends HumanoidMobRenderer<MournerEntity, MournerModel<MournerEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(DefiledLands.MOD_ID, "textures/entity/the_mourner.png");

    public MournerRenderer(EntityRendererProvider.Context context) {
        super(context, new MournerModel<>(context.bakeLayer(ModModelLayers.MOURNER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MournerEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(MournerEntity entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
    }
}
