package com.github.flybird.backpack.mixins;

import baubles.api.BaubleSlotHelper;
import baubles.api.BaublesApi;
import com.github.flybird.backpack.BPModInit;
import com.github.flybird.backpack.BPRegistryInit;
import net.minecraft.*;
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
    public void canBePickedUpBy(EntityLivingBase entity_living_base, CallbackInfoReturnable<Boolean> cir)
    {
        if (this.instance.getEntityItem().getItem().itemID == BPRegistryInit.backpack.blockID
                && (entity_living_base instanceof EntityPlayer player))
        {
            boolean flag = false;

            if (BPModInit.HAS_BAUBLES)
            {
                IInventory baubles = BaublesApi.getBaubles(player);
                ItemStack back = baubles != null ? baubles.getStackInSlot(BaubleSlotHelper.BACK_SLOT) : null;
                if (back != null && back.getItem().itemID == BPRegistryInit.backpack.blockID)
                {
                    flag = true;
                }
            }

            if (!flag)
            {
                flag = player.inventory.hasItemStack(new ItemStack(BPRegistryInit.backpack));
            }

            if (flag)
            {
                cir.setReturnValue(false);
            }
        }
    }
}