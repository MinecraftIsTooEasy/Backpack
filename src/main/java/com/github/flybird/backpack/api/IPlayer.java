package com.github.flybird.backpack.api;

import net.minecraft.IInventory;

public interface IPlayer {
    default void backpack$DisplayGuiBackpack(IInventory minePocketInventory) {
    }
}
