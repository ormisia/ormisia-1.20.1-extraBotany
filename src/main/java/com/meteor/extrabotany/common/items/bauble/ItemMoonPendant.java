package com.meteor.extrabotany.common.items.bauble;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.handler.IAdvancementRequirement;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import vazkii.botania.common.advancements.RelicBindTrigger;
import vazkii.botania.common.BotaniaDamageTypes;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.equipment.bauble.CrimsonPendantItem;
import vazkii.botania.common.item.equipment.bauble.NimbusAmuletItem;
import vazkii.botania.common.item.equipment.bauble.SpectatorItem;
import vazkii.botania.common.item.relic.RelicImpl;

import java.util.List;
import java.util.UUID;

public class ItemMoonPendant extends NimbusAmuletItem implements IAdvancementRequirement {

    private static final String TAG_SOULBIND_UUID = "soulbindUUID";

    public ItemMoonPendant(Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::onDamage);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean held) {
        if (entity instanceof Player) {
            updateRelic(stack, (Player) entity);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        RelicImpl.addDefaultTooltip(stack, tooltip);
    }

    private void onDamage(LivingAttackEvent evt) {
        if (evt.getSource().is(DamageTypeTags.IS_FIRE)
                && !EquipmentHandler.findOrEmpty(this, evt.getEntity()).isEmpty()) {
            evt.setCanceled(true);
        }
    }

    @Override
    public void onWornTick(ItemStack stack, LivingEntity player) {
        super.onWornTick(stack, player);
        if (player instanceof Player) {
            Player ePlayer = (Player) player;
            updateRelic(stack, ePlayer);
            if (isRightPlayer(ePlayer, stack)) {
                onValidPlayerWornTick(stack, ePlayer);
            }
        }
    }

    public void onValidPlayerWornTick(ItemStack stack, Player player) {
        ((CrimsonPendantItem) BotaniaItems.superLavaPendant).onWornTick(stack, player);
        ((SpectatorItem) BotaniaItems.itemFinder).onWornTick(stack, player);

        if (!player.level().isClientSide && !player.isShiftKeyDown()) {
            boolean lastOnGround = player.onGround();
            player.setOnGround(true);
            FrostWalkerEnchantment.onEntityMoved(player, player.level(), player.blockPosition(), 8);
            player.setOnGround(lastOnGround);
        }else if (player.level().isClientSide && !player.isShiftKeyDown()) {
            if (player.level().random.nextFloat() >= 0.25F) {
                player.level().addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SNOW_BLOCK.defaultBlockState()), player.getX() + player.level().random.nextFloat() * 0.6 - 0.3, player.getY() + 1.1, player.getZ() + player.level().random.nextFloat() * 0.6 - 0.3, 0, -0.15, 0);
            }
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(getBaubleUUID(stack), "Moon Pendant", 1, AttributeModifier.Operation.ADDITION));
        return attributes;
    }

    @Override
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return entity instanceof Player
                && isRightPlayer((Player) entity, stack)
                && EquipmentHandler.findOrEmpty(this, entity).isEmpty();
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, Level world) {
        return Integer.MAX_VALUE;
    }

    // Relic binding (ported inline; Botania 1.20.1 has no public addon API to register the RELIC capability)
    public void updateRelic(ItemStack stack, Player player) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemMoonPendant)) {
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

        if (!rightPlayer && player.tickCount % 10 == 0) {
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

    @Override
    public String getAdvancementName() {
        return LibAdvancementNames.EGODEFEAT;
    }
}
