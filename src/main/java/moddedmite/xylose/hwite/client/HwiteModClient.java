package moddedmite.xylose.hwite.client;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import moddedmite.xylose.hwite.config.HwiteConfigs;
import net.fabricmc.api.ClientModInitializer;

public class HwiteModClient implements ClientModInitializer, ModMenuApi {

    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> {
            return HwiteConfigs.getInstance().getConfigScreen(screen);
        };
    }

    @Override
    public void onInitializeClient() {
    }
}
