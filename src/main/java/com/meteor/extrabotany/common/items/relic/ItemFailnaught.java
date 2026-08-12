package com.meteor.extrabotany.common.items.relic;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.entities.projectile.EntityMagicArrow;
import com.meteor.extrabotany.common.handler.IAdvancementRequirement;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.advancements.RelicBindTrigger;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ItemFailnaught extends BowItem implements IAdvancementRequirement {

    private static final String TAG_SOULBIND_UUID = "soulbindUUID";
    private static final int MANA_PER_DAMAGE = 160;

    public ItemFailnaught(Item.Properties builder) {
        super(builder);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player playerIn, @Nonnull InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public void releaseUsing(@Nonnull ItemStack stack, @Nonnull Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player) {
            Player player = (Player) entityLiving;
            int i = (int) ((getUseDuration(stack) - timeLeft) * 1F);
            if (i < 8)
                return;
            int rank = (i - 8) / 5;
            if (isRightPlayer(player, stack)
                    && ManaItemHandler.instance().requestManaExactForTool(stack, player, Math.min(800, 350 + rank * 20), true)) {
                EntityMagicArrow arrow = new EntityMagicArrow(level, player);
                arrow.setPos(player.getX(), player.getY() + 1.1D, player.getZ());
                arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
                arrow.setDamage((int) Math.min(50, ExtraBotanyAPI.INSTANCE.calcDamage(7 + rank * 0.5F, player)));
                arrow.setYRot(player.getYRot());
                int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
                if (j > 0) {
                    arrow.setDamage(arrow.getDamage() + j);
                }
                arrow.setLife(Math.min(150, 5 + i * 4));

                if (!level.isClientSide)
                    level.addFreshEntity(arrow);

                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT,
                        SoundSource.NEUTRAL, 1.0F, 0.5F);
            }
        }
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, MANA_PER_DAMAGE);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && entity instanceof Player) {
            updateRelic(stack, (Player) entity);
            if (stack.getDamageValue() > 0 && ManaItemHandler.instance().requestManaExactForTool(stack, (Player) entity, MANA_PER_DAMAGE * 2, true))
                stack.setDamageValue(stack.getDamageValue() - 1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(final ItemStack stack, @Nullable Level level, final List<Component> tooltip, TooltipFlag flags) {
        if (!hasUUID(stack)) {
            tooltip.add(Component.translatable("botaniamisc.relicUnbound"));
        } else {
            if (!getSoulbindUUID(stack).equals(Minecraft.getInstance().player.getUUID())) {
                tooltip.add(Component.translatable("botaniamisc.notYourSagittarius"));
            } else {
                tooltip.add(Component.translatable("botaniamisc.relicSoulbound", Minecraft.getInstance().player.getName()));
            }
        }
    }

    public boolean shouldDamageWrongPlayer() {
        return true;
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, Level level) {
        return Integer.MAX_VALUE;
    }

    public void updateRelic(ItemStack stack, Player player) {
        if (stack.isEmpty() || !ItemSwordRelic.isRelicItem(stack)) {
            return;
        }

        if (!player.level().isClientSide && stack.getDamageValue() > 0 && ManaItemHandler.instance().requestManaExact(stack, player, MANA_PER_DAMAGE * 2, true)) {
            stack.setDamageValue(stack.getDamageValue() - 1);
        }

        boolean rightPlayer = true;

        if (!hasUUID(stack)) {
            bindToUUID(player.getUUID(), stack);
            if (player instanceof ServerPlayer) {
                RelicBindTrigger.INSTANCE.trigger((ServerPlayer) player, stack);
            }
        } else if (!getSoulbindUUID(stack).equals(player.getUUID())) {
            rightPlayer = false;
        }

        // Original 1.16 also checked (stack.getItem() instanceof ItemRelic) && shouldDamageWrongPlayer();
        // all ExtraBotany relics damage the wrong player, so this is kept unconditional.
        if (!rightPlayer && player.tickCount % 10 == 0) {
            player.hurt(damageSource(player.level()), 2);
        }
    }

    public boolean isRightPlayer(Player player, ItemStack stack) {
        return hasUUID(stack) && getSoulbindUUID(stack).equals(player.getUUID());
    }

    public static DamageSource damageSource(Level level) {
        return level.damageSources().generic();
    }

    public void bindToUUID(UUID uuid, ItemStack stack) {
        ItemNBTHelper.setString(stack, TAG_SOULBIND_UUID, uuid.toString());
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

    @Override
    public String getAdvancementName() {
        return LibAdvancementNames.EGODEFEAT;
    }

}
