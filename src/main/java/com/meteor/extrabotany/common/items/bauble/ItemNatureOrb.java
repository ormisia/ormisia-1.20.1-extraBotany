package com.meteor.extrabotany.common.items.bauble;

import com.meteor.extrabotany.common.entities.ego.EntityEGO;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.helper.ItemNBTHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class ItemNatureOrb extends ItemBauble{

    public static final String TAG_XP = "xp";
    public static final int MAX_XP = 500000;

    public ItemNatureOrb(Properties props) {
        super(props);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("extrabotany.natureorb", getXP(stack), getMaxXP(stack)).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("extrabotany.natureorbeffect1").withStyle(getXP(stack) >= 100000 ? ChatFormatting.AQUA : ChatFormatting.GRAY));
        tooltip.add(Component.translatable("extrabotany.natureorbeffect2").withStyle(getXP(stack) >= 300000 ? ChatFormatting.DARK_RED : ChatFormatting.GRAY));
        tooltip.add(Component.translatable("extrabotany.natureorbeffect3").withStyle(getXP(stack) >= 400000 ? ChatFormatting.DARK_GREEN : ChatFormatting.GRAY));
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        ItemStack stack = ctx.getItemInHand();
        return EntityEGO.spawn(ctx.getPlayer(), stack, ctx.getLevel(), ctx.getClickedPos()) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }

    @Override
    public void onWornTick(ItemStack stack, LivingEntity entity) {
        super.onWornTick(stack, entity);
        if(entity instanceof Player) {
            Player player = (Player) entity;
            if(!player.level().isClientSide) {
                if (getXP(stack) > 100000 && player.tickCount % 5 == 0)
                    ManaItemHandler.instance().dispatchManaExact(stack, player, 5, true);
                if (getXP(stack) > 200000 && player.tickCount % 5 == 0)
                    ManaItemHandler.instance().dispatchManaExact(stack, player, 5, true);
                if (getXP(stack) > 300000 && player.tickCount % 5 == 0) {
                    ManaItemHandler.instance().dispatchManaExact(stack, player, 5, true);
                    if (player.tickCount % 60 == 0)
                        player.heal(1F);
                }
                if (getXP(stack) > 400000) {
                    if (player.tickCount % 40 == 0) {
                        clearPotions(stack, player);
                    }
                }
            }
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F - 13.0F * (float) getXP(stack) / (float) getMaxXP(stack));
    }

    public boolean addXP(ItemStack stack, int xp){
        if(getXP(stack) >= getMaxXP(stack))
            return false;
        setXP(stack, Math.min(Math.max(getXP(stack) + xp, 0), getMaxXP(stack)));
        return true;
    }

    public void setXP(ItemStack stack, int xp) {
        ItemNBTHelper.setInt(stack, TAG_XP, xp);
    }

    public int getXP(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_XP, 0);
    }


    public int getMaxXP(ItemStack stack) {
        return MAX_XP;
    }

    public void clearPotions(ItemStack stack, Player player) {
        List<MobEffect> potionsToRemove = player.getActiveEffects().stream()
                .filter(effect -> effect.getEffect().getCategory() == MobEffectCategory.HARMFUL
                        && effect.getCurativeItems().stream().anyMatch(e -> e.is(Items.MILK_BUCKET)))
                .map(MobEffectInstance::getEffect)
                .distinct()
                .collect(Collectors.toList());

        potionsToRemove.forEach(potion -> {
            player.removeEffect(potion);
            addXP(stack, -50);
        });
    }

}
