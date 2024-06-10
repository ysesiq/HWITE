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
    public static final ConfigBoolean shiftMoreInfo = new ConfigBoolean("按住Shift显示详细信息", true, "按住Shift显示详细信息");
    public static final ConfigBoolean HUDPos = new ConfigBoolean("打开HUD位置调整", false, "用于调整HUD位置,打开后请使用后2项配置项调整");
    public static final ConfigBoolean IDSUB = new ConfigBoolean("ID与附加值", false, "ID与附加值");
    public static final ConfigBoolean CanBreak = new ConfigBoolean("挖掘提示", true, "挖掘提示");

    public static final ConfigInteger HUDX = new ConfigInteger("HUD的x轴渲染位置", 190, 0, 500, "请打开HUD位置调整");
    public static final ConfigInteger HUDY = new ConfigInteger("HUD的y轴渲染位置", 18, 0, 300, "请打开HUD位置调整");
    public static final ConfigInteger EntityInfoX = new ConfigInteger("实体渲染x轴位置", 180, 0, 500, "请关闭隐藏实体渲染(WIP)");
    public static final ConfigInteger EntityInfoY = new ConfigInteger("实体渲染y轴位置", 43, 0, 300, "请关闭隐藏实体渲染(WIP)");
    public static final ConfigInteger EntityInfoSize = new ConfigInteger("实体渲染大小", 18, 0, 100, "请关闭隐藏实体渲染(WIP)");
    public static final ConfigInteger HUDBGColor = new ConfigInteger("HUD背景颜色", -267386864, Integer.MIN_VALUE, Integer.MAX_VALUE, "背景颜色");
    public static final ConfigInteger HUDBGColor1 = new ConfigInteger("HUD背景颜色1", 16777215, Integer.MIN_VALUE, Integer.MAX_VALUE, "背景颜色1");
    public static final ConfigInteger HUDBGColor2 = new ConfigInteger("HUD背景颜色2", -369098752, Integer.MIN_VALUE, Integer.MAX_VALUE, "数值为0时为半透明");
    public static final ConfigInteger HUDFrameColor = new ConfigInteger("HUD外框颜色", 1347420415, Integer.MIN_VALUE, Integer.MAX_VALUE, "HUD外框颜色");
    public static final ConfigInteger HUDFrameColor1 = new ConfigInteger("HUD外框颜色1", 16711422, Integer.MIN_VALUE, Integer.MAX_VALUE, "HUD外框颜色1");
    public static final ConfigInteger HUDFrameColor2 = new ConfigInteger("HUD外框颜色2", -16777216, Integer.MIN_VALUE, Integer.MAX_VALUE, "HUD外框颜色2");

    private static HwiteConfigs Instance;
    public static List<ConfigBase> hide;
    public static List<ConfigBase> pos;

    public HwiteConfigs(String name, List<ConfigHotkey> hotkeys, List<ConfigBase> values) {
        super(name, hotkeys, values);
    }

    public static void init() {
        hide = List.of(InfoHide, BlockInfoHide, EntityInfoHide, Liquids, shiftMoreInfo, HUDPos, IDSUB, CanBreak);
        pos = List.of(HUDX, HUDY, EntityInfoX, EntityInfoY, EntityInfoSize, HUDBGColor, HUDBGColor1, HUDBGColor2, HUDFrameColor, HUDFrameColor1, HUDFrameColor2);
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
