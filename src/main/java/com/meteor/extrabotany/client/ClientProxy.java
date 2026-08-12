package com.meteor.extrabotany.client;

import com.meteor.extrabotany.ExtraBotany;
import com.meteor.extrabotany.client.handler.*;
import com.meteor.extrabotany.client.renderer.entity.*;
import com.meteor.extrabotany.common.blocks.ModBlocks;
import com.meteor.extrabotany.common.handler.ContributorListHandler;
import com.meteor.extrabotany.common.core.IProxy;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.handler.MemeHandler;
import com.meteor.extrabotany.common.items.brew.ItemBrewBase;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import vazkii.botania.common.block.decor.BotaniaMushroomBlock;
import vazkii.botania.common.block.decor.FloatingFlowerBlock;

import java.util.Map;

import static com.meteor.extrabotany.common.items.ModItems.*;

public class ClientProxy implements IProxy {

    public void registerModels(FMLClientSetupEvent evt) {
        EntityRenderers.register(ModEntities.MOTOR.get(), RenderMotor::new);
        EntityRenderers.register(ModEntities.KEY_OF_TRUTH.get(), RenderKeyOfTruth::new);
        EntityRenderers.register(ModEntities.SLASH.get(), RenderSlash::new);
        EntityRenderers.register(ModEntities.UFO.get(), RenderUfo::new);
        EntityRenderers.register(ModEntities.PHANTOMSWORD.get(), RenderPhantomSword::new);
        EntityRenderers.register(ModEntities.FLAMESCIONSLASH.get(), RenderFlamescionSlash::new);
        EntityRenderers.register(ModEntities.SRENGTHENSLASH.get(), RenderStrengthenSlash::new);
        EntityRenderers.register(ModEntities.ULT.get(), RenderFlamescionUlt::new);
        EntityRenderers.register(ModEntities.SWORD.get(), RenderFlamescionSword::new);
        EntityRenderers.register(ModEntities.VOID.get(), RenderFlamescionVoid::new);

        EntityRenderers.register(ModEntities.EGO.get(), RenderEGO::new);
        EntityRenderers.register(ModEntities.EGOMINION.get(), RenderEGO::new);
        EntityRenderers.register(ModEntities.EGOLANDMINE.get(), RenderEGOLandmine::new);

        EntityRenderers.register(ModEntities.MAGICARROW.get(), RenderDummy::new);
        EntityRenderers.register(ModEntities.AURAFIRE.get(), RenderDummy::new);
        EntityRenderers.register(ModEntities.INFLUXWAVER.get(), RenderProjectileBase::new);
        EntityRenderers.register(ModEntities.TRUETERRABLADE.get(), RenderProjectileBase::new);
        EntityRenderers.register(ModEntities.TRUESHADOWKATANA.get(), RenderProjectileBase::new);
        EntityRenderers.register(ModEntities.BUTTERFLY.get(), RenderButterflyProjectile::new);

        EntityRenderers.register(ModEntities.SPLASHGRENADE.get(), ThrownItemRenderer::new);
    }

    public void onClientSetUpEvent(FMLClientSetupEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Options options = mc.options;
        ExtraBotany.keyForward = options.keyUp;
        ExtraBotany.keyBackward = options.keyDown;
        ExtraBotany.keyLeft = options.keyLeft;
        ExtraBotany.keyRight = options.keyRight;
        ExtraBotany.keyUp = options.keyJump;
        ExtraBotany.keyFlight = options.keySprint;
        registerRenderTypes();
        event.enqueueWork(ClientProxy::registerPropertyGetters);
    }

    @Override
    public void registerHandlers() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::onClientSetUpEvent);
        modBus.addListener(this::loadComplete);
        modBus.addListener(this::registerModels);
        modBus.addListener(MiscellaneousIcons.INSTANCE::onModelRegister);
        modBus.addListener(MiscellaneousIcons.INSTANCE::onModelBake);
        modBus.addListener(ModelHandler::registerModels);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(HUDHandler::onOverlayRender);
        forgeBus.addListener(ClientTickHandler::clientTickEnd);
    }

    private static void registerRenderTypes() {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.powerframe.get(), RenderType.cutout());
        BuiltInRegistries.BLOCK.stream().filter(b -> BuiltInRegistries.BLOCK.getKey(b).getNamespace().equals(LibMisc.MOD_ID))
                .forEach(b -> {
                    if (b instanceof FloatingFlowerBlock || b instanceof FlowerBlock
                            || b instanceof TallFlowerBlock || b instanceof BotaniaMushroomBlock) {
                        ItemBlockRenderTypes.setRenderLayer(b, RenderType.cutout());
                    }
                });
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            initAuxiliaryRender();
            ColorHandler.init();
            MemeHandler.spam();
        });
    }

    private static void registerPropertyGetter(ItemLike item, ResourceLocation id, ItemPropertyFunction propGetter) {
        ItemProperties.register(item.asItem(), id, propGetter);
    }

    private static void registerPropertyGetters() {
        ItemPropertyFunction brewGetter = (stack, level, entity, seed) -> {
            ItemBrewBase item = ((ItemBrewBase) stack.getItem());
            return item.getSwigs() - item.getSwigsLeft(stack);
        };
        registerPropertyGetter(cocktail.get(), prefix("swigs_taken"), brewGetter);
        registerPropertyGetter(infinitewine.get(), prefix("swigs_taken"), brewGetter);
    }

    private void initAuxiliaryRender() {
        Map<String, EntityRenderer<? extends Player>> skinMap = Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap();
        for (EntityRenderer<? extends Player> value : skinMap.values()) {
            if (value instanceof PlayerRenderer render) {
                render.addLayer(new LayerHerrscher(render));
                render.addLayer(new LayerFlamescion(render));
            }
        }
    }

}
