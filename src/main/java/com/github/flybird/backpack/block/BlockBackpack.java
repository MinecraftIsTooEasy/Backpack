package com.github.flybird.backpack.block;

import com.github.flybird.backpack.api.IServerPlayer;
import com.github.flybird.backpack.tileentity.TileEntityBackpack;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.AxisAlignedBB;
import net.minecraft.Block;
import net.minecraft.BlockBreakInfo;
import net.minecraft.BlockConstants;
import net.minecraft.BlockDirectionalWithTileEntity;
import net.minecraft.CreativeTabs;
import net.minecraft.Curse;
import net.minecraft.Entity;
import net.minecraft.EntityItem;
import net.minecraft.EntityLivingBase;
import net.minecraft.EntityOcelot;
import net.minecraft.EntityPlayer;
import net.minecraft.EnumDirection;
import net.minecraft.EnumFace;
import net.minecraft.IBlockAccess;
import net.minecraft.IInventory;
import net.minecraft.Icon;
import net.minecraft.IconRegister;
import net.minecraft.ItemStack;
import net.minecraft.Material;
import net.minecraft.Minecraft;
import net.minecraft.NBTTagCompound;
import net.minecraft.NBTTagList;
import net.minecraft.TileEntity;
import net.minecraft.Vec3;
import net.minecraft.World;
import net.xiaoyu233.fml.reload.utils.IdUtil;

public class BlockBackpack extends BlockDirectionalWithTileEntity {
    private NBTTagCompound stackTagCompound;
    public static final int backpackRenderType = IdUtil.getNextRenderType();
    private Icon icon;

    public void registerIcons(IconRegister par1IconRegister) {
        this.icon = par1IconRegister.registerIcon("texture");
    }

    public BlockBackpack(int blockID) {
        super(blockID, Material.wood, new BlockConstants());
        setMaxStackSize(1);
        setStepSound(soundClothFootstep);
        setTickRandomly(true);
    }

    public void setBlockBoundsBasedOnStateAndNeighbors(IBlockAccess par1IBlockAccess, int x, int y, int z) {
        EnumDirection direction = getDirectionFacing(par1IBlockAccess.getBlockMetadata(x, y, z));
        if (direction == EnumDirection.WEST || direction == EnumDirection.EAST) {
            setBlockBounds(0.1875d, 0.0d, 0.0625d, 0.8125d, 0.8125d, 0.9375d, false);
        }
        if (direction == EnumDirection.SOUTH || direction == EnumDirection.NORTH) {
            setBlockBounds(0.0625d, 0.0d, 0.1875d, 0.9375d, 0.8125d, 0.8125d, false);
        }
    }

    public int getRenderType() {
        return backpackRenderType;
    }

    public boolean isValidMetadata(int metadata) {
        return metadata > 1 && metadata < 6;
    }

    public final EnumDirection getDirectionFacing(int metadata) {
        return getDirectionFacingStandard6(metadata, false);
    }

    public int getMetadataForDirectionFacing(int metadata, EnumDirection direction) {
        int metadata2 = direction == EnumDirection.NORTH ? 2 : direction == EnumDirection.SOUTH ? 3 : direction == EnumDirection.WEST ? 4 : direction == EnumDirection.EAST ? 5 : -1;
        return metadata2;
    }

    public boolean updateTick(World world, int x, int y, int z, Random rand) {
        return super.updateTick(world, x, y, z, rand) && world.getBlock(x, y, z) != this;
    }

    public boolean onNeighborBlockChange(World world, int x, int y, int z, int neighbor_block_id) {
        if (super.onNeighborBlockChange(world, x, y, z, neighbor_block_id)) {
            return true;
        }
        TileEntityBackpack tileEntityBackpack = (TileEntityBackpack) world.getBlockTileEntity(x, y, z);
        if (tileEntityBackpack != null) {
            tileEntityBackpack.updateContainingBlockInfo();
            return true;
        }
        return false;
    }

