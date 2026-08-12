package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.common.entities.EntityFlamescionSword;
import com.meteor.extrabotany.common.entities.EntityFlamescionVoid;
import com.meteor.extrabotany.common.entities.EntityStrengthenSlash;
import com.meteor.extrabotany.common.entities.mountable.EntityMotor;
import com.meteor.extrabotany.common.ExtraBotanyGroup;
import com.meteor.extrabotany.common.handler.FlamescionHandler;
import com.meteor.extrabotany.common.network.NetworkHandler;
import com.meteor.extrabotany.common.network.flamescion.FlamescionStrengthenPack;
import com.meteor.extrabotany.common.potions.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ItemFlamescionWeapon extends SwordItem {

    public ItemFlamescionWeapon() {
        super(Tiers.NETHERITE, 5, -1.6F, new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).setNoRepair());
        MinecraftForge.EVENT_BUS.addListener(this::leftClick);
        MinecraftForge.EVENT_BUS.addListener(this::leftClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::attackEntity);
    }

    @SubscribeEvent
    public void attackEntity(AttackEntityEvent evt) {
        if (!evt.getEntity().level().isClientSide) {
            tryStrengthenAttack(evt.getEntity());
        }
    }

    @SubscribeEvent
    public void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
        if (!evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
            NetworkHandler.INSTANCE.sendToServer(new FlamescionStrengthenPack());
        }
    }

    @SubscribeEvent
    public void leftClickBlock(PlayerInteractEvent.LeftClickBlock evt) {
        if (evt.getEntity().level().isClientSide && !evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
            NetworkHandler.INSTANCE.sendToServer(new FlamescionStrengthenPack());
        }
    }

    public void tryStrengthenAttack(Player player) {
        if (!player.level().isClientSide && !player.getMainHandItem().isEmpty() && player.getMainHandItem().getItem() == this
                && player.getAttackStrengthScale(0.0F) == 1) {
            if (player.hasEffect(ModPotions.flamescion.get())) {
                for (int i = 0; i < 3; i++) {
                    EntityStrengthenSlash slash = new EntityStrengthenSlash(player.level(), player);
                    Vec3 targetPos = player.position().add(player.getLookAngle().yRot((float) Math.toRadians(-15F + 15F * i)).scale(5D));
                    Vec3 vec = targetPos.subtract(player.position()).normalize();
                    slash.setDeltaMovement(vec);
                    slash.setPos(player.getX(), player.getY() + 0.5F, player.getZ());
                    slash.faceEntity(BlockPos.containing(targetPos.x, targetPos.y, targetPos.z));
                    player.level().addFreshEntity(slash);
                }
                player.removeEffect(ModPotions.flamescion.get());
            } else if (FlamescionHandler.isFlamescionMode(player)) {
                EntityFlamescionSword sword = new EntityFlamescionSword(player.level(), player);
                Vec3 targetPos = player.position().add(player.getLookAngle().scale(5D));
                Vec3 vec = targetPos.subtract(player.position()).normalize();
                sword.setDeltaMovement(vec);
                sword.setPos(player.getX(), player.getY() + 0.5F, player.getZ());
                player.level().addFreshEntity(sword);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!FlamescionHandler.isOverloaded(playerIn)) {
            if (playerIn.isShiftKeyDown() && !FlamescionHandler.isFlamescionMode(playerIn)) {
                if (playerIn.onGround()) {
                    List<LivingEntity> entities = EntityMotor.getEntitiesAround(playerIn.blockPosition(), 3F, worldIn);
                    for (LivingEntity entity : entities) {
                        entity.setDeltaMovement(entity.getDeltaMovement().add(0, 1D, 0));
                        if (entity != playerIn)
                            entity.addEffect(new MobEffectInstance(ModPotions.timelock.get(), 60));
                    }
                    if (worldIn.isClientSide)
                        for (int i = 0; i < 360; i += 30) {
                            double r = 3D;
                            double x = playerIn.getX() + r * Math.cos(Math.toRadians(i));
                            double y = playerIn.getY() + 0.5D;
                            double z = playerIn.getZ() + r * Math.sin(Math.toRadians(i));
                            for (int j = 0; j < 6; j++)
                                worldIn.addParticle(ParticleTypes.FLAME, x, y, z, 0, 0.12F * j, 0);
                        }
                } else {
                    if (worldIn.isClientSide)
                        for (int i = 0; i < 360; i += 15) {
                            double r = 0.5D;
                            double x = playerIn.getX() + r * Math.cos(Math.toRadians(i));
                            double y = playerIn.getY() + 0.5D;
                            double z = playerIn.getZ() + r * Math.sin(Math.toRadians(i));
                            Vec3 vec = new Vec3(x - playerIn.getX(), 0, z - playerIn.getZ()).normalize();
                            for (int j = 0; j < 3; j++)
                                worldIn.addParticle(ParticleTypes.FLAME, x, y, z, vec.scale(0.25D + 0.01D * j).x, 0, vec.scale(0.25D + 0.01D * j).z);
                        }
                }
                playerIn.addEffect(new MobEffectInstance(ModPotions.incandescence.get(), 60));
            } else if (FlamescionHandler.isFlamescionMode(playerIn)) {
                Vec3 targetPos = playerIn.position().add(playerIn.getLookAngle().scale(5D));
                EntityFlamescionVoid fvoid = new EntityFlamescionVoid(worldIn, playerIn);
                fvoid.setPos(targetPos.x, targetPos.y, targetPos.z);
                if (!worldIn.isClientSide)
                    worldIn.addFreshEntity(fvoid);
                playerIn.addEffect(new MobEffectInstance(ModPotions.incandescence.get(), 80));
                playerIn.getCooldowns().addCooldown(this, 40);
            }
        }

        return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
    }

}
