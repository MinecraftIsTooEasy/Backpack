package com.github.flybird.backpack.inventory;

import com.github.flybird.backpack.BPModInit;
import com.github.flybird.backpack.network.S2CChangeKeyValue;
import java.util.Arrays;
import moddedmite.rustedironcore.network.Network;
import net.minecraft.*;

public class InventoryBackpack implements IInventory {
    private final ItemStack itemStack;
    private EntityPlayer player;
    private ItemStack[] chestContents = new ItemStack[36];

    public InventoryBackpack(ItemStack itemStack, EntityPlayer entityPlayer) {
        this.itemStack = itemStack;
        this.player = entityPlayer;
        if (itemStack.stackTagCompound != null) {
            deserializeNBT(this, itemStack.stackTagCompound);
        }
    }

    public NBTTagCompound serializeNBT(IInventory iInventory) {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < iInventory.getSizeInventory(); i++) {
            ItemStack inSlot = iInventory.getStackInSlot(i);
            if (inSlot != null) {
                NBTTagCompound itemStackTag = new NBTTagCompound();
                itemStackTag.setInteger("Slot", i);
                inSlot.writeToNBT(itemStackTag);
                nbtTagList.appendTag(itemStackTag);
            }
        }
        if (nbtTagList.tagCount() != 0) {
            nbt.setTag("Items", nbtTagList);
            nbt.setInteger("Size", iInventory.getSizeInventory());
        }
        return nbt;
    }

    public void deserializeNBT(IInventory iInventory, NBTTagCompound nbt) {
        NBTTagList nbtTagList;
        if (nbt.hasKey("Items") && (nbtTagList = (NBTTagList) nbt.getTag("Items")) != null) {
            for (int i = 0; i < nbtTagList.tagCount(); i++) {
                NBTTagCompound itemStackTag = (NBTTagCompound) nbtTagList.tagAt(i);
                int slot = itemStackTag.getInteger("Slot");
                if (slot >= 0 && slot < iInventory.getSizeInventory()) {
                    iInventory.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemStackTag));
                }
            }
        }
    }

    public int getSizeInventory() {
        return 27;
    }

    public ItemStack getStackInSlot(int par1) {
        return this.chestContents[par1];
    }

    public ItemStack decrStackSize(int par1, int par2) {
        if (this.chestContents[par1] != null) {
            if (this.chestContents[par1].stackSize <= par2) {
                ItemStack itemStack = this.chestContents[par1];
                this.chestContents[par1] = null;
                onInventoryChanged();
                return itemStack;
            }
            ItemStack var3 = this.chestContents[par1].splitStack(par2);
            if (this.chestContents[par1].stackSize == 0) {
                this.chestContents[par1] = null;
            }
            onInventoryChanged();
            return var3;
        }
        return null;
    }

    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.chestContents[par1] != null) {
            ItemStack var2 = this.chestContents[par1];
            this.chestContents[par1] = null;
            return var2;
        }
        return null;
    }

    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.chestContents[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit()) {
            par2ItemStack.stackSize = getInventoryStackLimit();
        }
        onInventoryChanged();
    }

    public String getCustomNameOrUnlocalized() {
        return I18n.getString("container.backpack");
    }

    public boolean hasCustomName() {
        return true;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public void onInventoryChanged() {
        if (this.itemStack != null) {
            this.itemStack.stackTagCompound = serializeNBT(this);
        }
    }

    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return true;
    }

    public void openChest() {
    }

    public void closeChest() {
        this.itemStack.stackTagCompound = serializeNBT(this);
        Network.sendToClient((ServerPlayer) this.player, new S2CChangeKeyValue());
    }

    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
//        BPModInit.logger.info("现在是第" + i + "槽位");
        return true;
    }

    public void destroyInventory() {
        ItemStack[] item_stacks = this.chestContents;
        Arrays.fill(item_stacks, null);
    }
}
