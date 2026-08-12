package com.meteor.extrabotany.client.model.armor;

import com.meteor.extrabotany.client.model.ModelArmor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;

public class ModelShootingGuardianHelmet extends ModelArmor {

    private final ModelPart helmet;
    private final ModelPart root;

    public ModelShootingGuardianHelmet(EquipmentSlot slot) {
        this(createBodyLayer().bakeRoot(), slot);
    }

    public ModelShootingGuardianHelmet(ModelPart root, EquipmentSlot slot) {
        super(root, slot);
        this.root = root;
        this.helmet = root.getChild("helmet");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition part = mesh.getRoot();

        PartDefinition helmet = part.addOrReplaceChild("helmet", CubeListBuilder.create(), PartPose.offset(0F, 24F, 0F));
            PartDefinition bone1688 = helmet.addOrReplaceChild("bone1688", CubeListBuilder.create()
                    .texOffs(0, 28).addBox(-1.5F, -4.5F, -0.01F, 6.0F, 6.0F, 0.0F), PartPose.offsetAndRotation(0F, -6F, -4.5F, 0F, 0F, -0.7854F));
            PartDefinition bone1477 = helmet.addOrReplaceChild("bone1477", CubeListBuilder.create()
                    .texOffs(30, 18).addBox(-10.8F, -4.0F, -4.5F, 10.0F, 2.0F, 0.0F), PartPose.offset(5.8F, -5F, 0F));
            PartDefinition bone966 = helmet.addOrReplaceChild("bone966", CubeListBuilder.create()
                    .texOffs(30, 0).addBox(-10.8F, -4.0F, 3.5F, 10.0F, 8.0F, 1.0F), PartPose.offset(5.8F, -5F, 0F));
            PartDefinition bone1555 = helmet.addOrReplaceChild("bone1555", CubeListBuilder.create()
                    .texOffs(0, 0).addBox(-2.7F, -0.5F, -4.5F, 5.0F, 1.0F, 9.0F), PartPose.offsetAndRotation(2.5F, -8.5F, 0.02F, 0F, 0F, 0.2618F));
            PartDefinition bone834 = helmet.addOrReplaceChild("bone834", CubeListBuilder.create()
                    .texOffs(0, 10).addBox(-2.3F, -0.5F, -4.5F, 5.0F, 1.0F, 9.0F), PartPose.offsetAndRotation(-2.5F, -8.5F, 0.02F, 0F, 0F, -0.2618F));
            PartDefinition bone1221 = helmet.addOrReplaceChild("bone1221", CubeListBuilder.create()
                    .texOffs(0, 10).addBox(-3.5F, -4.0F, -4.48F, 3.0F, 8.0F, 0.0F), PartPose.offsetAndRotation(5.8F, -5F, 0F, 0F, 0F, -0.1745F));
            PartDefinition bone1021 = helmet.addOrReplaceChild("bone1021", CubeListBuilder.create()
                    .texOffs(6, 0).addBox(-1.5F, -4.0F, 4.49F, 1.0F, 8.0F, 0.0F), PartPose.offsetAndRotation(5.8F, -5F, 0F, 0F, 0F, -0.1745F));
            PartDefinition bone721 = helmet.addOrReplaceChild("bone721", CubeListBuilder.create()
                    .texOffs(19, 1).addBox(-1.5F, -4.0F, -4.49F, 1.0F, 8.0F, 9.0F), PartPose.offsetAndRotation(5.8F, -5F, 0F, 0F, 0F, -0.1745F));
            PartDefinition bone1821 = helmet.addOrReplaceChild("bone1821", CubeListBuilder.create()
                    .texOffs(32, 24).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(5.1F, -5F, -1.5F, -0.6981F, 0F, -0.1745F));
            PartDefinition bone1733 = helmet.addOrReplaceChild("bone1733", CubeListBuilder.create()
                    .texOffs(0, 34).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(-5.1F, -5F, -1.5F, -0.6981F, 0F, 0.1745F));
            PartDefinition bone2034 = helmet.addOrReplaceChild("bone2034", CubeListBuilder.create()
                    .texOffs(12, 28).addBox(-0.1F, -1.1F, -2.19F, 1.0F, 5.0F, 1.0F), PartPose.offsetAndRotation(5.8F, -5F, 0F, 0F, 0.7854F, -0.1745F));
            PartDefinition bone1923 = helmet.addOrReplaceChild("bone1923", CubeListBuilder.create()
                    .texOffs(30, 20).addBox(-0.9F, -1.1F, -2.19F, 1.0F, 5.0F, 1.0F), PartPose.offsetAndRotation(-5.8F, -5F, 0F, 0F, -0.7854F, 0.1745F));
            PartDefinition bone1111 = helmet.addOrReplaceChild("bone1111", CubeListBuilder.create()
                    .texOffs(19, 19).addBox(0.5F, -4.0F, -4.49F, 1.0F, 8.0F, 9.0F), PartPose.offsetAndRotation(-5.8F, -5F, 0F, 0F, 0F, 0.1745F));
            PartDefinition bone1322 = helmet.addOrReplaceChild("bone1322", CubeListBuilder.create()
                    .texOffs(0, 0).addBox(0.5F, -4.0F, -4.48F, 3.0F, 8.0F, 0.0F), PartPose.offsetAndRotation(-5.8F, -5F, 0F, 0F, 0F, 0.1745F));
            PartDefinition bone611 = helmet.addOrReplaceChild("bone611", CubeListBuilder.create()
                    .texOffs(6, 10).addBox(0.5F, -4.0F, 4.49F, 1.0F, 8.0F, 0.0F), PartPose.offsetAndRotation(-5.8F, -5F, 0F, 0F, 0F, 0.1745F));
            PartDefinition bone444 = helmet.addOrReplaceChild("bone444", CubeListBuilder.create()
                    .texOffs(22, 17).addBox(-5.0F, -29.0F, -4.5F, 0.0F, 5.0F, 3.0F), PartPose.offset(0F, 23F, 0F));
            PartDefinition bone544 = helmet.addOrReplaceChild("bone544", CubeListBuilder.create()
                    .texOffs(19, 0).addBox(5.0F, -29.0F, -4.5F, 0.0F, 5.0F, 3.0F), PartPose.offset(0F, 23F, 0F));
            PartDefinition bone344 = helmet.addOrReplaceChild("bone344", CubeListBuilder.create()
                    .texOffs(0, 20).addBox(-5.0F, -31.0F, -4.3F, 10.0F, 7.0F, 1.0F), PartPose.offset(0F, 23F, 0F));
            PartDefinition bone244 = helmet.addOrReplaceChild("bone244", CubeListBuilder.create()
                    .texOffs(19, 0).addBox(0.0F, -2.5F, 0.0F, 3.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(0F, -1F, -5F, 0F, -0.2618F, 0F));
            PartDefinition bone233 = helmet.addOrReplaceChild("bone233", CubeListBuilder.create()
                    .texOffs(22, 25).addBox(-3.0F, -2.5F, 0.0F, 3.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(0F, -1F, -5F, 0F, 0.2618F, 0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack ms, VertexConsumer buffer, int light, int overlay, float r, float g, float b, float a) {
        hat.visible = false;
        helmet.visible = slot == EquipmentSlot.HEAD;
        super.renderToBuffer(ms, buffer, light, overlay, r, g, b, a);
    }

}
