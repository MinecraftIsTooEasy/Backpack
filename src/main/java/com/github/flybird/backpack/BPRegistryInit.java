package com.github.flybird.backpack;

import com.github.flybird.backpack.block.BlockBackpack;
import com.github.flybird.backpack.block.CreativeTabBackpack;
import huix.glacier.api.entrypoint.IGameRegistry;
import huix.glacier.api.registry.MinecraftRegistry;
import net.minecraft.Block;
import net.minecraft.CreativeTabs;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent;
import net.xiaoyu233.fml.reload.utils.IdUtil;

public class BPRegistryInit implements IGameRegistry {
    public static final CreativeTabs tabBackpack = new CreativeTabBackpack();
    public static final MinecraftRegistry registry = new MinecraftRegistry(BPModInit.NAMESPACE).initAutoItemRegister();

    public static final Block backpack = new BlockBackpack(IdUtil.getNextBlockID()).setCreativeTab(tabBackpack);

    @Override
    public void onGameRegistry() {
        registry.registerBlock(backpack, "backpack");
    }

    public static void registerRecipes(RecipeRegistryEvent register) {
        register.registerShapedRecipe(new ItemStack(backpack, 1),
                true,
                "ABA",
                "ACA",
                "BBB",
                'A', new ItemStack(Item.silk, 1),
                'B', new ItemStack(Item.leather, 1),
                'C', new ItemStack(Block.chest, 1));
        register.registerShapedRecipe(new ItemStack(backpack, 1), true,
                "ABA",
                "ACA",
                "BBB",
                'A', new ItemStack(Item.sinew, 1),
                'B', new ItemStack(Item.leather, 1),
                'C', new ItemStack(Block.chest, 1));
    }
}
