package com.meteor.extrabotany.common.core;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class Helper {

    public static Vec3 PosToVec(BlockPos pos){
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

}
