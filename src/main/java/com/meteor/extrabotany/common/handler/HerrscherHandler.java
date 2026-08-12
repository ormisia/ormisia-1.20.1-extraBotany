package com.meteor.extrabotany.common.handler;

import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.capability.IHerrscherEnergy;
import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.network.HerrscherEnergyUpdatePack;
import com.meteor.extrabotany.common.network.HerrscherSkillPack;
import com.meteor.extrabotany.common.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber
public class HerrscherHandler {

    @SubscribeEvent
    public static void onHerrscherAttacked(LivingAttackEvent event){
        if(event.getSource().getDirectEntity() != null)
            if(event.getEntity() instanceof Player){
                Player player = (Player) event.getEntity();
                if(isHerrscherOfThunder(player)) {
                    LazyOptional<IHerrscherEnergy> cap = player.getCapability(CapabilityHandler.HERRSCHERENERGY_CAPABILITY);
                    cap.ifPresent((c) -> {
                        if(c.getEnergy() >= 200 || player.isCreative()) {
                            if (!player.swinging && player.getMainHandItem().getItem() instanceof SwordItem) {
                                player.swing(InteractionHand.MAIN_HAND, true);
                                player.attack(event.getSource().getDirectEntity());
                                c.setEnergy(c.getEnergy() - 200);
                                c.markDirty(true);
                                sync(player);
                                event.setCanceled(true);
                            }
                        }
                    });
                }
            }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event){
        Player player = event.getEntity();
        Minecraft mc = Minecraft.getInstance();
        HitResult pos = mc.hitResult;
        if(isHerrscherOfThunder(player)) {
            if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof SwordItem) {
                LazyOptional<IHerrscherEnergy> cap = player.getCapability(CapabilityHandler.HERRSCHERENERGY_CAPABILITY);
                cap.ifPresent((c) -> {
                    int energy = c.getEnergy();
                    if(pos != null) {
                        BlockPos p = BlockPos.containing(pos.getLocation());
                        if ((energy == 600 || player.isCreative()) && mc.options.keySprint.isDown()) {
                            c.setEnergy(0);
                            c.markDirty(true);
                            NetworkHandler.INSTANCE.sendToServer(new HerrscherSkillPack(p.offset(0, 2, 0)));
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(!isHerrscherOfThunder(event.player))
            return;
        LazyOptional<IHerrscherEnergy> cap = event.player.getCapability(CapabilityHandler.HERRSCHERENERGY_CAPABILITY);
        cap.ifPresent((c) -> {
            int energy = c.getEnergy();
            if(energy < 600) {
                c.setEnergy(Math.min(600, energy + 2));
                c.markDirty(true);
            }
        });
        if(!event.player.level().isClientSide)
            sync(event.player);
    }

    public static void sync(Player player){
        LazyOptional<IHerrscherEnergy> cap = player.getCapability(CapabilityHandler.HERRSCHERENERGY_CAPABILITY);
        if(!player.level().isClientSide)
            cap.ifPresent((c) -> {
                if(c.isDirty()){
                    NetworkHandler.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> {
                                        return (ServerPlayer) player;
                                    }
                            ),
                            new HerrscherEnergyUpdatePack(c.getEnergy()));
                    c.markDirty(false);
                }
            });
    }

    public static DamageSource damageSource(Entity target) {
        // TODO: 1.20.1 removed the DamageSource builder; magic() no longer bypasses armor/absolute damage.
        return target.damageSources().magic();
    }

    public static DamageSource iceSource(Entity target) {
        // TODO: 1.20.1 removed the DamageSource builder; magic() no longer bypasses armor/absolute damage.
        return target.damageSources().magic();
    }

    public static void thunderAttack(Entity target, Player player, float dmg){
        /**
         * 伤害结算
         */
        target.hurt(damageSource(target), dmg);
    }

    public static void iceAttack(Entity target, Player player, float dmg){
        /**
         * 伤害结算
         */
        target.hurt(iceSource(target), dmg);
    }

    public static boolean isHerrscherOfThunder(Player player){
        return !EquipmentHandler.findOrEmpty(ModItems.gemofconquest.get(), player).isEmpty();
    }

}
