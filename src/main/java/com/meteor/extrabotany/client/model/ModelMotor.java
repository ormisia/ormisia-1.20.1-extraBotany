package com.meteor.extrabotany.client.model;

import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ModelMotor extends HierarchicalModel<Entity> {

    private final ModelPart all;
    public ModelPart tirefront3;
    public ModelPart tireback;

    public ModelMotor() {
        this(createBodyLayer().bakeRoot());
    }

    public ModelMotor(ModelPart root) {
        this.all = root.getChild("all");
        this.tirefront3 = this.all.getChild("tirefront3");
        this.tireback = this.all.getChild("tireback");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();

        PartDefinition all = part.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 16.0F));

        PartDefinition tirefront3 = all.addOrReplaceChild("tirefront3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -33.0F));

        PartDefinition bone9 = tirefront3.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 12.0F));

        PartDefinition bone10 = tirefront3.addOrReplaceChild("bone10", CubeListBuilder.create()
                .texOffs(18, 51).addBox(2.01F, -2.0F, -14.0F, 1.0F, 4.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 12.0F));

        PartDefinition bone11 = tirefront3.addOrReplaceChild("bone11", CubeListBuilder.create()
                .texOffs(0, 51).addBox(-3.01F, -2.0F, -14.0F, 1.0F, 4.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 12.0F));

        PartDefinition bone12 = tirefront3.addOrReplaceChild("bone12", CubeListBuilder.create()
                .texOffs(68, 96).addBox(-3.0F, -5.0F, -14.0F, 6.0F, 10.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 12.0F));

        PartDefinition bone13 = tirefront3.addOrReplaceChild("bone13", CubeListBuilder.create()
                .texOffs(48, 92).addBox(-3.0F, 3.4853F, -10.4853F, 6.0F, 10.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 12.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition bone14 = tirefront3.addOrReplaceChild("bone14", CubeListBuilder.create()
                .texOffs(90, 45).addBox(-3.0F, 7.0F, -2.0F, 6.0F, 10.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 12.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition bone15 = tirefront3.addOrReplaceChild("bone15", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.0F, -13.4853F, -10.4853F, 6.0F, 10.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 12.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition side1 = all.addOrReplaceChild("side1", CubeListBuilder.create(), PartPose.offset(1.0F, 5.0F, -16.0F));

        PartDefinition bone18 = side1.addOrReplaceChild("bone18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone19 = side1.addOrReplaceChild("bone19", CubeListBuilder.create()
                .texOffs(50, 27).addBox(-1.5F, -1.0F, -3.0F, 1.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(4.0F, -5.0F, -15.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition bone21 = side1.addOrReplaceChild("bone21", CubeListBuilder.create()
                .texOffs(46, 38).addBox(3.0F, -7.5F, -16.0F, 1.0F, 2.0F, 16.0F)
                .texOffs(18, 45).addBox(3.0F, -4.5F, -16.0F, 1.0F, 2.0F, 16.0F)
                .texOffs(33, 0).addBox(3.5F, -6.0F, -17.0F, 1.0F, 2.0F, 18.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone25 = side1.addOrReplaceChild("bone25", CubeListBuilder.create()
                .texOffs(24, 47).addBox(-1.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(4.0F, -5.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition bone29 = side1.addOrReplaceChild("bone29", CubeListBuilder.create()
                .texOffs(66, 32).addBox(2.0F, -9.0F, -11.0F, 1.0F, 5.0F, 12.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone31 = side1.addOrReplaceChild("bone31", CubeListBuilder.create()
                .texOffs(59, 75).addBox(1.99F, -7.0F, -11.0F, 1.0F, 6.0F, 11.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition bone30 = side1.addOrReplaceChild("bone30", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone41 = side1.addOrReplaceChild("bone41", CubeListBuilder.create()
                .texOffs(0, 90).addBox(2.0F, -7.0F, 6.0F, 1.0F, 2.0F, 10.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition bone43 = side1.addOrReplaceChild("bone43", CubeListBuilder.create()
                .texOffs(26, 26).addBox(2.5F, 0.0F, 9.0F, 1.0F, 2.0F, 7.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, -0.0873F, 0.0F));

        PartDefinition bone47 = side1.addOrReplaceChild("bone47", CubeListBuilder.create()
                .texOffs(100, 79).addBox(2.5F, -10.0F, -10.0F, 1.0F, 2.0F, 9.0F), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone48 = side1.addOrReplaceChild("bone48", CubeListBuilder.create()
                .texOffs(26, 89).addBox(1.5F, -11.0F, -11.0F, 1.0F, 2.0F, 10.0F), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.1745F, -0.0873F, 0.0F));

        PartDefinition centerfront = all.addOrReplaceChild("centerfront", CubeListBuilder.create()
                .texOffs(0, 32).addBox(-3.0F, -11.5F, -17.0F, 6.0F, 1.0F, 2.0F)
                .texOffs(36, 45).addBox(-3.0F, -11.5F, 13.0F, 6.0F, 1.0F, 6.0F)
                .texOffs(0, 0).addBox(-3.0F, -5.0F, -11.0F, 6.0F, 2.0F, 21.0F)
                .texOffs(44, 38).addBox(-4.0F, -5.5F, -17.5F, 8.0F, 1.0F, 1.0F)
                .texOffs(0, 35).addBox(-3.5F, -5.5F, 15.5F, 7.0F, 1.0F, 1.0F), PartPose.offset(0.0F, 5.0F, -16.0F));

        PartDefinition bone49 = centerfront.addOrReplaceChild("bone49", CubeListBuilder.create()
                .texOffs(26, 23).addBox(-5.0F, -10.5F, -9.5F, 10.0F, 1.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition bone24 = centerfront.addOrReplaceChild("bone24", CubeListBuilder.create()
                .texOffs(80, 32).addBox(-6.0F, -1.5F, -5.0F, 6.0F, 1.0F, 8.0F), PartPose.offsetAndRotation(3.0F, -7.0F, -12.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition bone26 = centerfront.addOrReplaceChild("bone26", CubeListBuilder.create()
                .texOffs(81, 11).addBox(-3.0F, -14.0F, -7.5F, 6.0F, 3.0F, 7.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition bone27 = centerfront.addOrReplaceChild("bone27", CubeListBuilder.create()
                .texOffs(102, 35).addBox(-2.5F, -10.0F, -9.0F, 5.0F, 3.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition bone28 = centerfront.addOrReplaceChild("bone28", CubeListBuilder.create()
                .texOffs(0, 23).addBox(-2.5F, -9.0F, -11.0F, 5.0F, 4.0F, 16.0F)
                .texOffs(48, 75).addBox(-2.0F, -9.5F, -2.0F, 4.0F, 1.0F, 7.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone32 = centerfront.addOrReplaceChild("bone32", CubeListBuilder.create()
                .texOffs(85, 77).addBox(-3.0F, -11.0F, 2.0F, 6.0F, 5.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition bone33 = centerfront.addOrReplaceChild("bone33", CubeListBuilder.create()
                .texOffs(0, 63).addBox(-2.5F, -7.0F, 8.0F, 5.0F, 3.0F, 11.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition bone46 = bone33.addOrReplaceChild("bone46", CubeListBuilder.create()
                .texOffs(0, 77).addBox(2.0F, -4.5F, 12.0F, 1.0F, 2.0F, 11.0F)
                .texOffs(77, 49).addBox(-3.0F, -4.5F, 12.0F, 1.0F, 2.0F, 11.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition bone39 = centerfront.addOrReplaceChild("bone39", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition bone45 = bone39.addOrReplaceChild("bone45", CubeListBuilder.create()
                .texOffs(79, 64).addBox(-3.0F, -4.5F, 11.0F, 1.0F, 2.0F, 11.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0524F, 0.0F));

        PartDefinition bone44 = bone39.addOrReplaceChild("bone44", CubeListBuilder.create()
                .texOffs(72, 81).addBox(2.0F, -4.5F, 11.0F, 1.0F, 2.0F, 11.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.0524F, 0.0F));

        PartDefinition bone40 = centerfront.addOrReplaceChild("bone40", CubeListBuilder.create()
                .texOffs(72, 0).addBox(-3.0F, 0.0F, 9.0F, 6.0F, 2.0F, 9.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone50 = centerfront.addOrReplaceChild("bone50", CubeListBuilder.create()
                .texOffs(0, 14).addBox(-2.5F, -11.0F, -12.0F, 5.0F, 2.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition centerback = all.addOrReplaceChild("centerback", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, -16.0F));

        PartDefinition bone35 = centerback.addOrReplaceChild("bone35", CubeListBuilder.create()
                .texOffs(0, 43).addBox(3.0F, -4.0F, 4.0F, 2.0F, 2.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone34 = centerback.addOrReplaceChild("bone34", CubeListBuilder.create()
                .texOffs(33, 9).addBox(-5.0F, -4.0F, 4.0F, 2.0F, 2.0F, 6.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bone42 = centerback.addOrReplaceChild("bone42", CubeListBuilder.create()
                .texOffs(43, 9).addBox(-5.0F, -9.0F, 3.0F, 2.0F, 3.0F, 2.0F)
                .texOffs(52, 21).addBox(3.0F, -9.0F, 3.0F, 2.0F, 3.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition bone36 = centerback.addOrReplaceChild("bone36", CubeListBuilder.create()
                .texOffs(34, 35).addBox(-6.0F, -3.5F, 21.0F, 2.0F, 2.0F, 2.0F)
                .texOffs(26, 35).addBox(4.0F, -3.5F, 21.0F, 2.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition bone2 = bone36.addOrReplaceChild("bone2", CubeListBuilder.create()
                .texOffs(58, 59).addBox(-6.51F, -4.0F, 9.0F, 3.0F, 3.0F, 13.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone = bone36.addOrReplaceChild("bone", CubeListBuilder.create()
                .texOffs(62, 16).addBox(3.51F, -4.0F, 9.0F, 3.0F, 3.0F, 13.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone37 = centerback.addOrReplaceChild("bone37", CubeListBuilder.create()
                .texOffs(39, 56).addBox(3.5F, 0.0F, 9.0F, 3.0F, 3.0F, 13.0F)
                .texOffs(53, 8).addBox(4.0F, 0.5F, 21.0F, 2.0F, 2.0F, 2.0F)
                .texOffs(6, 51).addBox(-6.0F, 0.5F, 21.0F, 2.0F, 2.0F, 2.0F)
                .texOffs(53, 0).addBox(-6.5F, 0.0F, 9.0F, 3.0F, 3.0F, 13.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition bone38 = centerback.addOrReplaceChild("bone38", CubeListBuilder.create()
                .texOffs(33, 0).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 2.0F, 7.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition tireback = all.addOrReplaceChild("tireback", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone6 = tireback.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 12.0F));

        PartDefinition bone16 = tireback.addOrReplaceChild("bone16", CubeListBuilder.create()
                .texOffs(18, 51).addBox(2.01F, -2.0F, -14.0F, 1.0F, 4.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 12.0F));

        PartDefinition bone17 = tireback.addOrReplaceChild("bone17", CubeListBuilder.create()
                .texOffs(0, 51).addBox(-3.01F, -2.0F, -14.0F, 1.0F, 4.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 12.0F));

        PartDefinition bone20 = tireback.addOrReplaceChild("bone20", CubeListBuilder.create()
                .texOffs(68, 96).addBox(-3.0F, -5.0F, -14.0F, 6.0F, 10.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 12.0F));

        PartDefinition bone22 = tireback.addOrReplaceChild("bone22", CubeListBuilder.create()
                .texOffs(48, 92).addBox(-3.0F, 3.4853F, -10.4853F, 6.0F, 10.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 12.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition bone23 = tireback.addOrReplaceChild("bone23", CubeListBuilder.create()
                .texOffs(90, 45).addBox(-3.0F, 7.0F, -2.0F, 6.0F, 10.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 12.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition bone62 = tireback.addOrReplaceChild("bone62", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.0F, -13.4853F, -10.4853F, 6.0F, 10.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 12.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition side4 = all.addOrReplaceChild("side4", CubeListBuilder.create(), PartPose.offset(-1.0F, 5.0F, -16.0F));

        PartDefinition bone69 = side4.addOrReplaceChild("bone69", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone70 = side4.addOrReplaceChild("bone70", CubeListBuilder.create()
                .texOffs(50, 27).addBox(0.5F, -1.0F, -3.0F, 1.0F, 4.0F, 4.0F, true), PartPose.offsetAndRotation(-4.0F, -5.0F, -15.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition bone71 = side4.addOrReplaceChild("bone71", CubeListBuilder.create()
                .texOffs(46, 38).addBox(-4.0F, -7.5F, -16.0F, 1.0F, 2.0F, 16.0F, true)
                .texOffs(18, 45).addBox(-4.0F, -4.5F, -16.0F, 1.0F, 2.0F, 16.0F, true)
                .texOffs(33, 0).addBox(-4.5F, -6.0F, -17.0F, 1.0F, 2.0F, 18.0F, true), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone72 = side4.addOrReplaceChild("bone72", CubeListBuilder.create()
                .texOffs(24, 47).addBox(0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, true), PartPose.offsetAndRotation(-4.0F, -5.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition bone73 = side4.addOrReplaceChild("bone73", CubeListBuilder.create()
                .texOffs(66, 32).addBox(-3.0F, -9.0F, -11.0F, 1.0F, 5.0F, 12.0F, true), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone74 = side4.addOrReplaceChild("bone74", CubeListBuilder.create()
                .texOffs(59, 75).addBox(-2.99F, -7.0F, -11.0F, 1.0F, 6.0F, 11.0F, true), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition bone75 = side4.addOrReplaceChild("bone75", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone76 = side4.addOrReplaceChild("bone76", CubeListBuilder.create()
                .texOffs(0, 90).addBox(-3.0F, -7.0F, 6.0F, 1.0F, 2.0F, 10.0F, true), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition bone77 = side4.addOrReplaceChild("bone77", CubeListBuilder.create()
                .texOffs(26, 26).addBox(-3.5F, 0.0F, 9.0F, 1.0F, 2.0F, 7.0F, true), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0873F, 0.0F));

        PartDefinition bone78 = side4.addOrReplaceChild("bone78", CubeListBuilder.create()
                .texOffs(100, 79).addBox(-3.5F, -10.0F, -10.0F, 1.0F, 2.0F, 9.0F, true), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone79 = side4.addOrReplaceChild("bone79", CubeListBuilder.create()
                .texOffs(26, 89).addBox(-2.5F, -11.0F, -11.0F, 1.0F, 2.0F, 10.0F, true), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.1745F, 0.0873F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public ModelPart root() {
        return this.all;
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
        if (entity instanceof EntityMotor) {
            EntityMotor motor = (EntityMotor) entity;
            if (motor.getDeltaMovement().length() > 0) {
                tirefront3.xRot = Mth.wrapDegrees(40 * motor.ridingTicks);
                tireback.xRot = Mth.wrapDegrees(40 * motor.ridingTicks);
            }
        }
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        all.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
