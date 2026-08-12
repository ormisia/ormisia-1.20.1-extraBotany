package com.meteor.extrabotany;

import com.meteor.extrabotany.client.ClientProxy;
import com.meteor.extrabotany.common.ExtraBotanyGroup;
import com.meteor.extrabotany.common.ServerProxy;
import com.meteor.extrabotany.common.blocks.ModBlocks;
import com.meteor.extrabotany.common.blocks.ModSubtiles;
import com.meteor.extrabotany.common.blocks.tile.ModTiles;
import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.core.ConfigHandler;
import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.core.IProxy;
import com.meteor.extrabotany.common.core.ModSounds;
import com.meteor.extrabotany.common.crafting.ModRecipeSerializers;
import com.meteor.extrabotany.common.entities.ModEntities;
import com.meteor.extrabotany.common.entities.ego.EntityEGO;
import com.meteor.extrabotany.common.handler.ContributorListHandler;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.brew.ModBrew;
import com.meteor.extrabotany.common.libs.LibMisc;
import com.meteor.extrabotany.common.potions.ModPotions;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vazkii.patchouli.api.PatchouliAPI;

import static com.meteor.extrabotany.common.items.ModItems.prefix;

@Mod(LibMisc.MOD_ID)
public class ExtraBotany {

    public static final CreativeModeTab itemGroup = ExtraBotanyGroup.TAB;

    public static IProxy proxy;

    public static boolean curiosLoaded = false;

    public static final Logger LOGGER = LogManager.getLogger(LibMisc.MOD_ID);

    @OnlyIn(Dist.CLIENT)
    public static KeyMapping keyForward;
    @OnlyIn(Dist.CLIENT)
    public static KeyMapping keyBackward;
    @OnlyIn(Dist.CLIENT)
    public static KeyMapping keyLeft;
    @OnlyIn(Dist.CLIENT)
    public static KeyMapping keyRight;
    @OnlyIn(Dist.CLIENT)
    public static KeyMapping keyUp;
    @OnlyIn(Dist.CLIENT)
    public static KeyMapping keyFlight;

    public ExtraBotany() {
        proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
        proxy.registerHandlers();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::registerAttributes);

        ModSounds.SOUNDS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModRecipeSerializers.SERIALIZERS.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
        ModBlocks.ITEMS.register(modBus);
        ModSubtiles.BLOCKS.register(modBus);
        ModSubtiles.ITEMS.register(modBus);
        ModSubtiles.TILES.register(modBus);
        ModTiles.TILES.register(modBus);
        ModEntities.ENTITIES.register(modBus);
        ModPotions.MODS.register(modBus);
        ModBrew.BREWS.register(modBus);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        curiosLoaded = ModList.get().isLoaded("curios");
        CapabilityHandler.register();
        EquipmentHandler.init();

        event.enqueueWork(() -> {

            ContributorListHandler.firstStart();

            PatchouliAPI.get().registerMultiblock(prefix("frame_adv"), com.meteor.extrabotany.common.blocks.tile.TilePowerFrame.MULTIBLOCK_ADV.get());
        });
    }

    public void registerAttributes(EntityAttributeCreationEvent event) {
        AttributeSupplier.Builder ego = Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.55)
                .add(Attributes.MAX_HEALTH, EntityEGO.MAX_HP)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.FOLLOW_RANGE, 35)
                .add(Attributes.ATTACK_DAMAGE, 8);
        event.put(ModEntities.EGO.get(), ego.build());

        AttributeSupplier.Builder egominion = Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.MAX_HEALTH, 60)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.ARMOR, 15)
                .add(Attributes.FOLLOW_RANGE, 35)
                .add(Attributes.ATTACK_DAMAGE, 7);
        event.put(ModEntities.EGOMINION.get(), egominion.build());
    }

}
