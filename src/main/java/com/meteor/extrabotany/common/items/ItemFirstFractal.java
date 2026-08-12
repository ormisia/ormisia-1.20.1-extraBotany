package com.meteor.extrabotany.common.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.meteor.extrabotany.common.ExtraBotanyGroup;
import com.meteor.extrabotany.common.entities.projectile.EntityPhantomSword;
import com.meteor.extrabotany.common.handler.IAdvancementRequirement;
import com.meteor.extrabotany.common.items.relic.ItemSwordRelic;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ItemFirstFractal extends ItemSwordRelic implements IAdvancementRequirement {

    public ItemFirstFractal() {
        super(Tiers.NETHERITE, 10, -1.6F, new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).setNoRepair());
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@Nonnull EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> ret = super.getDefaultAttributeModifiers(slot);
        if (slot == EquipmentSlot.MAINHAND) {
            ret = HashMultimap.create(ret);
            ret.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("995829fa-94c0-41bd-b046-0468c509a488"), "Fractal modifier", 0.3D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        return ret;
    }

    public void attackEntity(LivingEntity player, Entity target) {
        BlockPos targetpos = target == null ? raytraceFromEntity(player, 80F, true).getBlockPos().offset(0, 1, 0) : target.blockPosition().offset(0, 1, 0);
        double range = 13D;
        double j = -Math.PI + 2 * Math.PI * Math.random();
        double k;
        double x, y, z;
        for (int i = 0; i < 3; i++) {
            EntityPhantomSword sword = new EntityPhantomSword(player.level(), player, targetpos);
            sword.setDelay(5 + 5 * i);
            k = 0.12F * Math.PI * Math.random() + 0.28F * Math.PI;
            x = targetpos.getX() + range * Math.sin(k) * Math.cos(j);
            y = targetpos.getY() + range * Math.cos(k);
            z = targetpos.getZ() + range * Math.sin(k) * Math.sin(j);
            j += 2 * Math.PI * Math.random() * 0.08F + 2 * Math.PI * 0.17F;
            sword.setPos(x, y, z);
            sword.faceTarget(1.05F);
            player.level().addFreshEntity(sword);

        }
        EntityPhantomSword sword2 = new EntityPhantomSword(player.level(), player, targetpos);
        k = 0.12F * Math.PI * Math.random() + 0.28F * Math.PI;
        x = targetpos.getX() + range * Math.sin(k) * Math.cos(j);
        y = targetpos.getY() + range * Math.cos(k);
        z = targetpos.getZ() + range * Math.sin(k) * Math.sin(j);
        sword2.setPos(x, y, z);
        sword2.faceTarget(1.05F);
        sword2.setVariety(9);
        player.level().addFreshEntity(sword2);
    }

    @Override
    public void onLeftClick(Player player, Entity target) {
        if (!player.level().isClientSide && !player.getMainHandItem().isEmpty() && player.getMainHandItem().getItem() == this
                && player.getAttackStrengthScale(0.0F) == 1) {
            attackEntity(player, target);
        }
    }

    @Override
    public String getAdvancementName() {
        return LibAdvancementNames.EGODEFEAT;
    }

}
