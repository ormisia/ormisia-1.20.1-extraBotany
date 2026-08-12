package com.meteor.extrabotany.common.capability;

import net.minecraft.nbt.CompoundTag;

public class FlamescionCapability implements IFlamescion{

    private int energy;
    private boolean overloaded;
    private boolean isDirty;

    public FlamescionCapability(int energy, boolean overloaded){
        this.energy = energy;
        this.overloaded = overloaded;
        this.isDirty = false;
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }

    @Override
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public boolean isOverloaded() {
        return this.overloaded;
    }

    @Override
    public void setOverloaded(boolean overloaded) {
        this.overloaded = overloaded;
    }

    @Override
    public void markDirty(boolean dirty) {
        this.isDirty = dirty;
    }

    @Override
    public boolean isDirty() {
        return this.isDirty;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundNBT = new CompoundTag();
        compoundNBT.putInt("flamescion", this.energy);
        compoundNBT.putBoolean("overloaded", this.overloaded);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.energy = nbt.getInt("flamescion");
        this.overloaded = nbt.getBoolean("overloaded");
    }
}
