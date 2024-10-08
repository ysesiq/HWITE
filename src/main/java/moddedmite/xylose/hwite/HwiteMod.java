package moddedmite.xylose.hwite;

import moddedmite.xylose.hwite.config.HwiteConfigs;
import fi.dy.masa.malilib.config.ConfigManager;
import net.fabricmc.api.ModInitializer;

public class HwiteMod implements ModInitializer {
    @Override
    public void onInitialize() {
        HwiteConfigs.getInstance().load();
        ConfigManager.getInstance().registerConfig(HwiteConfigs.getInstance());
    }
}
