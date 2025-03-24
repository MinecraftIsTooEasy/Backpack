package com.github.flybird.backpack.mixins;

import com.github.flybird.backpack.api.IEntityClientPlayerMP;
import net.minecraft.EntityClientPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityClientPlayerMP.class)
public class EntityClientPlayerMPMixin implements IEntityClientPlayerMP {

    @Unique private boolean openedBackpack = true;

    @Override
    public boolean backpacks$getOpenedBackpack() {
        return this.openedBackpack;
    }

    @Override
    public void backpacks$setOpenBackpack(boolean b) {
        this.openedBackpack = b;
    }
}
