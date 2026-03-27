package lykrast.defiledlands.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.entity.monster.DefiledSlimeEntity;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DefiledSlimeRenderer extends MobRenderer<DefiledSlimeEntity, SlimeModel<DefiledSlimeEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DefiledLands.MOD_ID, "textures/entity/slime_defiled.png");

    public DefiledSlimeRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel<>(context.bakeLayer(ModelLayers.SLIME)), 0.25F);
        this.addLayer(new SlimeOuterLayer<>(this, context.getModelSet()));
    }

    @Override
    public void render(DefiledSlimeEntity slime, float entityYaw, float partialTicks, PoseStack matrixStack, net.minecraft.client.renderer.MultiBufferSource buffer, int packedLight) {
        this.shadowRadius = 0.25F * (float) slime.getSize();
        super.render(slime, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    protected void scale(DefiledSlimeEntity slime, PoseStack matrixStack, float partialTickTime) {
        float scale = 0.999F;
        matrixStack.scale(0.999F, 0.999F, 0.999F);
        matrixStack.translate(0.0F, 0.001F, 0.0F);

        float squish = Mth.lerp(partialTickTime, slime.oSquish, slime.squish);
        float size = (float) slime.getSize();
        float f1 = squish / (size * 0.5F + 1.0F);
        float f2 = 1.0F / (f1 + 1.0F);

        matrixStack.scale(f2 * size, 1.0F / f2 * size, f2 * size);
    }

    @Override
    public ResourceLocation getTextureLocation(DefiledSlimeEntity entity) {
        return TEXTURES;
    }
}
