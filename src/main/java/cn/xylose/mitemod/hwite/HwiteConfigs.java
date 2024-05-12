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

//    public static final ConfigBoolean Biome_Hide = new ConfigBoolean("隐藏生物群系组件", "隐藏生物群系组件");
//    public static final ConfigBoolean Direction_Hide = new ConfigBoolean("隐藏方向组件", "隐藏方向组件");
//    public static final ConfigBoolean Pos_Hide = new ConfigBoolean("隐藏坐标组件", "隐藏坐标组件");
//    public static final ConfigBoolean Light_Hide = new ConfigBoolean("隐藏方块亮度组件", "隐藏方块亮度组件");

    public static final ConfigInteger BGWidth = new ConfigInteger("背景框的宽度", 248, 0, 500);
    public static final ConfigInteger BGHeight = new ConfigInteger("背景框的高度", 45, 0, 300);
    public static final ConfigInteger BGLeftEliminate = new ConfigInteger("背景框左部剔除", 160, 0, 500);
    public static final ConfigInteger BGTopEliminate = new ConfigInteger("背景框顶部剔除", 8, 0, 300);
    public static final ConfigInteger InfoX = new ConfigInteger("信息x轴渲染位置", 183, 0, 500);
    public static final ConfigInteger InfoY = new ConfigInteger("信息y轴渲染位置", 12, 0, 300);
    public static final ConfigInteger EntityInfoX = new ConfigInteger("实体渲染x轴位置", 160, 0, 500);
    public static final ConfigInteger EntityInfoY = new ConfigInteger("实体渲染y轴位置", 43, 0, 300);
    public static final ConfigInteger EntityInfoSize = new ConfigInteger("实体渲染大小", 18, 0, 100);
    public static final ConfigInteger BlockInfoX = new ConfigInteger("方块渲染x轴位置", 165, 0, 500);
    public static final ConfigInteger BlockInfoYBig = new ConfigInteger("3行信息显示时的方块渲染y轴位置", 18, 0, 300);
    public static final ConfigInteger BlockInfoYSmall = new ConfigInteger("2行信息显示时的方块渲染y轴位置", 14, 0, 300);

    private static HwiteConfigs Instance;
//    public static List<ConfigBase> hide;
    public static List<ConfigBase> pos;

    public HwiteConfigs(String name, List<ConfigHotkey> hotkeys, List<ConfigBase> values) {
        super(name, hotkeys, values);
    }

    public static void init() {
//        hide = List.of(Biome_Hide, Direction_Hide, Pos_Hide, Light_Hide);
        pos = List.of(BGWidth, BGHeight, BGLeftEliminate, BGTopEliminate, InfoX, InfoY, EntityInfoX, EntityInfoY, EntityInfoSize, BlockInfoX, BlockInfoYBig, BlockInfoYSmall);
        ArrayList<ConfigBase> values = new ArrayList<ConfigBase>();
//        values.addAll(hide);
        values.addAll(pos);
        Instance = new HwiteConfigs("HWITE", null, values);
    }

    public static HwiteConfigs getInstance() {
        return Instance;
    }

    public void save() {
        JsonObject configRoot = new JsonObject();
//        ConfigUtils.writeConfigBase(configRoot, "Values", hide);
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
//                ConfigUtils.readConfigBase(obj, "Values", hide);
                JsonObject valuesRoot = JsonUtils.getNestedObject(obj, "Values", true);
                ConfigUtils.readConfigBase(valuesRoot, "pos", pos);
            }
        }
    }

}
