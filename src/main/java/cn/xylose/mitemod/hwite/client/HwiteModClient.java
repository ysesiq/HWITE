package cn.xylose.mitemod.hwite.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.Block;
import net.minecraft.EntityLivingBase;
import net.xiaoyu233.fml.api.INamespaced;
import net.xiaoyu233.fml.api.block.IBlock;

public class HwiteModClient implements ClientModInitializer, IBlock, INamespaced {

    public static String info = "";
    public static String info_line_1 = "";
    public static String info_line_2 = "";
    public static String info_line_3 = "";
    public static String break_info = "";
    public static EntityLivingBase entityInfo;
    public static Block blockInfo;
    public static String modInfo = "";
    public static int blockPosX = 0;
    public static int blockPosY = 0;
    public static int blockPosZ = 0;

    @Override
    public void onInitializeClient() {
    }
}
