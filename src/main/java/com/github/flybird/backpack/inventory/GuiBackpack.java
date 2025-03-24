package com.github.flybird.backpack.inventory;

import com.github.flybird.backpack.BPEvents;
import net.minecraft.EntityPlayer;
import net.minecraft.GuiContainer;
import net.minecraft.I18n;
import net.minecraft.IInventory;
import net.minecraft.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiBackpack extends GuiContainer {
    private static final ResourceLocation background = new ResourceLocation("textures/gui/container/generic_54.png");
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;
    private int inventoryRows;

    public GuiBackpack(EntityPlayer player, IInventory par2IInventory) {
        super(new ContainerBackpack(player, par2IInventory));
        this.upperChestInventory = player.inventory;
        this.lowerChestInventory = par2IInventory;
        this.allowUserInput = false;
        int var4 = 222 - 108;
        this.inventoryRows = par2IInventory.getSizeInventory() / 9;
        this.ySize = var4 + (this.inventoryRows * 18);
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRenderer.drawString(this.lowerChestInventory.hasCustomName() ? this.lowerChestInventory.getCustomNameOrUnlocalized() : I18n.getString(this.lowerChestInventory.getCustomNameOrUnlocalized()), 8, 6, 4210752);
        this.fontRenderer.drawString(this.upperChestInventory.hasCustomName() ? this.upperChestInventory.getCustomNameOrUnlocalized() : I18n.getString(this.upperChestInventory.getCustomNameOrUnlocalized()), 8, (this.ySize - 96) + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(background);
        int var4 = (this.width - this.xSize) / 2;
        int var5 = (this.height - this.ySize) / 2;
        drawTexturedModalRect(var4, var5, 0, 0, this.xSize, (this.inventoryRows * 18) + 17);
        drawTexturedModalRect(var4, var5 + (this.inventoryRows * 18) + 17, 0, 126, this.xSize, 96);
    }

    protected void keyTyped(char par1, int par2) {
        if (par2 == BPEvents.keyBindOpenBackpack.keyCode) {
            this.mc.thePlayer.closeScreen();
        }
        super.keyTyped(par1, par2);
    }
}
