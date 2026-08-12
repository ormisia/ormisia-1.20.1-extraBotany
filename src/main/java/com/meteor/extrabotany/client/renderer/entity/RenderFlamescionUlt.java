package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.common.entities.EntityFlamescionUlt;
import com.mojang.blaze3d.vertex.PoseStack;
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
public class RenderFlamescionUlt extends EntityRenderer<EntityFlamescionUlt> {

    Component[] texts = new Component[]{
            Component.translatable("mri.ult0"),
            Component.translatable("mri.ult1"),
            Component.translatable("mri.ult2"),
            Component.translatable("mri.ult3"),
            Component.translatable("mri.ult4"),
            Component.translatable("mri.ult5"),
            Component.translatable("mri.ult6"),
            Component.translatable("mri.ult7"),
    };

    public RenderFlamescionUlt(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityFlamescionUlt entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        Minecraft mc = Minecraft.getInstance();
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
        float f1 = 0.016666668F * 4F;
        matrixStackIn.scale(-f1, -f1, f1);
        matrixStackIn.translate(0, -60D, 0);
        for (Component text : texts) {

            matrixStackIn.translate(0, 12D, 0);
            int halfWidth = mc.font.width(text.getString()) / 2;
            mc.font.drawInBatch(text.copy().withStyle(ChatFormatting.DARK_RED), -halfWidth, 0, 0xFFFFFFFF, false, matrixStackIn.last().pose(), bufferIn, Font.DisplayMode.NORMAL, 0, packedLightIn);

        }
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityFlamescionUlt entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public boolean shouldRender(EntityFlamescionUlt entity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

}
