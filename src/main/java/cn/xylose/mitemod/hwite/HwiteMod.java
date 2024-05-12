package cn.xylose.mitemod.hwite;

import fi.dy.masa.malilib.gui.screen.ModsScreen;
import net.fabricmc.api.ModInitializer;

public class HwiteMod implements ModInitializer {

    @Override
    public void onInitialize() {
        HwiteConfigs.init();
        HwiteConfigs.getInstance().load();
        ModsScreen.getInstance().addConfig(HwiteConfigs.getInstance());
    }
}
