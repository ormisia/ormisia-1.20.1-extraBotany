package com.meteor.extrabotany.common.items;

import com.meteor.extrabotany.common.items.armor.shootingguardian.ItemShootingGuardianArmor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.botania.api.mana.BasicLensItem;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.ManaBlasterItem;

import javax.annotation.Nonnull;

public class ItemSilverBullet extends ManaBlasterItem {

    public ItemSilverBullet(Properties props) {
        super(props);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemNBTHelper.setBoolean(stack, "usemana", !((ItemShootingGuardianArmor) ModItems.armor_shootingguardian_helm.get()).hasArmorSet(player));
        return super.use(world, player, hand);
    }

    @Nonnull
    @Override
    public BurstProperties getBurstProps(Player player, ItemStack stack, boolean request, InteractionHand hand) {
        int maxMana = 240;
        int color = 0x87CEFA;
        int ticksBeforeManaLoss = 80;
        float manaLossPerTick = 3F;
        float motionModifier = 7.5F;
        float gravity = 0F;
        BurstProperties props = new BurstProperties(maxMana, ticksBeforeManaLoss, manaLossPerTick, gravity, motionModifier, color);

        ItemStack lens = ManaBlasterItem.getLens(stack);
        if (!lens.isEmpty()) {
            ((BasicLensItem) lens.getItem()).apply(lens, props, player.level());
        }
        return props;
    }

    public boolean usesMana(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, "usemana", true);
    }

}
