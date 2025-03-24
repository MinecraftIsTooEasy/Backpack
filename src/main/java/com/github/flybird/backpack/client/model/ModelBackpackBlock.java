package com.github.flybird.backpack.client.model;

import net.minecraft.Entity;
import net.minecraft.ModelBase;
import net.minecraft.ModelRenderer;

public class ModelBackpackBlock extends ModelBase {
    private final ModelRenderer backpack;
    private final ModelRenderer right_bottom_pouch;
    private final ModelRenderer right_top_pouch;
    private final ModelRenderer left_top_pouch;
    private final ModelRenderer left_bottom_pouch;
    private final ModelRenderer front_pouch;
    private final ModelRenderer base;
    private final ModelRenderer left_strap;
    private final ModelRenderer right_strap;
    private final ModelRenderer top_lip;
    private final ModelRenderer handle;
    private final ModelRenderer top_straps;
    private final ModelRenderer bone;
    private final ModelRenderer body;

    public ModelBackpackBlock() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.backpack = new ModelRenderer(this);
        this.backpack.setRotationPoint(0.0f, 16.0f, 0.0f);
        this.right_bottom_pouch = new ModelRenderer(this);
        this.right_bottom_pouch.setRotationPoint(7.0f, 0.0f, -0.5f);
        this.backpack.addChild(this.right_bottom_pouch);
        this.right_bottom_pouch.setTextureOffset(32, 3).addBox(-2.0f, 2.0f, -2.0f, 2, 4, 5, 0.0f);
        this.right_bottom_pouch.setTextureOffset(32, 3).addBox(-14.0f, 2.0f, -2.0f, 2, 4, 5, 0.0f);
        this.right_bottom_pouch.setTextureOffset(32, 12).addBox(-2.0f, 6.0f, -2.0f, 2, 2, 5, 0.0f);
        this.right_bottom_pouch.setTextureOffset(32, 12).addBox(-14.0f, 6.0f, -2.0f, 2, 2, 5, 0.0f);
        this.right_bottom_pouch.setTextureOffset(8, 41).addBox(-0.75f, 3.0f, 0.0f, 1, 2, 1, 0.0f);
        this.right_bottom_pouch.setTextureOffset(4, 41).addBox(-14.25f, 3.0f, 0.0f, 1, 2, 1, 0.0f);
        this.right_top_pouch = new ModelRenderer(this);
        this.right_top_pouch.setRotationPoint(7.0f, 0.0f, -0.5f);
        this.backpack.addChild(this.right_top_pouch);
        this.right_top_pouch.setTextureOffset(4, 41).addBox(-1.75f, -2.0f, 0.0f, 1, 2, 1, 0.0f);
        this.right_top_pouch.setTextureOffset(4, 41).addBox(-13.25f, -2.0f, 0.0f, 1, 2, 1, 0.0f);
        this.right_top_pouch.setTextureOffset(34, 28).addBox(-2.0f, -3.0f, -2.0f, 1, 4, 5, 0.0f);
        this.right_top_pouch.setTextureOffset(34, 28).addBox(-13.0f, -3.0f, -2.0f, 1, 4, 5, 0.0f);
        this.left_top_pouch = new ModelRenderer(this);
        this.left_top_pouch.setRotationPoint(-7.0f, 0.0f, -0.5f);
        this.backpack.addChild(this.left_top_pouch);
        this.left_bottom_pouch = new ModelRenderer(this);
        this.left_bottom_pouch.setRotationPoint(8.0f, 8.0f, -8.0f);
        this.backpack.addChild(this.left_bottom_pouch);
        this.front_pouch = new ModelRenderer(this);
        this.front_pouch.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.backpack.addChild(this.front_pouch);
        this.front_pouch.setTextureOffset(30, 19).addBox(-4.0f, 2.0f, -5.0f, 8, 4, 2, 0.0f);
        this.front_pouch.setTextureOffset(30, 25).addBox(-4.0f, 6.0f, -5.0f, 8, 1, 2, 0.0f);
        this.front_pouch.setTextureOffset(32, 0).addBox(-4.0f, 7.0f, -5.0f, 8, 1, 2, 0.0f);
        this.front_pouch.setTextureOffset(0, 41).addBox(2.0f, 3.0f, -5.25f, 1, 2, 1, 0.0f);
        this.front_pouch.setTextureOffset(0, 41).addBox(-3.0f, 3.0f, -5.25f, 1, 2, 1, 0.0f);
        this.base = new ModelRenderer(this);
        this.base.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.backpack.addChild(this.base);
        this.left_strap = new ModelRenderer(this);
        this.left_strap.setRotationPoint(-2.75f, 8.0f, -7.0f);
        this.base.addChild(this.left_strap);
        this.left_strap.setTextureOffset(34, 37).addBox(-1.5f, -13.0f, 10.0f, 2, 13, 1, 0.0f);
        this.right_strap = new ModelRenderer(this);
        this.right_strap.setRotationPoint(3.75f, 8.0f, -7.0f);
        this.base.addChild(this.right_strap);
        this.right_strap.setTextureOffset(34, 37).addBox(-1.5f, -13.0f, 10.0f, 2, 13, 1, 0.0f);
        this.top_lip = new ModelRenderer(this);
        this.top_lip.setRotationPoint(-4.0f, 1.0f, -4.0f);
        this.base.addChild(this.top_lip);
        this.top_lip.setTextureOffset(40, 37).addBox(0.25f, -2.25f, 0.75f, 7, 1, 1, 0.0f);
        this.top_lip.setTextureOffset(0, 19).addBox(-0.5f, -6.25f, 0.75f, 9, 4, 6, 0.0f);
        this.handle = new ModelRenderer(this);
        this.handle.setRotationPoint(0.75f, 0.25f, -6.0f);
        this.base.addChild(this.handle);
        this.handle.setTextureOffset(40, 39).addBox(-2.75f, -7.0f, 6.0f, 4, 2, 1, 0.0f);
        this.top_straps = new ModelRenderer(this);
        this.top_straps.setRotationPoint(2.5f, 2.0f, -4.0f);
        this.base.addChild(this.top_straps);
        this.top_straps.setTextureOffset(12, 41).addBox(-6.0f, -3.0f, 0.5f, 1, 2, 1, 0.0f);
        this.top_straps.setTextureOffset(12, 41).addBox(0.0f, -3.0f, 0.5f, 1, 2, 1, 0.0f);
        this.bone = new ModelRenderer(this);
        this.bone.setRotationPoint(-11.0f, -4.0f, 1.0f);
        this.top_straps.addChild(this.bone);
        this.bone.setTextureOffset(18, 29).addBox(5.0f, -3.5f, -0.5f, 1, 5, 7, 0.0f);
        this.bone.setTextureOffset(18, 29).addBox(11.0f, -3.5f, -0.5f, 1, 5, 7, 0.0f);
        this.body = new ModelRenderer(this);
        this.body.setRotationPoint(4.0f, 8.0f, 3.0f);
        this.base.addChild(this.body);
        this.body.setTextureOffset(0, 0).addBox(-9.0f, -13.0f, -6.0f, 10, 13, 6, 0.0f);
        this.body.setTextureOffset(0, 29).addBox(-8.0f, -12.0f, -0.5f, 8, 11, 1, 0.0f);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    }

    public void setRotation(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void renderBackpack(float rotation) {
        this.backpack.render(rotation);
    }
}
