package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.client.RenderHelper;
import com.meteor.extrabotany.client.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.entities.projectile.EntityPhantomSword;
import com.meteor.extrabotany.common.items.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RenderPhantomSword extends EntityRenderer<EntityPhantomSword> {

    public RenderPhantomSword(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EntityPhantomSword weapon, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        Minecraft mc = Minecraft.getInstance();
        if (weapon.getDelay() > 0)
            return;

        matrixStackIn.pushPose();

        matrixStackIn.pushPose();
        float s = 1.5F;
        matrixStackIn.scale(s, s, s);
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(weapon.getRotation() + 90F));
        matrixStackIn.mulPose(Axis.ZP.rotationDegrees(weapon.getPitch()));
        matrixStackIn.mulPose(Axis.ZP.rotationDegrees(-45));

        float alpha = weapon.getFake() ? Math.max(0F, 0.75F - weapon.tickCount * (0.75F / EntityPhantomSword.LIVE_TICKS) * 1.5F) : 1F;
        BakedModel model = MiscellaneousIcons.INSTANCE.firstFractalWeaponModels[weapon.getVariety()];
        int color = 0xFFFFFF | ((int) (alpha * 255F)) << 24;
        RenderHelper.renderItemCustomColor(mc.player, new ItemStack(ModItems.firstfractal.get()), color, matrixStackIn, bufferIn, 0xF000F0, OverlayTexture.NO_OVERLAY, model);

        matrixStackIn.scale(1 / s, 1 / s, 1 / s);
        matrixStackIn.popPose();

        matrixStackIn.popPose();
        super.render(weapon, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityPhantomSword entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

}
