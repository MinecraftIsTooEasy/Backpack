package com.github.flybird.backpack.tileentity;

import com.github.flybird.backpack.BPRegistryInit;
import com.github.flybird.backpack.block.BlockBackpack;
import com.github.flybird.backpack.inventory.ContainerBackpack;
import java.util.Arrays;
import java.util.List;

import net.minecraft.*;

public class TileEntityBackpack extends TileEntity implements IInventory {
    private ItemStack[] chestContents;
    public int numUsingPlayers;
    private int ticksSinceSync;
    public float lidAngle;

    public TileEntityBackpack() {
        this.chestContents = new ItemStack[36];
    }

    public TileEntityBackpack(BlockBackpack blockBackpack) {
        this.chestContents = new ItemStack[36];
        setBlock(blockBackpack);
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

    public String getUnlocalizedInvName() {
        return "container.backpack";
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.chestContents = new ItemStack[getSizeInventory()];
        for (int var3 = 0; var3 < var2.tagCount(); var3++) {
            NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
            int var5 = var4.getByte("Slot") & 255;
            if (var5 >= 0 && var5 < this.chestContents.length) {
                this.chestContents[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.chestContents.length; var3++) {
            if (this.chestContents[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.chestContents[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        par1NBTTagCompound.setTag("Items", var2);
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && par1EntityPlayer.getDistanceSq(((double) this.xCoord) + 0.5d, ((double) this.yCoord) + 0.5d, ((double) this.zCoord) + 0.5d) <= 64.0d;
    }

    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
    }

    public void updateEntity() {
        InventoryLargeChest lowerChestInventory;
        super.updateEntity();
        this.ticksSinceSync++;
        if (!this.worldObj.isRemote && this.numUsingPlayers != 0 && (((this.ticksSinceSync + this.xCoord) + this.yCoord) + this.zCoord) % 200 == 0) {
            this.numUsingPlayers = 0;
            List var2 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB(this.xCoord - 5.0f, this.yCoord - 5.0f, this.zCoord - 5.0f, this.xCoord + 1 + 5.0f, this.yCoord + 1 + 5.0f, this.zCoord + 1 + 5.0f));
            for (Object o : var2) {
                EntityPlayer entityPlayer = (EntityPlayer) o;
                IInventory inventory;
                if (!(entityPlayer.openContainer instanceof ContainerBackpack) || (inventory = ((ContainerBackpack)entityPlayer.openContainer).getLowerChestInventory()) != this && (!(inventory instanceof InventoryLargeChest) || !((InventoryLargeChest) (inventory)).isPartOfLargeChest(this))) continue;
                ++this.numUsingPlayers;
            }
        }
        if (this.numUsingPlayers > 0 && this.lidAngle == 0.0f) {
            double d = this.xCoord + 0.5d;
            double d2 = this.zCoord + 0.5d;
        }
        if ((this.numUsingPlayers == 0 && this.lidAngle > 0.0f) || (this.numUsingPlayers > 0 && this.lidAngle < 1.0f)) {
            float var9 = this.lidAngle;
            if (this.numUsingPlayers > 0) {
                this.lidAngle += 0.1f;
            } else {
                this.lidAngle -= 0.1f;
            }
            if (this.lidAngle > 1.0f) {
                this.lidAngle = 1.0f;
            }
            if (this.lidAngle < 0.5f && var9 >= 0.5f) {
                double d3 = this.xCoord + 0.5d;
                double d4 = this.zCoord + 0.5d;
            }
            if (this.lidAngle < 0.0f) {
                this.lidAngle = 0.0f;
            }
        }
    }

    public boolean receiveClientEvent(int par1, int par2) {
        if (par1 == 1) {
            this.numUsingPlayers = par2;
            return true;
        }
        return super.receiveClientEvent(par1, par2);
    }

    public void openChest() {
        this.worldObj.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, BPRegistryInit.backpack.blockID);
        if (this.numUsingPlayers < 0) {
            this.numUsingPlayers = 0;
        }
        this.numUsingPlayers++;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, getBlockType().blockID, 1, this.numUsingPlayers);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, getBlockType().blockID);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, getBlockType().blockID);
    }

    public void closeChest() {
        this.worldObj.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, BPRegistryInit.backpack.blockID);
        if (getBlockType() != null && (getBlockType() instanceof BlockBackpack)) {
            this.numUsingPlayers--;
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, getBlockType().blockID, 1, this.numUsingPlayers);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, getBlockType().blockID);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, getBlockType().blockID);
        }
    }

    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return itemStack.getItem().itemID != BPRegistryInit.backpack.blockID;
    }

    public void destroyInventory() {
        ItemStack[] item_stacks = this.chestContents;
        Arrays.fill(item_stacks, null);
    }

    public void invalidate() {
        super.invalidate();
        updateContainingBlockInfo();
    }
}
