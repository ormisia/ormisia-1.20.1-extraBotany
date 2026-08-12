package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.client.handler.ClientTickHandler;
import com.meteor.extrabotany.common.entities.EntityFlamescionVoid;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderFlamescionVoid extends EntityRenderer<EntityFlamescionVoid> {

    public RenderFlamescionVoid(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityFlamescionVoid entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        Minecraft mc = Minecraft.getInstance();
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
        matrixStackIn.mulPose(Axis.ZN.rotationDegrees(ClientTickHandler.ticksInGame * 10F));
        float f1 = 0.016666668F * 5F;
        matrixStackIn.scale(-f1, -f1, f1);
        Component text = Component.translatable("mri.void");
        int halfWidth = mc.font.width(text.getString()) / 2;
        mc.font.drawInBatch(text.copy().withStyle(ChatFormatting.GOLD), -halfWidth, 0, 0xFFFFFFFF, false, matrixStackIn.last().pose(), bufferIn, Font.DisplayMode.NORMAL, 0, packedLightIn);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityFlamescionVoid entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public boolean shouldRender(EntityFlamescionVoid entity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

}
