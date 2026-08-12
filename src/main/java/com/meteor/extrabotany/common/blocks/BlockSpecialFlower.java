package com.meteor.extrabotany.common.blocks;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import vazkii.botania.api.block_entity.SpecialFlowerBlockEntity;
import vazkii.botania.forge.block.ForgeSpecialFlowerBlock;

import java.util.function.Supplier;

public class BlockSpecialFlower extends ForgeSpecialFlowerBlock {

    public BlockSpecialFlower(MobEffect stewEffect, int stewDuration, BlockBehaviour.Properties props, Supplier<BlockEntityType<? extends SpecialFlowerBlockEntity>> teProvider) {
        super(stewEffect, stewDuration, props, teProvider);
    }

}
