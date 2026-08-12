package com.meteor.extrabotany.common.blocks.tile;

import com.meteor.extrabotany.common.blocks.ModBlocks;
import com.meteor.extrabotany.common.libs.LibBlockNames;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTiles {

    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LibMisc.MOD_ID);

    public static final RegistryObject<BlockEntityType<TilePowerFrame>> POWER_FRAME = TILES.register(LibBlockNames.POWER_FRAME,
            () -> BlockEntityType.Builder.of(TilePowerFrame::new, ModBlocks.powerframe.get()).build(null));

    public static final RegistryObject<BlockEntityType<TileManaBuffer>> MANA_BUFFER = TILES.register(LibBlockNames.MANA_BUFFER,
            () -> BlockEntityType.Builder.of(TileManaBuffer::new, ModBlocks.manabuffer.get()).build(null));

}
