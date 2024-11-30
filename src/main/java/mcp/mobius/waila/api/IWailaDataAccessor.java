package mcp.mobius.waila.api;

import net.minecraft.*;

/**
 * The Accessor is used to get some basic data out of the game without having to request direct access to the game
 * engine.<br>
 * It will also return things that are unmodified by the overriding systems (like getWailaStack).<br>
 * An instance of this interface is passed to most of Waila Block/TileEntity callbacks.
 * 
 * @author ProfMobius
 *
 */

public interface IWailaDataAccessor {

    World getWorld();

    EntityPlayer getPlayer();

    Block getBlock();

    int getBlockID();

    String getBlockQualifiedName();

    String getBlockUnlocalizedName();

    String getMod();

    int getMetadata();

    TileEntity getTileEntity();

    RaycastCollision getPosition();

    Vec3 getRenderingPosition();

    NBTTagCompound getNBTData();

    int getNBTInteger(NBTTagCompound tag, String keyname);

    double getPartialFrame();

    EnumDirection getSide();

    ItemStack getStack();
}
