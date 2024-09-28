package mcp.mobius.waila.api;

import net.minecraft.*;

/**
 * The Accessor is used to get some basic data out of the game without having to request direct access to the game
 * engine.<br>
 * It will also return things that are unmodified by the overriding systems (like getWailaStack).<br>
 * An instance of this interface is passed to most of Waila Entity callbacks.
 * 
 * @author ProfMobius
 *
 */

public interface IWailaEntityAccessor {

    World getWorld();

    EntityPlayer getPlayer();

    Entity getEntity();

    RaycastCollision getPosition();

    Vec3 getRenderingPosition();

    NBTTagCompound getNBTData();

    int getNBTInteger(NBTTagCompound tag, String keyname);

    double getPartialFrame();
}
