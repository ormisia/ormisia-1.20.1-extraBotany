package com.meteor.extrabotany.common.items.relic;

import com.meteor.extrabotany.common.core.Helper;
import com.meteor.extrabotany.common.entities.projectile.EntityInfluxWaverProjectile;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.phys.Vec3;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemInfluxWaver extends ItemSwordRelic {

    public static final int MANA_PER_DAMAGE = 500;

    public ItemInfluxWaver(Item.Properties prop) {
        super(Tiers.DIAMOND, 5, -2F, prop);
    }

    public void attackEntity(LivingEntity player, Entity target) {
        Vec3 targetpos = target == null ? Helper.PosToVec(raytraceFromEntity(player, 64F, true).getBlockPos()).add(0, 1, 0) : target.position().add(0, 1, 0);
        EntityInfluxWaverProjectile proj = new EntityInfluxWaverProjectile(player.level(), player);
        proj.setPos(player.getX(), player.getY() + 1.1D, player.getZ());
        proj.setTargetPos(targetpos);
        proj.faceTargetAccurately(0.7F);
        proj.setStrikeTimes(3);
        player.level().addFreshEntity(proj);
    }

    @Override
    public void onLeftClick(Player player, Entity target) {
        if (!player.level().isClientSide && !player.getMainHandItem().isEmpty() && player.getMainHandItem().getItem() == this
                && player.getAttackStrengthScale(0) == 1
                && ManaItemHandler.instance().requestManaExactForTool(player.getMainHandItem(), player, MANA_PER_DAMAGE, true)) {
            attackEntity(player, target);
        }
    }

}
