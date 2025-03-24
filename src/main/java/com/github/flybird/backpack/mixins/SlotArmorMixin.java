package com.github.flybird.backpack.mixins;

import com.github.flybird.backpack.BPModInit;
import com.github.flybird.backpack.BPRegistryInit;
import net.minecraft.ContainerPlayer;
import net.minecraft.IInventory;
import net.minecraft.ItemStack;
import net.minecraft.Slot;
import net.minecraft.SlotArmor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlotArmor.class)
public class SlotArmorMixin extends Slot {

    @Mutable @Final @Shadow final int armorType;

    SlotArmorMixin(ContainerPlayer par1ContainerPlayer, IInventory par2IInventory, int par3, int par4, int par5, int par6) {
        super(par2IInventory, par3, par4, par5);
        this.armorType = par6;
    }

    @Inject(method = "isItemValid", at = @At(value = "INVOKE", target = "Lnet/minecraft/ItemStack;getItem()Lnet/minecraft/Item;", ordinal = 0), cancellable = true)
    private void isBackpackOnCheatPlateSlot(ItemStack par1ItemStack, CallbackInfoReturnable<Boolean> cir) {
        if (par1ItemStack.getItem().itemID == BPRegistryInit.backpack.blockID && this.armorType == 1 && !BPModInit.HAS_BAUBLES) {
            cir.setReturnValue(true);
        }
    }
}
