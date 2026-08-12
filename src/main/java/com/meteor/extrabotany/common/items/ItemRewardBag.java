package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.api.items.BonusHelper;
import com.meteor.extrabotany.api.items.WeightCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemRewardBag extends Item {

    List<WeightCategory> categoryList = new ArrayList<>();

    public ItemRewardBag(Properties prop, List<WeightCategory> categoryList) {
        super(prop);
        this.categoryList = categoryList;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        DecimalFormat df = new DecimalFormat("0.00%");
        int sum = BonusHelper.sum(categoryList);
        for (WeightCategory category : categoryList) {
            String percentage = df.format((float) category.getWeight() / sum);
            String stackname = Component.translatable(category.getCategory().getDescriptionId()).getString();
            int count = category.getCategory().getCount();
            ChatFormatting color = (float) category.getWeight() / sum <= 0.01F ? ChatFormatting.GOLD : ChatFormatting.RESET;
            tooltip.add(Component.literal(String.format("%s x%d %s", stackname, count, percentage)).withStyle(color));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand handIn) {
        ItemStack itemstack = player.getItemInHand(handIn);

        ItemStack reward = BonusHelper.rollItem(player, categoryList);

        if (!reward.isEmpty() && !worldIn.isClientSide) {
            ItemStack stack = reward.copy();
            worldIn.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT,
                    SoundSource.PLAYERS, 0.5F, 0.4F / (worldIn.random.nextFloat() * 0.4F + 0.8F));
            player.spawnAtLocation(stack).setNoPickUpDelay();
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            return InteractionResultHolder.success(itemstack);
        }

        return InteractionResultHolder.fail(itemstack);
    }

}
