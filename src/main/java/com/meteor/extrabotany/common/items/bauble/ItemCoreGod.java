package com.meteor.extrabotany.common.items.bauble;

import com.meteor.extrabotany.client.handler.MiscellaneousIcons;
import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.handler.IAdvancementRequirement;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.fx.SparkleParticleData;
import vazkii.botania.client.render.AccessoryRenderer;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.relic.RelicBaubleItem;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemCoreGod extends RelicBaubleItem implements IAdvancementRequirement {

    private static final String TAG_VARIANT = "variant";

    private static final List<String> playersWithFlight = Collections.synchronizedList(new ArrayList<>());
    private static final int COST = 35;

    private static final int SUBTYPES = 4;

    public ItemCoreGod(Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::updatePlayerFlyStatus);
        MinecraftForge.EVENT_BUS.addListener(this::playerLoggedOut);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("extrabotany.wings" + getVariant(stack)).withStyle(ChatFormatting.GRAY));
    }

    private void updatePlayerFlyStatus(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack tiara = EquipmentHandler.findOrEmpty(this, player);

            if (playersWithFlight.contains(playerStr(player))) {
                if (shouldPlayerHaveFlight(player)) {
                    player.getAbilities().mayfly = true;
                    if (player.getAbilities().flying) {
                        if (!player.level().isClientSide) {
                            ManaItemHandler.instance().requestManaExact(tiara, player, COST, true);
                        } else if (Math.abs(player.getDeltaMovement().x()) > 0.1 || Math.abs(player.getDeltaMovement().z()) > 0.1) {
                            double x = event.getEntity().getX() - 0.5;
                            double y = event.getEntity().getY() - 0.5;
                            double z = event.getEntity().getZ() - 0.5;

                            float r = 1F;
                            float g = 1F;
                            float b = 1F;

                            int variant = getVariant(tiara);

                            switch (variant) {
                                case 0: {
                                    r = 1F;
                                    g = 0.55F;
                                    b = 0F;
                                    break;
                                }
                                case 1: {
                                    r = new float[]{0.4F, 0.98F, 0.98F, 0.98F, 0.6F, 0F, 0.15F}[player.level().random.nextInt(7)];
                                    g = new float[]{0.82F, 0.84F, 0.52F, 0.12F, 0.21F, 0.4F, 0.98F}[player.level().random.nextInt(7)];
                                    b = new float[]{0F, 0.18F, 0.18F, 0F, 0.98F, 0.81F, 0.82F}[player.level().random.nextInt(7)];
                                    break;
                                }
                                case 2: {
                                    r = 0.52F;
                                    g = 0.8F;
                                    b = 0.85F;
                                    break;
                                }
                                case 3: {
                                    r = 0.95F;
                                    g = 0.7F;
                                    b = 0.38F;
                                    break;
                                }
                            }

                            for (int i = 0; i < 2; i++) {
                                SparkleParticleData data = SparkleParticleData.sparkle(2F * (float) Math.random(), r, g, b, 20);
                                player.level().addParticle(data, x + Math.random() * event.getEntity().getBbWidth(), y + Math.random() * 0.4, z + Math.random() * event.getEntity().getBbWidth(), 0, 0, 0);
                            }
                        }
                    }
                } else {
                    if (!player.isSpectator() && !player.getAbilities().instabuild) {
                        player.getAbilities().mayfly = false;
                        player.getAbilities().flying = false;
                        player.getAbilities().invulnerable = false;
                    }
                    playersWithFlight.remove(playerStr(player));
                }
            } else if (shouldPlayerHaveFlight(player)) {
                playersWithFlight.add(playerStr(player));
                player.getAbilities().mayfly = true;
            }
        }
    }

    private void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        String username = event.getEntity().getGameProfile().getName();
        playersWithFlight.remove(username + ":false");
        playersWithFlight.remove(username + ":true");
    }

    private static String playerStr(Player player) {
        return player.getGameProfile().getName() + ":" + player.level().isClientSide;
    }

    private boolean shouldPlayerHaveFlight(Player player) {
        ItemStack armor = EquipmentHandler.findOrEmpty(this, player);
        if (!armor.isEmpty()) {
            return ManaItemHandler.instance().requestManaExact(armor, player, COST, false);
        }
        return false;
    }

    public void onWornTick(ItemStack stack, LivingEntity player) {
        if (player instanceof Player) {
            Player p = (Player) player;

        }
    }

    @Override
    public boolean hasRender(ItemStack stack, LivingEntity living) {
        return super.hasRender(stack, living) && living instanceof Player;
    }

    @Override
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return EquipmentHandler.findOrEmpty(this, entity).isEmpty();
    }

    public static int getVariant(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_VARIANT, 0);
    }

    @Override
    public String getAdvancementName() {
        return LibAdvancementNames.EGODEFEAT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Renderer implements AccessoryRenderer {

        @Override
        public void doRender(HumanoidModel<?> bipedModel, ItemStack stack, LivingEntity living, PoseStack ms, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            int meta = ItemCoreGod.getVariant(stack);
            if (meta < 0 || meta >= MiscellaneousIcons.INSTANCE.coregodWingsModel.length + 1) {
                return;
            }

            BakedModel model = MiscellaneousIcons.INSTANCE.coregodWingsModel[meta];
            boolean flying = living instanceof Player && ((Player) living).getAbilities().flying;
            float flap = 12F + (float) ((Math.sin((double) (living.tickCount + partialTicks) * (flying ? 0.2F : 0.12F)) + 0.4F) * (flying ? 30F : 5F));

            switch (meta) {
                case 0:
                    renderHerrscher(bipedModel, model, stack, ms, buffers, flap);
                    break;
                case 1:
                    renderBasic(bipedModel, model, stack, ms, buffers, light, flap * 0.25F);
                    break;
                case 2:
                case 3:
                    renderBasic(bipedModel, model, stack, ms, buffers, light, flap);
                    break;
            }
        }

        @OnlyIn(Dist.CLIENT)
        private static void renderHerrscher(HumanoidModel<?> bipedModel, BakedModel model, ItemStack stack, PoseStack ms, MultiBufferSource buffers, float flap) {
            ms.pushPose();
            bipedModel.body.translateAndRotate(ms);
            ms.translate(0, -0.2, 0.3);


            for(int i = 0; i < 3; i++) {
                ms.pushPose();
                ms.mulPose(com.mojang.math.Axis.YP.rotationDegrees(flap * 0.25F));
                ms.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(-35F * i));
                BakedModel model_ = MiscellaneousIcons.INSTANCE.coregodModel[0];
                ms.translate(-1.2, -0.1F * i, 0);

                ms.scale(1.9F, -1.9F, -1.9F);
                Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.NONE, false, ms, buffers, 0xF000F0, OverlayTexture.NO_OVERLAY, model_);
                ms.popPose();
            }

            ms.pushPose();
            ms.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180 - flap * 0.25F));

            ms.translate(-1.2, 0, 0);

            ms.scale(1.7F, -1.7F, -1.7F);
            Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.NONE, false, ms, buffers, 0xF000F0, OverlayTexture.NO_OVERLAY, model);
            ms.popPose();


            ms.popPose();
        }

        @OnlyIn(Dist.CLIENT)
        private static void renderBasic(HumanoidModel<?> bipedModel, BakedModel model, ItemStack stack, PoseStack ms, MultiBufferSource buffers, int light, float flap) {
            ms.pushPose();

            // attach to body
            bipedModel.body.translateAndRotate(ms);

            // position on body
            ms.translate(0, 0.2, 0.2);

            for (int i = 0; i < 2; i++) {
                ms.pushPose();
                ms.mulPose(com.mojang.math.Axis.YP.rotationDegrees(i == 0 ? flap : 180 - flap));

                // move so flapping about the edge instead of center of texture
                ms.translate(-1, 0, 0);

                ms.scale(1.5F, -1.5F, -1.5F);
                Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.NONE, false, ms, buffers, light, OverlayTexture.NO_OVERLAY, model);
                ms.popPose();
            }

            ms.popPose();
        }

    }

}
