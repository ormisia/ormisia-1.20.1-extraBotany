package com.meteor.extrabotany.common.items.armor.maid;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.model.armor.ModelMaidArmor;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.armor.miku.ItemMikuArmor;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class ItemMaidArmor extends ItemMikuArmor {

    public ItemMaidArmor(ArmorItem.Type type, Item.Properties props) {
        super(type, ExtraBotanyAPI.INSTANCE.getMaidArmorMaterial(), props);
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> ret = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID(BuiltInRegistries.ITEM.getKey(this).hashCode() + slot.toString().hashCode(), 0);
        if (slot == getEquipmentSlot()) {
            ret = HashMultimap.create(ret);
            ret.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "Maid modifier " + type, 5, AttributeModifier.Operation.ADDITION));
            ret.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Maid modifier " + type, type.getIndex() / 20, AttributeModifier.Operation.ADDITION));
        }
        return ret;
    }

    /**
     * TODO(1.20.1): armor model hook — see {@link ItemMikuArmor#provideArmorModelForSlot}.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public HumanoidModel<?> provideArmorModelForSlot(EquipmentSlot slot) {
        return new ModelMaidArmor(slot);
    }

    @Override
    public String getArmorTextureAfterInk(ItemStack stack, EquipmentSlot slot) {
        return LibMisc.MOD_ID + ":textures/model/armor_maid.png";
    }

    @Override
    public ItemStack[] getArmorSetStacks() {
        return new ItemStack[] {
            new ItemStack(ModItems.armor_maid_helm.get()),
            new ItemStack(ModItems.armor_maid_chest.get()),
            new ItemStack(ModItems.armor_maid_legs.get()),
            new ItemStack(ModItems.armor_maid_boots.get())
        };
    }

    @Override
    public boolean hasArmorSetItem(Player player, EquipmentSlot slot) {
        if (player == null) {
            return false;
        }

        ItemStack stack = player.getItemBySlot(slot);
        if (stack.isEmpty()) {
            return false;
        }

        switch (slot) {
            case HEAD:
                return stack.getItem() == ModItems.armor_maid_helm.get();
            case CHEST:
                return stack.getItem() == ModItems.armor_maid_chest.get();
            case LEGS:
                return stack.getItem() == ModItems.armor_maid_legs.get();
            case FEET:
                return stack.getItem() == ModItems.armor_maid_boots.get();
        }

        return false;
    }

    @Override
    public MutableComponent getArmorSetName() {
        return Component.translatable("extrabotany.armorset.maid.name");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addArmorSetDescription(ItemStack stack, List<Component> list) {
        list.add(Component.translatable("extrabotany.armorset.maid.desc0").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("extrabotany.armorset.maid.desc1").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("extrabotany.armorset.maid.desc2").withStyle(ChatFormatting.GRAY));
    }

}
