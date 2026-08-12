package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

public class SubTileSunBless extends GeneratingFlowerBlockEntity {

    private static final int RANGE = 2;

    public SubTileSunBless(BlockPos pos, BlockState state) {
        this(ModSubtiles.SUNBLESS.get(), pos, state);
    }

    protected SubTileSunBless(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();
        // Re-bind to the nearest mana collector whenever unbound (Botania only auto-binds on the first tick)
        if (!level.isClientSide && (getBindingPos() == null || !isValidBinding())) {
            setBindingPos(findClosestTarget());
        }


        if (getMana() < getMaxMana() && this.getLevel().isDay() && this.ticksExisted % 2 == 0)
            addMana(1);
    }

    @Override
    public int getMaxMana() {
        return 200;
    }

    @Override
    public int getColor() {
        return 0xFFA500;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }

}
