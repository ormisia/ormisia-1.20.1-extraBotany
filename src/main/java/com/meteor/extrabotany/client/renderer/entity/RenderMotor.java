package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.client.model.ModelMotor;
import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.meteor.extrabotany.common.libs.LibMisc;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderMotor extends EntityRenderer<EntityMotor> {

    private EntityModel<Entity> motorModel = new ModelMotor();

    public RenderMotor(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMotor entity) {
        return ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "textures/entity/motor.png");
    }

    @Override
    public void render(EntityMotor entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.0D, 1.500D, 0.0D);
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
        matrixStackIn.mulPose(Axis.ZP.rotationDegrees(entityIn.getRotation()));
        matrixStackIn.mulPose(Axis.XP.rotationDegrees(entityIn.getPitch()));
        //matrixStackIn.mulPose(Axis.YP.rotationDegrees(90.0F));
        this.motorModel.setupAnim(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.motorModel.renderType(this.getTextureLocation(entityIn)));
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        this.motorModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
