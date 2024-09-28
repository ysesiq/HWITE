package mcp.mobius.waila.api;

import net.minecraft.EntityPlayer;
import net.minecraft.NBTTagCompound;
import net.minecraft.TileEntity;
import net.minecraft.MovingObjectPosition;
import net.minecraft.Vec3;
import net.minecraft.World;

/**
 * The Accessor is used to get some basic data out of the game without having to request direct access to the game
 * engine.<br>
 * It will also return things that are unmodified by the overriding systems (like getWailaStack).<br>
 * An instance of this interface is passed to most of Waila FMP callbacks.
 * 
 * @author ProfMobius
 *
 */

public interface IWailaFMPAccessor {

    World getWorld();

    EntityPlayer getPlayer();

    TileEntity getTileEntity();

    MovingObjectPosition getPosition();

    NBTTagCompound getNBTData();

    NBTTagCompound getFullNBTData();

    int getNBTInteger(NBTTagCompound tag, String keyname);

    double getPartialFrame();

    Vec3 getRenderingPosition();

    String getID();
}
