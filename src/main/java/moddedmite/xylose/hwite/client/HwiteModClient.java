package moddedmite.xylose.hwite.client;

import moddedmite.xylose.hwite.config.HwiteConfigs;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.GuiScreen;

public class HwiteModClient implements ClientModInitializer {
    public static boolean isViewMode = false;
    public static int stringWidth1;

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
