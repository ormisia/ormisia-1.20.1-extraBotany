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

    // radius 8 -> a 17x17x17 working cube around the flower
    private static final int RANGE = 8;
    private static final String TAG_TIME = "time";
    private int time = 0;

    public SubTileTinkleFlower(BlockPos pos, BlockState state) {
        super(ModSubtiles.TINKLEFLOWER.get(), pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        // Re-bind to the nearest mana collector whenever unbound (Botania only auto-binds on the first tick)
        if (!level.isClientSide && (getBindingPos() == null || !isValidBinding())) {
            setBindingPos(findClosestTarget());
        }

        if(!level.isClientSide && level.getGameTime() % 20L == 0){
            CompoundTag tag = getPersistentData();
            int time = tag.getByte(TAG_TIME);
            int prevTime = time;

            AABB box = new AABB(getEffectivePos().offset(-RANGE, -RANGE, -RANGE), getEffectivePos().offset(RANGE + 1, RANGE + 1, RANGE + 1));
            for(Player player : getLevel().players()) {
                if(!(player instanceof ServerPlayer)) continue;
                if(!box.contains(player.getX(), player.getY(), player.getZ()))
                    continue;

                // Compare the player's position to the one recorded 20 ticks ago, tracked per player.
                // getX()/getY()/getZ() always reflect the real server position, so any movement is caught.
                String keyX = "px_" + player.getStringUUID();
                String keyY = "py_" + player.getStringUUID();
                String keyZ = "pz_" + player.getStringUUID();
                if(!tag.contains(keyX)) {
                    tag.putDouble(keyX, player.getX());
                    tag.putDouble(keyY, player.getY());
                    tag.putDouble(keyZ, player.getZ());
                    continue;
                }
                double dx = player.getX() - tag.getDouble(keyX);
                double dy = player.getY() - tag.getDouble(keyY);
                double dz = player.getZ() - tag.getDouble(keyZ);
                double moved = Math.sqrt(dx*dx + dy*dy + dz*dz);
                tag.putDouble(keyX, player.getX());
                tag.putDouble(keyY, player.getY());
                tag.putDouble(keyZ, player.getZ());

                if(player.hasEffect(MobEffects.MOVEMENT_SPEED))
                    moved *= 1.2;

                time += Mth.clamp((int) (moved * 10.0), 0, 8);

                final int limit = 10;

                if(time >= limit){
                    if(getMana() < getMaxMana())
                        addMana(120); // 4x the original 30 per hit

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
        return 4000; // scaled up to match the 4x generation rate
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
