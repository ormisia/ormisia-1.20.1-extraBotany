package com.meteor.extrabotany.common.items.armor.maid;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.botania.api.mana.ManaItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemMaidHelm extends ItemMaidArmor {

    // 1.16 used mutable DamageSource constants (DamageSource.ANVIL, etc.) which are gone in 1.20.1;
    // damage types are now compared by ResourceKey (DamageSources are per-level).
    public List<ResourceKey<DamageType>> source = new ArrayList<>();

    public ItemMaidHelm(Item.Properties props) {
        super(ArmorItem.Type.HELMET, props);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityAttacked);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerAttacked);
        source.add(DamageTypes.FALLING_ANVIL);
        source.add(DamageTypes.CACTUS);
        source.add(DamageTypes.DROWN);
        source.add(DamageTypes.FALL);
        source.add(DamageTypes.FALLING_BLOCK);
        source.add(DamageTypes.IN_FIRE);
        source.add(DamageTypes.LAVA);
        source.add(DamageTypes.ON_FIRE);
        source.add(DamageTypes.LIGHTNING_BOLT);
        source.add(DamageTypes.FLY_INTO_WALL);
        source.add(DamageTypes.HOT_FLOOR);
        source.add(DamageTypes.SWEET_BERRY_BUSH);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        if (hasArmorSet(player) && !player.level().isClientSide) {
            ManaItemHandler.instance().dispatchManaExact(stack, player, 1, true);
            // 1.16 LivingEntity#shouldHeal() no longer exists in 1.20.1
            if (player.getHealth() > 0 && player.getHealth() < player.getMaxHealth() && player.tickCount % 40 == 0
                    && ManaItemHandler.instance().requestManaExactForTool(stack, player, 20, true))
                player.heal(1F);
            if (player.tickCount % 40 == 0)
                clearPotions(stack, player);
        }
    }

    @SubscribeEvent
    public void onEntityAttacked(LivingHurtEvent event) {
        Entity attacker = event.getSource().getDirectEntity();
        LivingEntity target = event.getEntity();
        if (attacker instanceof Player && target != null && target != attacker) {
            Player player = (Player) attacker;
            if (hasArmorSet(player)) {
                if (player.getMainHandItem().isEmpty() && player.getOffhandItem().isEmpty()
                        && ManaItemHandler.instance().requestManaExactForTool(new ItemStack(this), player, 200, true))
                    event.setAmount(event.getAmount() + 8F);
                // 1.16 LivingEntity#shouldHeal() no longer exists in 1.20.1
                if (player.getHealth() > 0 && player.getHealth() < player.getMaxHealth()
                        && ManaItemHandler.instance().requestManaExactForTool(new ItemStack(this), player, 80, true))
                    player.heal(event.getAmount() / 10F);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPlayerAttacked(LivingHurtEvent event) {
        Entity target = event.getEntity();
        if (target instanceof Player) {
            Player player = (Player) target;
            if (hasArmorSet(player)) {
                if (source.stream().anyMatch(event.getSource()::is))
                    event.setAmount(0F);
                // 1.16 compared a List to DamageSource.MAGIC (always false); this is the intended behaviour.
                if (event.getSource().is(DamageTypes.MAGIC))
                    event.setAmount(event.getAmount() * 0.75F);
            }
        }
    }

    public void clearPotions(ItemStack stack, Player player) {
        List<MobEffect> potionsToRemove = player.getActiveEffects().stream()
                .filter(effect -> effect.getEffect().getCategory() == MobEffectCategory.HARMFUL
                        && effect.getCurativeItems().stream().anyMatch(e -> e.is(Items.MILK_BUCKET)))
                .map(MobEffectInstance::getEffect)
                .distinct()
                .collect(Collectors.toList());

        // 1.20.1 LivingEntity#removeEffect sends the removal packet automatically, so the manual
        // SRemoveEntityEffectPacket broadcast from 1.16 is no longer needed.
        potionsToRemove.forEach(potion -> {
            if (ManaItemHandler.instance().requestManaExactForTool(stack, player, 100, true)) {
                player.removeEffect(potion);
            }
        });
    }

}
