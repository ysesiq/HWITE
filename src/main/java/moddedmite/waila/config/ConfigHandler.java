package moddedmite.waila.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.util.JsonUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.Minecraft;

public class ConfigHandler extends SimpleConfigs {
    public static final ConfigBoolean ViewMode = new ConfigBoolean("hwite.View", false, "配置界面预览");

    private static final ConfigHandler _instance;
    public static final List<ConfigBase<?>> hwite;
    public static final List<ConfigBase<?>> general;
    public static final List<ConfigBase<?>> appearance;
    public static final List<ConfigBase<?>> hwiteDev;
    public static final List<ConfigHotkey> hotkey;
    public static final List<ConfigBase<?>> hiwla;
    public static final List<ConfigTab> tabs;

    public ConfigHandler() {
        super("Waila", hotkey, hwite);
    }

    public List<ConfigTab> getConfigTabs() {
        return tabs;
    }

    public static ConfigHandler getInstance() {
        return _instance;
    }

    public void save() {
        JsonObject root = new JsonObject();
        ConfigUtils.writeConfigBase((JsonObject)root, (String)"HWITE", hwite);
        ConfigUtils.writeConfigBase((JsonObject)root, (String)"基础", general);
        ConfigUtils.writeConfigBase((JsonObject)root, (String)"外观", appearance);
        ConfigUtils.writeConfigBase((JsonObject)root, (String)"开发", hwiteDev);
        ConfigUtils.writeConfigBase((JsonObject)root, (String)"快捷键", hotkey);
        ConfigUtils.writeConfigBase((JsonObject)root, (String)"附属", hiwla);
        JsonUtils.writeJsonToFile((JsonObject)root, (File)this.optionsFile);
    }

    public void load() {
        if (!this.optionsFile.exists()) {
            this.save();
        } else {
            JsonElement jsonElement = JsonUtils.parseJsonFile((File)this.optionsFile);
            if (jsonElement != null && jsonElement.isJsonObject()) {
                JsonObject root = jsonElement.getAsJsonObject();
                ConfigUtils.readConfigBase((JsonObject)root, (String)"HWITE", hwite);
                ConfigUtils.readConfigBase((JsonObject)root, (String)"基础", general);
                ConfigUtils.readConfigBase((JsonObject)root, (String)"外观", appearance);
                ConfigUtils.readConfigBase((JsonObject)root, (String)"开发", hwiteDev);
                ConfigUtils.readConfigBase((JsonObject)root, (String)"快捷键", hotkey);
                ConfigUtils.readConfigBase((JsonObject)root, (String)"附属", hiwla);
            }
        }
    }

    static {
        tabs = new ArrayList<ConfigTab>();
        hwite = List.of(DisplayTooltip, DebugRenderTooltip, devMoveDownTooltip, DisplayModName, DisplayBlock, DisplayLiquids, DisplayEntity, DisplayNonCollidingEntity, BlockRender, EntityRender);
        ArrayList<Object> configValues = new ArrayList<Object>();
        configValues.addAll(hwite);
        configValues.addAll(appearance);
        configValues.addAll(hwiteDev);
        configValues.addAll(hiwla);
        configValues.addAll(hotkey);
        tabs.add(new ConfigTab("hwite.hwite", hwite));
        tabs.add(new ConfigTab("hwite.general", general));
        tabs.add(new ConfigTab("hwite.appearance", appearance));
        tabs.add(new ConfigTab("hwite.dev", hwiteDev));
        tabs.add(new ConfigTab("hwite.hiwla", hiwla));
        tabs.add(new ConfigTab("hwite.hotkey", hotkey));
        ConfigGuiHotkey.getKeybind().setCallback((keyAction, iKeybind) -> {
            Minecraft.getMinecraft().displayGuiScreen(HwiteConfigs.getInstance().getConfigScreen(null));
            return true;
        });
        _instance = new ConfigHandler();
    }
}
