package cn.xylose.mitemod.hwite.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class HwiteConfigs extends SimpleConfigs {
    //switch
    public static final ConfigBoolean ViewMode = new ConfigBoolean("预览", false, "配置界面预览");
    public static final ConfigBoolean RenderHwiteHud = new ConfigBoolean("HWITE总开关", true);
    public static final ConfigBoolean EntityRender = new ConfigBoolean("实体渲染(WIP)", false);
    public static final ConfigBoolean BlockRender = new ConfigBoolean("方块渲染", true);
    public static final ConfigBoolean Liquids = new ConfigBoolean("流体信息", false);
    public static final ConfigBoolean CanBreak = new ConfigBoolean("挖掘提示", true, "指向方块时,如果能挖掘显示'√',不能挖掘显示'X'");
    public static final ConfigBoolean BreakProgress = new ConfigBoolean("挖掘进度", true);
    public static final ConfigBoolean BreakProgressLine = new ConfigBoolean("挖掘进度条(WIP)", false);
    public static final ConfigBoolean NonCollidableEntity = new ConfigBoolean("不可碰撞实体", false, "例如经验球,掉落物,钓鱼竿的浮标");
    public static final ConfigBoolean GrowthValue = new ConfigBoolean("作物生长进度", true);
    public static final ConfigBoolean Redstone = new ConfigBoolean("红石信息", true, "红石能量强度,拉杆.压力板状态,比较器状态,中继器状态");
    public static final ConfigBoolean SpawnerType = new ConfigBoolean("刷怪笼种类", true);
    //values
    public static final ConfigBoolean HUDPosOverride = new ConfigBoolean("HUD位置调整", false, "打开后请使用位置子页面第1,2个配置项调整");
    public static final ConfigInteger HUDX = new ConfigInteger("HUD的x轴渲染位置", 190, 0, 500, true, "请打开HUD位置调整");
    public static final ConfigInteger HUDY = new ConfigInteger("HUD的y轴渲染位置", 18, 0, 300, true, "请打开HUD位置调整");
    public static final ConfigInteger EntityInfoX = new ConfigInteger("实体渲染x轴位置", 180, 0, 500, true, "请关闭实体渲染(WIP)");
    public static final ConfigInteger EntityInfoY = new ConfigInteger("实体渲染y轴位置", 43, 0, 300, true, "请关闭实体渲染(WIP)");
    public static final ConfigInteger EntityInfoSize = new ConfigInteger("实体渲染大小", 18, 0, 100, false, "请关闭实体渲染(WIP)");
    //appearance
    public static final ConfigBoolean HUDBackGround = new ConfigBoolean("背景总开关", true);
    public static final ConfigBoolean HUDRoundedRectangle = new ConfigBoolean("圆角矩形", true);
    public static final ConfigBoolean HUDFrame = new ConfigBoolean("边框", true);
    public static final ConfigBoolean HUDCentralBackground = new ConfigBoolean("中心背景", true);
    public static final ConfigBoolean HUDThemeSwitch = new ConfigBoolean("HUD主题切换开关", true, "自定义主题请关闭本项");
    public static final ConfigEnum<EnumHUDTheme> HUDTheme = new ConfigEnum<>("HUD主题", EnumHUDTheme.Waila);
    public static final ConfigColor HUDBGColor = new ConfigColor("自定义HUD背景颜色", "#CC10010F", "此项为16进制数,12位数为透明度,34位为红色通道,56位为绿色通道,78位为蓝色通道");
    public static final ConfigColor HUDFrameColor = new ConfigColor("自定义HUD外框梯度颜色(顶)", "#CC5001FE", "此项为16进制数,12位数为透明度,34位为红色通道,56位为绿色通道,78位为蓝色通道");
    public static final ConfigColor HUDFrameColor1 = new ConfigColor("自定义HUD外框梯度颜色(底)", "#CC28017E", "此项为16进制数,12位数为透明度,34位为红色通道,56位为绿色通道,78位为蓝色通道");
    public static final ConfigColor BreakProgressLineColor = new ConfigColor("自定义挖掘进度条颜色", "#FFFFFFFF", "此项为16进制数,12位数为透明度,34位为红色通道,56位为绿色通道,78位为蓝色通道");
    //dev
    public static final ConfigBoolean MITEDetailsInfo = new ConfigBoolean("MITE详细信息", false, "硬度,挖掘速度,挖掘等级,这些是原版MITE dev详细信息显示自带的");
    public static final ConfigBoolean ShowIDAndMetadata = new ConfigBoolean("ID与附加值", false);
    //hotkey
    public static final ConfigHotkey HUDHotkey = new ConfigHotkey("HUD热键开关(WIP)", 35);

    private static final HwiteConfigs Instance;
    public static final List<ConfigBase<?>> hwiteswitch;
    public static final List<ConfigBase<?>> values;
    public static final List<ConfigBase<?>> appearance;
    public static final List<ConfigBase<?>> dev;
//    public static final List<ConfigHotkey> hotkey;

    public static final List<ConfigTab> tabs = new ArrayList<>();

    public HwiteConfigs(String name, List<ConfigHotkey> hotkeys, List<?> values) {
        super(name, hotkeys, values);
    }

    static {
        hwiteswitch = List.of(RenderHwiteHud, BlockRender, EntityRender, Liquids, CanBreak, BreakProgress, BreakProgressLine, NonCollidableEntity, GrowthValue, Redstone, SpawnerType);
        values = List.of(HUDPosOverride, HUDX, HUDY, EntityInfoX, EntityInfoY, EntityInfoSize);
        appearance = List.of(HUDBackGround, HUDRoundedRectangle, HUDFrame, HUDCentralBackground, HUDThemeSwitch, HUDTheme, HUDBGColor, HUDFrameColor, HUDFrameColor1, BreakProgressLineColor);
        dev = List.of(ShowIDAndMetadata, MITEDetailsInfo);
//        hotkey = List.of(HUDHotkey);
        List<ConfigBase<?>> comfigValues = new ArrayList<>();
        comfigValues.addAll(hwiteswitch);
        comfigValues.addAll(appearance);
        comfigValues.addAll(dev);
        comfigValues.addAll(HwiteConfigs.values);
        tabs.add(new ConfigTab("开关", hwiteswitch));
        tabs.add(new ConfigTab("数值", HwiteConfigs.values));
        tabs.add(new ConfigTab("外观", appearance));
        tabs.add(new ConfigTab("开发选项", dev));
        Instance = new HwiteConfigs("HWITE", null, comfigValues);
    }

    @Override
    public List<ConfigTab> getConfigTabs() {
        return tabs;
    }

    public static HwiteConfigs getInstance() {
        return Instance;
    }

    @Override
    public void save() {
        JsonObject root = new JsonObject();
        ConfigUtils.writeConfigBase(root, "开关", hwiteswitch);
        ConfigUtils.writeConfigBase(root, "数值", values);
        ConfigUtils.writeConfigBase(root, "外观", appearance);
        ConfigUtils.writeConfigBase(root, "开发选项", dev);
        JsonUtils.writeJsonToFile(root, this.optionsFile);
    }

    @Override
    public void load() {
        if (!this.optionsFile.exists()) {
            this.save();
        } else {
            JsonElement jsonElement = JsonUtils.parseJsonFile(this.optionsFile);
            if (jsonElement != null && jsonElement.isJsonObject()) {
                JsonObject root = jsonElement.getAsJsonObject();
                ConfigUtils.readConfigBase(root, "开关", hwiteswitch);
                ConfigUtils.readConfigBase(root, "数值", values);
                ConfigUtils.readConfigBase(root, "外观", appearance);
                ConfigUtils.readConfigBase(root, "开发选项", dev);
            }
        }
    }
}
