package com.github.flybird.backpack.client.render;

import com.github.flybird.backpack.BPModInit;
import com.github.flybird.backpack.client.model.ModelBackpackBlock;
import com.github.flybird.backpack.tileentity.TileEntityBackpack;
import net.minecraft.EnumDirection;
import net.minecraft.Minecraft;
import net.minecraft.ResourceLocation;
import net.minecraft.TextureManager;
import net.minecraft.TileEntity;
import net.minecraft.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class RenderTileEntityBackpack extends TileEntitySpecialRenderer {
    private final ModelBackpackBlock modelBackpackBlock = new ModelBackpackBlock();
    public static final ResourceLocation texturePath = new ResourceLocation(BPModInit.NAMESPACE, "textures/models/backpack.png");
    public TextureManager engine;

    public void renderBackpack(double x, double y, double z, float var8) {
        this.engine = Minecraft.getMinecraft().getTextureManager();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        this.engine.bindTexture(texturePath);
        this.modelBackpackBlock.renderBackpack(0.0625f);
        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float g) {
        TileEntityBackpack tileEntityBackPack = (TileEntityBackpack) tileEntity;
        GL11.glPushMatrix();
        GL11.glTranslatef(((float) x) + 0.5f, ((float) y) + 1.5f, ((float) z) + 0.5f);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        if (tileEntityBackPack.getWorldObj() != null) {
            EnumDirection direction = tileEntityBackPack.getBlockType().getDirectionFacing(tileEntity.getBlockMetadata());
            if (direction == EnumDirection.WEST) {
                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            }
            if (direction == EnumDirection.EAST) {
                GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
            }
            if (direction == EnumDirection.NORTH) {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            }
        } else {
            GL11.glTranslatef(((float) x) - 0.5f, (float) y, ((float) z) - 0.5f);
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(1.3125f, 1.3125f, 1.3125f);
        }
        TextureManager manager = Minecraft.getMinecraft().getTextureManager();
        manager.bindTexture(texturePath);
        this.modelBackpackBlock.renderBackpack(0.0625f);
        GL11.glPopMatrix();
    }
}
