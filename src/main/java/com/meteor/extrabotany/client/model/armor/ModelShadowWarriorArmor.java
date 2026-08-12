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

public class ModelShadowWarriorArmor extends ModelArmor {

    private final ModelPart helmAnchor;
    private final ModelPart bodyAnchor;
    private final ModelPart armLAnchor;
    private final ModelPart armRAnchor;
    private final ModelPart pantsAnchor;
    private final ModelPart legR;
    private final ModelPart legL;
    private final ModelPart bootL;
    private final ModelPart bootR;
    private final ModelPart rightarm;
    private final ModelPart leftarm;
    private final ModelPart rightleg;
    private final ModelPart leftleg;
    private final ModelPart root;

    public ModelShadowWarriorArmor(EquipmentSlot slot) {
        this(createBodyLayer().bakeRoot(), slot);
    }

    public ModelShadowWarriorArmor(ModelPart root, EquipmentSlot slot) {
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
        this.rightarm = root.getChild("rightarm");
        this.leftarm = root.getChild("leftarm");
        this.rightleg = root.getChild("rightleg");
        this.leftleg = root.getChild("leftleg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition part = mesh.getRoot();

        PartDefinition helmAnchor = part.addOrReplaceChild("helmAnchor", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, -2.0F, 0.0F, 2, 2, 2, new CubeDeformation(0.01F)), PartPose.offset(0F, 0F, 0F));
            PartDefinition top = helmAnchor.addOrReplaceChild("top", CubeListBuilder.create()
                    .texOffs(0, 33).addBox(0F, 0F, 0F, 9, 3, 9, new CubeDeformation(0.01F)), PartPose.offset(-4.5F, -8.5F, -4.5F));
            PartDefinition back = helmAnchor.addOrReplaceChild("back", CubeListBuilder.create()
                    .texOffs(37, 33).addBox(0F, 0F, 0F, 9, 7, 1), PartPose.offsetAndRotation(-4.5F, -8F, 3F, 0.2617994F, 0F, 0F));
            PartDefinition front1 = helmAnchor.addOrReplaceChild("front1", CubeListBuilder.create()
                    .texOffs(58, 33).addBox(0F, 0F, 0F, 3, 5, 1), PartPose.offsetAndRotation(-5.7F, -6F, -1.9F, 0F, 0.9948377F, 0F));
            PartDefinition front2 = helmAnchor.addOrReplaceChild("front2", CubeListBuilder.create()
                    .texOffs(67, 33).addBox(0F, 0F, 0F, 3, 5, 1), PartPose.offsetAndRotation(3.7F, -6F, -4.5F, 0F, -0.9948377F, 0F));
            PartDefinition right2 = helmAnchor.addOrReplaceChild("right2", CubeListBuilder.create()
                    .texOffs(76, 33).addBox(0F, 0F, 0F, 1, 6, 9, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-4F, -8F, -4.5F, 0F, 0F, 0.3316126F));
            PartDefinition front = helmAnchor.addOrReplaceChild("front", CubeListBuilder.create()
                    .texOffs(97, 33).addBox(0F, 0F, 0F, 6, 2, 1, new CubeDeformation(0.01F)), PartPose.offset(-3F, -8F, -5.1F));
            PartDefinition right1 = helmAnchor.addOrReplaceChild("right1", CubeListBuilder.create()
                    .texOffs(0, 50).addBox(0F, 0F, 0F, 2, 6, 1, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-5F, -13F, -4.9F, 0.0872665F, 0.0872665F, -0.2617994F));
            PartDefinition medal = helmAnchor.addOrReplaceChild("medal", CubeListBuilder.create()
                    .texOffs(7, 50).addBox(0F, 0F, 0F, 2, 4, 1, new CubeDeformation(0.01F)), PartPose.offset(-1F, -11.5F, -5F));
            PartDefinition right3 = helmAnchor.addOrReplaceChild("right3", CubeListBuilder.create()
                    .texOffs(14, 50).addBox(0F, 0F, 0F, 3, 4, 1), PartPose.offsetAndRotation(-7F, -7.2F, -4.3F, 0F, 0F, 0.3316126F));
            PartDefinition left2 = helmAnchor.addOrReplaceChild("left2", CubeListBuilder.create()
                    .texOffs(23, 50).addBox(0F, 0F, 0F, 1, 6, 9), PartPose.offsetAndRotation(3F, -8F, -4.5F, 0F, 0F, -0.3316126F));
            PartDefinition left3 = helmAnchor.addOrReplaceChild("left3", CubeListBuilder.create()
                    .texOffs(44, 50).addBox(0F, 0F, 0F, 3, 4, 1), PartPose.offsetAndRotation(3.7F, -6.5F, -4.3F, 0F, 0F, -0.3316126F));
            PartDefinition left1 = helmAnchor.addOrReplaceChild("left1", CubeListBuilder.create()
                    .texOffs(53, 50).addBox(0F, 0F, 0F, 2, 6, 1), PartPose.offsetAndRotation(3F, -13.5F, -5F, 0.0872665F, -0.0872665F, 0.2617994F));
        PartDefinition bodyAnchor = part.addOrReplaceChild("bodyAnchor", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, new CubeDeformation(0.01F)), PartPose.offset(0F, 0F, 0F));
            PartDefinition body = bodyAnchor.addOrReplaceChild("body", CubeListBuilder.create()
                    .texOffs(16, 16).addBox(-4F, 0F, -2F, 8, 12, 4), PartPose.offset(0F, 0F, 0F));
                PartDefinition fronter = body.addOrReplaceChild("fronter", CubeListBuilder.create()
                        .texOffs(0, 84).addBox(0F, 0F, 0F, 6, 7, 1, new CubeDeformation(0.01F)), PartPose.offset(-3F, 1F, -3F));
                PartDefinition book = body.addOrReplaceChild("book", CubeListBuilder.create()
                        .texOffs(0, 93).addBox(0F, 0F, 0F, 4, 6, 2, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(1F, 1.2F, 2.7F, 0F, 0F, 0.7853982F));
                PartDefinition backer = body.addOrReplaceChild("backer", CubeListBuilder.create()
                        .texOffs(0, 102).addBox(0F, 0F, 0F, 6, 10, 1, new CubeDeformation(0.01F)), PartPose.offset(-3F, 1F, 1.7F));
        PartDefinition armLAnchor = part.addOrReplaceChild("armLAnchor", CubeListBuilder.create().mirror()
                .texOffs(0, 0).addBox(0.0F, -1.0F, -1.0F, 2, 2, 2, new CubeDeformation(0.01F)), PartPose.offset(4F, 2F, 0F));
            PartDefinition larm1 = armLAnchor.addOrReplaceChild("larm1", CubeListBuilder.create()
                    .texOffs(15, 84).addBox(0F, 0F, 0F, 5, 2, 5, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2F, -3F, -2.5F, 0F, 0F, -0.1745329F));
            PartDefinition larm4 = armLAnchor.addOrReplaceChild("larm4", CubeListBuilder.create()
                    .texOffs(36, 84).addBox(0F, 0F, 0F, 2, 5, 4, new CubeDeformation(0.25F)), PartPose.offset(0.8F, 3.2F, -2F));
            PartDefinition larm3 = armLAnchor.addOrReplaceChild("larm3", CubeListBuilder.create()
                    .texOffs(49, 84).addBox(0F, 0F, 0F, 1, 6, 4, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(1.7F, -1F, -2F, 0F, 0F, -0.1745329F));
            PartDefinition larm2 = armLAnchor.addOrReplaceChild("larm2", CubeListBuilder.create()
                    .texOffs(60, 84).addBox(0F, 0F, 0F, 1, 5, 4, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(1.7F, -2F, -2F, 0F, 0F, -0.3490659F));
        PartDefinition armRAnchor = part.addOrReplaceChild("armRAnchor", CubeListBuilder.create().mirror()
                .texOffs(0, 0).addBox(-2.0F, -1.0F, -1.0F, 2, 2, 2, new CubeDeformation(0.01F)), PartPose.offset(-4F, 2F, 0F));
            PartDefinition rarm1 = armRAnchor.addOrReplaceChild("rarm1", CubeListBuilder.create()
                    .texOffs(15, 96).addBox(0F, 0F, 0F, 5, 2, 5, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-3F, -4F, -2.5F, 0F, 0F, 0.1745329F));
            PartDefinition rarm2 = armRAnchor.addOrReplaceChild("rarm2", CubeListBuilder.create()
                    .texOffs(36, 96).addBox(0F, 0F, 0F, 1, 5, 4, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.7F, -2F, -2F, 0F, 0F, 0.3490659F));
            PartDefinition rarm3 = armRAnchor.addOrReplaceChild("rarm3", CubeListBuilder.create()
                    .texOffs(47, 96).addBox(0F, 0F, 0F, 1, 6, 4, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.7F, -1F, -2F, 0F, 0F, 0.1745329F));
            PartDefinition rarm4 = armRAnchor.addOrReplaceChild("rarm4", CubeListBuilder.create()
                    .texOffs(58, 96).addBox(0F, 0F, 0F, 2, 5, 4, new CubeDeformation(0.25F)), PartPose.offset(-2.8F, 3.2F, -2F));
        PartDefinition pantsAnchor = part.addOrReplaceChild("pantsAnchor", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, new CubeDeformation(0.01F)), PartPose.offset(0F, 0F, 0F));
            PartDefinition a1 = pantsAnchor.addOrReplaceChild("a1", CubeListBuilder.create()
                    .texOffs(0, 67).addBox(0F, 0F, 0F, 5, 5, 1), PartPose.offsetAndRotation(-2.5F, 10F, -3F, -0.2792527F, 0F, 0F));
            PartDefinition a2 = pantsAnchor.addOrReplaceChild("a2", CubeListBuilder.create()
                    .texOffs(0, 67).addBox(0F, 0F, 0F, 5, 5, 1), PartPose.offsetAndRotation(-2.5F, 12F, -3F, -0.2792527F, 0F, 0F));
            PartDefinition a3 = pantsAnchor.addOrReplaceChild("a3", CubeListBuilder.create()
                    .texOffs(0, 67).addBox(0F, 0F, 0F, 5, 5, 1), PartPose.offsetAndRotation(-2.5F, 14F, -3F, -0.2792527F, 0F, 0F));
            PartDefinition d1 = pantsAnchor.addOrReplaceChild("d1", CubeListBuilder.create()
                    .texOffs(0, 74).addBox(0F, 0F, 0F, 1, 5, 4), PartPose.offsetAndRotation(3F, 10F, -2F, 0F, 0F, -0.2617994F));
            PartDefinition d2 = pantsAnchor.addOrReplaceChild("d2", CubeListBuilder.create()
                    .texOffs(0, 74).addBox(0F, 0F, 0F, 1, 5, 4), PartPose.offsetAndRotation(3F, 12F, -2F, 0F, 0F, -0.2617994F));
            PartDefinition d3 = pantsAnchor.addOrReplaceChild("d3", CubeListBuilder.create()
                    .texOffs(0, 74).addBox(0F, 0F, 0F, 1, 5, 4), PartPose.offsetAndRotation(3F, 14F, -2F, 0F, 0F, -0.2617994F));
            PartDefinition e1 = pantsAnchor.addOrReplaceChild("e1", CubeListBuilder.create()
                    .texOffs(0, 74).addBox(0F, 0F, 0F, 1, 5, 4), PartPose.offsetAndRotation(-4.2F, 9.8F, -2F, 0F, 0F, 0.2617994F));
            PartDefinition e2 = pantsAnchor.addOrReplaceChild("e2", CubeListBuilder.create()
                    .texOffs(0, 74).addBox(0F, 0F, 0F, 1, 5, 4), PartPose.offsetAndRotation(-4.2F, 11.8F, -2F, 0F, 0F, 0.2617994F));
            PartDefinition e3 = pantsAnchor.addOrReplaceChild("e3", CubeListBuilder.create()
                    .texOffs(0, 74).addBox(0F, 0F, 0F, 1, 5, 4), PartPose.offsetAndRotation(-4.2F, 13.8F, -2F, 0F, 0F, 0.2617994F));
            PartDefinition f1 = pantsAnchor.addOrReplaceChild("f1", CubeListBuilder.create()
                    .texOffs(11, 74).addBox(0F, 0F, 0F, 9, 5, 1), PartPose.offsetAndRotation(-4.5F, 10F, 1.8F, 0.2617994F, 0F, 0F));
            PartDefinition f2 = pantsAnchor.addOrReplaceChild("f2", CubeListBuilder.create()
                    .texOffs(11, 74).addBox(0F, 0F, 0F, 9, 5, 1), PartPose.offsetAndRotation(-4.5F, 12F, 1.8F, 0.2617994F, 0F, 0F));
            PartDefinition f3 = pantsAnchor.addOrReplaceChild("f3", CubeListBuilder.create()
                    .texOffs(11, 74).addBox(0F, 0F, 0F, 9, 5, 1), PartPose.offsetAndRotation(-4.5F, 14F, 1.8F, 0.2617994F, 0F, 0F));
        PartDefinition legR = part.addOrReplaceChild("legR", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-2F, 0F, -2F, 4, 12, 4, new CubeDeformation(0.01F)), PartPose.offset(-2F, 12F, 0F));
            PartDefinition b1 = legR.addOrReplaceChild("b1", CubeListBuilder.create()
                    .texOffs(13, 67).addBox(0F, 0F, 0F, 3, 3, 1), PartPose.offsetAndRotation(-3.5F, -2F, -3F, -0.2617994F, 0F, 0F));
            PartDefinition b2 = legR.addOrReplaceChild("b2", CubeListBuilder.create()
                    .texOffs(13, 67).addBox(0F, 0F, 0F, 3, 3, 1), PartPose.offsetAndRotation(-3.5F, 0F, -3F, -0.2617994F, 0F, 0F));
            PartDefinition b3 = legR.addOrReplaceChild("b3", CubeListBuilder.create()
                    .texOffs(13, 67).addBox(0F, 0F, 0F, 3, 3, 1), PartPose.offsetAndRotation(-3.5F, 2F, -3F, -0.2617994F, 0F, 0F));
        PartDefinition legL = part.addOrReplaceChild("legL", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-2F, 0F, -2F, 4, 12, 4, new CubeDeformation(0.01F)), PartPose.offset(2F, 12F, 0F));
            PartDefinition c1 = legL.addOrReplaceChild("c1", CubeListBuilder.create()
                    .texOffs(22, 67).addBox(0F, 0F, 0F, 3, 3, 1), PartPose.offsetAndRotation(0.5F, -2F, -3F, -0.2617994F, 0F, 0F));
            PartDefinition c2 = legL.addOrReplaceChild("c2", CubeListBuilder.create()
                    .texOffs(22, 67).addBox(0F, 0F, 0F, 3, 3, 1), PartPose.offsetAndRotation(0.5F, 0F, -3F, -0.2617994F, 0F, 0F));
            PartDefinition c3 = legL.addOrReplaceChild("c3", CubeListBuilder.create()
                    .texOffs(22, 67).addBox(0F, 0F, 0F, 3, 3, 1), PartPose.offsetAndRotation(0.5F, 2F, -3F, -0.2617994F, 0F, 0F));
        PartDefinition bootL = part.addOrReplaceChild("bootL", CubeListBuilder.create().mirror()
                .texOffs(0, 0).addBox(-2.39F, 8.5F, -2.49F, 2, 2, 2, new CubeDeformation(0.01F)), PartPose.offset(1.9F, 12F, 0F));
            PartDefinition boot2 = bootL.addOrReplaceChild("boot2", CubeListBuilder.create()
                    .texOffs(0, 114).addBox(0F, 0F, 0F, 4, 4, 5, new CubeDeformation(0.01F)), PartPose.offset(-2F, 8F, -2.5F));
            PartDefinition fb2 = bootL.addOrReplaceChild("fb2", CubeListBuilder.create()
                    .texOffs(19, 114).addBox(0F, 0F, 0F, 3, 3, 1, new CubeDeformation(0.01F)), PartPose.offset(-2F, 9F, -3F));
        PartDefinition bootR = part.addOrReplaceChild("bootR", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-2.5F, 8.5F, -2.51F, 2, 2, 2, new CubeDeformation(0.01F)), PartPose.offset(-2F, 12F, 0F));
            PartDefinition boot1 = bootR.addOrReplaceChild("boot1", CubeListBuilder.create()
                    .texOffs(0, 114).addBox(0F, 0F, 0F, 4, 4, 5, new CubeDeformation(0.01F)), PartPose.offset(-2F, 8F, -2.5F));
            PartDefinition fb1 = bootR.addOrReplaceChild("fb1", CubeListBuilder.create()
                    .texOffs(19, 114).addBox(0F, 0F, 0F, 3, 3, 1, new CubeDeformation(0.01F)), PartPose.offset(-1F, 9F, -3F));
        PartDefinition rightarm = part.addOrReplaceChild("rightarm", CubeListBuilder.create()
                .texOffs(40, 16).addBox(-3F, -2F, -2F, 4, 12, 4), PartPose.offset(-5F, 2F, 0F));
        PartDefinition leftarm = part.addOrReplaceChild("leftarm", CubeListBuilder.create()
                .texOffs(40, 16).addBox(-1F, -2F, -2F, 4, 12, 4), PartPose.offset(5F, 2F, 0F));
        PartDefinition rightleg = part.addOrReplaceChild("rightleg", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-2F, 0F, -2F, 4, 12, 4), PartPose.offset(-2F, 12F, 0F));
        PartDefinition leftleg = part.addOrReplaceChild("leftleg", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-2F, 0F, -2F, 4, 12, 4), PartPose.offset(2F, 12F, 0F));

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
