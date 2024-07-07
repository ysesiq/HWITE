package cn.xylose.mitemod.hwite.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.util.JsonUtils;
import net.minecraft.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class HwiteConfigs extends SimpleConfigs {
    //switch
    public static final ConfigBoolean ViewMode = new ConfigBoolean("hwite.View", false, "配置界面预览");
    public static final ConfigBoolean RenderHwiteHud = new ConfigBoolean("hwite.RenderHwiteHud", true);
    public static final ConfigBoolean EntityRender = new ConfigBoolean("hwite.EntityRender", false);
    public static final ConfigBoolean BlockRender = new ConfigBoolean("hwite.BlockRender", true);
    public static final ConfigBoolean Liquids = new ConfigBoolean("hwite.Liquids", false);
    public static final ConfigBoolean CanBreak = new ConfigBoolean("hwite.CanBreak", true);
    public static final ConfigBoolean BreakProgress = new ConfigBoolean("hwite.BreakProgress", true);
    public static final ConfigBoolean BreakProgressLine = new ConfigBoolean("hwite.BreakProgressLine", false);
    public static final ConfigBoolean NonCollidingEntity = new ConfigBoolean("hwite.NonCollidingEntity", false, "例如经验球,掉落物,钓鱼竿的浮标");
    public static final ConfigBoolean GrowthValue = new ConfigBoolean("hwite.GrowthValue", true);
    public static final ConfigBoolean Redstone = new ConfigBoolean("hwite.Redstone", true, "红石能量强度,拉杆.压力板状态,比较器状态,中继器状态");
    public static final ConfigBoolean SpawnerType = new ConfigBoolean("hwite.SpawnerType", true);
    //values
    public static final ConfigBoolean HUDPosOverride = new ConfigBoolean("hwite.HUDPosOverride", false, "打开后请使用位置子页面第1,2个配置项调整");
    public static final ConfigInteger HUDX = new ConfigInteger("hwite.HUDX", 215, 0, 500, true, "请打开HUD位置调整");
    public static final ConfigInteger HUDY = new ConfigInteger("hwite.HUDY", 16, 0, 300, true, "请打开HUD位置调整");
    public static final ConfigInteger EntityInfoX = new ConfigInteger("hwite.EntityInfoX", 180, 0, 500, true, "请关闭实体渲染(WIP)");
    public static final ConfigInteger EntityInfoY = new ConfigInteger("hwite.EntityInfoY", 43, 0, 300, true, "请关闭实体渲染(WIP)");
    public static final ConfigInteger EntityInfoSize = new ConfigInteger("hwite.EntityInfoSize", 18, 0, 100, false, "请关闭实体渲染(WIP)");
    //appearance
    public static final ConfigBoolean HUDBackGround = new ConfigBoolean("hwite.HUDBackGround", true);
    public static final ConfigBoolean HUDRoundedRectangle = new ConfigBoolean("hwite.HUDRoundedRectangle", true);
    public static final ConfigBoolean HUDFrame = new ConfigBoolean("hwite.HUDFrame", true);
    public static final ConfigBoolean HUDCentralBackground = new ConfigBoolean("hwite.HUDCentralBackground", true);
    public static final ConfigBoolean HUDThemeSwitch = new ConfigBoolean("hwite.HUDThemeSwitch", true, "自定义主题请关闭本项");
    public static final ConfigEnum<EnumHUDTheme> HUDTheme = new ConfigEnum<>("HUDTheme", EnumHUDTheme.Waila);
    public static final ConfigColor HUDBGColor = new ConfigColor("hwite.HUDBGColor", "#CC10010F", "此项为16进制数,12位数为透明度,34位为红色通道,56位为绿色通道,78位为蓝色通道");
    public static final ConfigColor HUDFrameColor = new ConfigColor("hwite.HUDFrameColor", "#CC5001FE", "此项为16进制数,12位数为透明度,34位为红色通道,56位为绿色通道,78位为蓝色通道");
    public static final ConfigColor HUDFrameColor1 = new ConfigColor("hwite.HUDFrameColor1", "#CC28017E", "此项为16进制数,12位数为透明度,34位为红色通道,56位为绿色通道,78位为蓝色通道");
    public static final ConfigColor BreakProgressLineColor = new ConfigColor("hwite.BreakProgressLineColor", "#FFFFFFFF", "此项为16进制数,12位数为透明度,34位为红色通道,56位为绿色通道,78位为蓝色通道");
    //dev
    public static final ConfigBoolean MITEDetailsInfo = new ConfigBoolean("hwite.MITEDetailsInfo", false, "硬度,挖掘速度,挖掘等级,这些是原版MITE dev详细信息显示自带的");
    public static final ConfigBoolean ShowIDAndMetadata = new ConfigBoolean("hwite.ShowIDAndMetadata", false);
    //hotkey
    public static final ConfigHotkey ConfigGuiHotkey = new ConfigHotkey("hwite.ConfigGuiHotkey", 82);
    public static final ConfigHotkey HUDHotkey = new ConfigHotkey("hwite.HUDHotkey", 79);
    public static final ConfigHotkey LiquidsHotkey = new ConfigHotkey("hwite.LiquidsHotkey", 80);

    private static final HwiteConfigs Instance;
    public static final List<ConfigBase<?>> hwiteswitch;
    public static final List<ConfigBase<?>> values;
    public static final List<ConfigBase<?>> appearance;
    public static final List<ConfigBase<?>> dev;
    public static final List<ConfigHotkey> hotkey;

    public static final List<ConfigTab> tabs = new ArrayList<>();

    public HwiteConfigs() {
        super("HWITE", hotkey, values, "⚡⚡⚡ 你看到的我~~~ ⚡⚡⚡", "⚡⚡⚡ 是哪一种颜色~~~ ⚡⚡⚡");
    }

    static {
        hwiteswitch = List.of(RenderHwiteHud, BlockRender, EntityRender, Liquids, CanBreak, BreakProgress, BreakProgressLine, NonCollidingEntity, GrowthValue, Redstone, SpawnerType);
        values = List.of(HUDPosOverride, HUDX, HUDY, EntityInfoX, EntityInfoY, EntityInfoSize);
        appearance = List.of(HUDBackGround, HUDRoundedRectangle, HUDFrame, HUDCentralBackground, HUDThemeSwitch, HUDTheme, HUDBGColor, HUDFrameColor, HUDFrameColor1, BreakProgressLineColor);
        dev = List.of(ShowIDAndMetadata, MITEDetailsInfo);
        hotkey = List.of(ConfigGuiHotkey, HUDHotkey, LiquidsHotkey);
        List<ConfigBase<?>> comfigValues = new ArrayList<>();
        comfigValues.addAll(hwiteswitch);
        comfigValues.addAll(appearance);
        comfigValues.addAll(dev);
        comfigValues.addAll(HwiteConfigs.values);
        tabs.add(new ConfigTab("hwite.switch", hwiteswitch));
        tabs.add(new ConfigTab("hwite.values", HwiteConfigs.values));
        tabs.add(new ConfigTab("hwite.appearance", appearance));
        tabs.add(new ConfigTab("hwite.dev", dev));

        ConfigGuiHotkey.setHotKeyPressCallBack(minecraft -> {
            minecraft.displayGuiScreen(HwiteConfigs.getInstance().getValueScreen((GuiScreen) null));
        });
        HUDHotkey.setHotKeyPressCallBack(minecraft -> RenderHwiteHud.toggleBooleanValue());
        LiquidsHotkey.setHotKeyPressCallBack(minecraft -> Liquids.toggleBooleanValue());

        Instance = new HwiteConfigs();
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
        ConfigUtils.writeConfigBase(root, "开发", dev);
        ConfigUtils.writeConfigBase(root, "快捷键", hotkey);
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
                ConfigUtils.readConfigBase(root, "开发", dev);
                ConfigUtils.readConfigBase(root, "快捷键", hotkey);
            }
        }
    }
}
