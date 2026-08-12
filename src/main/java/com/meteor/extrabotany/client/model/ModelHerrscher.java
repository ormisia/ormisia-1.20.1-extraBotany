package com.meteor.extrabotany.client.model;

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

public class ModelHerrscher extends ModelArmor {

    private final ModelPart rightArm;
    private final ModelPart bone2;
    private final ModelPart katanaSheath;
    private final ModelPart bone4;
    private final ModelPart bone3;
    private final ModelPart leftArm;
    private final ModelPart bone5;
    private final ModelPart bone6;
    private final ModelPart katana;
    private final ModelPart bone7;
    private final ModelPart back;

    public ModelHerrscher() {
        this(createBodyLayer().bakeRoot());
    }

    public ModelHerrscher(ModelPart root) {
        super(root, EquipmentSlot.CHEST);
        this.rightArm = root.getChild("rightArm");
        this.bone2 = this.rightArm.getChild("bone2");
        this.katanaSheath = this.rightArm.getChild("katanaSheath");
        this.bone4 = this.katanaSheath.getChild("bone4");
        this.bone3 = this.rightArm.getChild("bone3");
        this.leftArm = root.getChild("leftArm");
        this.bone5 = this.leftArm.getChild("bone5");
        this.bone6 = this.bone5.getChild("bone6");
        this.katana = this.leftArm.getChild("katana");
        this.bone7 = this.katana.getChild("bone7");
        this.back = root.getChild("back");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition part = mesh.getRoot();

        PartDefinition rightArm = part.addOrReplaceChild("rightArm", CubeListBuilder.create()
                .texOffs(0, 13).addBox(0.6859F, 1.3201F, -2.6295F, 3.0F, 9.0F, 5.0F)
                .texOffs(11, 0).addBox(0.6859F, 2.3201F, -3.6295F, 2.0F, 7.0F, 1.0F)
                .texOffs(0, 0).addBox(0.6859F, 2.3201F, 2.3705F, 2.0F, 7.0F, 1.0F)
                .texOffs(0, 0).addBox(1.1859F, 3.3201F, -4.6295F, 1.0F, 4.0F, 9.0F)
                .texOffs(12, 5).addBox(2.1859F, 3.3201F, -4.1295F, 1.0F, 2.0F, 8.0F)
                .texOffs(17, 0).addBox(3.1859F, 1.3201F, -1.6295F, 1.0F, 2.0F, 3.0F)
                .texOffs(22, 0).addBox(3.1859F, 3.3201F, -0.6295F, 1.0F, 1.0F, 1.0F)
                .texOffs(16, 16).addBox(0.1859F, 10.3201F, -1.1295F, 3.0F, 3.0F, 3.0F)
                .texOffs(11, 15).addBox(0.1859F, 10.3201F, -2.1295F, 3.0F, 2.0F, 1.0F)
                .texOffs(16, 7).addBox(1.1859F, 4.3201F, 4.3705F, 1.0F, 1.0F, 1.0F)
                .texOffs(5, 7).addBox(1.1859F, 4.3201F, -5.6295F, 1.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(5.75F, 1.0F, 2.75F, -0.4363F, 0.0F, -0.3491F));

        PartDefinition bone2 = rightArm.addOrReplaceChild("bone2", CubeListBuilder.create()
                .texOffs(0, 13).addBox(-0.2835F, -2.991F, -0.5F, 1.0F, 4.0F, 1.0F), PartPose.offsetAndRotation(3.6859F, 1.3201F, -0.1295F, 0.0F, 0.0F, 0.7854F));

        PartDefinition katanaSheath = rightArm.addOrReplaceChild("katanaSheath", CubeListBuilder.create()
                .texOffs(31, 49).addBox(-0.1555F, -2.0137F, -1.1343F, 1.0F, 2.0F, 13.0F), PartPose.offsetAndRotation(1.2474F, 13.2096F, 0.6816F, -0.0873F, 0.0F, -0.0873F));

        PartDefinition bone4 = katanaSheath.addOrReplaceChild("bone4", CubeListBuilder.create()
                .texOffs(46, 52).addBox(-0.1555F, -2.2105F, -1.1147F, 1.0F, 2.0F, 8.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 13.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone3 = rightArm.addOrReplaceChild("bone3", CubeListBuilder.create()
                .texOffs(16, 22).addBox(-2.0F, -2.0F, -2.0F, 2.0F, 3.0F, 3.0F)
                .texOffs(22, 2).addBox(0.0F, -1.0F, -2.0F, 1.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(2.1859F, 0.5701F, 0.3705F, 0.0F, 0.0F, -0.2618F));

        PartDefinition leftArm = part.addOrReplaceChild("leftArm", CubeListBuilder.create()
                .texOffs(30, 0).addBox(-1.0F, -1.0F, -3.0F, 3.0F, 10.0F, 4.0F)
                .texOffs(44, 0).addBox(-2.0F, 1.0F, -2.0F, 1.0F, 4.0F, 2.0F)
                .texOffs(30, 20).addBox(-1.0F, -3.0F, -3.0F, 3.0F, 2.0F, 1.0F)
                .texOffs(30, 23).addBox(-1.0F, -3.0F, 0.0F, 3.0F, 2.0F, 1.0F)
                .texOffs(44, 6).addBox(-1.0F, -4.0F, -2.0F, 3.0F, 1.0F, 2.0F)
                .texOffs(54, 0).addBox(-0.6381F, -0.7174F, -4.039F, 2.0F, 5.0F, 1.0F)
                .texOffs(54, 6).addBox(-0.4238F, -0.7607F, 0.9059F, 2.0F, 5.0F, 1.0F), PartPose.offsetAndRotation(-8.0F, 3.0F, 2.5F, -0.4363F, 0.0F, 0.3491F));

        PartDefinition bone5 = leftArm.addOrReplaceChild("bone5", CubeListBuilder.create()
                .texOffs(30, 14).addBox(-3.1094F, -0.3639F, -1.0F, 3.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition bone6 = bone5.addOrReplaceChild("bone6", CubeListBuilder.create()
                .texOffs(30, 18).addBox(-2.7531F, -1.3651F, -0.5F, 4.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(-3.9575F, 1.2207F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition katana = leftArm.addOrReplaceChild("katana", CubeListBuilder.create()
                .texOffs(31, 13).addBox(-0.1555F, -2.0137F, -1.1343F, 1.0F, 2.0F, 13.0F)
                .texOffs(46, 14).addBox(-0.1596F, -1.9492F, -7.5283F, 1.0F, 2.0F, 3.0F)
                .texOffs(46, 9).addBox(-1.6025F, -3.4558F, -0.4588F, 4.0F, 5.0F, 0.0F), PartPose.offsetAndRotation(1.0F, 8.0F, -4.0F, -0.0873F, 3.0543F, 0.0F));

        PartDefinition bone7 = katana.addOrReplaceChild("bone7", CubeListBuilder.create()
                .texOffs(46, 16).addBox(-0.1555F, -2.2105F, -1.1147F, 1.0F, 2.0F, 8.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 13.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition back = part.addOrReplaceChild("back", CubeListBuilder.create()
                .texOffs(0, 28).addBox(-16.0F, -34.0F, 10.0F, 32.0F, 21.0F, 0.0F), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    public void renderLeftArm(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.rightArm.render(matrixStack, buffer, 0xF000F0, packedOverlay);
    }

    public void renderRightArm(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.leftArm.render(matrixStack, buffer, 0xF000F0, packedOverlay);
    }

    public void renderStigma(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.back.render(matrixStack, buffer, 0xF000F0, packedOverlay);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}
