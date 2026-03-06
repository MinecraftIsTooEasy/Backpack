package com.github.flybird.backpack.mixins;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaublesApi;
import com.github.flybird.backpack.BPModInit;
import com.github.flybird.backpack.BPRegistryInit;
import net.minecraft.IInventory;
import net.minecraft.InventoryPlayer;
import net.minecraft.ItemStack;
import net.minecraft.Slot;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public class SlotMixin {

    @Unique private final Slot instance = ReflectHelper.dyCast(this);

    @Inject(method = "isItemValid", at = @At("HEAD"), cancellable = true)
    public void isItemValid(ItemStack par1ItemStack, CallbackInfoReturnable<Boolean> cir)
    {
        if (par1ItemStack == null)
        {
            cir.setReturnValue(true);
            return;
        }

        if (par1ItemStack.getItem().itemID != BPRegistryInit.backpack.blockID)
        {
            return;
        }

        if (!(instance.inventory instanceof InventoryPlayer inv))
        {
            return;
        }

        boolean flag = false;

        if (BPModInit.HAS_BAUBLES)
        {
            IInventory baubles = BaublesApi.getBaubles(inv.player);
            ItemStack back = baubles != null ? baubles.getStackInSlot(BaubleSlotHelper.BACK_SLOT) : null;
            if (back != null && back.getItem().itemID == BPRegistryInit.backpack.blockID)
            {
                flag = true;
            }
        }
        if (!flag)
        {
            flag = inv.player.inventory.hasItemStack(new ItemStack(BPRegistryInit.backpack));
        }
        cir.setReturnValue(!flag);
    }
}