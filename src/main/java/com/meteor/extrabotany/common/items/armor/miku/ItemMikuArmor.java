package com.meteor.extrabotany.common.items.armor.miku;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.client.model.armor.ModelMikuArmor;
import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.api.item.PhantomInkable;
import vazkii.botania.api.mana.ManaDiscountArmor;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.gui.TooltipHandler;
import vazkii.botania.client.lib.ResourcesLib;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

/**
 * Ported from 1.16. NOTE: 1.16's {@code IManaUsingItem} interface is gone in 1.20.1; mana repair is kept via
 * {@code ManaItemHandler}. The 1.16 {@code getArmorModel}/{@code provideArmorModelForSlot} item hooks no longer
 * exist in 1.20.1 — armor models must be supplied through {@code IClientItemExtensions.getGenericArmorModel},
 * so the model reference below is kept as a plain helper and marked TODO.
 */
public class ItemMikuArmor extends ArmorItem implements ManaDiscountArmor, PhantomInkable, net.minecraftforge.client.extensions.common.IClientItemExtensions {

    private static final int MANA_PER_DAMAGE = 70;

    private static final String TAG_PHANTOM_INK = "phantomInk";

    public final EquipmentSlot type;

    public ItemMikuArmor(ArmorItem.Type armorType, Item.Properties props) {
        this(armorType, ExtraBotanyAPI.INSTANCE.getMikuArmorMaterial(), props);
    }

    public ItemMikuArmor(ArmorItem.Type armorType, ArmorMaterial mat, Item.Properties props) {
        super(mat, armorType, props);
        this.type = armorType.getSlot();
    }

    @Override
    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
        return provideArmorModelForSlot(equipmentSlot);
    }

    @Override
    public float getDiscount(ItemStack stack, int slot, Player player, @Nullable ItemStack tool) {
        return hasArmorSet(player) ? 0.15F : 0F;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        TooltipHandler.addOnShift(list, () -> addInformationAfterShift(stack, level, list, flags));
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformationAfterShift(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        Player player = Minecraft.getInstance().player;
        list.add(getArmorSetTitle(player));
        addArmorSetDescription(stack, list);
        ItemStack[] stacks = getArmorSetStacks();
        for (ItemStack armor : stacks) {
            MutableComponent cmp = Component.literal(" - ").append(armor.getHoverName());
            EquipmentSlot slot = ((ArmorItem) armor.getItem()).getEquipmentSlot();
            cmp.withStyle(hasArmorSetItem(player, slot) ? ChatFormatting.GREEN : ChatFormatting.GRAY);
            list.add(cmp);
        }
        if (hasPhantomInk(stack)) {
            list.add(Component.translatable("botaniamisc.hasPhantomInk").withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity player, int slot, boolean selected) {
        if (player instanceof Player) {
            onArmorTick(stack, level, (Player) player);
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide && stack.getDamageValue() > 0 && ManaItemHandler.instance().requestManaExact(stack, player, MANA_PER_DAMAGE * 2, true)) {
            stack.setDamageValue(stack.getDamageValue() - 1);
        }
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, MANA_PER_DAMAGE);
    }

    @Nonnull
    @Override
    public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return hasPhantomInk(stack) ? ResourcesLib.MODEL_INVISIBLE_ARMOR : getArmorTextureAfterInk(stack, slot);
    }

    public String getArmorTextureAfterInk(ItemStack stack, EquipmentSlot slot) {
        return LibMisc.MOD_ID + ":textures/model/armor_miku.png";
    }

    /**
     * TODO(1.20.1): armor model hook. In 1.16 this was wired through the item's {@code getArmorModel} override.
     * In 1.20.1 the armor model must be returned from {@code IClientItemExtensions.getGenericArmorModel}, e.g.
     * via {@code IClientItemExtensions.of(stack)}. The model class reference is kept for that future wiring.
     */
    @OnlyIn(Dist.CLIENT)
    public HumanoidModel<?> provideArmorModelForSlot(EquipmentSlot slot) {
        return new ModelMikuArmor(slot);
    }

    private static final ItemStack[] armorSet = new ItemStack[] {
            new ItemStack(ModItems.armor_miku_helm.get()),
            new ItemStack(ModItems.armor_miku_chest.get()),
            new ItemStack(ModItems.armor_miku_legs.get()),
            new ItemStack(ModItems.armor_miku_boots.get())
    };

    public ItemStack[] getArmorSetStacks() {
        return armorSet;
    }

    public boolean hasArmorSet(Player player) {
        return hasArmorSetItem(player, EquipmentSlot.HEAD) && hasArmorSetItem(player, EquipmentSlot.CHEST) && hasArmorSetItem(player, EquipmentSlot.LEGS) && hasArmorSetItem(player, EquipmentSlot.FEET);
    }

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
                return stack.getItem() == ModItems.armor_miku_helm.get();
            case CHEST:
                return stack.getItem() == ModItems.armor_miku_chest.get();
            case LEGS:
                return stack.getItem() == ModItems.armor_miku_legs.get();
            case FEET:
                return stack.getItem() == ModItems.armor_miku_boots.get();
        }

        return false;
    }

    private int getSetPiecesEquipped(Player player) {
        int pieces = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR && hasArmorSetItem(player, slot)) {
                pieces++;
            }
        }

        return pieces;
    }

    public MutableComponent getArmorSetName() {
        return Component.translatable("extrabotany.armorset.miku.name");
    }

    private Component getArmorSetTitle(Player player) {
        Component end = getArmorSetName()
                .append(" (" + getSetPiecesEquipped(player) + "/" + getArmorSetStacks().length + ")")
                .withStyle(ChatFormatting.GRAY);
        return Component.translatable("botaniamisc.armorset")
                .append(" ")
                .append(end);
    }

    @OnlyIn(Dist.CLIENT)
    public void addArmorSetDescription(ItemStack stack, List<Component> list) {
        list.add(Component.translatable("extrabotany.armorset.miku.desc").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean hasPhantomInk(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, TAG_PHANTOM_INK, false);
    }

    @Override
    public void setPhantomInk(ItemStack stack, boolean ink) {
        ItemNBTHelper.setBoolean(stack, TAG_PHANTOM_INK, ink);
    }

}
