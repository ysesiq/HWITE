package cn.xylose.mitemod.hwite.api;

import net.minecraft.*;

public interface IRaycastCollision {
    Block setBlockHit();

    int setBlockHitID();

    boolean isTileEntity();

    boolean isTileEntityAt(int x, int y, int z);

    TileEntity getTileEntityHit();

//    Material getTileEntityHitMaterial();

    NBTTagCompound getNBTData();

    int getNBTInteger(NBTTagCompound tag, String key);

    boolean isTagCorrect(NBTTagCompound tag);
}
