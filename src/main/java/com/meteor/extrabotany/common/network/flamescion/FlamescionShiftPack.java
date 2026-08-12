package com.meteor.extrabotany.common.network.flamescion;

import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.meteor.extrabotany.common.handler.FlamescionHandler;
import com.meteor.extrabotany.common.potions.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;



public class FlamescionShiftPack {

    public FlamescionShiftPack(FriendlyByteBuf buffer) {

    }

    public FlamescionShiftPack() {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                ServerPlayer player = ctx.get().getSender();
                Vec3 lookvec = player.getLookAngle().scale(4D);
                Vec3 playervec = player.position();
                Vec3 newvec = playervec.add(lookvec);
                player.teleportTo(newvec.x, newvec.y, newvec.z);
                boolean flag = false;
                // TODO: EntityMotor.getEntitiesAround signature must be adapted when the entity is ported to 1.20.1
                for (LivingEntity living : EntityMotor.getEntitiesAround(BlockPos.containing(newvec), 8F, player.level())) {
                    if(living == player)
                        continue;
                    boolean hit = living.getBoundingBox().inflate(4).clip(playervec.subtract(lookvec), newvec.add(lookvec))
                            .isPresent();
                    if (hit) {
                        living.addEffect(new MobEffectInstance(ModPotions.timelock.get(), 40));
                        living.hurtTime=0;
                        living.hurt(FlamescionHandler.flameSource(living), 6);
                        flag = true;
                    }
                }
                if(flag) {
                    player.addEffect(new MobEffectInstance(ModPotions.incandescence.get(), 80));
                    player.addEffect(new MobEffectInstance(ModPotions.flamescion.get(), 200));
                }
                player.getCooldowns().addCooldown(FlamescionHandler.getFlamescionWeapon(), 20);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
