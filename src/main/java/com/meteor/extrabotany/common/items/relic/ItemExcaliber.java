package com.meteor.extrabotany.common.items.relic;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.common.handler.DamageHandler;
import com.meteor.extrabotany.common.handler.IAdvancementRequirement;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.internal.ManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.mana.LensEffectItem;
import vazkii.botania.common.entity.ManaBurstEntity;
import vazkii.botania.common.handler.BotaniaSounds;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.helper.VecHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

public class ItemExcaliber extends ItemSwordRelic implements LensEffectItem, IAdvancementRequirement {

    private static final String TAG_ATTACKER_USERNAME = "attackerUsername";
    private static final String TAG_HOME_ID = "homeID";
    private static final int MANA_PER_DAMAGE = 160;

    public ItemExcaliber(Item.Properties prop) {
        super(Tiers.NETHERITE, 8, -2F, prop);
    }

    @Override
    public void onLeftClick(Player player, Entity target) {
        if (!player.getMainHandItem().isEmpty() && player.getMainHandItem().getItem() == this
                && player.getAttackStrengthScale(0) == 1) {
            ManaBurstEntity burst = getBurst(player, player.getMainHandItem());
            player.level().addFreshEntity(burst);
            ToolCommons.damageItemIfPossible(player.getMainHandItem(), 1, player, MANA_PER_DAMAGE);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), BotaniaSounds.terraBlade,
                    SoundSource.PLAYERS, 0.4F, 1.4F);
        }
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@Nonnull EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> ret = super.getDefaultAttributeModifiers(slot);
        if (slot == EquipmentSlot.MAINHAND) {
            ret = HashMultimap.create(ret);
            ret.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("995829fa-94c0-41bd-b046-0468c509a488"), "Excaliber modifier", 0.3D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        return ret;
    }

    public static ManaBurstEntity getBurst(Player player, ItemStack stack) {
        ManaBurstEntity burst = new ManaBurstEntity(player);

        float motionModifier = 9F;
        burst.setColor(0xFFFF20);
        burst.setMana(MANA_PER_DAMAGE);
        burst.setStartingMana(MANA_PER_DAMAGE);
        burst.setMinManaLoss(40);
        burst.setManaLossPerTick(4F);
        burst.setGravity(0F);
        burst.setDeltaMovement(burst.getDeltaMovement().x * motionModifier, burst.getDeltaMovement().y * motionModifier, burst.getDeltaMovement().z * motionModifier);

        ItemStack lens = stack.copy();
        ItemNBTHelper.setString(lens, TAG_ATTACKER_USERNAME, player.getName().getString());
        burst.setSourceLens(lens);
        return burst;
    }

    @Override
    public void apply(ItemStack stack, BurstProperties props, Level level) {

    }

    @Override
    public boolean collideBurst(ManaBurst burst, HitResult pos, boolean isManaBlock, boolean dead, ItemStack stack) {
        return dead;
    }

    @Override
    public void updateBurst(ManaBurst burst, ItemStack stack) {
        ThrowableProjectile entity = burst.entity();
        AABB axis = new AABB(entity.getX(), entity.getY(), entity.getZ(), entity.xo, entity.yo, entity.zo).inflate(1);
        String attacker = ItemNBTHelper.getString(burst.getSourceLens(), TAG_ATTACKER_USERNAME, "");


        int homeID = ItemNBTHelper.getInt(stack, TAG_HOME_ID, -1);
        if (homeID == -1) {
            AABB axis1 = new AABB(entity.getX() - 5F, entity.getY() - 5F, entity.getZ() - 5F,
                    entity.xo + 5F, entity.yo + 5F, entity.zo + 5F);
            List<LivingEntity> entities = entity.level().getEntitiesOfClass(LivingEntity.class, axis1);
            for (LivingEntity living : entities) {
                if (living instanceof Player || !(living instanceof Enemy) || living.hurtTime != 0)
                    continue;
                homeID = living.getId();
                ItemNBTHelper.setInt(stack, TAG_HOME_ID, homeID);
                break;
            }
        }

        List<LivingEntity> entities = entity.level().getEntitiesOfClass(LivingEntity.class, axis);
        if (homeID != -1) {
            Entity home = entity.level().getEntity(homeID);
            if (home != null) {
                Vec3 vecEntity = VecHelper.fromEntityCenter(home);
                Vec3 vecThis = VecHelper.fromEntityCenter(entity);
                Vec3 vecMotion = vecEntity.subtract(vecThis);
                Vec3 vecCurrentMotion = entity.getDeltaMovement();
                vecMotion = vecMotion.normalize().scale(vecCurrentMotion.length());
                entity.setDeltaMovement(vecMotion);
            }
        }

        for (LivingEntity living : entities) {
            if (living instanceof Player && (living.getName().getString().equals(attacker)))
                continue;

            if (!living.isRemoved()) {
                int cost = MANA_PER_DAMAGE / 3;
                int mana = burst.getMana();
                if (mana >= cost) {
                    burst.setMana(mana - cost);
                    float damage = BotaniaAPI.instance().getTerrasteelItemTier().getAttackDamageBonus() + 3F;
                    if (!burst.isFake() && !entity.level().isClientSide) {
                        Player player = living.level().getPlayerByUUID(getSoulbindUUID(stack));
                        DamageHandler.INSTANCE.dmg(living, player, damage, DamageHandler.INSTANCE.NETURAL_PIERCING);
                        entity.discard();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public boolean doParticles(ManaBurst burst, ItemStack stack) {
        return true;
    }

    @Override
    public String getAdvancementName() {
        return LibAdvancementNames.EGODEFEAT;
    }
}
