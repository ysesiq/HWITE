package cn.xylose.mitemod.hwite.client;

import cn.xylose.mitemod.hwite.HwiteConfigs;
import fi.dy.masa.malilib.gui.screen.ValueScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.Block;
import net.minecraft.EntityLivingBase;
import net.minecraft.Gui;
import net.minecraft.GuiScreen;

public class HwiteModClient implements ClientModInitializer{

    public static String info;
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
    public static boolean isViewMode = false;

    public void setIsViewMode() {
        GuiScreen guiScreen = new GuiScreen();
        if (guiScreen == HwiteConfigs.getInstance().getValueScreen(guiScreen)) {
            isViewMode = true;
        }
    }

    @Override
    public void onInitializeClient() {
    }
}
