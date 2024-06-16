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

    public static final ConfigBoolean HwiteHud = new ConfigBoolean("HWITE总开关", true);
    public static final ConfigBoolean EntityRender = new ConfigBoolean("实体渲染(WIP)", false);
    public static final ConfigBoolean BlockRender = new ConfigBoolean("方块渲染", true);
    public static final ConfigBoolean Liquids = new ConfigBoolean("流体信息", false);
    public static final ConfigBoolean shiftMoreInfo = new ConfigBoolean("Shift显示详细信息", true, "按住Shift显示详细信息");
    public static final ConfigBoolean IDSUB = new ConfigBoolean("ID与附加值", false);
    public static final ConfigBoolean CanBreak = new ConfigBoolean("挖掘提示", true, "指向方块时,如果能挖掘显示'√',不能挖掘显示'X'");
    public static final ConfigBoolean BreakProgress = new ConfigBoolean("挖掘进度", true);
    public static final ConfigBoolean BreakProgressLine = new ConfigBoolean("挖掘进度条(WIP)", false);
    public static final ConfigBoolean NonCollidableEntity = new ConfigBoolean("不可碰撞实体", false, "例如经验球,掉落物,钓鱼竿的浮标");
    public static final ConfigBoolean HUDBackGround = new ConfigBoolean("背景总开关", true);
    public static final ConfigBoolean HUDRoundedRectangle = new ConfigBoolean("圆角矩形", true);
    public static final ConfigBoolean HUDFrame = new ConfigBoolean("边框", true);
    public static final ConfigBoolean HUDCentralBackground = new ConfigBoolean("中心背景", true);
    public static final ConfigBoolean HUDPos = new ConfigBoolean("HUD位置调整", false, "打开后请使用后2项配置项调整");

    public static final ConfigInteger HUDX = new ConfigInteger("HUD的x轴渲染位置", 190, 0, 500, true, "请打开HUD位置调整");
    public static final ConfigInteger HUDY = new ConfigInteger("HUD的y轴渲染位置", 18, 0, 300, true, "请打开HUD位置调整");
    public static final ConfigInteger EntityInfoX = new ConfigInteger("实体渲染x轴位置", 180, 0, 500, true, "请关闭实体渲染(WIP)");
    public static final ConfigInteger EntityInfoY = new ConfigInteger("实体渲染y轴位置", 43, 0, 300, true, "请关闭实体渲染(WIP)");
    public static final ConfigInteger EntityInfoSize = new ConfigInteger("实体渲染大小", 18, 0, 100, false, "请关闭实体渲染(WIP)");
    public static final ConfigInteger HUDBGColor = new ConfigInteger("HUD背景颜色", -267386864, Integer.MIN_VALUE, Integer.MAX_VALUE, true, "背景颜色");
    public static final ConfigInteger HUDBGColor1 = new ConfigInteger("HUD背景颜色1", 16777215, Integer.MIN_VALUE, Integer.MAX_VALUE, true, "背景颜色1");
    public static final ConfigInteger HUDBGColor2 = new ConfigInteger("HUD背景颜色2", -369098752, Integer.MIN_VALUE, Integer.MAX_VALUE,  true,"数值为0时为半透明");
    public static final ConfigInteger HUDFrameColor = new ConfigInteger("HUD外框颜色", 1347420415, Integer.MIN_VALUE, Integer.MAX_VALUE, true, "HUD外框颜色");
    public static final ConfigInteger HUDFrameColor1 = new ConfigInteger("HUD外框颜色1", 16711422, Integer.MIN_VALUE, Integer.MAX_VALUE, true, "HUD外框颜色1");
    public static final ConfigInteger HUDFrameColor2 = new ConfigInteger("HUD外框颜色2", -16777216, Integer.MIN_VALUE, Integer.MAX_VALUE, true, "HUD外框颜色2");

//    public static final ConfigHotkey HUDHotkey = new ConfigHotkey("HUD热键开关(WIP)");

    private static HwiteConfigs Instance;
    public static List<ConfigBase> hide;
    public static List<ConfigBase> pos;

    public HwiteConfigs(String name, List<ConfigHotkey> hotkeys, List<ConfigBase> values) {
        super(name, hotkeys, values);
    }

    public static void init() {
        hide = List.of(HwiteHud, BlockRender, EntityRender, Liquids, shiftMoreInfo, IDSUB, CanBreak, BreakProgress, BreakProgressLine, NonCollidableEntity, HUDBackGround, HUDRoundedRectangle, HUDFrame, HUDCentralBackground, HUDPos);
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
