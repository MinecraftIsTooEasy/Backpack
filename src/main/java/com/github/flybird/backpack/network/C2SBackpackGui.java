package com.github.flybird.backpack.network;

import baubles.api.BaublesApi;
import com.github.flybird.backpack.BPModInit;
import com.github.flybird.backpack.BPRegistryInit;
import com.github.flybird.backpack.api.IServerPlayer;
import com.github.flybird.backpack.inventory.InventoryBackpack;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.EntityPlayer;
import net.minecraft.IInventory;
import net.minecraft.ItemStack;
import net.minecraft.ResourceLocation;

public class C2SBackpackGui implements Packet {
    private boolean isOpenBackpacked;

    public C2SBackpackGui(PacketByteBuf packetByteBuf) {
        this(packetByteBuf.readBoolean());
    }

    public C2SBackpackGui(boolean isOpenBackpackGui) {
        this.isOpenBackpacked = isOpenBackpackGui;
    }

    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBoolean(this.isOpenBackpacked);
    }

    public void apply(EntityPlayer entityPlayer) {
        try {
            ItemStack itemStack = getHeldBackpack(entityPlayer);
            if (itemStack == null) {
                if (BPModInit.HAS_BAUBLES) {
                    IInventory baublesInventory = BaublesApi.getBaubles(entityPlayer);
                    itemStack = baublesInventory.getStackInSlot(3);
                }
                if (itemStack == null) {
                    itemStack = getArmorSlotBackpack(entityPlayer);
                }
            }
            if (itemStack == null || itemStack.itemID != BPRegistryInit.backpack.blockID) {
                BPModInit.logger.warning("No valid backpack found!");
                return;
            }
            InventoryBackpack virtualInventory = new InventoryBackpack(itemStack, entityPlayer);
            if (this.isOpenBackpacked) {
                if (entityPlayer instanceof IServerPlayer) {
                    ((IServerPlayer) entityPlayer).backpack$DisplayBackpackGui(virtualInventory);
                } else {
                    BPModInit.logger.warning("Invalid player!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ItemStack getHeldBackpack(EntityPlayer player) {
        ItemStack heldItem = player.inventory.getCurrentItemStack();
        if (heldItem == null || heldItem.itemID != BPRegistryInit.backpack.blockID) {
            return null;
        }
        return heldItem;
    }

    public static ItemStack getArmorSlotBackpack(EntityPlayer player) {
        ItemStack armorItem = player.inventory.armorItemInSlot(2);
        if (armorItem == null || armorItem.itemID != BPRegistryInit.backpack.blockID) {
            return null;
        }
        return armorItem;
    }

    public ResourceLocation getChannel() {
        return BPPackets.BackpackGui;
    }
}
