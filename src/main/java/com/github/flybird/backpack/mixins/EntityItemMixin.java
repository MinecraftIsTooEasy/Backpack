package com.github.flybird.backpack.mixins;

import com.github.flybird.backpack.BPModInit;
import com.github.flybird.backpack.BPRegistryInit;
import com.github.flybird.backpack.compat.BaubleImpl;
import net.minecraft.EntityItem;
import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import net.minecraft.IInventory;
import net.minecraft.ItemStack;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityItem.class)
public class EntityItemMixin {

    @Unique private final EntityItem instance = ReflectHelper.dyCast(this);

    @Inject(method = "canBePickedUpBy", at = @At("HEAD"), cancellable = true)
    public void canBePickedUpBy(EntityLivingBase entity_living_base, CallbackInfoReturnable<Boolean> cir) {
        if (this.instance.getEntityItem().getItem().itemID == BPRegistryInit.backpack.blockID && (entity_living_base instanceof EntityPlayer)) {
            EntityPlayer player = (EntityPlayer) entity_living_base;
            boolean flag = false;
            ItemStack itemStack = null;
            if (BPModInit.HAS_BAUBLES) {
                IInventory baublesInventory = BaubleImpl.getBaublesInventory(player);
                itemStack = baublesInventory.getStackInSlot(3);
                flag = itemStack != null;
            }
            if (itemStack == null) {
                flag = player.inventory.hasItemStack(new ItemStack(BPRegistryInit.backpack));
            }
            if (flag) {
                cir.setReturnValue(false);
            }
        }
    }
}
