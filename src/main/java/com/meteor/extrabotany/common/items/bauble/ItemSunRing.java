package com.meteor.extrabotany.common.items.bauble;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.common.core.EquipmentHandler;
import com.meteor.extrabotany.common.handler.IAdvancementRequirement;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import vazkii.botania.common.handler.PixieHandler;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.equipment.bauble.BandOfAuraItem;
import vazkii.botania.common.item.equipment.bauble.RingOfChordataItem;
import vazkii.botania.common.item.equipment.bauble.RingOfCorrectionItem;
import vazkii.botania.common.item.equipment.bauble.RingOfTheMantleItem;
import vazkii.botania.common.item.relic.RelicBaubleItem;

import static com.meteor.extrabotany.common.items.ModItems.*;

public class ItemSunRing extends RelicBaubleItem implements IAdvancementRequirement {

    public ItemSunRing(Properties props) {
        super(props);
    }

    @Override
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return EquipmentHandler.findOrEmpty(this, entity).isEmpty();
    }

    @Override
    public void onWornTick(ItemStack stack, LivingEntity player) {
        super.onWornTick(stack, player);
        ((RingOfTheMantleItem) BotaniaItems.miningRing).onWornTick(stack, player);
        ((BandOfAuraItem) BotaniaItems.auraRingGreater).onWornTick(stack, player);
        ((RingOfCorrectionItem) BotaniaItems.swapRing).onWornTick(stack, player);
        ((RingOfChordataItem) BotaniaItems.waterRing).onWornTick(stack, player);
        ((ItemDeathRing) deathring.get()).onWornTick(stack, player);
        ((ItemFrostStar) froststar.get()).onWornTick(stack, player);
        ((ItemManaDriveRing) manadrivering.get()).onWornTick(stack, player);
    }

    @Override
    public void onUnequipped(ItemStack stack, LivingEntity player) {
        super.onUnequipped(stack, player);
        ((RingOfTheMantleItem) BotaniaItems.miningRing).onUnequipped(stack, player);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(getBaubleUUID(stack), "Sun Ring", 3.5, AttributeModifier.Operation.ADDITION));
        attributes.put(PixieHandler.PIXIE_SPAWN_CHANCE, new AttributeModifier(getBaubleUUID(stack), "Ring modifier", 0.25, AttributeModifier.Operation.ADDITION));
        return attributes;
    }

    @Override
    public String getAdvancementName() {
        return LibAdvancementNames.EGODEFEAT;
    }
}
