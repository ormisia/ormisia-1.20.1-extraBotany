package com.meteor.extrabotany.common.network.flamescion;

import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.capability.IFlamescion;
import com.meteor.extrabotany.common.entities.EntityFlamescionUlt;
import com.meteor.extrabotany.common.handler.FlamescionHandler;
import com.meteor.extrabotany.common.potions.ModPotions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;



public class FlamescionQPack {

    public FlamescionQPack(FriendlyByteBuf buffer) {

    }

    public FlamescionQPack() {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayer player = ctx.get().getSender();
                LazyOptional<IFlamescion> cap = player.getCapability(CapabilityHandler.FLAMESCION_CAPABILITY);
                cap.ifPresent((c) -> {
                    c.setEnergy(FlamescionHandler.MAX_FLAMESCION_ENERGY);
                    c.setOverloaded(true);
                });
                // TODO: EntityFlamescionUlt constructor signature must be adapted when the entity is ported to 1.20.1
                EntityFlamescionUlt ult = new EntityFlamescionUlt(player.level(), player);
                Vec3 lookVec = player.getLookAngle().normalize().scale(5D);
                Vec3 spawnPoint = player.position().add(lookVec.x, 0.25D, lookVec.z);
                ult.setPos(spawnPoint.x, spawnPoint.y, spawnPoint.z);
                player.level().addFreshEntity(ult);
                player.addEffect(new MobEffectInstance(ModPotions.timelock.get(), 40));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
