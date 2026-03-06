package com.github.flybird.backpack;

import com.github.flybird.backpack.network.BPPackets;
import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.FishModLoader;
import net.xiaoyu233.fml.ModResourceManager;
import net.xiaoyu233.fml.reload.event.MITEEvents;

import java.util.logging.Logger;

public class BPModInit implements ModInitializer {
    public static boolean HAS_BAUBLES;
    public static String NAMESPACE = "backpack";
    public static final Logger logger = Logger.getLogger("BackpackMod");

    public void onInitialize() {
        ModResourceManager.addResourcePackDomain(NAMESPACE);
        MITEEvents.MITE_EVENT_BUS.register(new BPEvents());
        BPEvents.register();
        BPPackets.init();
        HAS_BAUBLES = FishModLoader.hasMod("baubles");
    }
}
