package com.meteor.extrabotany.api;

import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ExtraBotanyAPI {

    public static ExtraBotanyAPI INSTANCE = new ExtraBotanyAPI();

    private enum ArmorMaterial implements net.minecraft.world.item.ArmorMaterial {
        MIKU("miku", 5, new int[] { 2, 4, 5, 1 }, 22, () -> SoundEvents.ARMOR_EQUIP_LEATHER, () -> ModItems.manadrink.get(), 0),
        MAID("maid", 40, new int[] { 4, 7, 9, 4 }, 32, () -> SoundEvents.ARMOR_EQUIP_DIAMOND, () -> ModItems.goldcloth.get(), 3),
        GOBLINSLAYER("goblinslayer", 27, new int[] { 3, 5, 7, 2 }, 30, () -> SoundEvents.ARMOR_EQUIP_IRON, () -> ModItems.photonium.get(), 1),
        SHADOWWARRIOR("shadowwarrior", 24, new int[] { 2, 5, 6, 2 }, 26, () -> SoundEvents.ARMOR_EQUIP_IRON, () -> ModItems.shadowium.get(), 1),
        SHOOTINGGUARDIAN("shootingguardian", 34, new int[] { 3, 7, 8, 4 }, 34, () -> SoundEvents.ARMOR_EQUIP_IRON, () -> ModItems.orichalcos.get(), 2),
        SILENTSAGES("silentsages", 50, new int[] { 4, 8, 9, 5 }, 40, () -> SoundEvents.ARMOR_EQUIP_IRON, () -> ModItems.orichalcos.get(), 3);

        private final String name;
        private final int durabilityMultiplier;
        private final int[] damageReduction;
        private final int enchantability;
        private final Supplier<SoundEvent> equipSound;
        private final Supplier<Item> repairItem;
        private final float toughness;
        private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };

        ArmorMaterial(String name, int durabilityMultiplier, int[] damageReduction, int enchantability, Supplier<SoundEvent> equipSound, Supplier<Item> repairItem, float toughness) {
            this.name = name;
            this.durabilityMultiplier = durabilityMultiplier;
            this.damageReduction = damageReduction;
            this.enchantability = enchantability;
            this.equipSound = equipSound;
            this.repairItem = repairItem;
            this.toughness = toughness;
        }

        @Override
        public int getDurabilityForType(ArmorItem.Type pType) {
            return durabilityMultiplier * MAX_DAMAGE_ARRAY[pType.getSlot().getIndex()];
        }

        @Override
        public int getDefenseForType(ArmorItem.Type pType) {
            return damageReduction[pType.getSlot().getIndex()];
        }

        @Override
        public int getEnchantmentValue() {
            return enchantability;
        }

        @Nonnull
        @Override
        public SoundEvent getEquipSound() {
            return equipSound.get();
        }

        @Nonnull
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(repairItem.get());
        }

        @Nonnull
        @Override
        public String getName() {
            return name;
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    }

    public net.minecraft.world.item.ArmorMaterial getMaidArmorMaterial() {
        return ArmorMaterial.MAID;
    }

    public net.minecraft.world.item.ArmorMaterial getMikuArmorMaterial() {
        return ArmorMaterial.MIKU;
    }

    public net.minecraft.world.item.ArmorMaterial getGoblinSlayerArmorMaterial() {
        return ArmorMaterial.GOBLINSLAYER;
    }

    public net.minecraft.world.item.ArmorMaterial getShadowWarriorArmorMaterial() {
        return ArmorMaterial.SHADOWWARRIOR;
    }

    public net.minecraft.world.item.ArmorMaterial getShootingGuardianArmorMaterial() {
        return ArmorMaterial.SHOOTINGGUARDIAN;
    }

    public net.minecraft.world.item.ArmorMaterial getSilentSagesArmorMaterial() {
        return ArmorMaterial.SILENTSAGES;
    }

    public static void addPotionEffect(LivingEntity entity, MobEffect potion, int time, int max, boolean multi) {
        if (!entity.hasEffect(potion))
            entity.addEffect(new MobEffectInstance(potion, time, 0));
        else {
            int amp = entity.getEffect(potion).getAmplifier();
            int t = multi ? time + 200 * amp : time;
            entity.addEffect(new MobEffectInstance(potion, t, Math.min(max, amp + 1)));
        }
    }

    public static void addPotionEffect(LivingEntity entity, MobEffect potion, int max) {
        addPotionEffect(entity, potion, 100, max, false);
    }

    public static float calcDamage(float orig, Player player){
        if(player == null)
            return orig;
        double value = player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        return (float) (orig + value);
    }

}