    public boolean onBlockPlacedMITE(World world, int x, int y, int z, int metadata, Entity placer, boolean test_only) {
        TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
        if (!test_only && (placer instanceof EntityLivingBase)) {
            ItemStack item_stack = placer.getAsEntityLivingBase().getHeldItemStack();
            if (item_stack.hasDisplayName() && tile_entity != null) {
                tile_entity.setCustomInvName(item_stack.getDisplayName());
            }
        }
        if ((placer instanceof EntityPlayer) && tile_entity != null) {
            ItemStack itemStack = ((EntityPlayer) placer).getHeldItemStack();
            if (itemStack.stackTagCompound != null && itemStack.hasTagCompound() && itemStack.stackTagCompound.getTag("Items") != null) {
                deserializeNBT((TileEntityBackpack) tile_entity, ((EntityPlayer) placer).getHeldItemStack().stackTagCompound);
                return true;
            }
            return true;
        }
        return true;
    }

    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
        TileEntityBackpack entity = (TileEntityBackpack) par1World.getBlockTileEntity(par2, par3, par4);
        if (entity != null) {
            this.stackTagCompound = serializeNBT(entity);
        }
        par1World.func_96440_m(par2, par3, par4, par5);
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    protected EntityItem dropBlockAsItem_do(BlockBreakInfo info, ItemStack item_stack) {
        World world = info.world;
        if (world.isRemote) {
            Minecraft.setErrorMessage("dropBlockAsItem_do: not meant to be called on client");
            return null;
        }
        if (world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            if (this.stackTagCompound != null) {
                item_stack.stackTagCompound = this.stackTagCompound;
            }
            EntityItem entity_item = info.createEntityItem(item_stack);
            entity_item.delayBeforeCanPickup = 10;
            if (info.wasPickedByPlayer()) {
                EntityPlayer player = info.getResponsiblePlayer();
                entity_item.motionZ = 0.0d;
                entity_item.motionY = 0.0d;
                entity_item.motionX = 0.0d;
                Vec3 player_center = player.getCenterPoint();
                entity_item.setPosition(player_center.xCoord, player_center.yCoord, player_center.zCoord);
                entity_item.delayBeforeCanPickup = 0;
            } else if (info.wasWindfall()) {
                entity_item.motionZ = 0.0d;
                entity_item.motionY = 0.0d;
                entity_item.motionX = 0.0d;
            }
            if (info.wasExploded()) {
                world.addToSpawnPendingList(entity_item, world.getTotalWorldTime() + 1);
            } else {
                world.spawnEntityInWorld(entity_item);
            }
            if (info.wasExploded()) {
                entity_item.applyExplosionMotion(info.explosion);
            }
            return entity_item;
        }
        return null;
    }

    public NBTTagCompound serializeNBT(TileEntityBackpack entity) {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < entity.getSizeInventory(); i++) {
            ItemStack inSlot = entity.getStackInSlot(i);
            if (inSlot != null) {
                NBTTagCompound itemStackTag = new NBTTagCompound();
                itemStackTag.setInteger("Slot", i);
                inSlot.writeToNBT(itemStackTag);
                nbtTagList.appendTag(itemStackTag);
            }
        }
        if (nbtTagList.tagCount() != 0) {
            nbt.setTag("Items", nbtTagList);
            nbt.setInteger("Size", entity.getSizeInventory());
        }
        return nbt;
    }

    public void deserializeNBT(TileEntityBackpack entity, NBTTagCompound nbt) {
        NBTTagList nbtTagList = (NBTTagList) nbt.getTag("Items");
        for (int i = 0; i < nbtTagList.tagCount(); i++) {
            NBTTagCompound itemStackTag = (NBTTagCompound) nbtTagList.tagAt(i);
            int slot = itemStackTag.getInteger("Slot");
            if (slot >= 0 && slot < entity.getSizeInventory()) {
                entity.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemStackTag));
            }
        }
    }

    public boolean canOpenChest(World world, int x, int y, int z, EntityLivingBase entity_living_base) {
        return world.isAirOrPassableBlock(x, y + 1, z, true) && !entity_living_base.hasCurse(Curse.cannot_open_chests, true);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, EnumFace face, float dx, float dy, float dz) {
        IInventory inventory;
        if (player.onServer() && canOpenChest(world, x, y, z, player) && (inventory = getInventory(world, x, y, z)) != null) {
            ((IServerPlayer) player.getAsEntityPlayerMP()).backpack$DisplayBackpackGui(inventory);
            return true;
        }
        return true;
    }

    public IInventory getInventory(World par1World, int par2, int par3, int par4) {
        TileEntityBackpack tileEntityBackpack = (TileEntityBackpack) par1World.getBlockTileEntity(par2, par3, par4);
        if (tileEntityBackpack == null || par1World.isBlockNormalCube(par2, par3 + 1, par4) || isOcelotBlockingChest(par1World, par2, par3, par4)) {
            return null;
        }
        return tileEntityBackpack;
    }

    private static boolean isOcelotBlockingChest(World par0World, int par1, int par2, int par3) {
        Iterator<EntityOcelot> var4 = par0World.getEntitiesWithinAABB(EntityOcelot.class, AxisAlignedBB.getAABBPool().getAABB(par1, par2 + 1, par3, par1 + 1, par2 + 2, par3 + 1)).iterator();
        while (var4.hasNext()) {
            if (var4.next().isSitting()) {
                return true;
            }
        }
        return false;
    }

    public boolean playerSwingsOnBlockActivated(boolean empty_handed) {
        return empty_handed;
    }

    public boolean canBePlacedOnBlock(int metadata, Block block_below, int block_below_metadata, double block_below_bounds_max_y) {
        return block_below.isTopFlatAndSolid(block_below_metadata) && super.canBePlacedOnBlock(metadata, block_below, block_below_metadata, block_below_bounds_max_y);
    }

    public boolean isStandardFormCube(boolean[] is_standard_form_cube, int metadata) {
        return false;
    }

    public boolean blocksPrecipitation(boolean[] blocks_precipitation, int metadata) {
        return true;
    }

    public TileEntity createNewTileEntity(World var1) {
        return new TileEntityBackpack(this);
    }
}
