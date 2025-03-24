package com.github.flybird.backpack.network;

import com.github.flybird.backpack.BPModInit;
import moddedmite.rustedironcore.network.PacketReader;
import net.minecraft.ResourceLocation;
import net.xiaoyu233.fml.FishModLoader;

public class BPPackets {
    public static final ResourceLocation BackpackGui = new ResourceLocation(BPModInit.NAMESPACE, "BackpackGui");
    public static final ResourceLocation OpenWindow = new ResourceLocation(BPModInit.NAMESPACE, "OpenWindow");
    public static final ResourceLocation changeKeyValue = new ResourceLocation(BPModInit.NAMESPACE, "KeyValue");

    public static void init() {
        if (!FishModLoader.isServer()) {
            initClient();
        }
        initServer();
    }

    private static void initClient() {
        PacketReader.registerClientPacketReader(OpenWindow, S2COpenWindow::new);
        PacketReader.registerClientPacketReader(changeKeyValue, packetByteBuf -> {
            return new S2CChangeKeyValue();
        });
    }

    private static void initServer() {
        PacketReader.registerServerPacketReader(BackpackGui, C2SBackpackGui::new);
    }
}
