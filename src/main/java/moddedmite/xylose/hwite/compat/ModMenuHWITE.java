package moddedmite.xylose.hwite.compat;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import moddedmite.xylose.hwite.config.HwiteConfigs;

public class ModMenuHWITE implements ModMenuApi {

    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> {
            return HwiteConfigs.getInstance().getConfigScreen(screen);
        };
    }
}
