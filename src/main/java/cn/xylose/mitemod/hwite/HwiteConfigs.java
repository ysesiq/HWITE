package cn.xylose.mitemod.hwite;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class HwiteConfigs extends SimpleConfigs {

    public static final ConfigBoolean InfoHide = new ConfigBoolean("HWITE Main Switch", true,  "HWITE Main Switch");
    public static final ConfigBoolean EntityInfoHide = new ConfigBoolean("Hide Entity Render(WIP)", true, "Hide Entity Render(WIP)");
    public static final ConfigBoolean BlockInfoHide = new ConfigBoolean("Hide Block Render", false,"Hide Block Render");
    public static final ConfigBoolean Liquids = new ConfigBoolean("Display Liquid Render", false, "Display Liquid Render");
    public static final ConfigBoolean shiftMoreInfo = new ConfigBoolean("Shift Display Detailed Information(WIP)", false, "Shift Display Detailed Information(WIP)");
    public static final ConfigBoolean HUDPos = new ConfigBoolean("Open HUD Pos Adjustment", false, "Used to adjust the HUD pos. After opening, please use the next two configuration to adjust");

    public static final ConfigInteger HUDX = new ConfigInteger("The x-axis render pos of HUD", 190, 0, 500, "Please open HUD pos adjustment");
    public static final ConfigInteger HUDY = new ConfigInteger("The y-axis render pos of HUD", 18, 0, 300, "Please open HUD pos adjustment");
    public static final ConfigInteger EntityInfoX = new ConfigInteger("The x-axis render pos of Entity", 180, 0, 500, "Please turn off hide entity render (WIP");
    public static final ConfigInteger EntityInfoY = new ConfigInteger("The y-axis render pos of Entity", 43, 0, 300, "Please turn off hide entity render (WIP");
    public static final ConfigInteger EntityInfoSize = new ConfigInteger("Entity rendering size", 18, 0, 100, "Please turn off hide entity render (WIP)");
    public static final ConfigInteger BlockInfoX = new ConfigInteger("The x-axis render pos of Block", 165, 0, 500, "Please turn off hide block render");
    public static final ConfigInteger BlockInfoYBig = new ConfigInteger("The y-axis pos of the block rendering when displaying 4-line information", 18, 0, 300, "Please turn off hide block render");
    public static final ConfigInteger BlockInfoYSmall = new ConfigInteger("The y-axis pos of the block rendering when displaying 3-line information", 14, 0, 300, "Please turn off hide block render");

    private static HwiteConfigs Instance;
    public static List<ConfigBase> hide;
    public static List<ConfigBase> pos;

    public HwiteConfigs(String name, List<ConfigHotkey> hotkeys, List<ConfigBase> values) {
        super(name, hotkeys, values);
    }

    public static void init() {
        hide = List.of(InfoHide, BlockInfoHide, EntityInfoHide, Liquids, shiftMoreInfo, HUDPos);
        pos = List.of(HUDX, HUDY, EntityInfoX, EntityInfoY, EntityInfoSize, BlockInfoX, BlockInfoYBig, BlockInfoYSmall);
        ArrayList<ConfigBase> values = new ArrayList<ConfigBase>();
        values.addAll(hide);
        values.addAll(pos);
        Instance = new HwiteConfigs("HWITE", null, values);
    }

    public static HwiteConfigs getInstance() {
        return Instance;
    }

    public void save() {
        JsonObject configRoot = new JsonObject();
        ConfigUtils.writeConfigBase(configRoot, "Values", hide);
        JsonObject valuesRoot = JsonUtils.getNestedObject(configRoot, "Values", true);
        ConfigUtils.writeConfigBase(valuesRoot, "pos", pos);
        JsonUtils.writeJsonToFile(configRoot, this.getOptionsFile());
    }

    public void load() {
        if (!this.getOptionsFile().exists()) {
            this.save();
        } else {
            JsonElement jsonElement = JsonUtils.parseJsonFile(this.getOptionsFile());
            if (jsonElement != null && jsonElement.isJsonObject()) {
                JsonObject obj = jsonElement.getAsJsonObject();
                ConfigUtils.readConfigBase(obj, "Values", hide);
                JsonObject valuesRoot = JsonUtils.getNestedObject(obj, "Values", true);
                ConfigUtils.readConfigBase(valuesRoot, "pos", pos);
            }
        }
    }

}
