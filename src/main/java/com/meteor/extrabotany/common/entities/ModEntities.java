package com.meteor.extrabotany.common.entities;

import com.meteor.extrabotany.common.entities.ego.EntityEGO;
import com.meteor.extrabotany.common.entities.ego.EntityEGOLandmine;
import com.meteor.extrabotany.common.entities.ego.EntityEGOMinion;
import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.meteor.extrabotany.common.entities.mountable.EntityUfo;
import com.meteor.extrabotany.common.entities.projectile.EntityAuraFire;
import com.meteor.extrabotany.common.entities.projectile.EntityButterflyProjectile;
import com.meteor.extrabotany.common.entities.projectile.EntityInfluxWaverProjectile;
import com.meteor.extrabotany.common.entities.projectile.EntityMagicArrow;
import com.meteor.extrabotany.common.entities.projectile.EntityPhantomSword;
import com.meteor.extrabotany.common.entities.projectile.EntityTrueShadowKatanaProjectile;
import com.meteor.extrabotany.common.entities.projectile.EntityTrueTerrabladeProjectile;
import com.meteor.extrabotany.common.libs.LibEntityNames;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LibMisc.MOD_ID);

    public static final RegistryObject<EntityType<EntityMotor>> MOTOR = ENTITIES.register(LibEntityNames.MOTOR,
            () -> EntityType.Builder.<EntityMotor>of(EntityMotor::new, MobCategory.MISC)
                    .sized(1.675F, 1.875F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.MOTOR));

    public static final RegistryObject<EntityType<EntityKeyOfTruth>> KEY_OF_TRUTH = ENTITIES.register(LibEntityNames.KEYOFTRUTH,
            () -> EntityType.Builder.<EntityKeyOfTruth>of(EntityKeyOfTruth::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.KEYOFTRUTH));

    public static final RegistryObject<EntityType<EntitySlash>> SLASH = ENTITIES.register(LibEntityNames.SLASH,
            () -> EntityType.Builder.<EntitySlash>of(EntitySlash::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.SLASH));

    public static final RegistryObject<EntityType<EntityUfo>> UFO = ENTITIES.register(LibEntityNames.UFO,
            () -> EntityType.Builder.<EntityUfo>of(EntityUfo::new, MobCategory.MISC)
                    .sized(1.5F, 1.5F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.UFO));

    public static final RegistryObject<EntityType<EntityPhantomSword>> PHANTOMSWORD = ENTITIES.register(LibEntityNames.PHANTONSWORD,
            () -> EntityType.Builder.<EntityPhantomSword>of(EntityPhantomSword::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.PHANTONSWORD));

    public static final RegistryObject<EntityType<EntityFlamescionSlash>> FLAMESCIONSLASH = ENTITIES.register(LibEntityNames.FLAMESCIONSLASH,
            () -> EntityType.Builder.<EntityFlamescionSlash>of(EntityFlamescionSlash::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.FLAMESCIONSLASH));

    public static final RegistryObject<EntityType<EntityStrengthenSlash>> SRENGTHENSLASH = ENTITIES.register(LibEntityNames.STRENGTHENSLASH,
            () -> EntityType.Builder.<EntityStrengthenSlash>of(EntityStrengthenSlash::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.STRENGTHENSLASH));

    public static final RegistryObject<EntityType<EntityFlamescionUlt>> ULT = ENTITIES.register(LibEntityNames.FLAMESCIONULT,
            () -> EntityType.Builder.<EntityFlamescionUlt>of(EntityFlamescionUlt::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.FLAMESCIONULT));

    public static final RegistryObject<EntityType<EntityFlamescionVoid>> VOID = ENTITIES.register(LibEntityNames.VOID,
            () -> EntityType.Builder.<EntityFlamescionVoid>of(EntityFlamescionVoid::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.VOID));

    public static final RegistryObject<EntityType<EntityFlamescionSword>> SWORD = ENTITIES.register(LibEntityNames.FLAMESCIONSWORD,
            () -> EntityType.Builder.<EntityFlamescionSword>of(EntityFlamescionSword::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.FLAMESCIONSWORD));

    public static final RegistryObject<EntityType<EntityMagicArrow>> MAGICARROW = ENTITIES.register(LibEntityNames.MAGICARROW,
            () -> EntityType.Builder.<EntityMagicArrow>of(EntityMagicArrow::new, MobCategory.MISC)
                    .sized(0.05F, 0.05F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.MAGICARROW));

    public static final RegistryObject<EntityType<EntitySplashGrenade>> SPLASHGRENADE = ENTITIES.register(LibEntityNames.SPLASHGRENADE,
            () -> EntityType.Builder.<EntitySplashGrenade>of(EntitySplashGrenade::new, MobCategory.MISC)
                    .sized(0.05F, 0.05F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.SPLASHGRENADE));

    public static final RegistryObject<EntityType<EntityInfluxWaverProjectile>> INFLUXWAVER = ENTITIES.register(LibEntityNames.INFLUXWAVER_PROJECTILE,
            () -> EntityType.Builder.<EntityInfluxWaverProjectile>of(EntityInfluxWaverProjectile::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.INFLUXWAVER_PROJECTILE));

    public static final RegistryObject<EntityType<EntityTrueTerrabladeProjectile>> TRUETERRABLADE = ENTITIES.register(LibEntityNames.TRUETERRABLADE_PROJECTILE,
            () -> EntityType.Builder.<EntityTrueTerrabladeProjectile>of(EntityTrueTerrabladeProjectile::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.TRUETERRABLADE_PROJECTILE));

    public static final RegistryObject<EntityType<EntityTrueShadowKatanaProjectile>> TRUESHADOWKATANA = ENTITIES.register(LibEntityNames.TRUESHADOWKATANA_PROJECTILE,
            () -> EntityType.Builder.<EntityTrueShadowKatanaProjectile>of(EntityTrueShadowKatanaProjectile::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.TRUESHADOWKATANA_PROJECTILE));

    public static final RegistryObject<EntityType<EntityAuraFire>> AURAFIRE = ENTITIES.register(LibEntityNames.AURAFIRE,
            () -> EntityType.Builder.<EntityAuraFire>of(EntityAuraFire::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.AURAFIRE));

    public static final RegistryObject<EntityType<EntityEGO>> EGO = ENTITIES.register(LibEntityNames.EGO,
            () -> EntityType.Builder.<EntityEGO>of(EntityEGO::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.8F)
                    .clientTrackingRange(128)
                    .updateInterval(2)
                    .build(LibEntityNames.EGO));

    public static final RegistryObject<EntityType<EntityEGOMinion>> EGOMINION = ENTITIES.register(LibEntityNames.EGOMINION,
            () -> EntityType.Builder.<EntityEGOMinion>of(EntityEGOMinion::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.8F)
                    .clientTrackingRange(128)
                    .updateInterval(2)
                    .build(LibEntityNames.EGOMINION));

    public static final RegistryObject<EntityType<EntityEGOLandmine>> EGOLANDMINE = ENTITIES.register(LibEntityNames.EGOLANDMINE,
            () -> EntityType.Builder.<EntityEGOLandmine>of(EntityEGOLandmine::new, MobCategory.MISC)
                    .sized(3F, 0.1F)
                    .clientTrackingRange(128)
                    .updateInterval(2)
                    .build(LibEntityNames.EGOLANDMINE));

    public static final RegistryObject<EntityType<EntityButterflyProjectile>> BUTTERFLY = ENTITIES.register(LibEntityNames.BUTTERFLY_PROJECTILE,
            () -> EntityType.Builder.<EntityButterflyProjectile>of(EntityButterflyProjectile::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(64)
                    .updateInterval(10)
                    .build(LibEntityNames.BUTTERFLY_PROJECTILE));

}
