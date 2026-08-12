package com.meteor.extrabotany.client.model;

import com.meteor.extrabotany.client.handler.ClientTickHandler;
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

public class ModelUfo extends HierarchicalModel<Entity> {
    private final ModelPart body;
    private final ModelPart glow;

    public ModelUfo() {
        this(createBodyLayer().bakeRoot());
    }

    public ModelUfo(ModelPart root) {
        this.body = root.getChild("body");
        this.glow = root.getChild("glow");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();

        PartDefinition body = part.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(112, 0).addBox(-24.0F, 0.0F, -8.0F, 32.0F, 2.0F, 32.0F)
                .texOffs(80, 38).addBox(-19.0F, -7.0F, -3.0F, 4.0F, 5.0F, 22.0F)
                .texOffs(0, 86).addBox(-15.0F, -9.0F, 15.0F, 14.0F, 7.0F, 4.0F)
                .texOffs(80, 65).addBox(-15.0F, -7.0F, -3.0F, 14.0F, 5.0F, 4.0F)
                .texOffs(132, 38).addBox(-1.0F, -7.0F, -3.0F, 4.0F, 5.0F, 22.0F)
                .texOffs(0, 62).addBox(-12.0F, -5.0F, -5.0F, 8.0F, 3.0F, 2.0F)
                .texOffs(1, 82).addBox(-18.0F, -8.0F, 6.0F, 2.0F, 1.0F, 2.0F)
                .texOffs(1, 83).addBox(0.0F, -8.0F, 6.0F, 2.0F, 1.0F, 2.0F)
                .texOffs(184, 53).addBox(-13.0F, -4.0F, 3.0F, 10.0F, 2.0F, 10.0F), PartPose.offset(6.0F, 24.0F, -6.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-19.0F, 6.0F, -17.0F, 36.0F, 2.0F, 36.0F), PartPose.offsetAndRotation(-7.0F, -8.0F, 7.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition glow = part.addOrReplaceChild("glow", CubeListBuilder.create()
                .texOffs(0, 67).addBox(-2.0F, -6.0F, -4.0F, 6.0F, 4.0F, 6.0F)
                .texOffs(0, 77).addBox(-1.0F, -13.0F, 5.0F, 4.0F, 5.0F, 4.0F)
                .texOffs(16, 77).addBox(-19.0F, -13.0F, 5.0F, 4.0F, 5.0F, 4.0F)
                .texOffs(24, 67).addBox(-20.0F, -6.0F, -4.0F, 6.0F, 4.0F, 6.0F)
                .texOffs(0, 38).addBox(-18.0F, 2.0F, -2.0F, 20.0F, 4.0F, 20.0F)
                .texOffs(36, 89).addBox(3.0F, 2.0F, 5.0F, 3.0F, 2.0F, 6.0F)
                .texOffs(54, 89).addBox(-22.0F, 2.0F, 5.0F, 3.0F, 2.0F, 6.0F)
                .texOffs(0, 0).addBox(-9.0F, -4.0F, -6.0F, 2.0F, 2.0F, 1.0F)
                .texOffs(6, 12).addBox(4.0F, -10.0F, 6.0F, 1.0F, 8.0F, 1.0F)
                .texOffs(4, 9).addBox(3.0F, -10.0F, 6.0F, 1.0F, 1.0F, 1.0F)
                .texOffs(0, 14).addBox(-20.0F, -10.0F, 6.0F, 1.0F, 1.0F, 1.0F)
                .texOffs(4, 12).addBox(-21.0F, -10.0F, 6.0F, 1.0F, 8.0F, 1.0F), PartPose.offset(6.0F, 24.0F, -6.0F));

        return LayerDefinition.create(mesh, 256, 256);
    }

    @Override
    public ModelPart root() {
        return this.body;
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        int light = (int) (0xF00090 + 0x000060 * Math.sin(ClientTickHandler.ticksInGame * 0.15F));
        glow.render(matrixStack, buffer, light, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
