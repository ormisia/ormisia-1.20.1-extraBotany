package com.meteor.extrabotany.client;

import com.meteor.extrabotany.client.model.ModelHerrscher;
import com.meteor.extrabotany.common.handler.HerrscherHandler;
import com.meteor.extrabotany.common.libs.LibMisc;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class LayerHerrscher extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private ModelHerrscher layer = new ModelHerrscher();
    private ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, "textures/entity/herrscher.png");

    public LayerHerrscher(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer) {
        super(renderer);
    }

    @Override
    public void render(@Nonnull PoseStack ms, @Nonnull MultiBufferSource buffers, int light, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (player != null && HerrscherHandler.isHerrscherOfThunder(player)) {
            VertexConsumer buffer = buffers.getBuffer(layer.renderType(texture));
            ms.pushPose();
            ms.translate(0, -0.4F, 0);
            getParentModel().leftArm.translateAndRotate(ms);
            layer.renderLeftArm(ms, buffer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            ms.popPose();

            ms.pushPose();
            ms.translate(0, -0.4F, 0);
            getParentModel().rightArm.translateAndRotate(ms);
            layer.renderRightArm(ms, buffer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            ms.popPose();

            ms.pushPose();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            ms.scale(1.4F, 1.4F, 1.4F);
            ms.translate(0, -0.2F, 0);
            getParentModel().body.translateAndRotate(ms);
            layer.renderStigma(ms, buffer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.75F);
            ms.scale(1 / 1.4F, 1 / 1.4F, 1 / 1.4F);
            RenderSystem.disableBlend();
            ms.popPose();
        }
    }

}
