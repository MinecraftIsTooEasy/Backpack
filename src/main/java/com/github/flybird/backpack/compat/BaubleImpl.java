package com.github.flybird.backpack.compat;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBaublePlugin;
import com.github.flybird.backpack.BPRegistryInit;
import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import net.minecraft.IInventory;
import net.minecraft.ItemStack;

public class BaubleImpl implements IBaublePlugin {
    public boolean canPutBaubleSlot(ItemStack itemStack, BaubleType baubleType) {
        return itemStack.getItem().itemID == BPRegistryInit.backpack.blockID && baubleType == BaubleType.BELT;
    }

    public void onWornTick(ItemStack itemStack, EntityLivingBase entityLivingBase) {
    }

    public void onEquipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {
    }

    public void onUnequipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {
//        if (!(entityLivingBase instanceof EntityPlayer) || ((EntityPlayer) entityLivingBase).inventory.hasItemStack(new ItemStack(BPRegistryInit.backpack))) {
//        }
    }

    public static IInventory getBaublesInventory(EntityPlayer player) {
        return BaublesApi.getBaubles(player);
    }
}
