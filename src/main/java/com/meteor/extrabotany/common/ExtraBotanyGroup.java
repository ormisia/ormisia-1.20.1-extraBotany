package com.meteor.extrabotany.common;

import com.meteor.extrabotany.common.items.ModItems;
import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ExtraBotanyGroup {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LibMisc.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.pylon.get()))
            .title(Component.translatable("itemGroup.extrabotany"))
            .withSearchBar()
            .displayItems((params, output) -> ModItems.ITEMS.getEntries().forEach(entry -> output.accept(new ItemStack(entry.get()))))
            .build());

    private ExtraBotanyGroup() {}
}
