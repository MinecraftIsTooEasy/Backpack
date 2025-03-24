package com.github.flybird.backpack.block;

import com.github.flybird.backpack.BPRegistryInit;
import huix.glacier.api.extension.creativetab.GlacierCreativeTabs;

public class CreativeTabBackpack extends GlacierCreativeTabs {
    public CreativeTabBackpack() {
        super("backpack");
    }

    @Override
    public int getTabIconItemIndex() {
        return BPRegistryInit.backpack.blockID;
    }
}
