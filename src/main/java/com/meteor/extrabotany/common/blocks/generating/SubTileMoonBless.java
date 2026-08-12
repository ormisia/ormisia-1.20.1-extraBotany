package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SubTileMoonBless extends SubTileSunBless{

    public SubTileMoonBless(BlockPos pos, BlockState state) {
        super(ModSubtiles.MOONBLESS.get(), pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();
        // Re-bind to the nearest mana collector whenever unbound (Botania only auto-binds on the first tick)
        if (!level.isClientSide && (getBindingPos() == null || !isValidBinding())) {
            setBindingPos(findClosestTarget());
        }


        if (getMana() < getMaxMana() && !this.getLevel().isDay() && this.ticksExisted % 4 == 0)
            addMana(1);
    }

    @Override
    public int getColor() {
        return 0xFFFF00;
    }

}
