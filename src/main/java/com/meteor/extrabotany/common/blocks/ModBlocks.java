package com.meteor.extrabotany.common.blocks;

import com.meteor.extrabotany.common.libs.LibMisc;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LibMisc.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LibMisc.MOD_ID);

    public static final RegistryObject<Block> powerframe = registerBlock("powerframe", () -> new BlockPowerFrame(BlockBehaviour.Properties.copy(Blocks.SPAWNER)));
    public static final RegistryObject<Block> manabuffer = registerBlock("manabuffer", () -> new BlockManaBuffer(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> dimensioncatalyst = registerBlock("dimensioncatalyst", () -> new BlockDimensionCatalyst(BlockBehaviour.Properties.copy(Blocks.STONE)));

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> block) {
        RegistryObject<Block> obj = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new BlockItem(obj.get(), new Item.Properties()));
        return obj;
    }

}
