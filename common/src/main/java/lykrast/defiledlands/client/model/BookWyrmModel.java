package lykrast.defiledlands.client.model;

import lykrast.defiledlands.common.entity.passive.BookWyrmEntity;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class BookWyrmModel<T extends BookWyrmEntity> extends QuadrupedModel<T> {

    public BookWyrmModel(ModelPart root) {

        super(root, true, 4.0F, 2.0F, 2.0F, 2.0F, 24);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3.0F, -3.0F, -6.0F, 6.0F, 6.0F, 6.0F)
                        .texOffs(0, 12).addBox(-2.0F, -1.0F, -12.0F, 4.0F, 3.0F, 6.0F),
                PartPose.offset(0.0F, 17.0F, -6.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(32, 10).addBox(-5.0F, -10.0F, -2.0F, 10.0F, 16.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 20.0F, 3.0F, ((float) Math.PI / 2F), 0.0F, 0.0F));

        PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create()
                        .texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F),
                PartPose.offset(-7.0F, 18.0F, 7.0F));

        PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create()
                        .texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F).mirror(),
                PartPose.offset(7.0F, 18.0F, 7.0F));

        PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
                        .texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F),
                PartPose.offset(-7.0F, 18.0F, -5.0F));

        PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
                        .texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F).mirror(),
                PartPose.offset(7.0F, 18.0F, -5.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (entity.isBaby()) {
            this.head.setPos(0.0F, 22.0F, -3.0F);
        } else {
            this.head.setPos(0.0F, 17.0F, -6.0F);
        }
    }
}
