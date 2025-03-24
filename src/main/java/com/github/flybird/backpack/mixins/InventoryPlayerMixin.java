package com.github.flybird.backpack.mixins;

import com.github.flybird.backpack.block.BlockBackpack;
import net.minecraft.DamageSource;
import net.minecraft.EntityItem;
import net.minecraft.EntityPlayer;
import net.minecraft.InventoryPlayer;
import net.minecraft.ItemStack;
import net.minecraft.NBTTagCompound;
import net.minecraft.NBTTagList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryPlayer.class)
public class InventoryPlayerMixin {

    @Shadow public EntityPlayer player;

    @Inject(
            method = "takeDamage(Lnet/minecraft/ItemStack;Lnet/minecraft/DamageSource;F)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/InventoryPlayer;setInventorySlotContents(ILnet/minecraft/ItemStack;)V",
                    ordinal = 1
            )
    )
    private void beforeSetInventorySlotContents(ItemStack item_stack, DamageSource damage_source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (item_stack.isBlock() && (item_stack.getItem().getAsItemBlock().getBlock() instanceof BlockBackpack)) {
            NBTTagCompound nbt = item_stack.stackTagCompound;
            NBTTagList nbtTagList = (NBTTagList) nbt.getTag("Items");
            for (int i = 0; i < nbtTagList.tagCount(); i++) {
                NBTTagCompound itemStackTag = (NBTTagCompound) nbtTagList.tagAt(i);
                ItemStack itemStack = ItemStack.loadItemStackFromNBT(itemStackTag);
                if (itemStack != null) {
                    float var10 = (this.player.worldObj.rand.nextFloat() * 0.8f) + 0.1f;
                    float var11 = (this.player.worldObj.rand.nextFloat() * 0.8f) + 0.1f;
                    float var12 = (this.player.worldObj.rand.nextFloat() * 0.8f) + 0.1f;
                    while (itemStack.stackSize > 0) {
                        int var13 = this.player.worldObj.rand.nextInt(21) + 10;
                        if (var13 > itemStack.stackSize) {
                            var13 = itemStack.stackSize;
                        }
                        itemStack.stackSize -= var13;
                        EntityItem entityItem = new EntityItem(this.player.worldObj, this.player.posX + var10, this.player.posY + var11, this.player.posZ + var12, new ItemStack(itemStack.itemID, var13, itemStack.getItemSubtype()));
                        if (itemStack.isItemDamaged()) {
                            entityItem.getEntityItem().setItemDamage(itemStack.getItemDamage());
                        }
                        entityItem.motionX = ((float) this.player.worldObj.rand.nextGaussian()) * 0.05f;
                        entityItem.motionY = (((float) this.player.worldObj.rand.nextGaussian()) * 0.05f) + 0.2f;
                        entityItem.motionZ = ((float) this.player.worldObj.rand.nextGaussian()) * 0.05f;
                        if (itemStack.getItem().hasQuality()) {
                            entityItem.getEntityItem().setQuality(itemStack.getQuality());
                        }
                        if (itemStack.hasTagCompound()) {
                            entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                        }
                        this.player.worldObj.spawnEntityInWorld(entityItem);
                    }
                }
            }
        }
    }
}
