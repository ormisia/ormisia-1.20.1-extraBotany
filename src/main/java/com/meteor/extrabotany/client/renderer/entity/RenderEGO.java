package com.meteor.extrabotany.client.renderer.entity;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.meteor.extrabotany.client.renderer.entity.layers.HeldFakeItemLayer;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RenderEGO extends HumanoidMobRenderer<Mob, HumanoidModel<Mob>> {

    private static final Cache<String, GameProfile> GAME_PROFILE_CACHE = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(0, 2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue());
    private static final GameProfile EMPTY_GAME_PROFILE = new GameProfile(null, "EMPTY");

    private static final ResourceLocation TEXTURE_ALEX = new ResourceLocation("textures/entity/alex.png");

    public RenderEGO(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PlayerModel(LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE, false), 64, 64).bakeRoot(), false), 0F);
        this.addLayer(new HeldFakeItemLayer(this));
    }

    @Override
    public void render(@Nonnull Mob mob, float yaw, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light) {
        super.render(mob, yaw, partialTicks, ms, buffers, light);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull Mob entity) {
        if (entity.getCustomName() != null)
            return getPlayerSkin(entity.getCustomName().getString());
        else
            return getPlayerSkin("ExtraMeteorP");
    }

    public static ResourceLocation getPlayerSkin(String name) {
        GameProfile newProfile = null;
        Minecraft minecraft = Minecraft.getInstance();

        try {
            newProfile = GAME_PROFILE_CACHE.get(name, () -> {
                THREAD_POOL.submit(() -> {
                    GameProfile profile = new GameProfile(null, name);
                    SkullBlockEntity.updateGameprofile(profile, profileNew -> minecraft.execute(() -> {
                        if (profileNew != null) {
                            GAME_PROFILE_CACHE.put(name, profileNew);
                        }
                    }));
                });
                return EMPTY_GAME_PROFILE;
            });
        } catch (ExecutionException ignore) {
        }

        if (newProfile != null) {
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(newProfile);
            if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                return minecraft.getSkinManager().registerTexture(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
            } else {
                UUID uuid = newProfile.getId();
                if (uuid == null && newProfile.getName() != null)
                    uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + newProfile.getName()).getBytes(StandardCharsets.UTF_8));
                return DefaultPlayerSkin.getDefaultSkin(uuid);
            }
        }

        return TEXTURE_ALEX;
    }

}
