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

public class ModelGoblinSlayerArmor extends ModelArmor {

    private final ModelPart bootL;
    private final ModelPart bootR;
    private final ModelPart root;

    public ModelGoblinSlayerArmor(EquipmentSlot slot) {
        this(createBodyLayer().bakeRoot(), slot);
    }

    public ModelGoblinSlayerArmor(ModelPart root, EquipmentSlot slot) {
        super(root, slot);
        this.root = root;
        this.bootL = root.getChild("bootL");
        this.bootR = root.getChild("bootR");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition part = mesh.getRoot();

        PartDefinition head = part.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 18).addBox(-2.0F, -1.0F, -5.0F, 4.0F, 2.0F, 2.0F)
                .texOffs(4, 23).addBox(-1.5F, -6.0F, -5.0F, 1.0F, 5.0F, 1.0F)
                .texOffs(8, 23).addBox(0.5F, -6.0F, -5.0F, 1.0F, 5.0F, 1.0F)
                .texOffs(0, 0).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F), PartPose.offset(0F, 0F, 0F));
            PartDefinition plates = head.addOrReplaceChild("plates", CubeListBuilder.create()
                    .texOffs(0, 49).addBox(-3.0F, -0.5F, -3.0F, 6.0F, 1.0F, 6.0F)
                    .texOffs(0, 56).addBox(-3.0F, -1.5F, -2.0F, 5.0F, 1.0F, 5.0F)
                    .texOffs(0, 65).addBox(-1.0F, -2.5F, 1.0F, 2.0F, 1.0F, 3.0F)
                    .texOffs(0, 74).addBox(-3.0F, -0.5F, 3.0F, 4.0F, 1.0F, 1.0F)
                    .texOffs(0, 69).addBox(-4.0F, -0.5F, -1.0F, 1.0F, 1.0F, 4.0F)
                    .texOffs(0, 62).addBox(-4.0F, -2.5F, -1.0F, 5.0F, 1.0F, 2.0F)
                    .texOffs(0, 35).addBox(-2.0F, 1.5F, -4.0F, 6.0F, 1.0F, 6.0F)
                    .texOffs(0, 29).addBox(-1.5F, 2.5F, -3.5F, 5.0F, 1.0F, 5.0F)
                    .texOffs(0, 42).addBox(-2.5F, 0.5F, -3.5F, 6.0F, 1.0F, 6.0F), PartPose.offsetAndRotation(0F, -8.5F, -2F, 0F, 0.7854F, 0F));
            PartDefinition bang2 = head.addOrReplaceChild("bang2", CubeListBuilder.create()
                    .texOffs(0, 22).addBox(-0.75F, -6.25F, 0.0F, 1.0F, 6.0F, 1.0F), PartPose.offsetAndRotation(-2F, 0F, -5F, 0F, 0F, -0.1745F));
            PartDefinition bang1 = head.addOrReplaceChild("bang1", CubeListBuilder.create()
                    .texOffs(12, 22).addBox(-0.25F, -6.25F, -1.0F, 1.0F, 6.0F, 1.0F), PartPose.offsetAndRotation(2F, 0F, -4F, 0F, 0F, 0.1745F));
            PartDefinition ear = head.addOrReplaceChild("ear", CubeListBuilder.create()
                    .texOffs(16, 18).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 3.0F, 3.0F)
                    .texOffs(16, 24).addBox(-11.0F, -1.0F, -2.0F, 1.0F, 3.0F, 3.0F), PartPose.offsetAndRotation(5.5F, -4F, 0F, 0.7854F, 0F, 0F));
            PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create()
                    .texOffs(35, 0).addBox(-1.5F, -0.5F, 0.25F, 3.0F, 0.0F, 4.0F), PartPose.offsetAndRotation(0F, -9.5F, 2F, 0.1745F, -0.3491F, 0F));
            PartDefinition hair2 = head.addOrReplaceChild("hair2", CubeListBuilder.create()
                    .texOffs(34, 4).addBox(-1.25F, -0.5F, 0.0F, 3.0F, 0.0F, 5.0F), PartPose.offsetAndRotation(-0.25F, -9.5F, 2.25F, -0.3491F, 0.2618F, 0F));
        PartDefinition body = part.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(100, 0).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 13.0F, 5.0F)
                .texOffs(50, 8).addBox(-5.0F, 6.0F, -3.0F, 10.0F, 1.0F, 6.0F)
                .texOffs(68, 0).addBox(-5.0F, 11.0F, -3.0F, 10.0F, 2.0F, 6.0F)
                .texOffs(110, 18).addBox(-4.0F, 0.0F, 2.25F, 8.0F, 6.0F, 1.0F), PartPose.offset(0F, 0F, 0F));
            PartDefinition bone = body.addOrReplaceChild("bone", CubeListBuilder.create()
                    .texOffs(50, 0).addBox(5.5F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F)
                    .texOffs(82, 8).addBox(-1.5F, -2.5F, -1.0F, 4.0F, 4.0F, 1.0F)
                    .texOffs(54, 0).addBox(-0.5F, -5.5F, -2.0F, 6.0F, 6.0F, 1.0F)
                    .texOffs(96, 18).addBox(-3.5F, -2.5F, 5.0F, 6.0F, 6.0F, 1.0F)
                    .texOffs(46, 9).addBox(0.0F, -6.5F, -1.0F, 4.0F, 1.0F, 1.0F)
                    .texOffs(50, 15).addBox(0.5F, -4.5F, -3.0F, 4.0F, 2.0F, 1.0F)
                    .texOffs(60, 15).addBox(2.5F, -2.5F, -3.0F, 2.0F, 2.0F, 1.0F)
                    .texOffs(92, 8).addBox(-5.5F, 2.5F, -2.5F, 3.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(0F, 7F, -2.5F, 0F, 0F, -0.7854F));
            PartDefinition bone3 = body.addOrReplaceChild("bone3", CubeListBuilder.create()
                    .texOffs(66, 15).addBox(-5.0F, -1.0F, -4.0F, 5.0F, 2.0F, 1.0F)
                    .texOffs(66, 15).addBox(-5.0F, -1.0F, 3.0F, 5.0F, 2.0F, 1.0F)
                    .texOffs(66, 21).addBox(-6.0F, -1.0F, -3.0F, 1.0F, 2.0F, 6.0F), PartPose.offsetAndRotation(0F, 12F, 0F, 0F, 0F, 0.2618F));
                PartDefinition bone7 = bone3.addOrReplaceChild("bone7", CubeListBuilder.create()
                        .texOffs(66, 18).addBox(-5.0F, -0.25F, -4.0F, 5.0F, 2.0F, 1.0F)
                        .texOffs(66, 18).addBox(-5.0F, -0.25F, 3.0F, 5.0F, 2.0F, 1.0F)
                        .texOffs(80, 21).addBox(-6.0F, -0.25F, -3.0F, 1.0F, 2.0F, 6.0F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, -0.5236F));
            PartDefinition bone2 = body.addOrReplaceChild("bone2", CubeListBuilder.create()
                    .texOffs(78, 15).addBox(0.0F, -1.0F, -4.0F, 5.0F, 2.0F, 1.0F)
                    .texOffs(78, 15).addBox(0.0F, -1.0F, 3.0F, 5.0F, 2.0F, 1.0F)
                    .texOffs(66, 21).addBox(5.0F, -1.0F, -3.0F, 1.0F, 2.0F, 6.0F), PartPose.offsetAndRotation(0F, 12F, 0F, 0F, 0F, -0.2618F));
                PartDefinition bone8 = bone2.addOrReplaceChild("bone8", CubeListBuilder.create()
                        .texOffs(78, 18).addBox(0.0F, -0.25F, -4.0F, 5.0F, 2.0F, 1.0F)
                        .texOffs(78, 18).addBox(0.0F, -0.25F, 3.0F, 5.0F, 2.0F, 1.0F)
                        .texOffs(80, 21).addBox(5.0F, -0.25F, -3.0F, 1.0F, 2.0F, 6.0F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0.5236F));
        PartDefinition leftArm = part.addOrReplaceChild("leftArm", CubeListBuilder.create()
                .texOffs(108, 48).addBox(-3.75F, 2.5F, -2.5F, 5.0F, 8.0F, 5.0F)
                .texOffs(98, 48).addBox(-4.25F, 3.0F, -2.0F, 1.0F, 6.0F, 4.0F), PartPose.offset(-5F, 2F, 0F));
            PartDefinition bone5 = leftArm.addOrReplaceChild("bone5", CubeListBuilder.create()
                    .texOffs(100, 25).addBox(-5.25F, -4.0F, -3.5F, 7.0F, 5.0F, 7.0F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0.3491F));
            PartDefinition handL = leftArm.addOrReplaceChild("handL", CubeListBuilder.create()
                    .texOffs(104, 37).addBox(-4.0F, 0.0F, -3.0F, 6.0F, 4.0F, 6.0F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0.1745F));
        PartDefinition rightArm = part.addOrReplaceChild("rightArm", CubeListBuilder.create().mirror()
                .texOffs(108, 48).addBox(-1.25F, 2.5F, -2.5F, 5.0F, 8.0F, 5.0F)
                .texOffs(98, 48).addBox(3.25F, 3.0F, -2.0F, 1.0F, 6.0F, 4.0F), PartPose.offset(5F, 2F, 0F));
            PartDefinition bone4 = rightArm.addOrReplaceChild("bone4", CubeListBuilder.create().mirror()
                    .texOffs(100, 25).addBox(-1.75F, -4.0F, -3.5F, 7.0F, 5.0F, 7.0F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, -0.3491F));
            PartDefinition handR = rightArm.addOrReplaceChild("handR", CubeListBuilder.create().mirror()
                    .texOffs(104, 37).addBox(-2.0F, 0.0F, -3.0F, 6.0F, 4.0F, 6.0F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, -0.1745F));
        PartDefinition rightLeg = part.addOrReplaceChild("rightLeg", CubeListBuilder.create().mirror()
                .texOffs(112, 112).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F)
                .texOffs(108, 61).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 13.0F, 5.0F)
                .texOffs(98, 61).addBox(-2.1F, 1.0F, -3.5F, 4.0F, 5.0F, 1.0F)
                .texOffs(84, 70).addBox(-3.1F, 2.0F, -3.0F, 6.0F, 3.0F, 6.0F), PartPose.offset(1.9F, 12F, 0F));
        PartDefinition leftLeg = part.addOrReplaceChild("leftLeg", CubeListBuilder.create()
                .texOffs(112, 112).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F)
                .texOffs(108, 61).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 13.0F, 5.0F)
                .texOffs(98, 61).addBox(-2.0F, 1.0F, -3.5F, 4.0F, 5.0F, 1.0F)
                .texOffs(84, 70).addBox(-3.0F, 2.0F, -3.0F, 6.0F, 3.0F, 6.0F), PartPose.offset(-1.9F, 12F, 0F));
        PartDefinition bootL = part.addOrReplaceChild("bootL", CubeListBuilder.create()
                .texOffs(24, 31).addBox(-3.0F, 8.0F, -3.0F, 6.0F, 5.0F, 6.0F)
                .texOffs(24, 42).addBox(-3.0F, 6.0F, 2.0F, 6.0F, 2.0F, 1.0F)
                .texOffs(24, 45).addBox(2.0F, 6.0F, -1.0F, 1.0F, 2.0F, 3.0F)
                .texOffs(24, 45).addBox(-3.0F, 6.0F, -1.0F, 1.0F, 2.0F, 3.0F)
                .texOffs(29, 45).addBox(2.0F, 7.0F, -2.0F, 1.0F, 1.0F, 1.0F)
                .texOffs(29, 45).addBox(-3.0F, 7.0F, -2.0F, 1.0F, 1.0F, 1.0F)
                .texOffs(24, 27).addBox(-2.0F, 10.0F, -4.0F, 4.0F, 3.0F, 1.0F)
                .texOffs(21, 33).addBox(-1.0F, 9.0F, -4.0F, 2.0F, 1.0F, 1.0F), PartPose.offset(-1.9F, 12F, 0F));
        PartDefinition bootR = part.addOrReplaceChild("bootR", CubeListBuilder.create().mirror()
                .texOffs(24, 31).addBox(-3.0F, 8.0F, -3.0F, 6.0F, 5.0F, 6.0F)
                .texOffs(24, 42).addBox(-3.0F, 6.0F, 2.0F, 6.0F, 2.0F, 1.0F)
                .texOffs(24, 45).addBox(-3.0F, 6.0F, -1.0F, 1.0F, 2.0F, 3.0F)
                .texOffs(24, 45).addBox(2.0F, 6.0F, -1.0F, 1.0F, 2.0F, 3.0F)
                .texOffs(29, 45).addBox(-3.0F, 7.0F, -2.0F, 1.0F, 1.0F, 1.0F)
                .texOffs(29, 45).addBox(2.0F, 7.0F, -2.0F, 1.0F, 1.0F, 1.0F)
                .texOffs(24, 27).addBox(-2.0F, 10.0F, -4.0F, 4.0F, 3.0F, 1.0F)
                .texOffs(21, 33).addBox(-1.0F, 9.0F, -4.0F, 2.0F, 1.0F, 1.0F), PartPose.offset(1.9F, 12F, 0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack ms, VertexConsumer buffer, int light, int overlay, float r, float g, float b, float a) {
        head.visible = slot == EquipmentSlot.HEAD;
        body.visible = slot == EquipmentSlot.CHEST;
        leftArm.visible = slot == EquipmentSlot.CHEST;
        rightArm.visible = slot == EquipmentSlot.CHEST;
        leftLeg.visible = slot == EquipmentSlot.LEGS;
        rightLeg.visible = slot == EquipmentSlot.LEGS;
        bootL.visible = slot == EquipmentSlot.FEET;
        bootR.visible = slot == EquipmentSlot.FEET;
        hat.visible = false;
        if (slot == EquipmentSlot.LEGS) {
        } else {
        }
        super.renderToBuffer(ms, buffer, light, overlay, r, g, b, a);
    }

}
