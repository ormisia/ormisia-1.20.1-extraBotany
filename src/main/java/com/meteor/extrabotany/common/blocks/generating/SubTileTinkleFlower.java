package com.meteor.extrabotany.common.blocks.generating;

import com.meteor.extrabotany.common.blocks.ModSubtiles;
import com.meteor.extrabotany.common.handler.AdvancementHandler;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

public class SubTileTinkleFlower extends GeneratingFlowerBlockEntity {

    private static final int RANGE = 8;
    private static final String TAG_TIME = "time";
    private int time = 0;

    public SubTileTinkleFlower(BlockPos pos, BlockState state) {
        super(ModSubtiles.TINKLEFLOWER.get(), pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        if(!level.isClientSide && level.getGameTime() % 20L == 0){
            CompoundTag tag = getPersistentData();
            int time = tag.getByte(TAG_TIME);
            int prevTime = time;
            for(Player player : getLevel().getEntitiesOfClass(Player.class, new AABB(getEffectivePos().offset(-RANGE, -RANGE, -RANGE), getEffectivePos().offset(RANGE + 1, RANGE + 1, RANGE + 1)))) {
                // 1.20.1: use delta movement (velocity) for reliable movement detection
                double vx = player.getDeltaMovement().x;
                double vy = player.getDeltaMovement().y;
                double vz = player.getDeltaMovement().z;
                double vel = Math.sqrt(vx*vx + vy*vy + vz*vz);
                if(player.hasEffect(MobEffects.MOVEMENT_SPEED))
                    vel *= 1.2;

                time += Mth.clamp((int) (vel * 10.0), 0, 8);

                final int limit = 10;

                if(time >= limit){
                    if(getMana() < getMaxMana())
                        addMana(30);

                    player.causeFoodExhaustion(0.02F);
                    try {
                        AdvancementHandler.INSTANCE.grantAdvancement((ServerPlayer) player, LibAdvancementNames.TINKLEUSE);
                    } catch (Exception ignored) {
                    }
                    time %= limit;
                }

                if(time != prevTime)
                    tag.putByte(TAG_TIME, (byte) time);
            }
        }

    }

    @Override
    public int getMaxMana() {
        return 1000;
    }

    @Override
    public int getColor() {
        return 0xCCFF00;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }

    @Override
    public void writeToPacketNBT(CompoundTag cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt(TAG_TIME, time);
    }

    @Override
    public void readFromPacketNBT(CompoundTag cmp) {
        super.readFromPacketNBT(cmp);
        time = cmp.getInt(TAG_TIME);
    }

}
