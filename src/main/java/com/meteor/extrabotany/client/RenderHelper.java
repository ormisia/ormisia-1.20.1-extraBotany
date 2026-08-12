package com.meteor.extrabotany.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.List;

public class RenderHelper {

    public static void renderItemCustomColor(LivingEntity entity, ItemStack stack, int color, PoseStack ms, MultiBufferSource buffers, int light, int overlay, @Nullable BakedModel model) {
        ms.pushPose();
        if (model == null) {
            model = Minecraft.getInstance().getItemRenderer().getModel(stack, entity.level(), entity, 0);
        }
        model = ForgeHooksClient.handleCameraTransforms(ms, model, ItemDisplayContext.NONE, false);
        ms.translate(-0.5D, -0.5D, -0.5D);

        if (!model.isCustomRenderer() && stack.getItem() != Items.TRIDENT) {
            RenderType rendertype = ItemBlockRenderTypes.getRenderType(stack, true);
            VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(buffers, rendertype, true, stack.hasFoil());
            renderBakedItemModel(model, stack, color, light, overlay, ms, ivertexbuilder);
        } else {
            IClientItemExtensions.of(stack).getCustomRenderer().renderByItem(stack, ItemDisplayContext.NONE, ms, buffers, light, overlay);
        }

        ms.popPose();
    }

    public static void renderItemCustomColor(LivingEntity entity, ItemStack stack, int color, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        renderItemCustomColor(entity, stack, color, ms, buffers, light, overlay, null);
    }

    // [VanillaCopy] ItemRenderer with custom color
    private static void renderBakedItemModel(BakedModel model, ItemStack stack, int color, int light, int overlay, PoseStack ms, VertexConsumer buffer) {
        RandomSource random = RandomSource.create();

        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            renderBakedItemQuads(ms, buffer, color, model.getQuads(null, direction, random), stack, light, overlay);
        }

        random.setSeed(42L);
        renderBakedItemQuads(ms, buffer, color, model.getQuads(null, null, random), stack, light, overlay);
    }

    // [VanillaCopy] ItemRenderer, with custom color + alpha support
    private static void renderBakedItemQuads(PoseStack ms, VertexConsumer buffer, int color, List<BakedQuad> quads, ItemStack stack, int light, int overlay) {
        PoseStack.Pose matrixstack$entry = ms.last();

        for (BakedQuad bakedquad : quads) {
            int i = color;

            float f = (float) (i >> 16 & 255) / 255.0F;
            float f1 = (float) (i >> 8 & 255) / 255.0F;
            float f2 = (float) (i & 255) / 255.0F;
            float alpha = ((color >> 24) & 0xFF) / 255.0F;
            buffer.putBulkData(matrixstack$entry, bakedquad, f, f1, f2, alpha, light, overlay, true);
        }
    }

}
