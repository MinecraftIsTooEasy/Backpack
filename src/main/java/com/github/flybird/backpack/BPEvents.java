package com.github.flybird.backpack;

import com.github.flybird.backpack.client.render.RenderTileEntityBackpack;
import com.github.flybird.backpack.tileentity.TileEntityBackpack;
import com.google.common.eventbus.Subscribe;
import moddedmite.rustedironcore.api.event.Handlers;
import moddedmite.rustedironcore.api.event.listener.IKeybindingListener;
import net.minecraft.KeyBinding;
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent;
import net.xiaoyu233.fml.reload.event.TileEntityRegisterEvent;
import net.xiaoyu233.fml.reload.event.TileEntityRendererRegisterEvent;

import java.util.function.Consumer;

public class BPEvents extends Handlers {

    public static KeyBinding keyBindOpenBackpack;

    @Subscribe
    public void onRecipeRegister(RecipeRegistryEvent event) {
        BPRegistryInit.registerRecipes(event);
    }

    @Subscribe
    public void onTileEntityRegister(TileEntityRegisterEvent event) {
        event.register(TileEntityBackpack.class, BPModInit.NAMESPACE);
    }

    @Subscribe
    public void onTileEntityRendererRegister(TileEntityRendererRegisterEvent event) {
        event.register(TileEntityBackpack.class, new RenderTileEntityBackpack());
    }

    public static void register() {
        Keybinding.register(new IKeybindingListener() {
            static {
                if (BPModInit.HAS_BAUBLES) {
                    keyBindOpenBackpack = new KeyBinding("key.openBackpack", 76);
                } else {
                    keyBindOpenBackpack = new KeyBinding("key.openBackpack", 48);
                }
            }
            public void onKeybindingRegister(Consumer<KeyBinding> registry) {
                registry.accept(keyBindOpenBackpack);
            }
        });
    }
}
