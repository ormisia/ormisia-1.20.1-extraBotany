package com.meteor.extrabotany.common.items.relic;

import com.meteor.extrabotany.api.items.IItemWithLeftClick;
import com.meteor.extrabotany.common.network.LeftClickPack;
import com.meteor.extrabotany.common.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.advancements.RelicBindTrigger;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.relic.RelicItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * Ported from 1.16. NOTE: Botania's {@code vazkii.botania.api.item.Relic} interface changed in 1.20.1 to a
 * non-ItemStack-based, capability-oriented design (see {@code vazkii.botania.common.item.relic.RelicImpl}).
 * ExtraBotany's relic items keep the 1.16-style ItemStack-based soul-bind helpers instead of implementing
 * the new interface directly. The new {@code RelicItem} base handles tickBinding/tooltip via the per-stack
 * {@code BotaniaForgeCapabilities.RELIC} capability, which needs to be wired up for these items.
 */
public class ItemSwordRelic extends SwordItem implements IItemWithLeftClick {

    private static final String TAG_SOULBIND_UUID = "soulbindUUID";
    private static final int MANA_PER_DAMAGE = 120;

    public ItemSwordRelic(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        MinecraftForge.EVENT_BUS.addListener(this::leftClick);
        MinecraftForge.EVENT_BUS.addListener(this::leftClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::attackEntity);
    }

    public static boolean isRelicItem(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof ItemSwordRelic || item instanceof ItemFailnaught || item instanceof RelicItem;
    }

    public void attackEntity(AttackEntityEvent evt) {
        if (!evt.getEntity().level().isClientSide) {
            onLeftClick(evt.getEntity(), evt.getTarget());
        }
    }

    public void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
        if (!evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
            NetworkHandler.INSTANCE.sendToServer(new LeftClickPack(evt.getItemStack()));
        }
    }

    public void leftClickBlock(PlayerInteractEvent.LeftClickBlock evt) {
        if (evt.getEntity().level().isClientSide && !evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
            NetworkHandler.INSTANCE.sendToServer(new LeftClickPack(evt.getItemStack()));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && entity instanceof Player) {
            updateRelic(stack, (Player) entity);
        }
    }

    @Override
    public void onLeftClick(Player living, Entity target) {

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
        if (stack.isEmpty() || !isRelicItem(stack)) {
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

    public static BlockHitResult raytraceFromEntity(Entity e, double distance, boolean fluids) {
        return (BlockHitResult) e.pick(distance, 1, fluids);
    }

}
