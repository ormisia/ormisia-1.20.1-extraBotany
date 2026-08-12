package com.meteor.extrabotany.common.items.armor.shadowwarrior;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.model.armor.ModelShadowWarriorArmor;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.armor.miku.ItemMikuArmor;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.common.helper.ItemNBTHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class ItemShadowWarriorArmor extends ItemMikuArmor {

    public static final String TAG_NIGHT = "isnight";

    public ItemShadowWarriorArmor(ArmorItem.Type type, Properties props) {
        super(type, ExtraBotanyAPI.INSTANCE.getShadowWarriorArmorMaterial(), props);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        super.onArmorTick(stack, world, player);
        if(hasArmorSet(player) && !world.isDay()) {
            ItemNBTHelper.setBoolean(stack, TAG_NIGHT, true);
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 1));
        }else
            ItemNBTHelper.setBoolean(stack, TAG_NIGHT, false);
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> ret = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID(BuiltInRegistries.ITEM.getKey(this).hashCode() + slot.toString().hashCode(), 0);
        boolean night = ItemNBTHelper.getBoolean(stack, TAG_NIGHT, false);
        if (slot == getEquipmentSlot()) {
            ret = HashMultimap.create(ret);
            ret.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "ShadowWarrior modifier health" + type, night ? 0.25F : 0,  AttributeModifier.Operation.MULTIPLY_BASE));
            ret.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "ShadowWarrior modifier attack speed" + type, night ? 0.125F : 0, AttributeModifier.Operation.MULTIPLY_BASE));
            ret.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "ShadowWarrior modifier attack damage" + type, night ? 0.05F : 0, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return ret;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public HumanoidModel<?> provideArmorModelForSlot(EquipmentSlot slot) {
        return new ModelShadowWarriorArmor(slot);
    }

    @Override
    public String getArmorTextureAfterInk(ItemStack stack, EquipmentSlot slot) {
        return LibMisc.MOD_ID + ":textures/model/armor_shadowwarrior.png";
    }

    private static final ItemStack[] armorSet = new ItemStack[] {
            new ItemStack(ModItems.armor_shadowwarrior_helm.get()),
            new ItemStack(ModItems.armor_shadowwarrior_chest.get()),
            new ItemStack(ModItems.armor_shadowwarrior_legs.get()),
            new ItemStack(ModItems.armor_shadowwarrior_boots.get())
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
                return stack.getItem() == ModItems.armor_shadowwarrior_helm.get();
            case CHEST:
                return stack.getItem() == ModItems.armor_shadowwarrior_chest.get();
            case LEGS:
                return stack.getItem() == ModItems.armor_shadowwarrior_legs.get();
            case FEET:
                return stack.getItem() == ModItems.armor_shadowwarrior_boots.get();
        }

        return false;
    }

    @Override
    public MutableComponent getArmorSetName() {
        return Component.translatable("extrabotany.armorset.shadowwarrior.name");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addArmorSetDescription(ItemStack stack, List<Component> list) {
        list.add(Component.translatable("extrabotany.armorset.shadowwarrior.desc0").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("extrabotany.armorset.shadowwarrior.desc1").withStyle(ChatFormatting.GRAY));
    }

}
