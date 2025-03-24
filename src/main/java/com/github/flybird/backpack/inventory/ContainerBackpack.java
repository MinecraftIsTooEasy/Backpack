package com.github.flybird.backpack.inventory;

import com.github.flybird.backpack.BPRegistryInit;
import net.minecraft.Container;
import net.minecraft.EntityPlayer;
import net.minecraft.IInventory;
import net.minecraft.ItemStack;
import net.minecraft.Slot;

public class ContainerBackpack extends Container {
    private IInventory lowerChestInventory;
    private int numRows;

    public ContainerBackpack(EntityPlayer player, IInventory par2IInventory) {
        super(player);
        this.lowerChestInventory = par2IInventory;
        this.numRows = par2IInventory.getSizeInventory() / 9;
        par2IInventory.openChest();
        int var3 = (this.numRows - 4) * 18;
        for (int var4 = 0; var4 < this.numRows; var4++) {
            for (int var5 = 0; var5 < 9; var5++) {
                addSlotToContainer(new Slot(par2IInventory, var5 + (var4 * 9), 8 + (var5 * 18), 18 + (var4 * 18)) {
                    public boolean isItemValid(ItemStack par1ItemStack) {
                        return par1ItemStack.getItem().itemID != BPRegistryInit.backpack.blockID;
                    }
                });
            }
        }
        for (int var42 = 0; var42 < 3; var42++) {
            for (int var52 = 0; var52 < 9; var52++) {
                addSlotToContainer(new Slot(player.inventory, var52 + (var42 * 9) + 9, 8 + (var52 * 18), 103 + (var42 * 18) + var3));
            }
        }
        for (int var43 = 0; var43 < 9; var43++) {
            addSlotToContainer(new Slot(player.inventory, var43, 8 + (var43 * 18), 161 + var3));
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.lowerChestInventory.isUseableByPlayer(par1EntityPlayer);
    }

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot) this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 < this.numRows * 9) {
                if (!mergeItemStack(var5, this.numRows * 9, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!mergeItemStack(var5, 0, this.numRows * 9, false)) {
                return null;
            }
            if (var5.stackSize == 0) {
                var4.putStack((ItemStack) null);
            } else {
                var4.onSlotChanged();
            }
        }
        return var3;
    }

    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        this.lowerChestInventory.closeChest();
    }

    public IInventory getLowerChestInventory() {
        return this.lowerChestInventory;
    }
}
