package com.github.flybird.backpack.client.render;

import baubles.api.BaublesApi;
import com.github.flybird.backpack.BPModInit;
import com.github.flybird.backpack.BPRegistryInit;
import com.github.flybird.backpack.client.model.ModelBackpackBlock;
import net.minecraft.AbstractClientPlayer;
import net.minecraft.IInventory;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.Minecraft;
import net.minecraft.ModelBiped;
import net.minecraft.TextureManager;
import org.lwjgl.opengl.GL11;

public class RenderBackpackWithPlayer {
    private final ModelBackpackBlock modelBackpackBlock = new ModelBackpackBlock();
    public static RenderBackpackWithPlayer instance = new RenderBackpackWithPlayer();

    public boolean renderModel(AbstractClientPlayer par1AbstractClientPlayer, ModelBiped modelBipedMain) {
        ItemStack itemStack;
        if (BPModInit.HAS_BAUBLES) {
            IInventory inventory = BaublesApi.getBaubles(par1AbstractClientPlayer);
            itemStack = inventory.getStackInSlot(3);
            if (itemStack == null) {
                itemStack = par1AbstractClientPlayer.inventory.armorItemInSlot(2);
            }
        } else {
            itemStack = par1AbstractClientPlayer.inventory.armorItemInSlot(2);
        }
        if (itemStack != null) {
            Item var5 = itemStack.getItem();
            Item backpackItem = new ItemStack(BPRegistryInit.backpack).getItem();
            if (var5.itemID == backpackItem.itemID) {
                GL11.glColor3f(1.0f, 1.0f, 1.0f);
                GL11.glPushMatrix();
                modelBipedMain.bipedBody.postRender(0.0625f);
                GL11.glTranslatef(0.0f, -0.65f, 0.35f);
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                TextureManager manager = Minecraft.getMinecraft().getTextureManager();
                manager.bindTexture(RenderTileEntityBackpack.texturePath);
                this.modelBackpackBlock.renderBackpack(0.0625f);
                GL11.glPopMatrix();
                return true;
            }
            return true;
        }
        return true;
    }
}
