//package com.github.flybird.backpack.mixins;
//
//import com.github.flybird.backpack.BPModInit;
//import com.github.flybird.backpack.BPRegistryInit;
//import com.github.flybird.backpack.compat.BaubleImpl;
//import net.minecraft.Container;
//import net.minecraft.ContainerPlayer;
//import net.minecraft.IInventory;
//import net.minecraft.ItemStack;
//import net.minecraft.Slot;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(Slot.class)
//public class SlotMixin {
//    @Shadow public Container getContainer() {
//        return null;
//    }
//
//    @Inject(method = "isItemValid", at = @At("HEAD"), cancellable = true)
//    public void isItemValid(ItemStack par1ItemStack, CallbackInfoReturnable<Boolean> cir) {
//        if (par1ItemStack == null) {
//            cir.setReturnValue(true);
//            return;
//        }
//        if (getContainer() instanceof ContainerPlayer) {
//            boolean flag = false;
//            ItemStack itemStack = null;
//            ContainerPlayer container = (ContainerPlayer) this.getContainer();
//            if (BPModInit.HAS_BAUBLES) {
//                IInventory baublesInventory = BaubleImpl.getBaublesInventory(container.player);
//                itemStack = baublesInventory.getStackInSlot(3);
//                flag = itemStack != null;
//            }
//            if (itemStack == null) {
//                flag = container.player.inventory.hasItemStack(new ItemStack(BPRegistryInit.backpack));
//            }
//            cir.setReturnValue(Boolean.valueOf(!flag));
//        }
//    }
//}
