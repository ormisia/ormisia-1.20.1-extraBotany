package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.client.model.ModelSlash;
import com.meteor.extrabotany.common.entities.EntityFlamescionSlash;
import com.meteor.extrabotany.common.libs.LibMisc;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
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
public class RenderFlamescionSlash extends EntityRenderer<EntityFlamescionSlash> {

    private EntityModel<Entity> slashModel = new ModelSlash();
    private int frames = 6;

    public RenderFlamescionSlash(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityFlamescionSlash entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        Minecraft mc = Minecraft.getInstance();
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
        VertexConsumer buffer = bufferIn.getBuffer(this.slashModel.renderType(this.getTextureLocation(entityIn))).uv2(0xF000F0);
        float s = 3.0F;
        matrixStackIn.scale(s, s, s);
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        matrixStackIn.mulPose(Axis.XP.rotationDegrees(entityIn.getPitch()));
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(entityIn.getRotation()));
        this.slashModel.renderToBuffer(matrixStackIn, buffer, 0xF000F0, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        matrixStackIn.scale(1F / s, 1F / s, 1F / s);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityFlamescionSlash entity) {
        return ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "textures/entity/flamescionslash_" + entity.tickCount % frames + ".png");
    }
}
