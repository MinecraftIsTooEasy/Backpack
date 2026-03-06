package com.github.flybird.backpack.mixins.client;

import com.github.flybird.backpack.api.EnumItemRenderType;
import com.github.flybird.backpack.api.IRenderBlock;
import net.minecraft.EntityLivingBase;
import net.minecraft.ItemRenderer;
import net.minecraft.ItemStack;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Unique private final ItemRenderer instance = ReflectHelper.dyCast(this);

    @Inject(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/RenderBlocks;renderBlockAsItem(Lnet/minecraft/Block;IF)V"))
    private void renderItem(EntityLivingBase par1EntityLivingBase, ItemStack par2ItemStack, int par3, CallbackInfo ci) {
        ((IRenderBlock) this.instance.renderBlocksInstance).backpack$setFlag(EnumItemRenderType.HAND);
    }
}
