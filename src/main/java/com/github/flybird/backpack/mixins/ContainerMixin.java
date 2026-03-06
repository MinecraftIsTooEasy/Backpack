package com.github.flybird.backpack.mixins;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaublesApi;
import baubles.common.container.SlotBauble;
import com.github.flybird.backpack.BPModInit;
import com.github.flybird.backpack.BPRegistryInit;
import net.minecraft.Container;
import net.minecraft.EntityPlayer;
import net.minecraft.IInventory;
import net.minecraft.InventoryPlayer;
import net.minecraft.ItemStack;
import net.minecraft.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Container.class)
public abstract class ContainerMixin {

    @Inject(method = "slotClick", at = @At("HEAD"), cancellable = true)
    private void blockBackpackShiftClick(int par1, int par2, int par3, boolean holding_shift, EntityPlayer player, CallbackInfoReturnable<ItemStack> cir)
    {
        if (par3 != 1) return;

        Container self = (Container) (Object) this;

        if (par1 < 0 || par1 >= self.inventorySlots.size()) return;

        Slot slot = (Slot) self.inventorySlots.get(par1);

        if (!slot.getHasStack()) return;

        ItemStack stack = slot.getStack();

        if (stack == null || stack.getItem().itemID != BPRegistryInit.backpack.blockID) return;

        if (BPModInit.HAS_BAUBLES && slot instanceof SlotBauble) return;

        if (slot.inventory instanceof InventoryPlayer) return;

        boolean hasInBaubleSlot = false;

        if (BPModInit.HAS_BAUBLES)
        {
            IInventory baubles = BaublesApi.getBaubles(player);
            ItemStack back = baubles != null ? baubles.getStackInSlot(BaubleSlotHelper.BACK_SLOT) : null;
            if (back != null && back.getItem().itemID == BPRegistryInit.backpack.blockID)
            {
                hasInBaubleSlot = true;
            }
        }

        boolean hasInInventory = player.inventory.hasItemStack(new ItemStack(BPRegistryInit.backpack));

        if (hasInInventory || hasInBaubleSlot)
        {
            cir.setReturnValue(null);
        }
    }
}