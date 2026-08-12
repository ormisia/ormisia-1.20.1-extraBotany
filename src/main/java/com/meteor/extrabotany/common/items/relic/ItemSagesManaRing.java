package com.meteor.extrabotany.common.items.relic;

import com.meteor.extrabotany.common.handler.IAdvancementRequirement;
import com.meteor.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.relic.RelicBaubleItem;

/**
 * Ported from 1.16. NOTE: 1.16's {@code vazkii.botania.api.mana.IManaItem} / {@code IManaTooltipDisplay}
 * are gone in 1.20.1. Mana storage is now a per-stack capability ({@code BotaniaForgeCapabilities.MANA_ITEM},
 * typically implemented via {@code vazkii.botania.common.item.equipment.bauble.BandOfManaItem$ManaItemImpl}).
 * This item keeps the NBT-backed mana helpers; the capability provider must still be wired up so that
 * {@code ManaItemHandler} can see it as a mana source. The creative-tab "full mana" variant (old
 * {@code fillItemGroup}) also needs to be re-added through Botania's {@code CustomCreativeTabContents}.
 */
public class ItemSagesManaRing extends RelicBaubleItem implements IAdvancementRequirement {

    protected static final int MAX_MANA = Integer.MAX_VALUE - 1;

    private static final String TAG_MANA = "mana";

    public ItemSagesManaRing(Item.Properties props) {
        super(props);
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, Level level) {
        return Integer.MAX_VALUE;
    }

    public static void setMana(ItemStack stack, int mana) {
        ItemNBTHelper.setInt(stack, TAG_MANA, mana);
    }

    public int getMana(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_MANA, 0) * stack.getCount();
    }

    public int getMaxMana(ItemStack stack) {
        return MAX_MANA * stack.getCount();
    }

    public void addMana(ItemStack stack, int mana) {
        int space = Math.max(getMaxMana(stack) - getMana(stack), 0);
        int manaToTransfer = Math.min(space, mana);
        setMana(stack, Math.min(getMana(stack) + manaToTransfer, getMaxMana(stack)) / stack.getCount());
    }

    public float getManaFractionForDisplay(ItemStack stack) {
        return (float) getMana(stack) / (float) getMaxMana(stack);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F * getManaFractionForDisplay(stack));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return Mth.hsvToRgb(Math.max(0, getManaFractionForDisplay(stack) / 3.0F), 1.0F, 1.0F);
    }

    @Override
    public String getAdvancementName() {
        return LibAdvancementNames.EGODEFEAT;
    }

}
