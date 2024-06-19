//package cn.xylose.mitemod.hwite;
//
//import cn.xylose.mitemod.hwite.api.IBreakingProgress;
//import cn.xylose.mitemod.hwite.api.IHighlightHandler;
//import net.minecraft.*;
//import java.util.List;
//
//public class HUDHandlerHwite implements IHighlightHandler {
//    public ItemStack identifyHighlight(World world, EntityPlayer player, MovingObjectPosition mop) {
//        return null;
//    }
//
//    @Override
//    public List<String> handleTextData(World world, EntityPlayer player, List<String> currenttip, ItemInfo.Layout layout) {
//        return List.of();
//    }
//
//
//    public List<String> handleTextData(ItemStack itemStack, World world, EntityPlayer player, MovingObjectPosition mop, List<String> currenttip, ItemInfo.Layout layout) {
//        if (layout == ItemInfo.Layout.HEADER) {
////       if (currenttip.size() == 0) {
////         currenttip.add("< Unnamed >");
////       } else {
////         String name = currenttip.get(0);
////         currenttip.set(0, name + String.format(" %s:%s", world.getBlockId(mop.blockX, mop.blockY, mop.blockZ), world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ)));
////       }
//            if (mop != null) {
//                int blockId = world.getBlockId(mop.blockX, mop.blockY, mop.blockZ);
////                if (blockId == 149 || blockId == 150) blockId = 404;
////                else if (blockId == 93 || blockId == 94) blockId = 356;
//                currenttip.add(String.format("%s:%s", blockId, world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ)));
//                currenttip.add(String.format(" %f ", ((IBreakingProgress) Minecraft.getMinecraft().playerController).getCurrentBreakingProgress()));
//            }
//        } else if (layout == ItemInfo.Layout.FOOTER) {
////            String modName = WailaAddon.instance.getModName(itemStack);
////            if (modName != null && !modName.equals(""))
////                currenttip.add("§9§o" + modName);
//        }
//
//        return currenttip;
//    }
//}
