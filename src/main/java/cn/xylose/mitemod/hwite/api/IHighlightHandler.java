package cn.xylose.mitemod.hwite.api;

import cn.xylose.mitemod.hwite.ItemInfo;
import net.minecraft.*;
import java.util.List;

public interface IHighlightHandler {
    public ItemStack identifyHighlight(World world, EntityPlayer player, MovingObjectPosition mop);

    public List<String> handleTextData(ItemStack itemStack, World world, EntityPlayer player, RaycastCollision mop, List<String> currenttip, ItemInfo.Layout layout);
}
