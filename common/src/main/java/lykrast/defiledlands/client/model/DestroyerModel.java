package lykrast.defiledlands.client.model;

import lykrast.defiledlands.common.entity.boss.DestroyerEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class DestroyerModel<T extends DestroyerEntity> extends HumanoidModel<T> {

    public DestroyerModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        
        if (entity.isLeaping()) {
            this.rightLeg.xRot += ((float)Math.PI / 5F);
            this.leftLeg.xRot += ((float)Math.PI / 5F);
            this.rightArm.xRot = 3.7699115F; // roughly PI + something
            this.leftArm.xRot = 3.7699115F;
        }
    }
}
