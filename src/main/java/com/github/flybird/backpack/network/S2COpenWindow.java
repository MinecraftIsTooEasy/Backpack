package com.github.flybird.backpack.network;

import com.github.flybird.backpack.inventory.GuiBackpack;
import moddedmite.rustedironcore.network.Packet;
import moddedmite.rustedironcore.network.PacketByteBuf;
import net.minecraft.EntityPlayer;
import net.minecraft.InventoryBasic;
import net.minecraft.Minecraft;
import net.minecraft.ResourceLocation;

public class S2COpenWindow implements Packet {
    private final int windowId;
    private final String windowTitle;
    private final int slotsCount;
    private final boolean useProvidedWindowTitle;

    public S2COpenWindow(PacketByteBuf packetByteBuf) {
        this(packetByteBuf.readInt(), packetByteBuf.readString(), packetByteBuf.readInt(), packetByteBuf.readBoolean());
    }

    public S2COpenWindow(int windowId, String windowTitle, int slotsCount, boolean useProvidedWindowTitle) {
        windowTitle = windowTitle == null ? "" : windowTitle;
        this.windowId = windowId;
        this.windowTitle = windowTitle;
        this.slotsCount = slotsCount;
        this.useProvidedWindowTitle = useProvidedWindowTitle;
    }

    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeInt(this.windowId);
        packetByteBuf.writeString(this.windowTitle);
        packetByteBuf.writeInt(this.slotsCount);
        packetByteBuf.writeBoolean(this.useProvidedWindowTitle);
    }

    public void apply(EntityPlayer player) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiBackpack(player, new InventoryBasic(this.windowTitle, this.useProvidedWindowTitle, this.slotsCount)));
        player.openContainer.windowId = this.windowId;
    }

    public ResourceLocation getChannel() {
        return BPPackets.OpenWindow;
    }
}
