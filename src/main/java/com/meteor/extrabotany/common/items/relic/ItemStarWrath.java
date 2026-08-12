package com.meteor.extrabotany.common.items.relic;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.phys.Vec3;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.entity.FallingStarEntity;

public class ItemStarWrath extends ItemSwordRelic {

    public static final int MANA_PER_DAMAGE = 500;

    public ItemStarWrath(Item.Properties prop) {
        super(Tiers.DIAMOND, 6, -1.6F, prop);
    }

    public void attackEntity(LivingEntity player, Entity target) {
        BlockPos targetpos = target == null ? raytraceFromEntity(player, 64F, true).getBlockPos().offset(0, 1, 0) : target.blockPosition().offset(0, 1, 0);

        for (int i = 0; i < 5; i++) {
            Vec3 posVec = Vec3.atLowerCornerOf(targetpos).add((0.5F - Math.random()) * 6F, 0, (0.5F - Math.random()) * 6F);
            Vec3 motVec = new Vec3((0.5 * Math.random() - 0.25) * 18, 24, (0.5 * Math.random() - 0.25) * 18);
            posVec = posVec.add(motVec);
            motVec = motVec.normalize().reverse().scale(1.5);

            FallingStarEntity star = new FallingStarEntity(player, player.level());
            star.setPos(posVec.x, posVec.y, posVec.z);
            star.setDeltaMovement(motVec);
            player.level().addFreshEntity(star);
        }
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
