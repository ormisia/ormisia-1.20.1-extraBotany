package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.common.ExtraBotanyGroup;
import com.meteor.extrabotany.api.items.WeightCategory;
import com.meteor.extrabotany.common.core.ModSounds;
import com.meteor.extrabotany.common.items.armor.goblinslayer.ItemGoblinSlayerArmor;
import com.meteor.extrabotany.common.items.armor.maid.ItemMaidArmor;
import com.meteor.extrabotany.common.items.armor.maid.ItemMaidHelm;
import com.meteor.extrabotany.common.items.armor.miku.ItemMikuArmor;
import com.meteor.extrabotany.common.items.armor.shadowwarrior.ItemShadowWarriorArmor;
import com.meteor.extrabotany.common.items.armor.shootingguardian.ItemShootingGuardianArmor;
import com.meteor.extrabotany.common.items.armor.shootingguardian.ItemShootingGuardianHelm;
import com.meteor.extrabotany.common.items.armor.silentsages.ItemSilentSagesArmor;
import com.meteor.extrabotany.common.items.bauble.*;
import com.meteor.extrabotany.common.items.bauble.mount.ItemCosmicCarKeyAccessory;
import com.meteor.extrabotany.common.items.bauble.mount.ItemMotorAccessory;
import com.meteor.extrabotany.common.items.brew.ItemCocktail;
import com.meteor.extrabotany.common.items.brew.ItemInfiniteWine;
import com.meteor.extrabotany.common.items.brew.ItemSplashGrenade;
import com.meteor.extrabotany.common.items.lens.*;
import com.meteor.extrabotany.common.items.relic.*;
import com.meteor.extrabotany.common.libs.LibItemNames;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.common.item.record.BotaniaRecordItem;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LibMisc.MOD_ID);

    public static final FoodProperties SPIRITFUEL_PROP = new FoodProperties.Builder().nutrition(4).saturationMod(0.3F)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.HEAL, 1, 2), 1.0F)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 500), 1.0F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 500), 1.0F)
            .effect(new MobEffectInstance(MobEffects.LUCK, 500), 1.0F)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 500), 1.0F)
            .build();
    public static final FoodProperties NIGHTMAREFUEL_PROP = new FoodProperties.Builder().nutrition(4).saturationMod(0.3F)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.HARM, 1, 2), 1.0F)
            .effect(new MobEffectInstance(MobEffects.BLINDNESS, 500), 1.0F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 500), 1.0F)
            .effect(new MobEffectInstance(MobEffects.UNLUCK, 500), 1.0F)
            .effect(new MobEffectInstance(MobEffects.WEAKNESS, 500), 1.0F)
            .build();
    public static final FoodProperties GILDEDMASHEDPOTATO_PROP = new FoodProperties.Builder().nutrition(5).saturationMod(0.2F)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 3), 1.0F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 3), 1.0F)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1), 1.0F)
            .build();
    public static final FoodProperties MANADRINK_PROP = new FoodProperties.Builder().nutrition(0).saturationMod(0F)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0), 1.0F)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0), 1.0F)
            .effect(new MobEffectInstance(MobEffects.JUMP, 1200, 0), 1.0F)
            .build();
    public static final FoodProperties FRIEDCHICKEN_PROP = new FoodProperties.Builder().nutrition(6).saturationMod(0.5F).build();

    public static final RegistryObject<Item> spiritfuel = ITEMS.register(LibItemNames.SPIRITFUEL, () -> new Item(defaultBuilder().food(SPIRITFUEL_PROP)));
    public static final RegistryObject<Item> nightmarefuel = ITEMS.register(LibItemNames.NIGHTMAREFUEL, () -> new ItemNightmareFuel(defaultBuilder().food(NIGHTMAREFUEL_PROP)));
    public static final RegistryObject<Item> friedchicken = ITEMS.register(LibItemNames.FRIEDCHICKEN, () -> new Item(defaultBuilder().food(FRIEDCHICKEN_PROP)));
    public static final RegistryObject<Item> gildedmashedpotato = ITEMS.register(LibItemNames.GILDEDMASHEDPOTATO, () -> new Item(defaultBuilder().food(GILDEDMASHEDPOTATO_PROP)));

    public static final RegistryObject<Item> motor = ITEMS.register(LibItemNames.MOTOR, ItemMotor::new);
    public static final RegistryObject<Item> gemofconquest = ITEMS.register(LibItemNames.GEMOFCONQUEST, ItemGemOfConquest::new);
    public static final RegistryObject<Item> firstfractal = ITEMS.register(LibItemNames.FIRSTFRACTAL, ItemFirstFractal::new);
    public static final RegistryObject<Item> cosmiccarkey = ITEMS.register(LibItemNames.COSMICCARKEY, ItemCosmicCarKey::new);
    public static final RegistryObject<Item> flamescionweapon = ITEMS.register(LibItemNames.FLAMESCIONWEAPON, ItemFlamescionWeapon::new);
    public static final RegistryObject<Item> silverbullet = ITEMS.register(LibItemNames.SILVERBULLET, () -> new ItemSilverBullet(unstackable()));
    public static final RegistryObject<Item> walkingcane = ITEMS.register(LibItemNames.WALKINGCANE, () -> new ItemWalkingCane(unstackable()));
    public static final RegistryObject<Item> manareader = ITEMS.register(LibItemNames.MANAREADER, () -> new ItemManaReader(unstackable()));
    public static final RegistryObject<Item> shadowkatana = ITEMS.register(LibItemNames.SHADOWKATANA, () -> new ItemShadowKatana(unstackable()));
    public static final RegistryObject<Item> rodofdiscord = ITEMS.register(LibItemNames.RODOFDISCORD, () -> new ItemRodOfDiscord(unstackable()));
    public static final RegistryObject<Item> uuzfan = ITEMS.register(LibItemNames.UUZFAN, () -> new ItemUUZFan(unstackable()));

    public static final RegistryObject<Item> peaceamulet = ITEMS.register(LibItemNames.PEACEAMULET, () -> new ItemBauble(unstackable()));
    public static final RegistryObject<Item> aerostone = ITEMS.register(LibItemNames.AEROSTONE, () -> new ItemAeroStone(unstackable()));
    public static final RegistryObject<Item> earthstone = ITEMS.register(LibItemNames.EARTHSTONE, () -> new ItemEarthStone(unstackable()));
    public static final RegistryObject<Item> aquastone = ITEMS.register(LibItemNames.AQUASTONE, () -> new ItemAquaStone(unstackable()));
    public static final RegistryObject<Item> ignisstone = ITEMS.register(LibItemNames.IGNISSTONE, () -> new ItemIgnisStone(unstackable()));
    public static final RegistryObject<Item> thecommunity = ITEMS.register(LibItemNames.THECOMMUNITY, () -> new ItemTheCommunity(unstackable()));
    public static final RegistryObject<Item> froststar = ITEMS.register(LibItemNames.FROSTSTAR, () -> new ItemFrostStar(unstackable()));
    public static final RegistryObject<Item> deathring = ITEMS.register(LibItemNames.DEATHRING, () -> new ItemDeathRing(unstackable()));
    public static final RegistryObject<Item> manadrivering = ITEMS.register(LibItemNames.MANADRIVERRING, () -> new ItemManaDriveRing(unstackable()));
    public static final RegistryObject<Item> natureorb = ITEMS.register(LibItemNames.NATUREORB, () -> new ItemNatureOrb(unstackable()));
    public static final RegistryObject<Item> powerglove = ITEMS.register(LibItemNames.POWERGLOVE, () -> new ItemPowerGlove(unstackable()));
    public static final RegistryObject<Item> jingweifeather = ITEMS.register(LibItemNames.JINGWEIFEATHER, () -> new ItemJingweiFeather(unstackable()));
    public static final RegistryObject<Item> motoraccessory = ITEMS.register(LibItemNames.MOTORACCESSORY, () -> new ItemMotorAccessory(unstackable()));
    public static final RegistryObject<Item> cosmiccarkeyaccessory = ITEMS.register(LibItemNames.COSMICCARKEYACCESSORY, () -> new ItemCosmicCarKeyAccessory(unstackable()));

    public static final RegistryObject<Item> sagesmanaring = ITEMS.register(LibItemNames.SAGES_MANA_RING, () -> new ItemSagesManaRing(relic()));
    public static final RegistryObject<Item> excaliber = ITEMS.register(LibItemNames.EXCALIBER, () -> new ItemExcaliber(relic()));
    public static final RegistryObject<Item> failnaught = ITEMS.register(LibItemNames.FAILNAUGHT, () -> new ItemFailnaught(relic()));
    public static final RegistryObject<Item> influxwaver = ITEMS.register(LibItemNames.INFLUXWAVER, () -> new ItemInfluxWaver(relic()));
    public static final RegistryObject<Item> trueterrablade = ITEMS.register(LibItemNames.TRUETERRABLADE, () -> new ItemTrueTerrablade(relic()));
    public static final RegistryObject<Item> trueshadowkatana = ITEMS.register(LibItemNames.TRUESHADOWKATANA, () -> new ItemTrueShadowKatana(relic()));
    public static final RegistryObject<Item> starwrath = ITEMS.register(LibItemNames.STARWRATH, () -> new ItemStarWrath(relic()));
    public static final RegistryObject<Item> buddhistrelics = ITEMS.register(LibItemNames.BUDDHISTRELICS, () -> new ItemBuddhistrelics(relic()));
    public static final RegistryObject<Item> camera = ITEMS.register(LibItemNames.CAMERA, () -> new ItemCamera(relic()));
    public static final RegistryObject<Item> coregod = ITEMS.register(LibItemNames.COREGOD, () -> new ItemCoreGod(relic()));
    public static final RegistryObject<Item> sunring = ITEMS.register(LibItemNames.SUNRING, () -> new ItemSunRing(relic()));
    public static final RegistryObject<Item> moonpendant = ITEMS.register(LibItemNames.MOONPENDANT, () -> new ItemMoonPendant(relic()));
    public static final RegistryObject<Item> potatochips = ITEMS.register(LibItemNames.POTATOCHIPS, () -> new ItemPotatoChips(unstackable()));

    public static final RegistryObject<Item> spirit = ITEMS.register(LibItemNames.SPIRIT, () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> orichalcos = ITEMS.register(LibItemNames.ORICHALCOS, () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> gildedpotato = ITEMS.register(LibItemNames.GILDEDPOTATO, () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> heromedal = ITEMS.register(LibItemNames.HEROMEDAL, () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> shadowium = ITEMS.register(LibItemNames.SHADOWIUM, () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> goldcloth = ITEMS.register(LibItemNames.GOLDCLOTH, () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> photonium = ITEMS.register(LibItemNames.PHONTONIUM, () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> emptybottle = ITEMS.register(LibItemNames.EMPTYBOTTLE, () -> new ItemEmptyBottle(defaultBuilder()));
    public static final RegistryObject<Item> aerialite = ITEMS.register(LibItemNames.AERIALITE, () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> thechaos = ITEMS.register(LibItemNames.THECHAOS, () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> theorigin = ITEMS.register(LibItemNames.THEORIGIN, () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> theend = ITEMS.register(LibItemNames.THEEND, () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> theuniverse = ITEMS.register(LibItemNames.THEUNIVERSE, () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> universalpetal = ITEMS.register(LibItemNames.UNIVERSALPETAL, () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> elementrune = ITEMS.register(LibItemNames.ELEMENTRUNE, () -> new Item(defaultBuilder()));
    public static final RegistryObject<Item> sinrune = ITEMS.register(LibItemNames.SINRUNE, () -> new Item(defaultBuilder()));

    public static final RegistryObject<Item> challengeticket = ITEMS.register(LibItemNames.CHALLENGETICKET, () -> new ItemChallengeTicket(defaultBuilder()));

    public static final RegistryObject<Item> manadrink = ITEMS.register(LibItemNames.MANADRINK, () -> new ItemManaDrink(defaultBuilder().food(MANADRINK_PROP)));
    public static final RegistryObject<Item> cocktail = ITEMS.register(LibItemNames.BREW_COCKTAIL, () -> new ItemCocktail(unstackable()));
    public static final RegistryObject<Item> infinitewine = ITEMS.register(LibItemNames.BREW_INFINITEWINE, () -> new ItemInfiniteWine(unstackable()));
    public static final RegistryObject<Item> splashgrenade = ITEMS.register(LibItemNames.BREW_SPLASHGRENADE, () -> new ItemSplashGrenade(defaultBuilder().stacksTo(32)));

    public static final RegistryObject<Item> lenssmelt = ITEMS.register(LibItemNames.LENS_SMELT, () -> new ItemLens(unstackable(), new LensSmelt(), vazkii.botania.common.item.lens.LensItem.PROP_TOUCH));
    public static final RegistryObject<Item> lensmana = ITEMS.register(LibItemNames.LENS_MANA, () -> new ItemLens(unstackable(), new LensMana(), vazkii.botania.common.item.lens.LensItem.PROP_INTERACTION));
    public static final RegistryObject<Item> lenstrace = ITEMS.register(LibItemNames.LENS_TRACE, () -> new ItemLens(unstackable(), new LensTrace(), vazkii.botania.common.item.lens.LensItem.PROP_CONTROL));
    public static final RegistryObject<Item> lenspush = ITEMS.register(LibItemNames.LENS_PUSH, () -> new ItemLens(unstackable(), new LensPush(), vazkii.botania.common.item.lens.LensItem.PROP_INTERACTION));
    public static final RegistryObject<Item> lenspotion = ITEMS.register(LibItemNames.LENS_POTION, () -> new ItemLens(unstackable(), new LensPotion(), vazkii.botania.common.item.lens.LensItem.PROP_INTERACTION));
    public static final RegistryObject<Item> lenssupercondutor = ITEMS.register(LibItemNames.LENS_SUPERCONDUCTOR, () -> new ItemLens(unstackable(), new LensSuperconductor(), vazkii.botania.common.item.lens.LensItem.PROP_POWER));

    public static final RegistryObject<Item> foxear = ITEMS.register(LibItemNames.FOX_EAR, () -> new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.FOX_EAR, unstackable()));
    public static final RegistryObject<Item> foxmask = ITEMS.register(LibItemNames.FOX_MASK, () -> new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.FOX_MASK, unstackable()));
    public static final RegistryObject<Item> blackglasses = ITEMS.register(LibItemNames.BLACK_GLASSES, () -> new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.BLACK_GLASSES, unstackable()));
    public static final RegistryObject<Item> thuglife = ITEMS.register(LibItemNames.THUG_LIFE, () -> new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.THUG_LIFE, unstackable()));
    public static final RegistryObject<Item> redscarf = ITEMS.register(LibItemNames.RED_SCARF, () -> new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.RED_SCARF, unstackable()));
    public static final RegistryObject<Item> supercrown = ITEMS.register(LibItemNames.SUPER_CROWN, () -> new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.SUPER_CROWN, unstackable()));
    public static final RegistryObject<Item> pylon = ITEMS.register(LibItemNames.PYLON, () -> new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.PYLON, unstackable()));
    public static final RegistryObject<Item> mask = ITEMS.register(LibItemNames.MASK, () -> new ItemBaubleCosmetic(ItemBaubleCosmetic.Variant.MASK, unstackable()));

    public static final List<WeightCategory> categoryListA = new ArrayList<>();
    public static final List<WeightCategory> categoryListB = new ArrayList<>();
    public static final List<WeightCategory> categoryListC = new ArrayList<>();
    public static final List<WeightCategory> categoryListD = new ArrayList<>();

    public static final RegistryObject<Item> treasurebox = ITEMS.register(LibItemNames.TREASUREBOX, () -> new ItemTreasureBox(unstackable()));
    public static final RegistryObject<Item> rewardbaga = ITEMS.register(LibItemNames.REWARDBAGA, () -> new ItemRewardBag(defaultBuilder(), categoryListA));
    public static final RegistryObject<Item> rewardbagb = ITEMS.register(LibItemNames.REWARDBAGB, () -> new ItemRewardBag(defaultBuilder(), categoryListB));
    public static final RegistryObject<Item> rewardbagc = ITEMS.register(LibItemNames.REWARDBAGC, () -> new ItemRewardBag(defaultBuilder(), categoryListC));
    public static final RegistryObject<Item> rewardbagd = ITEMS.register(LibItemNames.REWARDBAGD, () -> new ItemRewardBag(defaultBuilder(), categoryListD));

    public static final RegistryObject<Item> armor_maid_helm = ITEMS.register(LibItemNames.ARMOR_MAID_HELM, () -> new ItemMaidHelm(unstackable()));
    public static final RegistryObject<Item> armor_maid_chest = ITEMS.register(LibItemNames.ARMOR_MAID_CHEST, () -> new ItemMaidArmor(ArmorItem.Type.CHESTPLATE, unstackable()));
    public static final RegistryObject<Item> armor_maid_legs = ITEMS.register(LibItemNames.ARMOR_MAID_LEGS, () -> new ItemMaidArmor(ArmorItem.Type.LEGGINGS, unstackable()));
    public static final RegistryObject<Item> armor_maid_boots = ITEMS.register(LibItemNames.ARMOR_MAID_BOOTS, () -> new ItemMaidArmor(ArmorItem.Type.BOOTS, unstackable()));

    public static final RegistryObject<Item> armor_miku_helm = ITEMS.register(LibItemNames.ARMOR_MIKU_HELM, () -> new ItemMikuArmor(ArmorItem.Type.HELMET, unstackable()));
    public static final RegistryObject<Item> armor_miku_chest = ITEMS.register(LibItemNames.ARMOR_MIKU_CHEST, () -> new ItemMikuArmor(ArmorItem.Type.CHESTPLATE, unstackable()));
    public static final RegistryObject<Item> armor_miku_legs = ITEMS.register(LibItemNames.ARMOR_MIKU_LEGS, () -> new ItemMikuArmor(ArmorItem.Type.LEGGINGS, unstackable()));
    public static final RegistryObject<Item> armor_miku_boots = ITEMS.register(LibItemNames.ARMOR_MIKU_BOOTS, () -> new ItemMikuArmor(ArmorItem.Type.BOOTS, unstackable()));

    public static final RegistryObject<Item> armor_goblinslayer_helm = ITEMS.register(LibItemNames.ARMOR_GOBLINSLAYER_HELM, () -> new ItemGoblinSlayerArmor(ArmorItem.Type.HELMET, unstackable()));
    public static final RegistryObject<Item> armor_goblinslayer_chest = ITEMS.register(LibItemNames.ARMOR_GOBLINSLAYER_CHEST, () -> new ItemGoblinSlayerArmor(ArmorItem.Type.CHESTPLATE, unstackable()));
    public static final RegistryObject<Item> armor_goblinslayer_legs = ITEMS.register(LibItemNames.ARMOR_GOBLINSLAYER_LEGS, () -> new ItemGoblinSlayerArmor(ArmorItem.Type.LEGGINGS, unstackable()));
    public static final RegistryObject<Item> armor_goblinslayer_boots = ITEMS.register(LibItemNames.ARMOR_GOBLINSLAYER_BOOTS, () -> new ItemGoblinSlayerArmor(ArmorItem.Type.BOOTS, unstackable()));

    public static final RegistryObject<Item> armor_shadowwarrior_helm = ITEMS.register(LibItemNames.ARMOR_SHADOWWARRIOR_HELM, () -> new ItemShadowWarriorArmor(ArmorItem.Type.HELMET, unstackable()));
    public static final RegistryObject<Item> armor_shadowwarrior_chest = ITEMS.register(LibItemNames.ARMOR_SHADOWWARRIOR_CHEST, () -> new ItemShadowWarriorArmor(ArmorItem.Type.CHESTPLATE, unstackable()));
    public static final RegistryObject<Item> armor_shadowwarrior_legs = ITEMS.register(LibItemNames.ARMOR_SHADOWWARRIOR_LEGS, () -> new ItemShadowWarriorArmor(ArmorItem.Type.LEGGINGS, unstackable()));
    public static final RegistryObject<Item> armor_shadowwarrior_boots = ITEMS.register(LibItemNames.ARMOR_SHADOWWARRIOR_BOOTS, () -> new ItemShadowWarriorArmor(ArmorItem.Type.BOOTS, unstackable()));

    public static final RegistryObject<Item> armor_shootingguardian_helm = ITEMS.register(LibItemNames.ARMOR_SHOOTINGGUARDIAN_HELM, () -> new ItemShootingGuardianHelm(unstackable()));
    public static final RegistryObject<Item> armor_shootingguardian_chest = ITEMS.register(LibItemNames.ARMOR_SHOOTINGGUARDIAN_CHEST, () -> new ItemShootingGuardianArmor(ArmorItem.Type.CHESTPLATE, unstackable()));
    public static final RegistryObject<Item> armor_shootingguardian_legs = ITEMS.register(LibItemNames.ARMOR_SHOOTINGGUARDIAN_LEGS, () -> new ItemShootingGuardianArmor(ArmorItem.Type.LEGGINGS, unstackable()));
    public static final RegistryObject<Item> armor_shootingguardian_boots = ITEMS.register(LibItemNames.ARMOR_SHOOTINGGUARDIAN_BOOTS, () -> new ItemShootingGuardianArmor(ArmorItem.Type.BOOTS, unstackable()));

    public static final RegistryObject<Item> recordego = ITEMS.register(LibItemNames.RECORDEGO, () -> new BotaniaRecordItem(1, ModSounds.swordland.get(), unstackable().rarity(Rarity.RARE), 4000));
    public static final RegistryObject<Item> recordherrscher = ITEMS.register(LibItemNames.RECORDHERRSCHER, () -> new BotaniaRecordItem(1, ModSounds.salvation.get(), unstackable().rarity(Rarity.RARE), 4000));

    public static Item.Properties defaultBuilder() {
        return new Item.Properties();
    }

    public static Item.Properties unstackable() {
        return defaultBuilder().stacksTo(1);
    }

    public static Item.Properties relic(){
        return unstackable().rarity(Rarity.EPIC);
    }

    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(LibMisc.MOD_ID, path);
    }

    public static void initCategories() {
        categoryListA.add(new WeightCategory(new ItemStack(universalpetal.get(), 4), 10));
        categoryListA.add(new WeightCategory(new ItemStack(universalpetal.get(), 8), 10));
        categoryListA.add(new WeightCategory(new ItemStack(universalpetal.get(), 6), 30));

        categoryListB.add(new WeightCategory(new ItemStack(elementrune.get(), 2), 80));
        categoryListB.add(new WeightCategory(new ItemStack(sinrune.get(), 1), 20));

        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.BotaniaItems.manaSteel, 4), 15));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.BotaniaItems.manaPearl, 4), 15));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.BotaniaItems.manaDiamond, 4), 15));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.BotaniaItems.elementium, 3), 11));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.BotaniaItems.pixieDust, 3), 11));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.BotaniaItems.dragonstone, 3), 11));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.BotaniaItems.manaPowder, 8), 10));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.BotaniaItems.terrasteel, 1), 9));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.BotaniaItems.lifeEssence, 4), 8));
        categoryListC.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.BotaniaItems.gaiaIngot, 1), 7));
        categoryListC.add(new WeightCategory(new ItemStack(heromedal.get(), 1), 1));

        categoryListD.add(new WeightCategory(new ItemStack(net.minecraft.world.item.Items.COAL, 6), 40));
        categoryListD.add(new WeightCategory(new ItemStack(net.minecraft.world.item.Items.IRON_INGOT, 4), 36));
        categoryListD.add(new WeightCategory(new ItemStack(net.minecraft.world.item.Items.GOLD_INGOT, 4), 24));
        categoryListD.add(new WeightCategory(new ItemStack(net.minecraft.world.item.Items.REDSTONE, 8), 22));
        categoryListD.add(new WeightCategory(new ItemStack(net.minecraft.world.item.Items.ENDER_PEARL, 4), 20));
        categoryListD.add(new WeightCategory(new ItemStack(net.minecraft.world.item.Items.DIAMOND, 1), 18));
        categoryListD.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.BotaniaItems.blackerLotus, 2), 16));
        categoryListD.add(new WeightCategory(new ItemStack(vazkii.botania.common.item.BotaniaItems.overgrowthSeed, 1), 12));
        categoryListD.add(new WeightCategory(new ItemStack(buddhistrelics.get()), 1));
    }

}
