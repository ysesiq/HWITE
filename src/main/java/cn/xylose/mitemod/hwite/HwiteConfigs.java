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

    public static final ConfigBoolean InfoHide = new ConfigBoolean("HWITE总开关", true,  "HWITE总开关");
    public static final ConfigBoolean EntityInfoHide = new ConfigBoolean("隐藏实体渲染(WIP)", true, "隐藏实体渲染(WIP)");
    public static final ConfigBoolean BlockInfoHide = new ConfigBoolean("隐藏方块渲染", false,"隐藏方块渲染");
    public static final ConfigBoolean Liquids = new ConfigBoolean("是否显示流体信息", false, "是否显示流体信息");
    public static final ConfigBoolean shiftMoreInfo = new ConfigBoolean("按住Shift显示详细信息(WIP)", false, "按住Shift显示详细信息(WIP)");
    public static final ConfigBoolean HUDPos = new ConfigBoolean("打开HUD位置调整", false, "用于调整HUD位置,打开后请使用后2项配置项调整");

    public static final ConfigInteger HUDX = new ConfigInteger("HUD的x轴渲染位置", 190, 0, 500, "请打开HUD位置调整");
    public static final ConfigInteger HUDY = new ConfigInteger("HUD的y轴渲染位置", 18, 0, 300, "请打开HUD位置调整");
    public static final ConfigInteger EntityInfoX = new ConfigInteger("实体渲染x轴位置", 180, 0, 500, "请关闭隐藏实体渲染(WIP)");
    public static final ConfigInteger EntityInfoY = new ConfigInteger("实体渲染y轴位置", 43, 0, 300, "请关闭隐藏实体渲染(WIP)");
    public static final ConfigInteger EntityInfoSize = new ConfigInteger("实体渲染大小", 18, 0, 100, "请关闭隐藏实体渲染(WIP)");
    public static final ConfigInteger BlockInfoX = new ConfigInteger("方块渲染x轴位置", 165, 0, 500, "请关闭隐藏方块渲染");
    public static final ConfigInteger BlockInfoYBig = new ConfigInteger("4行信息显示时的方块渲染y轴位置", 18, 0, 300, "请关闭隐藏方块渲染");
    public static final ConfigInteger BlockInfoYSmall = new ConfigInteger("3行信息显示时的方块渲染y轴位置", 14, 0, 300, "请关闭隐藏方块渲染");

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
