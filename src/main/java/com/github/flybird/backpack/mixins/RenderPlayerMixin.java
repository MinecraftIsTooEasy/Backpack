package com.github.flybird.backpack.mixins;

import com.github.flybird.backpack.client.render.RenderBackpackWithPlayer;
import net.minecraft.AbstractClientPlayer;
import net.minecraft.ModelBiped;
import net.minecraft.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public class RenderPlayerMixin {

    @Shadow private ModelBiped modelBipedMain;

    @Inject(method = "renderSpecials", at = @At("HEAD"))
    private void renderBackpackOnPlayer(AbstractClientPlayer par1AbstractClientPlayer, float par2, CallbackInfo ci) {
        RenderBackpackWithPlayer.instance.renderModel(par1AbstractClientPlayer, this.modelBipedMain);
    }
}
