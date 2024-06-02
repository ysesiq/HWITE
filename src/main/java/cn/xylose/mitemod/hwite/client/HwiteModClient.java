package cn.xylose.mitemod.hwite.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.Block;
import net.minecraft.EntityLivingBase;
import net.minecraft.EnumChatFormatting;

public class HwiteModClient implements ClientModInitializer {

    public static String info = "";
    public static String info_line_1 = EnumChatFormatting.GRAY + "";
    public static String info_line_2 = "";
    public static String info_line_3 = "";
    public static String break_info = "";
    public static EntityLivingBase entityInfo;
    public static Block blockInfo;
    public static String modInfo = EnumChatFormatting.DARK_BLUE + "";

    @Override
    public void onInitializeClient() {
    }
}
