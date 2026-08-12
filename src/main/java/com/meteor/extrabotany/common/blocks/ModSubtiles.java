package com.meteor.extrabotany.common.blocks;

import com.meteor.extrabotany.common.blocks.functional.SubTileAnnoyingFlower;
import com.meteor.extrabotany.common.blocks.functional.SubTileSerenitian;
import com.meteor.extrabotany.common.blocks.generating.SubTileBellFlower;
import com.meteor.extrabotany.common.blocks.generating.SubTileBloodyEnchantress;
import com.meteor.extrabotany.common.blocks.generating.SubTileEdelweiss;
import com.meteor.extrabotany.common.blocks.generating.SubTileGeminiOrchid;
import com.meteor.extrabotany.common.blocks.generating.SubTileMoonBless;
import com.meteor.extrabotany.common.blocks.generating.SubTileOmniViolet;
import com.meteor.extrabotany.common.blocks.generating.SubTileReikarLily;
import com.meteor.extrabotany.common.blocks.generating.SubTileSunBless;
import com.meteor.extrabotany.common.blocks.generating.SubTileTinkleFlower;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.api.block_entity.SpecialFlowerBlockEntity;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.FloatingSpecialFlowerBlock;
import vazkii.botania.common.item.block.SpecialFlowerBlockItem;

public class ModSubtiles {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LibMisc.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LibMisc.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LibMisc.MOD_ID);

    private static final BlockBehaviour.Properties FLOWER_PROPS = BlockBehaviour.Properties.copy(Blocks.POPPY);

    public static final RegistryObject<BlockEntityType<SubTileBellFlower>> BELL_FLOWER = createFlower("bellflower", MobEffects.MOVEMENT_SPEED, 360, SubTileBellFlower::new);
    public static final RegistryObject<BlockEntityType<SubTileEdelweiss>> EDELWEISS = createFlower("edelweiss", MobEffects.MOVEMENT_SLOWDOWN, 80, SubTileEdelweiss::new);
    public static final RegistryObject<BlockEntityType<SubTileSunBless>> SUNBLESS = createFlower("sunbless", MobEffects.LUCK, 1600, SubTileSunBless::new);
    public static final RegistryObject<BlockEntityType<SubTileMoonBless>> MOONBLESS = createFlower("moonbless", MobEffects.UNLUCK, 1600, SubTileMoonBless::new);
    public static final RegistryObject<BlockEntityType<SubTileGeminiOrchid>> GEMINIORCHID = createFlower("geminiorchid", MobEffects.GLOWING, 1600, SubTileGeminiOrchid::new);
    public static final RegistryObject<BlockEntityType<SubTileTinkleFlower>> TINKLEFLOWER = createFlower("tinkleflower", MobEffects.DIG_SPEED, 360, SubTileTinkleFlower::new);
    public static final RegistryObject<BlockEntityType<SubTileOmniViolet>> OMNIVIOLET = createFlower("omniviolet", MobEffects.REGENERATION, 360, SubTileOmniViolet::new);
    public static final RegistryObject<BlockEntityType<SubTileReikarLily>> REIKARLILY = createFlower("reikarlily", MobEffects.JUMP, 1600, SubTileReikarLily::new);
    public static final RegistryObject<BlockEntityType<SubTileAnnoyingFlower>> ANNOYING_FLOWER = createFlower("annoyingflower", MobEffects.HUNGER, 360, SubTileAnnoyingFlower::new);
    public static final RegistryObject<BlockEntityType<SubTileBloodyEnchantress>> BLOODY_ENCHANTRESS = createFlower("bloodyenchantress", MobEffects.WITHER, 360, SubTileBloodyEnchantress::new);
    public static final RegistryObject<BlockEntityType<SubTileSerenitian>> SERENITIAN = createFlower("serenitian", MobEffects.HERO_OF_THE_VILLAGE, 360, SubTileSerenitian::new);

    private static <T extends SpecialFlowerBlockEntity> RegistryObject<BlockEntityType<T>> createFlower(String name, MobEffect stewEffect, int stewDuration, BlockEntityType.BlockEntitySupplier<T> factory) {
        RegistryObject<BlockEntityType<T>> be = TILES.register(name, () -> BlockEntityType.Builder.of(factory, new Block[0]).build(null));
        RegistryObject<Block> block = BLOCKS.register(name, () -> new BlockSpecialFlower(stewEffect, stewDuration, FLOWER_PROPS, be::get));
        RegistryObject<Block> floating = BLOCKS.register("floating_" + name, () -> new FloatingSpecialFlowerBlock(BotaniaBlocks.FLOATING_PROPS, be::get));
        ITEMS.register(name, () -> new SpecialFlowerBlockItem(block.get(), new Item.Properties()));
        ITEMS.register("floating_" + name, () -> new SpecialFlowerBlockItem(floating.get(), new Item.Properties()));
        return be;
    }

}
