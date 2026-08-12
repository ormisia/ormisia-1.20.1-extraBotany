package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.client.model.ModelSlash;
import com.meteor.extrabotany.common.entities.EntitySlash;
import com.meteor.extrabotany.common.libs.LibMisc;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
public class RenderSlash extends EntityRenderer<EntitySlash> {

    private EntityModel<Entity> slashModel = new ModelSlash();
    private int frames = 35;

    public RenderSlash(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySlash entity) {
        return ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "textures/entity/slash_" + entity.tickCount % frames + ".png");
    }

    @Override
    public void render(EntitySlash entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        Minecraft mc = Minecraft.getInstance();
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
        VertexConsumer buffer = bufferIn.getBuffer(this.slashModel.renderType(this.getTextureLocation(entityIn)));
        matrixStackIn.scale(1.75F, 1.75F, 1.75F);
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        this.slashModel.renderToBuffer(matrixStackIn, buffer, 0xF000F0, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        matrixStackIn.scale(1F / 1.75F, 1F / 1.75F, 1F / 1.75F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

}
