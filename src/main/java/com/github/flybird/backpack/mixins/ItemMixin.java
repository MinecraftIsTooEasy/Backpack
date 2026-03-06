package com.github.flybird.backpack.mixins;

import com.github.flybird.backpack.BPRegistryInit;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Inject(method = "addInformationBeforeEnchantments", at = @At("HEAD"))
    private void addInformationBeforeEnchantments(ItemStack item_stack, EntityPlayer player, List info, boolean extended_info, Slot slot, CallbackInfo ci)
    {
        if (item_stack.getItem() == Item.getItem(BPRegistryInit.backpack))
        {
            info.add(EnumChatFormatting.YELLOW + I18n.getString("item.backpack.single_hint"));
        }
    }
}