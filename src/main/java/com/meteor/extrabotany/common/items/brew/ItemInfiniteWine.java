package com.meteor.extrabotany.common.items.brew;

import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.advancements.RelicBindTrigger;
import vazkii.botania.common.BotaniaDamageTypes;
import vazkii.botania.common.helper.ItemNBTHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemInfiniteWine extends ItemBrewBase {

    private static final String TAG_SOULBIND_UUID = "soulbindUUID";
    private static final int MANA_PER_DAMAGE = 12000;

    public ItemInfiniteWine(Properties builder) {
        super(builder, 12, 18, 1.5F, 1, ()-> ModItems.heromedal.get());
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide && entity instanceof Player) {
            updateRelic(stack, (Player) entity);

            if(world.getGameTime() % 8000 == 0
                    && getSwigsLeft(stack) < 12
                    && ManaItemHandler.instance().requestManaExactForTool(stack, (Player) entity, MANA_PER_DAMAGE, true))
                setSwigsLeft(stack, getSwigsLeft(stack) + 1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(final ItemStack stack, @Nullable Level world, final List<Component> tooltip, TooltipFlag flags) {
        if (!hasUUID(stack)) {
            tooltip.add(Component.translatable("botaniamisc.relicUnbound"));
        } else {
            if (!getSoulbindUUID(stack).equals(Minecraft.getInstance().player.getUUID())) {
                tooltip.add(Component.translatable("botaniamisc.notYourSagittarius"));
            } else {
                tooltip.add(Component.translatable("botaniamisc.relicSoulbound", Minecraft.getInstance().player.getName()));
            }
        }
        super.appendHoverText(stack, world, tooltip, flags);
    }

    public boolean shouldDamageWrongPlayer() {
        return true;
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, Level world) {
        return Integer.MAX_VALUE;
    }

    public void updateRelic(ItemStack stack, Player player) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemInfiniteWine)) {
            return;
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

        if (!rightPlayer && player.tickCount % 10 == 0 && shouldDamageWrongPlayer()) {
            player.hurt(damageSource(player), 2);
        }
    }

    public boolean isRightPlayer(Player player, ItemStack stack) {
        return hasUUID(stack) && getSoulbindUUID(stack).equals(player.getUUID());
    }

    public static DamageSource damageSource(Player player) {
        return BotaniaDamageTypes.Sources.relicDamage(player.level().registryAccess());
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
}
