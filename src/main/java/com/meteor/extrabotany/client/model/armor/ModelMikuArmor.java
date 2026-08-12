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

public class ModelMikuArmor extends ModelArmor {

    private final ModelPart helmAnchor;
    private final ModelPart bodyAnchor;
    private final ModelPart armLAnchor;
    private final ModelPart armRAnchor;
    private final ModelPart pantsAnchor;
    private final ModelPart legR;
    private final ModelPart legL;
    private final ModelPart bootL;
    private final ModelPart bootR;
    private final ModelPart Shape1;
    private final ModelPart Shape2;
    private final ModelPart root;

    public ModelMikuArmor(EquipmentSlot slot) {
        this(createBodyLayer().bakeRoot(), slot);
    }

    public ModelMikuArmor(ModelPart root, EquipmentSlot slot) {
        super(root, slot);
        this.root = root;
        this.helmAnchor = root.getChild("helmAnchor");
        this.bodyAnchor = root.getChild("bodyAnchor");
        this.armLAnchor = root.getChild("armLAnchor");
        this.armRAnchor = root.getChild("armRAnchor");
        this.pantsAnchor = root.getChild("pantsAnchor");
        this.legR = root.getChild("legR");
        this.legL = root.getChild("legL");
        this.bootL = root.getChild("bootL");
        this.bootR = root.getChild("bootR");
        this.Shape1 = root.getChild("Shape1");
        this.Shape2 = root.getChild("Shape2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition part = mesh.getRoot();

        PartDefinition helmAnchor = part.addOrReplaceChild("helmAnchor", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, -2.0F, 0.0F, 2, 2, 2, new CubeDeformation(0.01F)), PartPose.offset(0F, 0F, 0F));
            PartDefinition lefthair = helmAnchor.addOrReplaceChild("lefthair", CubeListBuilder.create()
                    .texOffs(0, 33).addBox(0F, 0F, 0F, 2, 12, 2, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.5F, -9F, -1F, 0.2617994F, 0F, -0.4014257F));
            PartDefinition righthair = helmAnchor.addOrReplaceChild("righthair", CubeListBuilder.create()
                    .texOffs(9, 33).addBox(0F, 0F, 0F, 2, 12, 2, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-4.5F, -9.8F, 1F, 0.2617994F, 0F, 0.4014257F));
            PartDefinition circle2 = helmAnchor.addOrReplaceChild("circle2", CubeListBuilder.create()
                    .texOffs(18, 33).addBox(0F, 0F, 0F, 1, 3, 3, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(4.5F, -10F, -1F, 0F, 0F, -0.1047198F));
            PartDefinition circle1 = helmAnchor.addOrReplaceChild("circle1", CubeListBuilder.create()
                    .texOffs(27, 33).addBox(0F, 0F, 0F, 1, 3, 3, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-5.5F, -10F, -1F, 0F, 0F, 0.1047198F));
            PartDefinition hat = helmAnchor.addOrReplaceChild("hat", CubeListBuilder.create()
                    .texOffs(36, 33).addBox(0F, 0F, 0F, 10, 3, 1, new CubeDeformation(0.01F)), PartPose.offset(-5F, -9F, -1.3F));
        PartDefinition bodyAnchor = part.addOrReplaceChild("bodyAnchor", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, new CubeDeformation(0.01F)), PartPose.offset(0F, 0F, 0F));
            PartDefinition body = bodyAnchor.addOrReplaceChild("body", CubeListBuilder.create()
                    .texOffs(16, 16).addBox(-4F, 0F, -2F, 8, 12, 4), PartPose.offset(0F, 0F, 0F));
                PartDefinition oupai = body.addOrReplaceChild("oupai", CubeListBuilder.create()
                        .texOffs(0, 48).addBox(0F, 0F, 0F, 6, 3, 3, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-3F, 2F, -4F, 0.6108652F, 0F, 0F));
                PartDefinition pifeng = body.addOrReplaceChild("pifeng", CubeListBuilder.create()
                        .texOffs(65, 48).addBox(0F, 0F, 0F, 8, 12, 1, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-4F, 0F, 2F, 0.1396263F, 0F, 0F));
        PartDefinition armLAnchor = part.addOrReplaceChild("armLAnchor", CubeListBuilder.create().mirror()
                .texOffs(0, 0).addBox(0.0F, -1.0F, -1.0F, 2, 2, 2, new CubeDeformation(0.01F)), PartPose.offset(4F, 2F, 0F));
        PartDefinition armRAnchor = part.addOrReplaceChild("armRAnchor", CubeListBuilder.create().mirror()
                .texOffs(0, 0).addBox(-2.0F, -1.0F, -1.0F, 2, 2, 2, new CubeDeformation(0.01F)), PartPose.offset(-4F, 2F, 0F));
        PartDefinition pantsAnchor = part.addOrReplaceChild("pantsAnchor", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, new CubeDeformation(0.01F)), PartPose.offset(0F, 0F, 0F));
            PartDefinition a1 = pantsAnchor.addOrReplaceChild("a1", CubeListBuilder.create()
                    .texOffs(0, 65).addBox(0F, 0F, 0F, 6, 7, 1, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-3F, 9F, -2F, -0.2617994F, 0F, 0F));
            PartDefinition a2 = pantsAnchor.addOrReplaceChild("a2", CubeListBuilder.create()
                    .texOffs(15, 65).addBox(0F, 0F, 0F, 3, 7, 1, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.7F, 9F, -2F, -0.2617994F, -0.5235988F, 0F));
            PartDefinition a3 = pantsAnchor.addOrReplaceChild("a3", CubeListBuilder.create()
                    .texOffs(24, 65).addBox(0F, 0F, 0F, 3, 7, 1, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.2F, 9F, -1F, 0.2617994F, 3.665191F, 0F));
            PartDefinition c2 = pantsAnchor.addOrReplaceChild("c2", CubeListBuilder.create()
                    .texOffs(33, 65).addBox(0F, 0F, 0F, 1, 6, 3, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-4F, 9F, -2F, 0F, 0F, 0.3490659F));
            PartDefinition c1 = pantsAnchor.addOrReplaceChild("c1", CubeListBuilder.create()
                    .texOffs(42, 65).addBox(0F, 0F, 0F, 1, 6, 3, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(3F, 9F, -2F, 0F, 0F, -0.3490659F));
            PartDefinition b1 = pantsAnchor.addOrReplaceChild("b1", CubeListBuilder.create()
                    .texOffs(51, 65).addBox(0F, 0F, 0F, 6, 7, 1, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-3F, 9F, 1F, 0.2617994F, 0F, 0F));
            PartDefinition b2 = pantsAnchor.addOrReplaceChild("b2", CubeListBuilder.create()
                    .texOffs(66, 65).addBox(0F, 0F, 0F, 3, 7, 1, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(5.5F, 9F, 0.5F, -0.2617994F, 3.665191F, 0F));
            PartDefinition b3 = pantsAnchor.addOrReplaceChild("b3", CubeListBuilder.create()
                    .texOffs(75, 65).addBox(0F, 0F, 0F, 3, 7, 1, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-4.5F, 9F, -0.5F, 0.2617994F, -0.5235988F, 0F));
        PartDefinition legR = part.addOrReplaceChild("legR", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-2F, 0F, -2F, 0, 0, 0, new CubeDeformation(0.01F)), PartPose.offset(-2F, 12F, 0F));
        PartDefinition legL = part.addOrReplaceChild("legL", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-2F, 0F, -2F, 0, 0, 0, new CubeDeformation(0.01F)), PartPose.offset(2F, 12F, 0F));
        PartDefinition bootL = part.addOrReplaceChild("bootL", CubeListBuilder.create().mirror()
                .texOffs(0, 0).addBox(-2.39F, 8.5F, -2.49F, 2, 2, 2, new CubeDeformation(0.01F)), PartPose.offset(1.9F, 12F, 0F));
            PartDefinition legLeft = bootL.addOrReplaceChild("legLeft", CubeListBuilder.create()
                    .texOffs(0, 16).addBox(-2F, 0F, -2F, 4, 12, 4, new CubeDeformation(0.01F)), PartPose.offset(0F, 0F, 0F));
        PartDefinition bootR = part.addOrReplaceChild("bootR", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-2.5F, 8.5F, -2.51F, 2, 2, 2, new CubeDeformation(0.01F)), PartPose.offset(-2F, 12F, 0F));
            PartDefinition legRight = bootR.addOrReplaceChild("legRight", CubeListBuilder.create()
                    .texOffs(0, 16).addBox(-2F, 0F, -2F, 4, 12, 4, new CubeDeformation(0.01F)), PartPose.offset(0F, 0F, 0F));
        PartDefinition Shape1 = part.addOrReplaceChild("Shape1", CubeListBuilder.create()
                .texOffs(19, 48).addBox(0F, 0F, 0F, 5, 2, 6, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1F, -3F, -3F, 0F, 0F, 0.1745329F));
        PartDefinition Shape2 = part.addOrReplaceChild("Shape2", CubeListBuilder.create()
                .texOffs(42, 48).addBox(0F, 0F, 0F, 5, 2, 6, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-4F, -2F, -3F, 0F, 0F, -0.1745329F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack ms, VertexConsumer buffer, int light, int overlay, float r, float g, float b, float a) {
        helmAnchor.visible = slot == EquipmentSlot.HEAD;
        bodyAnchor.visible = slot == EquipmentSlot.CHEST;
        armRAnchor.visible = slot == EquipmentSlot.CHEST;
        armLAnchor.visible = slot == EquipmentSlot.CHEST;
        legR.visible = slot == EquipmentSlot.LEGS;
        legL.visible = slot == EquipmentSlot.LEGS;
        bootL.visible = slot == EquipmentSlot.FEET;
        bootR.visible = slot == EquipmentSlot.FEET;
        hat.visible = false;
        if (slot == EquipmentSlot.LEGS) {
        } else {
        }
        super.renderToBuffer(ms, buffer, light, overlay, r, g, b, a);
    }

}
