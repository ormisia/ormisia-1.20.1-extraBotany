package com.meteor.extrabotany.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class ModelKeyOfTruth extends HierarchicalModel<Entity> {

    private final ModelPart bone;

    public ModelKeyOfTruth() {
        this(createBodyLayer().bakeRoot());
    }

    public ModelKeyOfTruth(ModelPart root) {
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();

        PartDefinition bone = part.addOrReplaceChild("bone", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -1.0F, 1.0F, 4.0F, 6.0F, 2.0F)
                .texOffs(28, 27).addBox(-5.0F, 3.0F, 1.0F, 6.0F, 1.0F, 3.0F)
                .texOffs(16, 13).addBox(-4.0F, 3.0F, 4.0F, 4.0F, 1.0F, 9.0F)
                .texOffs(28, 23).addBox(-5.0F, -2.0F, 1.0F, 6.0F, 1.0F, 3.0F)
                .texOffs(0, 0).addBox(-4.0F, -2.0F, 4.0F, 4.0F, 1.0F, 12.0F)
                .texOffs(33, 9).addBox(-3.5F, -0.5F, 3.0F, 3.0F, 3.0F, 3.0F)
                .texOffs(20, 0).addBox(-3.53F, -1.14F, 2.96F, 3.0F, 4.0F, 1.0F)
                .texOffs(0, 10).addBox(-4.0F, -1.0F, 8.0F, 4.0F, 1.0F, 1.0F)
                .texOffs(0, 24).addBox(-1.0F, -1.0F, 8.0F, 1.0F, 4.0F, 1.0F)
                .texOffs(0, 8).addBox(-4.0F, 2.0F, 8.0F, 4.0F, 1.0F, 1.0F)
                .texOffs(21, 13).addBox(-4.0F, -1.0F, 8.0F, 1.0F, 4.0F, 1.0F)
                .texOffs(0, 13).addBox(-3.5F, -2.5F, 1.0F, 3.0F, 1.0F, 10.0F)
                .texOffs(10, 24).addBox(-4.0F, 0.0F, -6.0F, 4.0F, 1.0F, 4.0F)
                .texOffs(0, 24).addBox(0.0F, 0.0F, -7.3F, 1.0F, 2.0F, 8.0F)
                .texOffs(18, 23).addBox(-5.0F, 0.0F, -7.3F, 1.0F, 2.0F, 8.0F), PartPose.offset(0.0F, 19.0F, -1.0F));

        PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create()
                .texOffs(32, 32).addBox(-4.04F, -2.6285F, -3.0258F, 4.0F, 6.0F, 4.0F)
                .texOffs(15, 33).addBox(-3.5F, -5.7332F, -2.0606F, 3.0F, 4.0F, 3.0F), PartPose.offsetAndRotation(0.0F, 1.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition bone3 = bone.addOrReplaceChild("bone3", CubeListBuilder.create()
                .texOffs(20, 0).addBox(-6.0F, -2.4752F, -5.1498F, 4.0F, 1.0F, 8.0F), PartPose.offsetAndRotation(2.0F, 5.0F, -3.3F, -0.6109F, 0.0F, 0.0F));

        PartDefinition bone4 = bone.addOrReplaceChild("bone4", CubeListBuilder.create()
                .texOffs(16, 16).addBox(0.1F, -9.1213F, -1.4645F, 1.0F, 3.0F, 3.0F), PartPose.offsetAndRotation(0.0F, 6.5F, -4.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition bone5 = bone.addOrReplaceChild("bone5", CubeListBuilder.create()
                .texOffs(0, 13).addBox(-0.1F, -9.1213F, -1.4645F, 1.0F, 3.0F, 3.0F), PartPose.offsetAndRotation(-5.0F, 6.5F, -4.0F, -0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.bone;
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
