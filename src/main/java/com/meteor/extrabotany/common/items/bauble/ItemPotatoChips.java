package com.meteor.extrabotany.common.items.bauble;

import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.network.NetworkHandler;
import com.meteor.extrabotany.common.network.PotatoChipsPack;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.render.AccessoryRenderer;

public class ItemPotatoChips extends ItemBauble implements AccessoryRenderer {

    public static final int MANA_PER_DAMAGE = 3000;

    public ItemPotatoChips(Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerDeath);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerDeath(LivingDeathEvent event){
        if(!event.isCanceled() && event.getEntity() instanceof Player player){
            if(!EquipmentHandler.findOrEmpty(this, player).isEmpty()
                    && !player.getCooldowns().isOnCooldown(this)
                    && ManaItemHandler.instance().requestManaExactForTool(new ItemStack(this), player, MANA_PER_DAMAGE, true)){

                if(!player.level().isClientSide){
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                }

                event.setCanceled(true);
                player.setHealth(5.0F);
                player.removeAllEffects();
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                int ticks = 600;
                if(event.getSource().getEntity() != null)
                    ticks = 12000;
                player.getCooldowns().addCooldown(this, ticks);
                if(player instanceof ServerPlayer serverPlayer)
                    NetworkHandler.sendTo(serverPlayer, new PotatoChipsPack());
            }
        }
    }

    @Override
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return EquipmentHandler.findOrEmpty(this, entity).isEmpty();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void doRender(HumanoidModel<?> bipedModel, ItemStack stack, LivingEntity player, PoseStack ms, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ms.pushPose();
        bipedModel.head.translateAndRotate(ms);
        ms.translate(0, -0.3, -0.3);
        ms.scale(0.6F, -0.6F, -0.6F);
        renderItem(stack, ms, buffers, light);
        ms.popPose();
    }

    public static void renderItem(ItemStack stack, PoseStack ms, MultiBufferSource buffers, int light) {
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.NONE, light, OverlayTexture.NO_OVERLAY, ms, buffers, Minecraft.getInstance().level, 0);
    }

}
