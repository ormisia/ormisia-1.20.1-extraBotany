package com.meteor.extrabotany.client.handler;

import com.meteor.extrabotany.client.renderer.tile.RenderPowerFrame;
import com.meteor.extrabotany.common.blocks.ModSubtiles;
import com.meteor.extrabotany.common.blocks.tile.ModTiles;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import vazkii.botania.client.render.block_entity.SpecialFlowerBlockEntityRenderer;

public class ModelHandler {

    static boolean registeredModels = false;

    public static void registerModels(EntityRenderersEvent.RegisterRenderers evt) {
        if (!registeredModels) {
            registeredModels = true;
            // TODO(1.20.1): 1.16 registered Botania's FloatingFlowerModel loader via
            // ModelLoaderRegistry.registerLoader(FloatingFlowerModel.Loader.ID, FloatingFlowerModel.Loader.INSTANCE).
            // In 1.20.1 the loader lives at vazkii.botania.forge.client.ForgeFloatingFlowerModel.Loader and is
            // registered by Botania itself on ModelEvent.RegisterGeometryLoaders, so no action is needed here.
        }
        evt.registerBlockEntityRenderer(ModSubtiles.BELL_FLOWER.get(), SpecialFlowerBlockEntityRenderer::new);
        evt.registerBlockEntityRenderer(ModSubtiles.EDELWEISS.get(), SpecialFlowerBlockEntityRenderer::new);
        evt.registerBlockEntityRenderer(ModSubtiles.SUNBLESS.get(), SpecialFlowerBlockEntityRenderer::new);
        evt.registerBlockEntityRenderer(ModSubtiles.MOONBLESS.get(), SpecialFlowerBlockEntityRenderer::new);
        evt.registerBlockEntityRenderer(ModSubtiles.GEMINIORCHID.get(), SpecialFlowerBlockEntityRenderer::new);
        evt.registerBlockEntityRenderer(ModSubtiles.TINKLEFLOWER.get(), SpecialFlowerBlockEntityRenderer::new);
        evt.registerBlockEntityRenderer(ModSubtiles.OMNIVIOLET.get(), SpecialFlowerBlockEntityRenderer::new);
        evt.registerBlockEntityRenderer(ModSubtiles.REIKARLILY.get(), SpecialFlowerBlockEntityRenderer::new);
        evt.registerBlockEntityRenderer(ModSubtiles.ANNOYING_FLOWER.get(), SpecialFlowerBlockEntityRenderer::new);
        evt.registerBlockEntityRenderer(ModSubtiles.BLOODY_ENCHANTRESS.get(), SpecialFlowerBlockEntityRenderer::new);
        evt.registerBlockEntityRenderer(ModSubtiles.SERENITIAN.get(), SpecialFlowerBlockEntityRenderer::new);

        evt.registerBlockEntityRenderer(ModTiles.POWER_FRAME.get(), RenderPowerFrame::new);
    }

    private ModelHandler() {
    }
}
