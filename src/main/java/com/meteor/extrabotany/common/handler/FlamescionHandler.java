package com.meteor.extrabotany.common.handler;

import com.meteor.extrabotany.common.capability.CapabilityHandler;
import com.meteor.extrabotany.common.capability.IFlamescion;
import com.meteor.extrabotany.common.entities.EntityFlamescionSlash;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.network.flamescion.FlamescionQPack;
import com.meteor.extrabotany.common.network.flamescion.FlamescionShiftPack;
import com.meteor.extrabotany.common.network.flamescion.FlamescionStateUpdatePack;
import com.meteor.extrabotany.common.network.NetworkHandler;
import com.meteor.extrabotany.common.potions.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.atomic.AtomicBoolean;

@Mod.EventBusSubscriber
public class FlamescionHandler {

    public static final int MAX_FLAMESCION_ENERGY = 600;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Player p = Minecraft.getInstance().player;
        if(p == null)
            return;
        if(isFlamescionMode(p)){
            if(!p.getCooldowns().isOnCooldown(getFlamescionWeapon()))
                if(event.getAction() == GLFW.GLFW_PRESS && event.getKey() == GLFW.GLFW_KEY_LEFT_SHIFT){
                    NetworkHandler.INSTANCE.sendToServer(new FlamescionShiftPack());
                }
            if(event.getAction() == GLFW.GLFW_PRESS && event.getKey() == GLFW.GLFW_KEY_R){
                NetworkHandler.INSTANCE.sendToServer(new FlamescionQPack());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerAttack(LivingAttackEvent event){
        if(event.getSource().getEntity() instanceof Player){
            Player player = (Player) event.getSource().getEntity();
            if(isFlamescionMode(player)) {
                // TODO: EntityFlamescionSlash constructor signature must be adapted when the entity is ported to 1.20.1
                EntityFlamescionSlash slash = new EntityFlamescionSlash(player.level(), player);
                slash.setPos(event.getEntity().getX(), event.getEntity().getY()+1F, event.getEntity().getZ());
                if(!player.level().isClientSide)
                    player.level().addFreshEntity(slash);
                player.addEffect(new MobEffectInstance(ModPotions.incandescence.get(), 30));
                event.getEntity().addEffect(new MobEffectInstance(ModPotions.timelock.get(), 30));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        LazyOptional<IFlamescion> cap = event.player.getCapability(CapabilityHandler.FLAMESCION_CAPABILITY);
        Player player = event.player;
        if(event.phase == TickEvent.Phase.END) {
            cap.ifPresent((c) -> {
                int energy = c.getEnergy();
                if (isFlamescionMode(player)) {
                    if (energy < MAX_FLAMESCION_ENERGY) {
                        c.setEnergy(Math.min(MAX_FLAMESCION_ENERGY, energy + 2));
                    } else {
                        c.setOverloaded(true);
                    }
                    c.markDirty(true);
                }

                if (c.isOverloaded()) {
                    if (energy > 0) {
                        c.setEnergy(Math.max(0, energy - 3));
                    } else
                        c.setOverloaded(false);
                    c.markDirty(true);
                }

            });
            if (!event.player.level().isClientSide)
                sync(event.player);
        }
    }

    public static void sync(Player player){
        LazyOptional<IFlamescion> cap = player.getCapability(CapabilityHandler.FLAMESCION_CAPABILITY);
        if(!player.level().isClientSide)
            cap.ifPresent((c) -> {
                if(c.isDirty()){
                    NetworkHandler.INSTANCE.send(
                            PacketDistributor.PLAYER.with(
                                    () -> {
                                        return (ServerPlayer) player;
                                    }
                            ),
                            new FlamescionStateUpdatePack(c.getEnergy(), c.isOverloaded()));
                    c.markDirty(false);
                }
            });
    }

    public static boolean isFlamescionMode(Player player){
        return !player.onGround()
                && player.getMainHandItem() != null
                && player.getMainHandItem().getItem() == getFlamescionWeapon()
                && player.hasEffect(ModPotions.incandescence.get())
                && !isOverloaded(player);
    }

    public static boolean isOverloaded(Player player){
        LazyOptional<IFlamescion> cap = player.getCapability(CapabilityHandler.FLAMESCION_CAPABILITY);
        AtomicBoolean overloaded = new AtomicBoolean(false);
        cap.ifPresent((c) -> {
            overloaded.set(c.isOverloaded());
        });
        return overloaded.get();
    }

    public static Item getFlamescionWeapon(){
        // TODO: return ModItems.flamescionweapon.get() once ModItems is ported to DeferredRegister/RegistryObject
        return ModItems.flamescionweapon.get();
    }

    public static DamageSource flameSource(Entity target) {
        // TODO: 1.20.1 removed the DamageSource builder; magic() no longer bypasses armor/absolute damage.
        return target.damageSources().magic();
    }

}
