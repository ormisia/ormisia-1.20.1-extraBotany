package com.meteor.extrabotany.common.items.relic;

import com.meteor.extrabotany.common.handler.IAdvancementRequirement;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.server.level.ServerPlayer;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.relic.RelicItem;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ItemCamera extends RelicItem implements IAdvancementRequirement {

    private static final String TAG_SOULBIND_UUID = "soulbindUUID";
    public static final int MANA_PER_DAMAGE = 1500;
    public static final int RANGE = 20;
    public static final String TAG_FREEZETIME = "freezeTime";
    public static final String TAG_TIMES = "freezeTimes";

    public ItemCamera(Item.Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingUpdate);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            if (!(event.getEntity() instanceof Player)) {
                if (event.getEntity().getPersistentData().getInt(TAG_FREEZETIME) > 0) {
                    event.getEntity().getPersistentData().putInt(TAG_FREEZETIME,
                            event.getEntity().getPersistentData().getInt(TAG_FREEZETIME) - 1);
                    event.setCanceled(true);
                }
            }
        }
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (isRightPlayer(player, stack) && ManaItemHandler.instance().requestManaExactForTool(stack, player, MANA_PER_DAMAGE, true)
                && !world.isClientSide) {
            for (LivingEntity living : player.level().getEntitiesOfClass(LivingEntity.class,
                    new AABB(player.blockPosition().offset(-RANGE, -RANGE, -RANGE),
                            player.blockPosition().offset(RANGE + 1, RANGE + 1, RANGE + 1)))) {
                if (living == player)
                    continue;
                // 1.16 Entity#isSpectatedByPlayer(ServerPlayerEntity) is gone; getCamera() == target is the
                // equivalent of "the player is spectating this entity".
                if (((ServerPlayer) player).getCamera() == living) {
                    living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 5));
                    int time = 200;
                    // 1.16 Entity#isNonBoss() no longer exists; explicit boss check is used instead.
                    if (living instanceof WitherBoss || living instanceof EnderDragon)
                        time = 40;
                    if (living.getPersistentData().getInt(TAG_TIMES) > 10)
                        time = 0;
                    living.getPersistentData().putInt(TAG_FREEZETIME, time);
                    living.getPersistentData().putInt(TAG_TIMES,
                            living.getPersistentData().getInt(TAG_TIMES) + 1);
                }
            }

            for (Entity e : player.level().getEntitiesOfClass(Entity.class,
                    new AABB(player.blockPosition().offset(-RANGE, -RANGE, -RANGE),
                            player.blockPosition().offset(RANGE + 1, RANGE + 1, RANGE + 1)))) {

                if (e instanceof Projectile)
                    e.discard();
            }
            player.getCooldowns().addCooldown(stack.getItem(), 200);
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public String getAdvancementName() {
        return LibAdvancementNames.EGODEFEAT;
    }

    public boolean isRightPlayer(Player player, ItemStack stack) {
        return hasUUID(stack) && getSoulbindUUID(stack).equals(player.getUUID());
    }

    public UUID getSoulbindUUID(ItemStack stack) {
        if (ItemNBTHelper.verifyExistance(stack, TAG_SOULBIND_UUID)) {
            try {
                return UUID.fromString(ItemNBTHelper.getString(stack, TAG_SOULBIND_UUID, ""));
            } catch (IllegalArgumentException ex) { // Bad UUID in tag
                ItemNBTHelper.removeEntry(stack, TAG_SOULBIND_UUID);
            }
        }

        return null;
    }

    public boolean hasUUID(ItemStack stack) {
        return getSoulbindUUID(stack) != null;
    }
}
