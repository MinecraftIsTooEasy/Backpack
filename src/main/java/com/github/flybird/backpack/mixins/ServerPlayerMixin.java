package com.github.flybird.backpack.mixins;

import com.github.flybird.backpack.api.IServerPlayer;
import com.github.flybird.backpack.inventory.ContainerBackpack;
import com.github.flybird.backpack.network.S2COpenWindow;
import moddedmite.rustedironcore.network.Network;
import net.minecraft.EntityPlayer;
import net.minecraft.ICrafting;
import net.minecraft.IInventory;
import net.minecraft.ServerPlayer;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends EntityPlayer implements IServerPlayer, ICrafting {

    @Shadow private int currentWindowId;
    @Shadow protected abstract void incrementWindowID();

    public ServerPlayerMixin(World par1World, String par2Str) {
        super(par1World, par2Str);
    }

    @Override
    public void backpack$DisplayBackpackGui(IInventory inventory) {
        incrementWindowID();
        Network.sendToClient(getAsEntityPlayerMP(), new S2COpenWindow(this.currentWindowId, inventory.getCustomNameOrUnlocalized(), inventory.getSizeInventory(), inventory.hasCustomName()));
        this.openContainer = new ContainerBackpack(this, inventory);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
}
