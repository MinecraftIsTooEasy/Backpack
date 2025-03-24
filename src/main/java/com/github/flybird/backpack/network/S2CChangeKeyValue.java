package com.github.flybird.backpack.network;

import com.github.flybird.backpack.api.IEntityClientPlayerMP;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.EntityClientPlayerMP;
import net.minecraft.EntityPlayer;
import net.minecraft.ResourceLocation;

public class S2CChangeKeyValue implements Packet {
    public void write(PacketByteBuf packetByteBuf) {
    }

    public void apply(EntityPlayer player) {
        EntityClientPlayerMP clientPlayer = player.getAsEntityClientPlayerMP();
        boolean openedBackpack = ((IEntityClientPlayerMP) clientPlayer).backpacks$getOpenedBackpack();
        ((IEntityClientPlayerMP) clientPlayer).backpacks$setOpenBackpack(!openedBackpack);
    }

    public ResourceLocation getChannel() {
        return BPPackets.changeKeyValue;
    }
}
