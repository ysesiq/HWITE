package moddedmite.xylose.hwite.config;

import moddedmite.xylose.hwite.info.HwiteInfo;
import moddedmite.xylose.hwite.render.TooltipBGRender;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.util.JsonUtils;
import net.minecraft.Block;
import net.minecraft.Minecraft;
import net.xiaoyu233.fml.FishModLoader;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HwiteConfigs extends SimpleConfigs {
    //base
    public static final ConfigBoolean ViewMode = new ConfigBoolean("hwite.View", false, "配置界面预览");
    public static final ConfigBoolean DisplayTooltip = new ConfigBoolean("hwite.DisplayTooltip", true);
    public static final ConfigBoolean DebugRenderTooltip = new ConfigBoolean("hwite.debugRenderTooltip", true);
    public static final ConfigBoolean devMoveDownTooltip = new ConfigBoolean("hwite.devMoveDownTooltip", true);
    public static final ConfigBoolean DisplayBlock = new ConfigBoolean("hwite.DisplayBlock", true);
    public static final ConfigBoolean DisplayLiquids = new ConfigBoolean("hwite.DisplayLiquid", false);
    public static final ConfigBoolean DisplayEntity = new ConfigBoolean("hwite.DisplayEntity", true);
    public static final ConfigBoolean DisplayNonCollidingEntity = new ConfigBoolean("hwite.DisplayNonCollidingEntity", false, "例如经验球,掉落物,钓鱼竿的浮标");
    public static final ConfigBoolean EntityRender = new ConfigBoolean("hwite.EntityRender", false);
    public static final ConfigBoolean BlockRender = new ConfigBoolean("hwite.BlockRender", true);
    //general
    public static final ConfigBoolean BreakInfo = new ConfigBoolean("hwite.BreakInfo", true);
    public static final ConfigBoolean BreakProgress = new ConfigBoolean("hwite.BreakProgress", false);
    public static final ConfigBoolean BreakProgressLine = new ConfigBoolean("hwite.BreakProgressLine", true);
    public static final ConfigBoolean GrowthValue = new ConfigBoolean("hwite.GrowthValue", true);
    public static final ConfigBoolean Redstone = new ConfigBoolean("hwite.Redstone", true, "红石能量强度,拉杆.压力板状态,比较器状态,中继器状态");
    public static final ConfigBoolean SpawnerType = new ConfigBoolean("hwite.SpawnerType", true);
    //appearance
    public static final ConfigInteger TooltipX = new ConfigInteger("hwite.TooltipX", 50, 0, 100);
    public static final ConfigInteger TooltipY = new ConfigInteger("hwite.TooltipY", 1, 0, 100);
    public static final ConfigDouble TooltipScale = new ConfigDouble("hwite.TooltipScale", 1, 0.2, 2);
    public static final ConfigInteger TooltipAlpha = new ConfigInteger("hwite.TooltipAlpha", 80, 0, 100);
    public static final ConfigInteger EntityInfoX = new ConfigInteger("hwite.EntityInfoX", 180, 0, 500, true, "请关闭实体渲染(WIP)");
    public static final ConfigInteger EntityInfoY = new ConfigInteger("hwite.EntityInfoY", 43, 0, 300, true, "请关闭实体渲染(WIP)");
    public static final ConfigInteger EntityInfoSize = new ConfigInteger("hwite.EntityInfoSize", 18, 0, 100, false, "请关闭实体渲染(WIP)");
    public static final ConfigBoolean TooltipBackGround = new ConfigBoolean("hwite.TooltipBackGround", true);
    public static final ConfigBoolean TooltipRoundedRectangle = new ConfigBoolean("hwite.TooltipRoundedRectangle", true);
    public static final ConfigBoolean TooltipFrame = new ConfigBoolean("hwite.TooltipFrame", true);
    public static final ConfigBoolean TooltipCentralBackground = new ConfigBoolean("hwite.TooltipCentralBackground", true);
    public static final ConfigBoolean TooltipThemeSwitch = new ConfigBoolean("hwite.TooltipThemeSwitch", true, "自定义主题请关闭本项");
    public static final ConfigEnum<EnumTooltipTheme> TooltipTheme = new ConfigEnum<>("hwite.TooltipTheme", EnumTooltipTheme.Waila);
    public static final ConfigColor TooltipBGColor = new ConfigColor("hwite.TooltipBGColor", "#CC10010F");
    public static final ConfigColor TooltipFrameColorTop = new ConfigColor("hwite.TooltipFrameColorTop", "#CC5001FE");
    public static final ConfigColor TooltipFrameColorBottom = new ConfigColor("hwite.TooltipFrameColorBottom", "#CC28017E");
    public static final ConfigColor BreakProgressLineColorFront = new ConfigColor("hwite.BreakProgressLineColorFront", "#FF74766B");
    public static final ConfigColor BreakProgressLineColorBehind = new ConfigColor("hwite.BreakProgressLineColorBehind", "#FF74766B");
    public static final ConfigString ModNameTextColor = new ConfigString("hwite.ModNameTextColor", "§9§o");
    public static final ConfigString CanBreakString = new ConfigString("hwite.CanBreakString", "✔");
    public static final ConfigString CannotBreakString = new ConfigString("hwite.CannotBreakString", "✘");
    //dev
    public static final ConfigBoolean MITEDetailsInfo = new ConfigBoolean("hwite.MITEDetailsInfo", false, "硬度,挖掘速度,挖掘等级,这些是原版MITE dev详细信息显示自带的");
    public static final ConfigBoolean ShowIDAndMetadata = new ConfigBoolean("hwite.ShowIDAndMetadata", false);
    public static final ConfigBoolean ShowBlockOrEntityCoord = new ConfigBoolean("hwite.ShowBlockOrEntityCoord", false);
    public static final ConfigBoolean ShowBlockUnlocalizedName = new ConfigBoolean("hwite.ShowBlockUnlocalizedName", false);
    public static final ConfigBoolean ShowDistance = new ConfigBoolean("hwite.ShowDistance", false);
    public static final ConfigBoolean ShowDirection = new ConfigBoolean("hwite.ShowDirection", false);
    //hotkey
    public static final ConfigHotkey ConfigGuiHotkey = new ConfigHotkey("hwite.ConfigGuiHotkey", Keyboard.KEY_NUMPAD0);
    public static final ConfigHotkey TooltipHotkey = new ConfigHotkey("hwite.TooltipHotkey", Keyboard.KEY_NUMPAD1);
    public static final ConfigHotkey LiquidsHotkey = new ConfigHotkey("hwite.LiquidsHotkey", Keyboard.KEY_NUMPAD2);
    public static final ConfigHotkey RecipeHotkey = new ConfigHotkey("hwite.RecipeHotkey", Keyboard.KEY_NUMPAD3);
    public static final ConfigHotkey UsageHotkey = new ConfigHotkey("hwite.UsageHotkey", Keyboard.KEY_NUMPAD4);
    //Hard Is We Looking At
    public static final ConfigBoolean AnimalGrowthTime = new ConfigBoolean("hiwla.AnimalGrowthTime", true);
    public static final ConfigBoolean LivingProtectionAttack = new ConfigBoolean("hiwla.LivingProtection", true);
    public static final ConfigBoolean VillagerProfession = new ConfigBoolean("hiwla.VillagerProfession", true);
    public static final ConfigBoolean FurnaceInfo = new ConfigBoolean("hiwla.FurnaceInfo", false);
    public static final ConfigBoolean BeaconLevel = new ConfigBoolean("hiwla.BeaconLevel", true);
    public static final ConfigBoolean HorseInfo = new ConfigBoolean("hiwla.HorseInfo", true);
    public static final ConfigBoolean EffectInfo = new ConfigBoolean("hiwla.EffectInfo", false);

    private static final HwiteConfigs Instance;
    public static final List<ConfigBase<?>> hwite;
    public static final List<ConfigBase<?>> general;
    public static final List<ConfigBase<?>> appearance;
    public static final List<ConfigBase<?>> hwiteDev;
    public static final List<ConfigHotkey> hotkey;
    public static final List<ConfigBase<?>> hiwla;

    public static final List<ConfigTab> tabs = new ArrayList<>();

    public HwiteConfigs() {
        super("HWITE", hotkey, hwite, "⚡⚡⚡ 你看到的我~~~ ⚡⚡⚡");
    }

    static {
        hwite = List.of(DisplayTooltip, DebugRenderTooltip, devMoveDownTooltip, DisplayBlock, DisplayLiquids, DisplayEntity, DisplayNonCollidingEntity, BlockRender, EntityRender);
        general = List.of(BreakInfo, BreakProgress, BreakProgressLine, GrowthValue, Redstone, SpawnerType);
        appearance = List.of(TooltipX, TooltipY, TooltipScale, TooltipAlpha, EntityInfoX, EntityInfoY, EntityInfoSize, TooltipBackGround, TooltipRoundedRectangle, TooltipFrame, TooltipCentralBackground, TooltipThemeSwitch, TooltipTheme, TooltipBGColor, TooltipFrameColorTop, TooltipFrameColorBottom, BreakProgressLineColorFront, BreakProgressLineColorBehind, CanBreakString, CannotBreakString);
        hwiteDev = List.of(ShowIDAndMetadata, MITEDetailsInfo, ShowBlockOrEntityCoord, ShowDistance, ShowDirection, ShowBlockUnlocalizedName);
        hotkey = List.of(ConfigGuiHotkey, TooltipHotkey, LiquidsHotkey, RecipeHotkey, UsageHotkey);
        hiwla = List.of(AnimalGrowthTime, LivingProtectionAttack, VillagerProfession, FurnaceInfo, BeaconLevel, HorseInfo, EffectInfo);
        List<ConfigBase<?>> configValues = new ArrayList<>();
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
            Minecraft.getMinecraft().displayGuiScreen(getInstance().getConfigScreen(null));
            return true;
        });
        TooltipHotkey.getKeybind().setCallback((keyAction, iKeybind) -> {
            DisplayTooltip.toggleBooleanValue();
            return true;
        });
        LiquidsHotkey.getKeybind().setCallback((keyAction, iKeybind) -> {
            DisplayLiquids.toggleBooleanValue();
            return true;
        });

        if (FishModLoader.hasMod("emi")) {
            try {
                HwiteConfigs.RecipeHotkey.getKeybind().setCallback(((keyAction, iKeybind) -> {
                    dev.emi.emi.api.EmiApi.displayRecipes(Objects.requireNonNull(HwiteInfo.updateEmiStack()));
                return true;
                }));
                HwiteConfigs.UsageHotkey.getKeybind().setCallback(((keyAction, iKeybind) -> {
                    dev.emi.emi.api.EmiApi.displayUses(Objects.requireNonNull(HwiteInfo.updateEmiStack()));
                return true;
                }));
            } catch (Exception ignored) {
                ;
            }
        }

        if (ViewMode.getBooleanValue()) {
            Minecraft mc = Minecraft.getMinecraft();
            ArrayList<String> list = new ArrayList<>();
            TooltipBGRender tooltipBGRender = new TooltipBGRender();
            list.add(Block.runestoneAdamantium.getLocalizedName());
            list.add("MITE");
            tooltipBGRender.drawTooltipBackGround(list, false, mc);
        }
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
        ConfigUtils.writeConfigBase(root, "HWITE", hwite);
        ConfigUtils.writeConfigBase(root, "基础", general);
        ConfigUtils.writeConfigBase(root, "外观", appearance);
        ConfigUtils.writeConfigBase(root, "开发", hwiteDev);
        ConfigUtils.writeConfigBase(root, "快捷键", hotkey);
        ConfigUtils.writeConfigBase(root, "附属", hiwla);
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
                ConfigUtils.readConfigBase(root, "HWITE", hwite);
                ConfigUtils.readConfigBase(root, "基础", general);
                ConfigUtils.readConfigBase(root, "外观", appearance);
                ConfigUtils.readConfigBase(root, "开发", hwiteDev);
                ConfigUtils.readConfigBase(root, "快捷键", hotkey);
                ConfigUtils.readConfigBase(root, "附属", hiwla);
            }
        }
    }
}
