package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

public class SubTileEdelweiss extends GeneratingFlowerBlockEntity {

    private static final String TAG_BURN_TIME = "burnTime";
    private static final int RANGE = 1;
    private int burnTime = 0;

    public SubTileEdelweiss(BlockPos pos, BlockState state) {
        super(ModSubtiles.EDELWEISS.get(), pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();
        // Re-bind to the nearest mana collector whenever unbound (Botania only auto-binds on the first tick)
        if (!level.isClientSide && (getBindingPos() == null || !isValidBinding())) {
            setBindingPos(findClosestTarget());
        }


        if (burnTime > 0) {
            burnTime--;
        }

        if (findBoundTile() != null) {
            if (burnTime == 0) {
                if (getMana() < getMaxMana()) {
                    for (SnowGolem golem : getLevel().getEntitiesOfClass(SnowGolem.class, new AABB(getEffectivePos().offset(-RANGE, -RANGE, -RANGE), getEffectivePos().offset(RANGE + 1, RANGE + 1, RANGE + 1)))) {
                        if(!golem.isRemoved()){
                            golem.discard();
                            addMana(1600);
                            burnTime+=5;
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void writeToPacketNBT(CompoundTag cmp) {
        super.writeToPacketNBT(cmp);

        cmp.putInt(TAG_BURN_TIME, burnTime);
    }

    @Override
    public void readFromPacketNBT(CompoundTag cmp) {
        super.readFromPacketNBT(cmp);

        burnTime = cmp.getInt(TAG_BURN_TIME);
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }

    @Override
    public int getMaxMana() {
        return 1000;
    }

    @Override
    public int getColor() {
        return 0X4169E1;
    }

}
