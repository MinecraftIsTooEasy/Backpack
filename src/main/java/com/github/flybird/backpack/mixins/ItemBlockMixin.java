package com.github.flybird.backpack.mixins;

import com.github.flybird.backpack.BPRegistryInit;
import com.github.flybird.backpack.network.C2SBackpackGui;
import moddedmite.rustedironcore.network.Network;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemBlock;
import net.minecraft.ItemStack;
import net.minecraft.RaycastCollision;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ItemBlock.class})
public class ItemBlockMixin {
    @Inject(method = {"onItemRightClick"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/EntityPlayer;getSelectedObject(FZ)Lnet/minecraft/RaycastCollision;")}, cancellable = true)
    private void onItemRightClickMixin(EntityPlayer player, float partial_tick, boolean ctrl_is_down, CallbackInfoReturnable<Boolean> cir) {
        ItemStack item_stack = player.getHeldItemStack();
        if (item_stack.itemID == BPRegistryInit.backpack.blockID) {
            RaycastCollision rc = player.getSelectedObject(partial_tick, false);
            if (rc == null) {
                ItemStack var5 = player.getCurrentArmor(2);
                if (var5 == null) {
                    if (player.onServer()) {
                        player.setCurrentItemOrArmor(2, item_stack.copy());
                        player.convertOneOfHeldItem((ItemStack) null);
                        player.suppressNextStatIncrement();
                    }
                    cir.setReturnValue(true);
                    return;
                }
                if (player.worldObj.isRemote) {
                    Network.sendToServer(new C2SBackpackGui(true));
                }
            }
        }
    }

    @Inject(method = "getHeatLevel", at = @At("HEAD"), cancellable = true)
    private void onGetHeatLevel(ItemStack item_stack, CallbackInfoReturnable<Integer> cir) {
        if (item_stack != null && item_stack.getItem() instanceof ItemBlock) {
            if (item_stack.getItem().itemID == BPRegistryInit.backpack.blockID || ((ItemBlock)item_stack.getItem()).getBlock() == BPRegistryInit.backpack) {
                cir.setReturnValue(0);
            }
        }
    }
}