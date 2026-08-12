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

public class ModelShootingGuardianArmor extends ModelArmor {

    private final ModelPart armor;
    private final ModelPart shoeRight;
    private final ModelPart shoeLeft;
    private final ModelPart helmet;
    private final ModelPart medal;
    private final ModelPart root;

    public ModelShootingGuardianArmor(EquipmentSlot slot) {
        this(createBodyLayer().bakeRoot(), slot);
    }

    public ModelShootingGuardianArmor(ModelPart root, EquipmentSlot slot) {
        super(root, slot);
        this.root = root;
        this.armor = root.getChild("armor");
        this.shoeRight = root.getChild("shoeRight");
        this.shoeLeft = root.getChild("shoeLeft");
        this.helmet = root.getChild("helmet");
        this.medal = root.getChild("armor").getChild("medal");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition part = mesh.getRoot();

        PartDefinition rightArm = part.addOrReplaceChild("rightArm", CubeListBuilder.create(), PartPose.offset(-5F, 2F, 0F));
            PartDefinition bone54 = rightArm.addOrReplaceChild("bone54", CubeListBuilder.create(), PartPose.offset(5F, 22F, 0F));
            PartDefinition bone27 = rightArm.addOrReplaceChild("bone27", CubeListBuilder.create(), PartPose.offset(5F, 22F, 0F));
                PartDefinition bone28 = bone27.addOrReplaceChild("bone28", CubeListBuilder.create(), PartPose.offset(0F, 0F, 0F));
                    PartDefinition bone29 = bone28.addOrReplaceChild("bone29", CubeListBuilder.create()
                            .texOffs(48, 43).addBox(-8.4F, -14.5F, -2.35F, 5.0F, 1.0F, 2.0F), PartPose.offset(0F, 0F, 0F));
                    PartDefinition bone30 = bone28.addOrReplaceChild("bone30", CubeListBuilder.create()
                            .texOffs(35, 27).addBox(-8.4F, -14.5F, -0.65F, 5.0F, 1.0F, 3.0F), PartPose.offset(0F, 0F, 0F));
                PartDefinition bone32 = bone27.addOrReplaceChild("bone32", CubeListBuilder.create(), PartPose.offsetAndRotation(-6F, -12F, 0F, 0F, 0F, -0.1745F));
                    PartDefinition bone33 = bone32.addOrReplaceChild("bone33", CubeListBuilder.create()
                            .texOffs(10, 16).addBox(-8.4F, -13.4F, -2.35F, 3.0F, 2.0F, 2.0F), PartPose.offset(6F, 12F, 0F));
                    PartDefinition bone34 = bone32.addOrReplaceChild("bone34", CubeListBuilder.create()
                            .texOffs(49, 14).addBox(-8.4F, -13.4F, -0.65F, 3.0F, 2.0F, 3.0F), PartPose.offset(6F, 12F, 0F));
            PartDefinition bone24 = rightArm.addOrReplaceChild("bone24", CubeListBuilder.create(), PartPose.offset(5F, 22F, 0F));
                PartDefinition bone25 = bone24.addOrReplaceChild("bone25", CubeListBuilder.create(), PartPose.offset(0F, 0F, 0F));
                    PartDefinition bone17 = bone25.addOrReplaceChild("bone17", CubeListBuilder.create()
                            .texOffs(35, 21).addBox(-8.9F, -19.0F, -0.4F, 5.0F, 3.0F, 3.0F), PartPose.offset(0F, 0F, 0F));
                    PartDefinition bone16 = bone25.addOrReplaceChild("bone16", CubeListBuilder.create()
                            .texOffs(30, 37).addBox(-8.9F, -19.0F, -2.6F, 5.0F, 3.0F, 3.0F), PartPose.offset(0F, 0F, 0F));
                PartDefinition bone26 = bone24.addOrReplaceChild("bone26", CubeListBuilder.create(), PartPose.offset(0F, 0F, 0F));
                    PartDefinition bone15 = bone26.addOrReplaceChild("bone15", CubeListBuilder.create()
                            .texOffs(23, 50).addBox(-8.81F, -22.0F, -2.35F, 4.0F, 4.0F, 2.0F), PartPose.offset(0F, 0F, 0F));
                    PartDefinition bone14 = bone26.addOrReplaceChild("bone14", CubeListBuilder.create()
                            .texOffs(25, 43).addBox(-8.8F, -22.0F, -0.65F, 4.0F, 4.0F, 3.0F), PartPose.offset(0F, 0F, 0F));
            PartDefinition bone20 = rightArm.addOrReplaceChild("bone20", CubeListBuilder.create(), PartPose.offsetAndRotation(-1F, 8F, 0F, 0F, 0F, -0.1745F));
                PartDefinition bone31 = bone20.addOrReplaceChild("bone31", CubeListBuilder.create(), PartPose.offset(0F, -1F, 0F));
                    PartDefinition bone18 = bone31.addOrReplaceChild("bone18", CubeListBuilder.create()
                            .texOffs(49, 0).addBox(-1.9132F, -1.4756F, -1.6274F, 4.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(-0.5F, -1F, 1.3F, -0.2618F, 0F, 0F));
                    PartDefinition bone19 = bone31.addOrReplaceChild("bone19", CubeListBuilder.create()
                            .texOffs(48, 38).addBox(-1.6132F, -1.4756F, -1.3726F, 4.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(-0.81F, -1F, -1.3F, 0.2618F, 0F, 0F));
                    PartDefinition bone21 = bone31.addOrReplaceChild("bone21", CubeListBuilder.create()
                            .texOffs(8, 56).addBox(-0.2F, -1.5F, -1.0F, 1.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(-2.5F, -2.5F, -0.5F, -0.7854F, 0F, 0F));
            PartDefinition bone7 = rightArm.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, -2F, 0.5F, 0F, 0F, 1.7453F));
                PartDefinition bone11 = bone7.addOrReplaceChild("bone11", CubeListBuilder.create()
                        .texOffs(38, 0).addBox(-6.0F, -23.0F, -2.51F, 3.0F, 1.0F, 5.0F), PartPose.offset(7.5F, 24F, -0.5F));
                PartDefinition bone12 = bone7.addOrReplaceChild("bone12", CubeListBuilder.create()
                        .texOffs(22, 56).addBox(-1.5F, -1.0F, 0.2F, 2.0F, 3.0F, 1.0F), PartPose.offset(4F, -1F, -3.1F));
                PartDefinition bone13 = bone7.addOrReplaceChild("bone13", CubeListBuilder.create()
                        .texOffs(52, 32).addBox(-6.0F, -26.0F, 1.4F, 3.0F, 3.0F, 1.0F), PartPose.offset(7.5F, 24F, -0.5F));
            PartDefinition bone6 = rightArm.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, -2F, 0.5F, 0F, 0F, 1.8326F));
                PartDefinition bone8 = bone6.addOrReplaceChild("bone8", CubeListBuilder.create()
                        .texOffs(43, 32).addBox(-6.0F, -23.0F, -2.5F, 2.0F, 1.0F, 5.0F), PartPose.offset(7.5F, 24F, -0.5F));
                PartDefinition bone9 = bone6.addOrReplaceChild("bone9", CubeListBuilder.create()
                        .texOffs(53, 9).addBox(1.0F, -3.0F, -3.0F, 2.0F, 4.0F, 1.0F), PartPose.offset(0F, 0F, 0F));
                PartDefinition bone10 = bone6.addOrReplaceChild("bone10", CubeListBuilder.create()
                        .texOffs(4, 53).addBox(-6.5F, -27.0F, 1.5F, 2.0F, 4.0F, 1.0F), PartPose.offset(7.5F, 24F, -0.5F));
            PartDefinition bone = rightArm.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, -2F, 0.5F, 0F, 0F, 1.1345F));
                PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create()
                        .texOffs(41, 41).addBox(-8.0F, -26.0F, -2.5F, 1.0F, 5.0F, 5.0F), PartPose.offset(7.5F, 24F, -0.5F));
                PartDefinition bone5 = bone.addOrReplaceChild("bone5", CubeListBuilder.create()
                        .texOffs(49, 49).addBox(-7.0F, -22.0F, -2.0F, 2.0F, 1.0F, 4.0F), PartPose.offset(7.5F, 24F, -0.5F));
                PartDefinition bone3 = bone.addOrReplaceChild("bone3", CubeListBuilder.create()
                        .texOffs(35, 54).addBox(0.0F, -1.5F, -3.1F, 2.0F, 4.0F, 1.0F), PartPose.offset(0F, 0F, 0F));
                PartDefinition bone4 = bone.addOrReplaceChild("bone4", CubeListBuilder.create()
                        .texOffs(48, 54).addBox(-7.5F, -25.5F, 1.6F, 2.0F, 4.0F, 1.0F), PartPose.offset(7.5F, 24F, -0.5F));
        PartDefinition leftArm = part.addOrReplaceChild("leftArm", CubeListBuilder.create(), PartPose.offset(5F, 2F, 0F));
            PartDefinition bone55 = leftArm.addOrReplaceChild("bone55", CubeListBuilder.create(), PartPose.offset(-5F, 22F, 0F));
            PartDefinition bone73 = leftArm.addOrReplaceChild("bone73", CubeListBuilder.create(), PartPose.offset(-5F, 22F, 0F));
                PartDefinition bone74 = bone73.addOrReplaceChild("bone74", CubeListBuilder.create(), PartPose.offset(0F, 0F, 0F));
                    PartDefinition bone75 = bone74.addOrReplaceChild("bone75", CubeListBuilder.create().mirror()
                            .texOffs(48, 43).addBox(3.4F, -14.5F, -2.35F, 5.0F, 1.0F, 2.0F), PartPose.offset(0F, 0F, 0F));
                    PartDefinition bone76 = bone74.addOrReplaceChild("bone76", CubeListBuilder.create().mirror()
                            .texOffs(35, 27).addBox(3.4F, -14.5F, -0.65F, 5.0F, 1.0F, 3.0F), PartPose.offset(0F, 0F, 0F));
                PartDefinition bone77 = bone73.addOrReplaceChild("bone77", CubeListBuilder.create(), PartPose.offsetAndRotation(6F, -12F, 0F, 0F, 0F, 0.1745F));
                    PartDefinition bone78 = bone77.addOrReplaceChild("bone78", CubeListBuilder.create().mirror()
                            .texOffs(10, 16).addBox(5.4F, -13.4F, -2.35F, 3.0F, 2.0F, 2.0F), PartPose.offset(-6F, 12F, 0F));
                    PartDefinition bone79 = bone77.addOrReplaceChild("bone79", CubeListBuilder.create().mirror()
                            .texOffs(49, 14).addBox(5.4F, -13.4F, -0.65F, 3.0F, 2.0F, 3.0F), PartPose.offset(-6F, 12F, 0F));
            PartDefinition bone80 = leftArm.addOrReplaceChild("bone80", CubeListBuilder.create(), PartPose.offset(-5F, 22F, 0F));
                PartDefinition bone81 = bone80.addOrReplaceChild("bone81", CubeListBuilder.create(), PartPose.offset(0F, 0F, 0F));
                    PartDefinition bone82 = bone81.addOrReplaceChild("bone82", CubeListBuilder.create().mirror()
                            .texOffs(35, 21).addBox(3.9F, -19.0F, -0.4F, 5.0F, 3.0F, 3.0F), PartPose.offset(0F, 0F, 0F));
                    PartDefinition bone83 = bone81.addOrReplaceChild("bone83", CubeListBuilder.create().mirror()
                            .texOffs(30, 37).addBox(3.9F, -19.0F, -2.6F, 5.0F, 3.0F, 3.0F), PartPose.offset(0F, 0F, 0F));
                PartDefinition bone84 = bone80.addOrReplaceChild("bone84", CubeListBuilder.create(), PartPose.offset(0F, 0F, 0F));
                    PartDefinition bone85 = bone84.addOrReplaceChild("bone85", CubeListBuilder.create().mirror()
                            .texOffs(23, 50).addBox(4.81F, -22.0F, -2.35F, 4.0F, 4.0F, 2.0F), PartPose.offset(0F, 0F, 0F));
                    PartDefinition bone86 = bone84.addOrReplaceChild("bone86", CubeListBuilder.create().mirror()
                            .texOffs(25, 43).addBox(4.8F, -22.0F, -0.65F, 4.0F, 4.0F, 3.0F), PartPose.offset(0F, 0F, 0F));
            PartDefinition bone87 = leftArm.addOrReplaceChild("bone87", CubeListBuilder.create(), PartPose.offsetAndRotation(1F, 8F, 0F, 0F, 0F, 0.1745F));
                PartDefinition bone88 = bone87.addOrReplaceChild("bone88", CubeListBuilder.create(), PartPose.offset(0F, -1F, 0F));
                    PartDefinition bone89 = bone88.addOrReplaceChild("bone89", CubeListBuilder.create().mirror()
                            .texOffs(49, 0).addBox(-2.0868F, -1.4756F, -1.6274F, 4.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(0.5F, -1F, 1.3F, -0.2618F, 0F, 0F));
                    PartDefinition bone90 = bone88.addOrReplaceChild("bone90", CubeListBuilder.create().mirror()
                            .texOffs(48, 38).addBox(-2.3868F, -1.4756F, -1.3726F, 4.0F, 2.0F, 3.0F), PartPose.offsetAndRotation(0.81F, -1F, -1.3F, 0.2618F, 0F, 0F));
                    PartDefinition bone91 = bone88.addOrReplaceChild("bone91", CubeListBuilder.create().mirror()
                            .texOffs(8, 56).addBox(-0.8F, -1.5F, -1.0F, 1.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(2.5F, -2.5F, -0.5F, -0.7854F, 0F, 0F));
            PartDefinition bone92 = leftArm.addOrReplaceChild("bone92", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, -2F, 0.5F, 0F, 0F, -1.7453F));
                PartDefinition bone93 = bone92.addOrReplaceChild("bone93", CubeListBuilder.create().mirror()
                        .texOffs(38, 0).addBox(3.0F, -23.0F, -2.51F, 3.0F, 1.0F, 5.0F), PartPose.offset(-7.5F, 24F, -0.5F));
                PartDefinition bone94 = bone92.addOrReplaceChild("bone94", CubeListBuilder.create().mirror()
                        .texOffs(22, 56).addBox(-0.5F, -1.0F, 0.2F, 2.0F, 3.0F, 1.0F), PartPose.offset(-4F, -1F, -3.1F));
                PartDefinition bone95 = bone92.addOrReplaceChild("bone95", CubeListBuilder.create().mirror()
                        .texOffs(52, 32).addBox(3.0F, -26.0F, 1.4F, 3.0F, 3.0F, 1.0F), PartPose.offset(-7.5F, 24F, -0.5F));
            PartDefinition bone96 = leftArm.addOrReplaceChild("bone96", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, -2F, 0.5F, 0F, 0F, -1.8326F));
                PartDefinition bone97 = bone96.addOrReplaceChild("bone97", CubeListBuilder.create().mirror()
                        .texOffs(43, 32).addBox(4.0F, -23.0F, -2.5F, 2.0F, 1.0F, 5.0F), PartPose.offset(-7.5F, 24F, -0.5F));
                PartDefinition bone98 = bone96.addOrReplaceChild("bone98", CubeListBuilder.create().mirror()
                        .texOffs(53, 9).addBox(-3.0F, -3.0F, -3.0F, 2.0F, 4.0F, 1.0F), PartPose.offset(0F, 0F, 0F));
                PartDefinition bone99 = bone96.addOrReplaceChild("bone99", CubeListBuilder.create().mirror()
                        .texOffs(4, 53).addBox(4.5F, -27.0F, 1.5F, 2.0F, 4.0F, 1.0F), PartPose.offset(-7.5F, 24F, -0.5F));
            PartDefinition bone100 = leftArm.addOrReplaceChild("bone100", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, -2F, 0.5F, 0F, 0F, -1.1345F));
                PartDefinition bone101 = bone100.addOrReplaceChild("bone101", CubeListBuilder.create().mirror()
                        .texOffs(41, 41).addBox(7.0F, -26.0F, -2.5F, 1.0F, 5.0F, 5.0F), PartPose.offset(-7.5F, 24F, -0.5F));
                PartDefinition bone102 = bone100.addOrReplaceChild("bone102", CubeListBuilder.create().mirror()
                        .texOffs(49, 49).addBox(5.0F, -22.0F, -2.0F, 2.0F, 1.0F, 4.0F), PartPose.offset(-7.5F, 24F, -0.5F));
                PartDefinition bone103 = bone100.addOrReplaceChild("bone103", CubeListBuilder.create().mirror()
                        .texOffs(35, 54).addBox(-2.0F, -1.5F, -3.1F, 2.0F, 4.0F, 1.0F), PartPose.offset(0F, 0F, 0F));
                PartDefinition bone104 = bone100.addOrReplaceChild("bone104", CubeListBuilder.create().mirror()
                        .texOffs(48, 54).addBox(5.5F, -25.5F, 1.6F, 2.0F, 4.0F, 1.0F), PartPose.offset(-7.5F, 24F, -0.5F));
        PartDefinition armor = part.addOrReplaceChild("armor", CubeListBuilder.create(), PartPose.offset(0F, 0F, 0F));
            PartDefinition bone36 = armor.addOrReplaceChild("bone36", CubeListBuilder.create()
                    .texOffs(35, 12).addBox(-1.5F, -0.5F, -0.6F, 8.0F, 1.0F, 1.0F), PartPose.offset(-2.5F, 1F, -2F));
            PartDefinition bone48 = armor.addOrReplaceChild("bone48", CubeListBuilder.create()
                    .texOffs(35, 9).addBox(-1.5F, -0.5F, 3.6F, 8.0F, 2.0F, 1.0F), PartPose.offset(-2.5F, 1F, -2F));
            PartDefinition bone37 = armor.addOrReplaceChild("bone37", CubeListBuilder.create()
                    .texOffs(53, 19).addBox(-1.5F, 0.5F, -0.5F, 2.0F, 1.0F, 1.0F), PartPose.offset(-2.5F, 1F, -2F));
            PartDefinition bone38 = armor.addOrReplaceChild("bone38", CubeListBuilder.create()
                    .texOffs(43, 38).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F), PartPose.offset(3F, 2F, -2F));
            PartDefinition bone39 = armor.addOrReplaceChild("bone39", CubeListBuilder.create()
                    .texOffs(28, 56).addBox(-1.5F, -1.5F, -0.5F, 2.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0F, 2F, -2F, 0F, 0F, 0.7854F));
            PartDefinition bone40 = armor.addOrReplaceChild("bone40", CubeListBuilder.create()
                    .texOffs(0, 44).addBox(-2.0F, -2.0F, -0.41F, 3.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(0F, 2F, -2F, 0F, 0F, 0.7854F));
            PartDefinition bone41 = armor.addOrReplaceChild("bone41", CubeListBuilder.create()
                    .texOffs(13, 43).addBox(0.0F, 0.0F, -0.4F, 5.0F, 8.0F, 1.0F), PartPose.offset(-2.5F, 1F, -2F));
            PartDefinition bone47 = armor.addOrReplaceChild("bone47", CubeListBuilder.create()
                    .texOffs(15, 15).addBox(-2.0F, 0.0F, -0.38F, 5.0F, 11.0F, 5.0F), PartPose.offset(-2.5F, 1F, -2F));
            PartDefinition bone49 = armor.addOrReplaceChild("bone49", CubeListBuilder.create()
                    .texOffs(0, 0).addBox(2.0F, -0.01F, -0.37F, 5.0F, 11.0F, 5.0F), PartPose.offset(-2.5F, 1F, -2F));
            PartDefinition bone46 = armor.addOrReplaceChild("bone46", CubeListBuilder.create()
                    .texOffs(54, 54).addBox(1.0F, -2.0F, -0.5F, 2.0F, 3.0F, 1.0F)
                    .texOffs(51, 24).addBox(-2.0F, 1.0F, -0.5F, 3.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(0F, 9F, -1.89F, 0F, 0F, 0.7854F));
            PartDefinition bone42 = armor.addOrReplaceChild("bone42", CubeListBuilder.create()
                    .texOffs(20, 9).addBox(-1.0F, 1.0F, -0.41F, 1.0F, 3.0F, 1.0F), PartPose.offset(-2.5F, 1F, -2F));
            PartDefinition bone43 = armor.addOrReplaceChild("bone43", CubeListBuilder.create()
                    .texOffs(0, 16).addBox(5.0F, 1.0F, -0.41F, 1.0F, 3.0F, 1.0F), PartPose.offset(-2.5F, 1F, -2F));
            PartDefinition bone44 = armor.addOrReplaceChild("bone44", CubeListBuilder.create()
                    .texOffs(0, 53).addBox(-1.5F, -4.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(3.5F, 6F, -1.92F, 0F, 0F, 0.1745F));
            PartDefinition bone45 = armor.addOrReplaceChild("bone45", CubeListBuilder.create()
                    .texOffs(18, 52).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 8.0F, 1.0F), PartPose.offsetAndRotation(-2.5F, 6F, -1.92F, 0F, 0F, -0.1745F));
            PartDefinition bone50 = armor.addOrReplaceChild("bone50", CubeListBuilder.create()
                    .texOffs(44, 6).addBox(-1.0F, 9.1F, -0.4F, 7.0F, 2.0F, 1.0F), PartPose.offset(-2.5F, 1F, -2F));
            PartDefinition medal = armor.addOrReplaceChild("medal", CubeListBuilder.create(), PartPose.offset(0F, 0F, 0F));
                PartDefinition rightMedal = medal.addOrReplaceChild("rightMedal", CubeListBuilder.create(), PartPose.offset(0F, 24F, 0F));
                    PartDefinition bone35 = rightMedal.addOrReplaceChild("bone35", CubeListBuilder.create()
                            .texOffs(53, 46).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(-4F, -21F, -2.5F, 0F, 0F, 0.6981F));
                    PartDefinition bone51 = rightMedal.addOrReplaceChild("bone51", CubeListBuilder.create()
                            .texOffs(14, 52).addBox(-1.0F, -5.0F, -0.3F, 2.0F, 10.0F, 0.0F), PartPose.offsetAndRotation(-4F, -16F, -2.8F, -0.0873F, 0F, 0F));
                PartDefinition leftMedal = medal.addOrReplaceChild("leftMedal", CubeListBuilder.create(), PartPose.offset(0F, 24F, 0F));
                    PartDefinition bone52 = leftMedal.addOrReplaceChild("bone52", CubeListBuilder.create()
                            .texOffs(27, 31).addBox(4.0F, -7.0F, -0.5F, 2.0F, 2.0F, 1.0F), PartPose.offsetAndRotation(-4F, -21F, -2.5F, 0F, 0F, 0.8727F));
                    PartDefinition bone53 = leftMedal.addOrReplaceChild("bone53", CubeListBuilder.create()
                            .texOffs(44, 51).addBox(-1.1F, -5.0F, -0.3F, 2.0F, 10.0F, 0.0F), PartPose.offsetAndRotation(4F, -16F, -2.8F, -0.0873F, 0F, 0F));
        PartDefinition rightLeg = part.addOrReplaceChild("rightLeg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12F, 0F));
            PartDefinition bone56 = rightLeg.addOrReplaceChild("bone56", CubeListBuilder.create()
                    .texOffs(36, 48).addBox(-0.5F, -2.5F, -1.8F, 1.0F, 3.0F, 3.0F), PartPose.offsetAndRotation(-2.6F, 1.5F, -0.5F, -0.7854F, 0F, 0.0873F));
            PartDefinition bone57 = rightLeg.addOrReplaceChild("bone57", CubeListBuilder.create()
                    .texOffs(20, 9).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 1.0F, 5.0F), PartPose.offsetAndRotation(-0.1F, 1.5F, 0F, 0F, 0F, 0.0873F));
            PartDefinition bone60 = rightLeg.addOrReplaceChild("bone60", CubeListBuilder.create()
                    .texOffs(36, 43).addBox(-3.5F, 0.8F, -0.1F, 4.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(0.4F, 4.5F, -2.4F, 0.1745F, -0.1745F, 0.6981F));
            PartDefinition bone61 = rightLeg.addOrReplaceChild("bone61", CubeListBuilder.create()
                    .texOffs(0, 0).addBox(0.1F, -2.7F, -0.1F, 1.0F, 4.0F, 1.0F), PartPose.offsetAndRotation(0.4F, 4.5F, -2.4F, 0.1745F, -0.1745F, 0.8727F));
            PartDefinition bone58 = rightLeg.addOrReplaceChild("bone58", CubeListBuilder.create()
                    .texOffs(15, 0).addBox(-2.0F, -2.0F, -0.1F, 4.0F, 4.0F, 1.0F), PartPose.offsetAndRotation(-0.1F, 2F, -2.5F, 0.1745F, -0.1745F, 0.7854F));
            PartDefinition bone59 = rightLeg.addOrReplaceChild("bone59", CubeListBuilder.create()
                    .texOffs(30, 15).addBox(0.0F, 0.0F, -0.4F, 3.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(-0.1F, 2F, -2.5F, 0.0873F, -0.0873F, 0.7854F));
            PartDefinition bone62 = rightLeg.addOrReplaceChild("bone62", CubeListBuilder.create()
                    .texOffs(0, 30).addBox(-1.0F, -1.5F, -0.14F, 2.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(-0.1F, 6.5F, -2.35F, -0.1745F, 0F, 0F));
            PartDefinition bone63 = rightLeg.addOrReplaceChild("bone63", CubeListBuilder.create()
                    .texOffs(14, 31).addBox(-4.5F, -10.0F, -2.3F, 5.0F, 9.0F, 3.0F), PartPose.offset(1.9F, 12F, 0F));
            PartDefinition bone66 = rightLeg.addOrReplaceChild("bone66", CubeListBuilder.create()
                    .texOffs(48, 21).addBox(-4.5F, -7.0F, 0.7F, 5.0F, 1.0F, 2.0F), PartPose.offset(1.9F, 12F, 0F));
            PartDefinition bone64 = rightLeg.addOrReplaceChild("bone64", CubeListBuilder.create()
                    .texOffs(0, 30).addBox(-4.3F, -11.4F, -1.7F, 3.0F, 10.0F, 4.0F), PartPose.offset(1.9F, 12F, 0F));
            PartDefinition bone65 = rightLeg.addOrReplaceChild("bone65", CubeListBuilder.create()
                    .texOffs(0, 16).addBox(-2.7F, -11.41F, -1.71F, 3.0F, 10.0F, 4.0F), PartPose.offset(1.9F, 12F, 0F));
        PartDefinition leftLeg = part.addOrReplaceChild("leftLeg", CubeListBuilder.create(), PartPose.offset(1.9F, 12F, 0F));
            PartDefinition bone105 = leftLeg.addOrReplaceChild("bone105", CubeListBuilder.create().mirror()
                    .texOffs(36, 48).addBox(-0.5F, -2.5F, -1.8F, 1.0F, 3.0F, 3.0F), PartPose.offsetAndRotation(2.6F, 1.5F, -0.5F, -0.7854F, 0F, -0.0873F));
            PartDefinition bone106 = leftLeg.addOrReplaceChild("bone106", CubeListBuilder.create().mirror()
                    .texOffs(20, 9).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 1.0F, 5.0F), PartPose.offsetAndRotation(0.1F, 1.5F, 0F, 0F, 0F, -0.0873F));
            PartDefinition bone107 = leftLeg.addOrReplaceChild("bone107", CubeListBuilder.create().mirror()
                    .texOffs(36, 43).addBox(-0.5F, 0.8F, -0.1F, 4.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(-0.4F, 4.5F, -2.4F, 0.1745F, 0.1745F, -0.6981F));
            PartDefinition bone108 = leftLeg.addOrReplaceChild("bone108", CubeListBuilder.create().mirror()
                    .texOffs(0, 0).addBox(-1.1F, -2.7F, -0.1F, 1.0F, 4.0F, 1.0F), PartPose.offsetAndRotation(-0.4F, 4.5F, -2.4F, 0.1745F, 0.1745F, -0.8727F));
            PartDefinition bone109 = leftLeg.addOrReplaceChild("bone109", CubeListBuilder.create().mirror()
                    .texOffs(15, 0).addBox(-2.0F, -2.0F, -0.1F, 4.0F, 4.0F, 1.0F), PartPose.offsetAndRotation(0.1F, 2F, -2.5F, 0.1745F, 0.1745F, -0.7854F));
            PartDefinition bone110 = leftLeg.addOrReplaceChild("bone110", CubeListBuilder.create().mirror()
                    .texOffs(30, 15).addBox(-3.0F, 0.0F, -0.4F, 3.0F, 3.0F, 1.0F), PartPose.offsetAndRotation(0.1F, 2F, -2.5F, 0.0873F, 0.0873F, -0.7854F));
            PartDefinition bone111 = leftLeg.addOrReplaceChild("bone111", CubeListBuilder.create().mirror()
                    .texOffs(0, 30).addBox(-1.0F, -1.5F, -0.14F, 2.0F, 3.0F, 0.0F), PartPose.offsetAndRotation(0.1F, 6.5F, -2.35F, -0.1745F, 0F, 0F));
            PartDefinition bone112 = leftLeg.addOrReplaceChild("bone112", CubeListBuilder.create().mirror()
                    .texOffs(14, 31).addBox(-0.5F, -10.0F, -2.3F, 5.0F, 9.0F, 3.0F), PartPose.offset(-1.9F, 12F, 0F));
            PartDefinition bone113 = leftLeg.addOrReplaceChild("bone113", CubeListBuilder.create().mirror()
                    .texOffs(48, 21).addBox(-0.5F, -7.0F, 0.7F, 5.0F, 1.0F, 2.0F), PartPose.offset(-1.9F, 12F, 0F));
            PartDefinition bone114 = leftLeg.addOrReplaceChild("bone114", CubeListBuilder.create().mirror()
                    .texOffs(0, 30).addBox(1.3F, -11.4F, -1.7F, 3.0F, 10.0F, 4.0F), PartPose.offset(-1.9F, 12F, 0F));
            PartDefinition bone115 = leftLeg.addOrReplaceChild("bone115", CubeListBuilder.create().mirror()
                    .texOffs(0, 16).addBox(-0.3F, -11.41F, -1.71F, 3.0F, 10.0F, 4.0F), PartPose.offset(-1.9F, 12F, 0F));
        PartDefinition shoeRight = part.addOrReplaceChild("shoeRight", CubeListBuilder.create(), PartPose.offset(-1.9F, 12F, 0F));
            PartDefinition bone67 = shoeRight.addOrReplaceChild("bone67", CubeListBuilder.create()
                    .texOffs(20, 0).addBox(-5.0F, -2.2F, -3.0F, 6.0F, 3.0F, 6.0F), PartPose.offset(1.9F, 12F, 0F));
            PartDefinition bone70 = shoeRight.addOrReplaceChild("bone70", CubeListBuilder.create()
                    .texOffs(35, 15).addBox(-5.3F, -1.2F, -3.0F, 5.0F, 2.0F, 4.0F), PartPose.offsetAndRotation(1.9F, 12F, 0F, 0.0873F, 0F, 0F));
            PartDefinition bone71 = shoeRight.addOrReplaceChild("bone71", CubeListBuilder.create()
                    .texOffs(30, 31).addBox(-3.7F, -1.19F, -3.2F, 5.0F, 2.0F, 4.0F), PartPose.offsetAndRotation(1.9F, 12F, 0F, 0.0873F, 0F, 0F));
            PartDefinition bone68 = shoeRight.addOrReplaceChild("bone68", CubeListBuilder.create()
                    .texOffs(0, 48).addBox(0.01F, -0.5F, -3.5F, 3.0F, 1.0F, 4.0F), PartPose.offsetAndRotation(-0.1F, 9.5F, 2.5F, 0.1745F, 0F, 0F));
            PartDefinition bone69 = shoeRight.addOrReplaceChild("bone69", CubeListBuilder.create()
                    .texOffs(47, 27).addBox(-3.01F, -0.5F, -3.5F, 3.0F, 1.0F, 4.0F), PartPose.offsetAndRotation(-0.1F, 9.5F, 2.5F, 0.1745F, 0F, 0F));
            PartDefinition bone72 = shoeRight.addOrReplaceChild("bone72", CubeListBuilder.create()
                    .texOffs(10, 30).addBox(-3.8F, 0.5F, -3.5F, 1.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(-0.1F, 9.5F, 2.5F, -0.3491F, 0F, 0F));
        PartDefinition shoeLeft = part.addOrReplaceChild("shoeLeft", CubeListBuilder.create(), PartPose.offset(1.9F, 12F, 0F));
            PartDefinition bone116 = shoeLeft.addOrReplaceChild("bone116", CubeListBuilder.create().mirror()
                    .texOffs(20, 0).addBox(-1.0F, -2.2F, -3.0F, 6.0F, 3.0F, 6.0F), PartPose.offset(-1.9F, 12F, 0F));
            PartDefinition bone117 = shoeLeft.addOrReplaceChild("bone117", CubeListBuilder.create().mirror()
                    .texOffs(35, 15).addBox(0.3F, -1.2F, -3.0F, 5.0F, 2.0F, 4.0F), PartPose.offsetAndRotation(-1.9F, 12F, 0F, 0.0873F, 0F, 0F));
            PartDefinition bone118 = shoeLeft.addOrReplaceChild("bone118", CubeListBuilder.create().mirror()
                    .texOffs(30, 31).addBox(-1.3F, -1.19F, -3.2F, 5.0F, 2.0F, 4.0F), PartPose.offsetAndRotation(-1.9F, 12F, 0F, 0.0873F, 0F, 0F));
            PartDefinition bone119 = shoeLeft.addOrReplaceChild("bone119", CubeListBuilder.create().mirror()
                    .texOffs(0, 48).addBox(-3.01F, -0.5F, -3.5F, 3.0F, 1.0F, 4.0F), PartPose.offsetAndRotation(0.1F, 9.5F, 2.5F, 0.1745F, 0F, 0F));
            PartDefinition bone120 = shoeLeft.addOrReplaceChild("bone120", CubeListBuilder.create().mirror()
                    .texOffs(47, 27).addBox(0.01F, -0.5F, -3.5F, 3.0F, 1.0F, 4.0F), PartPose.offsetAndRotation(0.1F, 9.5F, 2.5F, 0.1745F, 0F, 0F));
            PartDefinition bone121 = shoeLeft.addOrReplaceChild("bone121", CubeListBuilder.create().mirror()
                    .texOffs(10, 30).addBox(2.8F, 0.5F, -3.5F, 1.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(0.1F, 9.5F, 2.5F, -0.3491F, 0F, 0F));
        PartDefinition helmet = part.addOrReplaceChild("helmet", CubeListBuilder.create(), PartPose.offset(0F, 0F, 0F));
            PartDefinition bone233 = helmet.addOrReplaceChild("bone233", CubeListBuilder.create()
                    .texOffs(22, 25).addBox(-3.0F, -2.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0F, 0F, -5F, 0F, 0.2618F, 0F));
            PartDefinition bone244 = helmet.addOrReplaceChild("bone244", CubeListBuilder.create()
                    .texOffs(19, 0).addBox(0.0F, -2.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0F, 0F, -5F, 0F, -0.2618F, 0F));
            PartDefinition bone344 = helmet.addOrReplaceChild("bone344", CubeListBuilder.create()
                    .texOffs(0, 20).addBox(-5.0F, -31.0F, -4.3F, 10.0F, 7.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0F, 24F, 0F));
            PartDefinition bone544 = helmet.addOrReplaceChild("bone544", CubeListBuilder.create()
                    .texOffs(19, 0).addBox(5.0F, -29.0F, -4.5F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0F, 24F, 0F));
            PartDefinition bone444 = helmet.addOrReplaceChild("bone444", CubeListBuilder.create()
                    .texOffs(22, 17).addBox(-5.0F, -29.0F, -4.5F, 0.0F, 5.0F, 3.0F), PartPose.offset(0F, 24F, 0F));
            PartDefinition bone611 = helmet.addOrReplaceChild("bone611", CubeListBuilder.create()
                    .texOffs(6, 10).addBox(0.5F, -4.0F, 4.49F, 1.0F, 8.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-5.8F, -4F, 0F, 0F, 0F, 0.1745F));
            PartDefinition bone1322 = helmet.addOrReplaceChild("bone1322", CubeListBuilder.create()
                    .texOffs(0, 0).addBox(0.5F, -4.0F, -4.48F, 3.0F, 8.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-5.8F, -4F, 0F, 0F, 0F, 0.1745F));
            PartDefinition bone1111 = helmet.addOrReplaceChild("bone1111", CubeListBuilder.create()
                    .texOffs(19, 19).addBox(0.5F, -4.0F, -4.49F, 1.0F, 8.0F, 9.0F), PartPose.offsetAndRotation(-5.8F, -4F, 0F, 0F, 0F, 0.1745F));
            PartDefinition bone1923 = helmet.addOrReplaceChild("bone1923", CubeListBuilder.create()
                    .texOffs(30, 20).addBox(-0.9F, -1.1F, -2.19F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-5.8F, -4F, 0F, 0F, -0.7854F, 0.1745F));
            PartDefinition bone2034 = helmet.addOrReplaceChild("bone2034", CubeListBuilder.create()
                    .texOffs(12, 28).addBox(-0.1F, -1.1F, -2.19F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(5.8F, -4F, 0F, 0F, 0.7854F, -0.1745F));
            PartDefinition bone1733 = helmet.addOrReplaceChild("bone1733", CubeListBuilder.create()
                    .texOffs(0, 34).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-5.1F, -4F, -1.5F, -0.6981F, 0F, 0.1745F));
            PartDefinition bone1821 = helmet.addOrReplaceChild("bone1821", CubeListBuilder.create()
                    .texOffs(32, 24).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(5.1F, -4F, -1.5F, -0.6981F, 0F, -0.1745F));
            PartDefinition bone721 = helmet.addOrReplaceChild("bone721", CubeListBuilder.create()
                    .texOffs(19, 1).addBox(-1.5F, -4.0F, -4.49F, 1.0F, 8.0F, 9.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(5.8F, -4F, 0F, 0F, 0F, -0.1745F));
            PartDefinition bone1021 = helmet.addOrReplaceChild("bone1021", CubeListBuilder.create()
                    .texOffs(6, 0).addBox(-1.5F, -4.0F, 4.49F, 1.0F, 8.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(5.8F, -4F, 0F, 0F, 0F, -0.1745F));
            PartDefinition bone1221 = helmet.addOrReplaceChild("bone1221", CubeListBuilder.create()
                    .texOffs(0, 10).addBox(-3.5F, -4.0F, -4.48F, 3.0F, 8.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(5.8F, -4F, 0F, 0F, 0F, -0.1745F));
            PartDefinition bone834 = helmet.addOrReplaceChild("bone834", CubeListBuilder.create()
                    .texOffs(0, 10).addBox(-2.3F, -0.5F, -4.5F, 5.0F, 1.0F, 9.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.5F, -7.5F, 0.02F, 0F, 0F, -0.2618F));
            PartDefinition bone1555 = helmet.addOrReplaceChild("bone1555", CubeListBuilder.create()
                    .texOffs(0, 0).addBox(-2.7F, -0.5F, -4.5F, 5.0F, 1.0F, 9.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.5F, -7.5F, 0.02F, 0F, 0F, 0.2618F));
            PartDefinition bone966 = helmet.addOrReplaceChild("bone966", CubeListBuilder.create()
                    .texOffs(30, 0).addBox(-10.8F, -4.0F, 3.5F, 10.0F, 8.0F, 1.0F), PartPose.offset(5.8F, -4F, 0F));
            PartDefinition bone1477 = helmet.addOrReplaceChild("bone1477", CubeListBuilder.create()
                    .texOffs(30, 18).addBox(-10.8F, -4.0F, -4.5F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(5.8F, -4F, 0F));
            PartDefinition bone1688 = helmet.addOrReplaceChild("bone1688", CubeListBuilder.create()
                    .texOffs(0, 28).addBox(-1.5F, -4.5F, -0.01F, 6.0F, 6.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0F, -5F, -4.5F, 0F, 0F, -0.7854F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack ms, VertexConsumer buffer, int light, int overlay, float r, float g, float b, float a) {
        armor.visible = slot == EquipmentSlot.CHEST;
        medal.visible = slot == EquipmentSlot.CHEST;
        rightArm.visible = slot == EquipmentSlot.CHEST;
        leftArm.visible = slot == EquipmentSlot.CHEST;
        rightLeg.visible = slot == EquipmentSlot.LEGS;
        leftLeg.visible = slot == EquipmentSlot.LEGS;
        shoeLeft.visible = slot == EquipmentSlot.FEET;
        shoeRight.visible = slot == EquipmentSlot.FEET;
        hat.visible = false;
        helmet.visible = slot == EquipmentSlot.HEAD;
        if (slot == EquipmentSlot.LEGS) {
        } else {
        }
        super.renderToBuffer(ms, buffer, light, overlay, r, g, b, a);
    }

}
