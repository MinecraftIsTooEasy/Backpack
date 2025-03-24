package com.github.flybird.backpack.mixins;

import com.github.flybird.backpack.api.EnumItemRenderType;
import com.github.flybird.backpack.api.IRenderBlock;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.Block;
import net.minecraft.EntityItem;
import net.minecraft.FontRenderer;
import net.minecraft.ItemStack;
import net.minecraft.RenderItem;
import net.minecraft.TextureManager;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public class RenderItemMixin {

    @Unique private final RenderItem instance = ReflectHelper.dyCast(this);

    @Inject(method = "renderItemIntoGUI", at = @At(value = "INVOKE", target = "Lnet/minecraft/RenderBlocks;renderBlockAsItem(Lnet/minecraft/Block;IF)V"))
    private void renderItemIntoGUIMixin(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5, CallbackInfo ci) {
        ((IRenderBlock) this.instance.itemRenderBlocks).backpack$setFlag(EnumItemRenderType.INVENTORY);
    }

    @Inject(method = "doRenderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/RenderBlocks;renderBlockAsItem(Lnet/minecraft/Block;IF)V"))
    private void renderItemIntoGUIMixin(EntityItem par1EntityItem, double par2, double par4, double par6, float par8, float par9, CallbackInfo ci) {
        ((IRenderBlock) this.instance.itemRenderBlocks).backpack$setFlag(EnumItemRenderType.ENTITY);
    }
}
