package com.meteor.extrabotany.common.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemRodOfDiscord extends Item {

    private static final int MANA_PER_DAMAGE = 2000;

    public ItemRodOfDiscord(Properties properties) {
        super(properties.durability(81));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand handIn) {
        ItemStack itemstack = player.getItemInHand(handIn);
        BlockHitResult rtr = getPlayerPOVHitResult(worldIn, player, ClipContext.Fluid.NONE);
        if (rtr.getType() != HitResult.Type.MISS && ManaItemHandler.instance().requestManaExactForTool(itemstack, player, MANA_PER_DAMAGE, true)) {
            Vec3 end = rtr.getLocation();
            player.teleportTo(end.x, end.y + 1, end.z);
            if (!worldIn.isClientSide)
                worldIn.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1F, 3F);
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100));
            if (itemstack.getDamageValue() > 0) {
                float health = Math.max(1F, player.getHealth() - player.getMaxHealth() / 6F);
                player.setHealth(health);
            }
            itemstack.setDamageValue(itemstack.getMaxDamage() - 1);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!world.isClientSide && stack.getDamageValue() > 0) {
            stack.setDamageValue(stack.getDamageValue() - 1);
        }
    }

}
