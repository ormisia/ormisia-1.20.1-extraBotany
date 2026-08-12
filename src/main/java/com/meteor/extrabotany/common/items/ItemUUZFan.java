package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.common.entities.projectile.EntityButterflyProjectile;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemUUZFan extends Item {

    public ItemUUZFan(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand handIn) {
        ItemStack itemstack = player.getItemInHand(handIn);
        player.getCooldowns().addCooldown(this, 10);
        if (!worldIn.isClientSide)
            for (int i = -1; i < 2; i++) {
                EntityButterflyProjectile proj = new EntityButterflyProjectile(worldIn, player);
                proj.setPos(player.getX(), player.getY(), player.getZ());
                proj.shootFromRotation(player, player.getXRot(), player.getYRot() + 25F * i, 0.0F, 0.5F, 1F);
                worldIn.addFreshEntity(proj);
            }
        return InteractionResultHolder.pass(itemstack);
    }

}
