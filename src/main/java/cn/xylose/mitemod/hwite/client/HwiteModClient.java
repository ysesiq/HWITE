package cn.xylose.mitemod.hwite.client;

import cn.xylose.mitemod.hwite.config.HwiteConfigs;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.GuiScreen;

public class HwiteModClient implements ClientModInitializer {
    public static boolean isViewMode = false;
//    public static boolean isHideMode = false;
    public static int stringWidth1;

    public void setIsViewMode() {
        GuiScreen guiScreen = new GuiScreen();
        if (guiScreen == HwiteConfigs.getInstance().getValueScreen(guiScreen)) {
            isViewMode = true;
        }
    }
//    public static boolean setIsHideMode() {
//        isViewMode = true;
//        return true;
//    }

    @Override
    public void onInitializeClient() {
    }
}
