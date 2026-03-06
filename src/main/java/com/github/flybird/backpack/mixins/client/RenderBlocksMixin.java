package com.github.flybird.backpack.mixins.client;

import com.github.flybird.backpack.api.EnumItemRenderType;
import com.github.flybird.backpack.api.IRenderBlock;
import com.github.flybird.backpack.block.BlockBackpack;
import com.github.flybird.backpack.client.render.RenderTileEntityBackpack;
import net.minecraft.Block;
import net.minecraft.RenderBlocks;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderBlocks.class)
public abstract class RenderBlocksMixin implements IRenderBlock {

    @Unique private EnumItemRenderType renderItemType;
    @Unique RenderTileEntityBackpack renderTileEntityBackpack = new RenderTileEntityBackpack();

    @Override
    public void backpack$setFlag(EnumItemRenderType type) {
        this.renderItemType = type;
    }

    @Override
    public EnumItemRenderType backpack$getFlag() {
        return this.renderItemType;
    }

    @Inject(method = "renderItemIn3d", at = @At("HEAD"), cancellable = true)
    private static void registerBackpackRender(int renderType, CallbackInfoReturnable<Boolean> cir) {
        if (renderType == BlockBackpack.backpackRenderType) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "renderBlockAsItem", at = {@At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/Block;getRenderType()I")})
    private void renderBackpack(Block par1Block, int par2, float par3, CallbackInfo ci) {
        int renderType = par1Block.getRenderType();
        if (renderType == BlockBackpack.backpackRenderType) {
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            if (this.renderItemType == EnumItemRenderType.INVENTORY) {
                GL11.glTranslatef(0.0F, -1.4F, 0.0F);
                GL11.glScalef(1.3125F, 1.3125F, 1.3125F);
                this.renderTileEntityBackpack.renderBackpack(0.0D, 0.0D, 0.0D, 0.0F);
                GL11.glTranslatef(0.0F, 0.1F, 0.0F);
                GL11.glEnable(32826);
            }
            if (this.renderItemType == EnumItemRenderType.ENTITY) {
                GL11.glScalef(1.5F, 1.5F, 1.5F);
                GL11.glTranslatef(0.0F, -1.5F, 0.0F);
                this.renderTileEntityBackpack.renderBackpack(0.0D, 0.0D, 0.0D, 0.0F);
                GL11.glEnable(32826);
            }
            if (this.renderItemType == EnumItemRenderType.HAND) {
                GL11.glScalef(2.0F, 2.0F, 2.0F);
                GL11.glTranslatef(0.0F, -0.8F, 0.0F);
                this.renderTileEntityBackpack.renderBackpack(0.0D, 0.0D, 0.0D, 0.0F);
                GL11.glEnable(32826);
            }
        }
    }
}
