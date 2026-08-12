package com.meteor.extrabotany.client;

import com.meteor.extrabotany.client.handler.ClientTickHandler;
import com.meteor.extrabotany.client.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.handler.FlamescionHandler;
import com.meteor.extrabotany.common.items.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

public class LayerFlamescion extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public LayerFlamescion(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack ms, MultiBufferSource buffers, int packedLightIn, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (player != null && player.getMainHandItem().getItem() == FlamescionHandler.getFlamescionWeapon()) {
            ms.pushPose();
            getParentModel().body.translateAndRotate(ms);
            float alpha = (float) (0.75F + 0.15F * Math.cos(ClientTickHandler.ticksInGame / 20F));
            int color = 0xFFFFFF | ((int) (alpha * 255F)) << 24;
            BakedModel model = MiscellaneousIcons.INSTANCE.flamescionringModel[0];
            ms.translate(-0.6F, -0.6F, 0);
            float s = 1.2F;
            ms.scale(s, s, s);
            ms.mulPose(Axis.YP.rotationDegrees(-20F));
            ms.mulPose(Axis.ZP.rotationDegrees(-40F));
            ms.mulPose(Axis.XP.rotationDegrees(100F));
            ms.mulPose(Axis.ZN.rotationDegrees(ClientTickHandler.ticksInGame / 5F));
            int light = (int) (0xF000B0 + 0x000030 * Math.cos(ClientTickHandler.ticksInGame / 20F));
            RenderHelper.renderItemCustomColor(player, new ItemStack(ModItems.flamescionweapon.get()), color, ms, buffers, light, OverlayTexture.NO_OVERLAY, model);
            ms.scale(1F / s, 1F / s, 1F / s);
            ms.popPose();
        }
    }
}
