package com.meteor.extrabotany.common.network;

import com.meteor.extrabotany.common.libs.LibMisc;
import com.meteor.extrabotany.common.network.flamescion.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {

    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(LibMisc.MOD_ID, "networking"),
                () -> "1.0",
                (s) -> true,
                (s) -> true
        );
        INSTANCE.registerMessage(
                nextID(),
                HerrscherEnergyUpdatePack.class,
                HerrscherEnergyUpdatePack::toBytes,
                HerrscherEnergyUpdatePack::new,
                HerrscherEnergyUpdatePack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                HerrscherSkillPack.class,
                HerrscherSkillPack::toBytes,
                HerrscherSkillPack::new,
                HerrscherSkillPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                MountableUpdatePack.class,
                MountableUpdatePack::toBytes,
                MountableUpdatePack::new,
                MountableUpdatePack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                UfoCatchPack.class,
                UfoCatchPack::toBytes,
                UfoCatchPack::new,
                UfoCatchPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                FlamescionStateUpdatePack.class,
                FlamescionStateUpdatePack::toBytes,
                FlamescionStateUpdatePack::new,
                FlamescionStateUpdatePack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                FlamescionShiftPack.class,
                FlamescionShiftPack::toBytes,
                FlamescionShiftPack::new,
                FlamescionShiftPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                FlamescionQPack.class,
                FlamescionQPack::toBytes,
                FlamescionQPack::new,
                FlamescionQPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                FlamescionStrengthenPack.class,
                FlamescionStrengthenPack::toBytes,
                FlamescionStrengthenPack::new,
                FlamescionStrengthenPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                LeftClickPack.class,
                LeftClickPack::toBytes,
                LeftClickPack::new,
                LeftClickPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                BuddhistChangePack.class,
                BuddhistChangePack::toBytes,
                BuddhistChangePack::new,
                BuddhistChangePack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                MountPack.class,
                MountPack::toBytes,
                MountPack::new,
                MountPack::handler
        );

        INSTANCE.registerMessage(
                nextID(),
                PotatoChipsPack.class,
                PotatoChipsPack::toBytes,
                PotatoChipsPack::new,
                PotatoChipsPack::handler
        );
    }

    public static void sendToNearby(Level world, BlockPos pos, Object toSend) {
        if (world instanceof ServerLevel) {
            ServerLevel ws = (ServerLevel) world;

            ws.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false).stream()
                    .filter(p -> p.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64)
                        .forEach(p -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> p), toSend));
        }
    }

    public static void sendToNearby(Level world, Entity e, Object toSend) {
        sendToNearby(world, BlockPos.containing(e.getX(), e.getY(), e.getZ()), toSend);
    }

    public static void sendTo(ServerPlayer playerMP, Object toSend) {
        Connection connection = playerMP.connection.connection;
        INSTANCE.sendTo(toSend, connection, NetworkDirection.PLAY_TO_CLIENT);
    }

}
