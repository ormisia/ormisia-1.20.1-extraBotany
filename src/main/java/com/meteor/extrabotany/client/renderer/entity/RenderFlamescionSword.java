package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.common.entities.EntityFlamescionSword;
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
public class RenderFlamescionSword extends EntityRenderer<EntityFlamescionSword> {

    public RenderFlamescionSword(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityFlamescionSword entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        Minecraft mc = Minecraft.getInstance();
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
        float f1 = 0.016666668F * 7F;
        matrixStackIn.scale(-f1, -f1, f1);
        Component text = Component.translatable("mri.sword");
        int halfWidth = mc.font.width(text.getString()) / 2;
        mc.font.drawInBatch(text.copy().withStyle(ChatFormatting.RED), -halfWidth, 0, 0xFFFFFFFF, false, matrixStackIn.last().pose(), bufferIn, Font.DisplayMode.NORMAL, 0, packedLightIn);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityFlamescionSword entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public boolean shouldRender(EntityFlamescionSword entity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

}
