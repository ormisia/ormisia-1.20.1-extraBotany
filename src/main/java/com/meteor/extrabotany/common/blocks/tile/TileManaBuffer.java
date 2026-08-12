package com.meteor.extrabotany.common.blocks.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.ManaReceiver;
import vazkii.botania.api.mana.spark.ManaSpark;
import vazkii.botania.api.mana.spark.SparkAttachable;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntity;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;
import vazkii.botania.common.block.block_entity.mana.ThrottledPacket;

import java.util.List;

public class TileManaBuffer extends BotaniaBlockEntity implements ManaReceiver, SparkAttachable, ThrottledPacket {

    private static final BlockPos[] POOL_LOCATIONS = { new BlockPos(1, 0, 0), new BlockPos(0, 0, 1),
            new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, -1, 0) };

    public static final int MAX_MANA = 64000000;
    public static final int TRANSFER_SPEED = 1000;

    private static final String TAG_MANA = "mana";

    private int mana;

    private int ticks = 0;
    private boolean sendPacket = false;

    public TileManaBuffer(BlockPos pos, BlockState state) {
        super(ModTiles.MANA_BUFFER.get(), pos, state);
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, TileManaBuffer self) {
        if (self.sendPacket && self.ticks % 10 == 0) {
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(self);
            self.sendPacket = false;
        }

        // Pull mana from the surrounding pools (4 sides + below) into the buffer
        for (BlockPos o : POOL_LOCATIONS)
            if (world.getBlockEntity(pos.offset(o)) instanceof ManaPoolBlockEntity) {
                ManaPoolBlockEntity p = (ManaPoolBlockEntity) world.getBlockEntity(pos.offset(o));
                int manaToGet = Math.min(TRANSFER_SPEED, p.getCurrentMana());
                int space = Math.max(0, MAX_MANA - self.getCurrentMana());
                int current = Math.min(space, manaToGet);
                p.receiveMana(-current);
                self.receiveMana(current);
            } else if (world.getBlockEntity(pos.offset(o)) instanceof TileManaBuffer) {
                TileManaBuffer p = (TileManaBuffer) world.getBlockEntity(pos.offset(o));
                int manaToGet = Math.min(TRANSFER_SPEED, p.getCurrentMana());
                int space = Math.max(0, MAX_MANA - self.getCurrentMana());
                int current = Math.min(space, manaToGet);
                p.receiveMana(-current);
                self.receiveMana(current);
            }

        // Push mana from the buffer to the pool above
        if (world.getBlockEntity(pos.above()) instanceof ManaPoolBlockEntity) {
            ManaPoolBlockEntity p = (ManaPoolBlockEntity) world.getBlockEntity(pos.above());
            int manaToGet = Math.min(TRANSFER_SPEED, self.getCurrentMana());
            int space = Math.max(0, p.getMaxMana() - p.getCurrentMana());
            int current = Math.min(space, manaToGet);
            p.receiveMana(current);
            self.receiveMana(-current);
        } else if (world.getBlockEntity(pos.above()) instanceof TileManaBuffer) {
            TileManaBuffer p = (TileManaBuffer) world.getBlockEntity(pos.above());
            int manaToGet = Math.min(TRANSFER_SPEED, self.getCurrentMana());
            int space = Math.max(0, p.MAX_MANA - p.getCurrentMana());
            int current = Math.min(space, manaToGet);
            p.receiveMana(current);
            self.receiveMana(-current);
        }

        self.ticks++;
    }

    @Override
    public void writePacketNBT(CompoundTag cmp) {
        cmp.putInt(TAG_MANA, mana);
    }

    @Override
    public void readPacketNBT(CompoundTag cmp) {
        mana = cmp.getInt(TAG_MANA);
    }

    @Override
    public boolean canAttachSpark(ItemStack stack) {
        return true;
    }

    @Override
    public void attachSpark(ManaSpark entity) {

    }

    @Override
    public int getAvailableSpaceForMana() {
        int space = Math.max(0, MAX_MANA - getCurrentMana());
        if (space > 0) {
            return space;
        } else {
            return 0;
        }
    }

    @Override
    public ManaSpark getAttachedSpark() {
        List<Entity> sparks = level.getEntitiesOfClass(Entity.class, new AABB(worldPosition.above(), worldPosition.above().offset(1, 1, 1)), e -> e instanceof ManaSpark);
        if (sparks.size() == 1) {
            Entity e = sparks.get(0);
            return (ManaSpark) e;
        }

        return null;
    }

    @Override
    public boolean areIncomingTranfersDone() {
        return false;
    }

    @Override
    public boolean isFull() {
        return getCurrentMana() >= MAX_MANA;
    }

    @Override
    public void receiveMana(int mana) {
        int old = this.mana;
        this.mana = Math.max(0, Math.min(getCurrentMana() + mana, MAX_MANA));
        if (old != this.mana) {
            setChanged();
            markDispatchable();
        }
    }

    @Override
    public boolean canReceiveManaFromBursts() {
        return true;
    }

    @Override
    public int getCurrentMana() {
        return mana;
    }

    @Override
    public void markDispatchable() {
        sendPacket = true;
    }

    @Override
    public Level getManaReceiverLevel() {
        return level;
    }

    @Override
    public BlockPos getManaReceiverPos() {
        return worldPosition;
    }
}
