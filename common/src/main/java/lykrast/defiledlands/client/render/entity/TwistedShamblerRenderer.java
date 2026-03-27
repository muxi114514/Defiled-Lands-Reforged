package lykrast.defiledlands.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import lykrast.defiledlands.client.render.entity.layers.TwistedShamblerEyesLayer;
import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.entity.monster.TwistedShamblerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TwistedShamblerRenderer extends ShamblerRenderer<TwistedShamblerEntity> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(DefiledLands.MOD_ID, "textures/entity/shambler_twisted.png");

    public TwistedShamblerRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new TwistedShamblerEyesLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(TwistedShamblerEntity entity) {
        return TEXTURES;
    }

    @Override
    protected void setupRotations(TwistedShamblerEntity entityLiving, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {

        float spinYaw = rotationYaw + (float) (Math.cos((double) entityLiving.tickCount * 3.25D) * Math.PI * 0.75D);
        super.setupRotations(entityLiving, matrixStack, ageInTicks, spinYaw, partialTicks);
    }
}
