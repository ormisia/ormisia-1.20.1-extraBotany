package com.meteor.extrabotany.client.renderer.tile;

import com.meteor.extrabotany.client.handler.ClientTickHandler;
import com.meteor.extrabotany.common.blocks.tile.TilePowerFrame;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class RenderPowerFrame implements BlockEntityRenderer<TilePowerFrame> {

    public RenderPowerFrame(BlockEntityRendererProvider.Context rendererDispatcherIn) {
    }

    @Override
    public void render(TilePowerFrame tile, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        ms.pushPose();
        ms.translate(0.5, 0.5, 0.5);
        boolean hasItem = !tile.getItemHandler().isEmpty();
        if (hasItem) {
            ms.mulPose(Axis.YP.rotationDegrees(ClientTickHandler.ticksInGame * 0.5F));
            ItemStack stack = tile.getItemHandler().getItem(0);
            Minecraft mc = Minecraft.getInstance();
            mc.getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, light, overlay, ms, buffers, mc.level, 0);
        }
        ms.popPose();
    }
}
