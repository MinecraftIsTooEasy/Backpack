package com.github.flybird.backpack.mixins;

import com.github.flybird.backpack.api.IPlayer;
import com.github.flybird.backpack.inventory.GuiBackpack;
import net.minecraft.AbstractClientPlayer;
import net.minecraft.ClientPlayer;
import net.minecraft.IInventory;
import net.minecraft.Minecraft;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayer.class)
public abstract class ClientPlayerMixin extends AbstractClientPlayer implements IPlayer {

    @Shadow protected Minecraft mc;

    public ClientPlayerMixin(World par1World, String par2Str) {
        super(par1World, par2Str);
    }

    @Override
    public void backpack$DisplayGuiBackpack(IInventory iInventory) {
        this.mc.displayGuiScreen(new GuiBackpack(this, iInventory));
    }
}
