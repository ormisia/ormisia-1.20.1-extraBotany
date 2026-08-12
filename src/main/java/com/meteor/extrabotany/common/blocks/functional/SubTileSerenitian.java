package com.meteor.extrabotany.common.blocks.functional;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

public class SubTileSerenitian extends FunctionalFlowerBlockEntity {

    private static final int RANGE = 3;

    public SubTileSerenitian(BlockPos pos, BlockState state) {
        super(ModSubtiles.SERENITIAN.get(), pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();
        if (redstoneSignal > 0)
            return;

        for(int dx = -RANGE; dx <= RANGE; dx++)
            for(int dz = -RANGE; dz <= RANGE; dz++){
                BlockPos pos = getEffectivePos().offset(dx, 0, dz);
                BlockEntity tile = getLevel().getBlockEntity(pos);
                if(tile instanceof GeneratingFlowerBlockEntity flower){
                    // 1.20.1 已无被动花腐烂机制(passiveDecayTicks 被移除)
                }
            }
    }

    @Override
    public int getColor() {
        return 0x000000;
    }

    @Override
    public int getMaxMana() {
        return 1;
    }

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }

}
