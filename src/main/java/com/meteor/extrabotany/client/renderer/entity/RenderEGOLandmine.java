package com.meteor.extrabotany.client.renderer.entity;

import com.meteor.extrabotany.common.entities.ego.EntityEGOLandmine;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.render.block_entity.SpecialFlowerBlockEntityRenderer;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class RenderEGOLandmine extends EntityRenderer<EntityEGOLandmine> {

    private static final double INITIAL_OFFSET = -1.0 / 16 + 0.005;
    // Global y offset so that overlapping landmines do not Z-fight
    public static double offY = INITIAL_OFFSET;

    public RenderEGOLandmine(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    public static void onWorldRenderLast(RenderLevelStageEvent evt) {
        offY = INITIAL_OFFSET;
    }

    @Override
    public void render(EntityEGOLandmine e, float entityYaw, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light) {
        super.render(e, entityYaw, partialTicks, ms, buffers, light);

        ms.pushPose();
        AABB aabb = e.getBoundingBox().move(e.position().scale(-1));

        float gs = (float) (Math.sin(ClientTickHandler.total() / 20) + 1) * 0.2F + 0.6F;
        int r = 0, g = 0, b = 0;
        switch (e.getLandmineType()) {
            case 0:
                b = 240;
                break;
            case 1:
                g = 240;
                break;
            case 2:
                r = 240;
                break;
        }
        r = (int) (r * gs);
        g = (int) (g * gs);
        b = (int) (b * gs);

        int alpha = 32;
        if (e.tickCount < 8) {
            alpha *= Math.min((e.tickCount + partialTicks) / 8F, 1F);
        } else if (e.tickCount > 47) {
            alpha *= Math.min(1F - (e.tickCount - 47 + partialTicks) / 8F, 1F);
        }

        // 1.20.1 Botania's renderRectangle no longer accepts a color/alpha parameter,
        // so the landmine color/alpha modulation above is computed but cannot be applied directly.
        SpecialFlowerBlockEntityRenderer.renderRectangle(ms, buffers, e.blockPosition(), aabb);
        offY += 0.001;
        ms.popPose();
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull EntityEGOLandmine entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
