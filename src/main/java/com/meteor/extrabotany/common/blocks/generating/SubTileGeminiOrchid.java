package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

public class SubTileGeminiOrchid extends GeneratingFlowerBlockEntity {

    private static final BlockPos[] OFFSETS = { new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(-1, 0, 1), new BlockPos(-1, 0, -1), new BlockPos(1, 0, 1), new BlockPos(1, 0, -1) };
    private static final int RANGE = 1;

    public SubTileGeminiOrchid(BlockPos pos, BlockState state) {
        super(ModSubtiles.GEMINIORCHID.get(), pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        int tempMax = 700;
        int tempMin = 700;

        for(int i = 0; i < OFFSETS.length; i++){
            BlockPos pos = this.getEffectivePos().offset(OFFSETS[i]);
            BlockState state = this.getLevel().getBlockState(pos);
            Block block = state.getBlock();

            if(block instanceof LiquidBlock){
                FluidState fluid = state.getFluidState();
                int temp = fluid.getFluidType().getTemperature(fluid, getLevel(), pos);
                tempMax = Math.max(tempMax, temp);
                tempMin = Math.min(tempMin, temp);
            }
        }

        if(getMana() < getMaxMana() && ticksExisted % 8 == 0)
            addMana((int)(Math.abs(tempMax - tempMin)/100F));
    }

    @Override
    public int getMaxMana() {
        return 1000;
    }

    @Override
    public int getColor() {
        return 0x99CCFF;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }

}
