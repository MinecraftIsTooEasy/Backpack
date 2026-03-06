package com.github.flybird.backpack.mixins.client;

import com.github.flybird.backpack.BPEvents;
import com.github.flybird.backpack.api.IEntityClientPlayerMP;
import com.github.flybird.backpack.network.C2SBackpackGui;
import moddedmite.rustedironcore.network.Network;
import net.minecraft.EntityClientPlayerMP;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Minecraft.class})
public class MinecraftMixin {

    @Shadow public EntityClientPlayerMP thePlayer;

    @Inject(method = {"runTick"}, at = {@At("TAIL")})
    private void injectCustomHotkey(CallbackInfo ci) {
        if (BPEvents.keyBindOpenBackpack.isPressed()) {
            boolean openedBackpack = ((IEntityClientPlayerMP) this.thePlayer).backpacks$getOpenedBackpack();
//            BPModInit.logger.info("openedBackpack:" + openedBackpack);
            Network.sendToServer(new C2SBackpackGui(openedBackpack));
            ((IEntityClientPlayerMP) this.thePlayer).backpacks$setOpenBackpack(!openedBackpack);
        }
    }
}
