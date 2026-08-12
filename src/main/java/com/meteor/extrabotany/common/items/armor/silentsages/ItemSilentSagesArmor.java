package com.meteor.extrabotany.common.items.armor.silentsages;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.model.armor.ModelShadowWarriorArmor;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.items.armor.miku.ItemMikuArmor;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemSilentSagesArmor extends ItemMikuArmor {

    public ItemSilentSagesArmor(ArmorItem.Type type, Properties props) {
        super(type, ExtraBotanyAPI.INSTANCE.getSilentSagesArmorMaterial(), props);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public HumanoidModel<?> provideArmorModelForSlot(EquipmentSlot slot) {
        return new ModelShadowWarriorArmor(slot);
    }

    @Override
    public String getArmorTextureAfterInk(ItemStack stack, EquipmentSlot slot) {
        return LibMisc.MOD_ID + ":textures/model/armor_silentsages.png";
    }

    @Override
    public ItemStack[] getArmorSetStacks() {
        return new ItemStack[] {
            new ItemStack(ModItems.armor_shadowwarrior_helm.get()),
            new ItemStack(ModItems.armor_shadowwarrior_chest.get()),
            new ItemStack(ModItems.armor_shadowwarrior_legs.get()),
            new ItemStack(ModItems.armor_shadowwarrior_boots.get())
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
        return Component.translatable("extrabotany.armorset.silentsages.name");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addArmorSetDescription(ItemStack stack, List<Component> list) {
        list.add(Component.translatable("extrabotany.armorset.silentsages.desc").withStyle(ChatFormatting.GRAY));
    }

}
