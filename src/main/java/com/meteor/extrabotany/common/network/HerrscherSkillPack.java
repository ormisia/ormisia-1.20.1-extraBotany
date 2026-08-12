package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.common.entities.EntitySlash;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;



public class HerrscherSkillPack {

    private BlockPos pos;

    public HerrscherSkillPack(FriendlyByteBuf buffer) {
        this.pos = BlockPos.of(buffer.readLong());
    }

    public HerrscherSkillPack(BlockPos pos) {
        this.pos = pos;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeLong(this.pos.asLong());
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayer player = ctx.get().getSender();
                // TODO: EntitySlash constructor signature must be adapted when the entity is ported to 1.20.1
                EntitySlash slash = new EntitySlash(player.level(), player);
                slash.setPos(this.pos.getX(), this.pos.getY(), this.pos.getZ());
                player.level().addFreshEntity(slash);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
