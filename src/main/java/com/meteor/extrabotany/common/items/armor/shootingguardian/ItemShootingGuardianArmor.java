package com.meteor.extrabotany.common.items.armor.shootingguardian;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.model.armor.ModelShootingGuardianArmor;
import com.meteor.extrabotany.client.model.armor.ModelShootingGuardianHelmet;
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

public class ItemShootingGuardianArmor extends ItemMikuArmor {

    public ItemShootingGuardianArmor(ArmorItem.Type type, Properties props) {
        super(type, ExtraBotanyAPI.INSTANCE.getShootingGuardianArmorMaterial(), props);
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> ret = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID(BuiltInRegistries.ITEM.getKey(this).hashCode() + slot.toString().hashCode(), 0);
        if (slot == getEquipmentSlot()) {
            ret = HashMultimap.create(ret);
            ret.put(Attributes.FLYING_SPEED, new AttributeModifier(uuid, "Shooting Guardian modifier flying speed" + type, 0.10F, AttributeModifier.Operation.MULTIPLY_BASE));
            ret.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Shooting Guardian modifier speed" + type, 0.10F, AttributeModifier.Operation.MULTIPLY_BASE));
            ret.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "Shooting Guardian modifier attack damage" + type, 0.10F, AttributeModifier.Operation.MULTIPLY_BASE));
            ret.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "Shooting Guardian modifier attack speed" + type, 0.03F, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return ret;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public HumanoidModel<?> provideArmorModelForSlot(EquipmentSlot slot) {
        return slot == EquipmentSlot.HEAD ? new ModelShootingGuardianHelmet(slot) : new ModelShootingGuardianArmor(slot);
    }

    @Override
    public String getArmorTextureAfterInk(ItemStack stack, EquipmentSlot slot) {
        return slot == EquipmentSlot.HEAD ? LibMisc.MOD_ID + ":textures/model/armor_shootingguardian_helmet.png" : LibMisc.MOD_ID + ":textures/model/armor_shootingguardian.png";
    }

    private static final ItemStack[] armorSet = new ItemStack[] {
            new ItemStack(ModItems.armor_shootingguardian_helm.get()),
            new ItemStack(ModItems.armor_shootingguardian_chest.get()),
            new ItemStack(ModItems.armor_shootingguardian_legs.get()),
            new ItemStack(ModItems.armor_shootingguardian_boots.get())
    };

    @Override
    public ItemStack[] getArmorSetStacks() {
        return armorSet;
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
                return stack.getItem() == ModItems.armor_shootingguardian_helm.get();
            case CHEST:
                return stack.getItem() == ModItems.armor_shootingguardian_chest.get();
            case LEGS:
                return stack.getItem() == ModItems.armor_shootingguardian_legs.get();
            case FEET:
                return stack.getItem() == ModItems.armor_shootingguardian_boots.get();
        }

        return false;
    }

    @Override
    public MutableComponent getArmorSetName() {
        return Component.translatable("extrabotany.armorset.shootingguardian.name");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addArmorSetDescription(ItemStack stack, List<Component> list) {
        list.add(Component.translatable("extrabotany.armorset.shootingguardian.desc0").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("extrabotany.armorset.shootingguardian.desc1").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("extrabotany.armorset.shootingguardian.desc2").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("extrabotany.armorset.shootingguardian.desc3").withStyle(ChatFormatting.GRAY));
    }

}
