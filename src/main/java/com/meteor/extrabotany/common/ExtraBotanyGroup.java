package com.meteor.extrabotany.common;

import com.meteor.extrabotany.common.items.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class ExtraBotanyGroup {

    public static final CreativeModeTab TAB = CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.pylon.get()))
            .title(Component.translatable("itemGroup.extrabotany"))
            .withSearchBar()
            .displayItems((params, output) -> ModItems.ITEMS.getEntries().forEach(entry -> output.accept(new ItemStack(entry.get()))))
            .build();

    // Alias kept for compatibility with code still referencing the old field name.
    public static final CreativeModeTab itemGroup = TAB;

    private ExtraBotanyGroup() {}
}
