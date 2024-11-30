package moddedmite.waila.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.gui.screen.DefaultConfigScreen;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.KeyCodes;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.overlay.OverlayConfig;
import moddedmite.waila.handlers.emi.EMIHandler;
import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;
import net.xiaoyu233.fml.FishModLoader;
import org.lwjgl.input.Keyboard;

import java.util.*;

public class WailaConfig extends SimpleConfigs implements IWailaConfigHandler {

    public static final ConfigBoolean showTooltip = new ConfigBoolean("showTooltip", true);
    public static final ConfigBoolean showEnts = new ConfigBoolean("showEnts", true);
    public static final ConfigBoolean metadata = new ConfigBoolean("metadata", false);
    public static final ConfigBoolean liquid = new ConfigBoolean("liquid", false);
    public static final ConfigBoolean shiftblock = new ConfigBoolean("shiftblock", false);
    public static final ConfigBoolean shiftents = new ConfigBoolean("shiftents", false);

    public static final ConfigBoolean showhp = new ConfigBoolean("showhp", true);
    public static final ConfigBoolean showcrop = new ConfigBoolean("showcrop", true);
    public static final ConfigBoolean spawnertype = new ConfigBoolean("spawnertype", true);
    public static final ConfigBoolean repeater = new ConfigBoolean("repeater", true);
    public static final ConfigBoolean redstone = new ConfigBoolean("redstone", true);
    public static final ConfigBoolean comparator = new ConfigBoolean("comparator", true);
    public static final ConfigBoolean leverstate = new ConfigBoolean("leverstate", true);
    public static final ConfigBoolean skull = new ConfigBoolean("skull", true);

    public static final ConfigInteger posX = new ConfigInteger("posX", 50, 0, 100, true, "");
    public static final ConfigInteger posY = new ConfigInteger("posY", 1, 0, 100, true, "");
    public static final ConfigInteger alpha = new ConfigInteger("alpha", 80, 0, 100, true, "");
    public static final ConfigColor bgcolor = new ConfigColor("bgcolor", "#FF100010");
    public static final ConfigColor gradient1 = new ConfigColor("gradient1", "#FF5000FF");
    public static final ConfigColor gradient2 = new ConfigColor("gradient2", "#FF28007F");
    public static final ConfigColor fontcolor = new ConfigColor("fontcolor", "#FFA0A0A0");
    public static final ConfigDouble scale = new ConfigDouble("scale", 1, 0.2, 2, true, "");

    public static final ConfigHotkey wailaconfig = new ConfigHotkey("wailaconfig", Keyboard.KEY_NUMPAD0);
    public static final ConfigHotkey wailadisplay = new ConfigHotkey("wailadisplay", Keyboard.KEY_NUMPAD1);
    public static final ConfigHotkey keyliquid = new ConfigHotkey("keyliquid", Keyboard.KEY_NUMPAD2);
    public static final ConfigHotkey recipe = new ConfigHotkey("recipe", Keyboard.KEY_NUMPAD3);
    public static final ConfigHotkey usage = new ConfigHotkey("usage", Keyboard.KEY_NUMPAD4);

    private static WailaConfig Instance;
    public static List<ConfigBase> main;
    public static List<ConfigBase> general;
    public static List<ConfigBase> screen;
    public static List<ConfigHotkey> key;

    public static final List<ConfigTab> tabs = new ArrayList<>();

    public WailaConfig() {
        super("Waila", key, main);
    }

    static {
        main = List.of(showTooltip, showEnts, metadata, liquid, shiftblock, shiftents);
        general = List.of(showhp, showcrop, spawnertype, repeater, redstone, comparator, leverstate, skull);
        screen = List.of(posX, posY, alpha, bgcolor, gradient1, gradient2, fontcolor, scale);
        key = List.of(wailaconfig, wailadisplay, keyliquid, recipe, usage);
        ArrayList<ConfigBase> values = new ArrayList<>();
        values.addAll(general);
        values.addAll(key);
        tabs.add(new ConfigTab("waila.main", main));
        tabs.add(new ConfigTab("waila.general", general));
        tabs.add(new ConfigTab("waila.screen", screen));
        tabs.add(new ConfigTab("waila.key", key));
        Instance = new WailaConfig();

        wailaconfig.getKeybind().setCallback((keyAction, iKeybind) -> {
            Minecraft.getMinecraft().displayGuiScreen(getInstance().getConfigScreen(null));
            return true;
        });
        wailadisplay.getKeybind().setCallback((keyAction, iKeybind) -> {
            showTooltip.toggleBooleanValue();
            return true;
        });
        keyliquid.getKeybind().setCallback((keyAction, iKeybind) -> {
            liquid.toggleBooleanValue();
            return true;
        });

        if (FishModLoader.hasMod("emi")) {
            try {
                recipe.getKeybind().setCallback(((keyAction, iKeybind) -> {
                    dev.emi.emi.api.EmiApi.displayRecipes(Objects.requireNonNull(EMIHandler.updateEmiStack()));
                    return true;
                }));
                usage.getKeybind().setCallback(((keyAction, iKeybind) -> {
                    dev.emi.emi.api.EmiApi.displayUses(Objects.requireNonNull(EMIHandler.updateEmiStack()));
                    return true;
                }));
            } catch (Exception ignored) {
            }
        }

//        if (bgcolor.isModified() || fontcolor.isModified() || gradient1.isModified() || gradient2.isModified() ||
//                scale.isModified() || alpha.isModified() || posX.isModified() || posY.isModified())
    }

    @Override
    public List<ConfigTab> getConfigTabs() {
        return tabs;
    }

    public static WailaConfig getInstance() {
        return Instance;
    }

    @Override
    public void save() {
        JsonObject root = new JsonObject();
        ConfigUtils.writeConfigBase(root, "main", main);
        ConfigUtils.writeConfigBase(root, "general", general);
        ConfigUtils.writeConfigBase(root, "screen", screen);
        ConfigUtils.writeConfigBase(root, "key", key);
        JsonUtils.writeJsonToFile(root, this.optionsFile);
        OverlayConfig.updateColors();
    }

    @Override
    public void load() {
        if (!this.optionsFile.exists()) {
            this.save();
        } else {
            JsonElement jsonElement = JsonUtils.parseJsonFile(this.optionsFile);
            if (jsonElement != null && jsonElement.isJsonObject()) {
                JsonObject root = jsonElement.getAsJsonObject();
                ConfigUtils.readConfigBase(root, "main", main);
                ConfigUtils.readConfigBase(root, "general", general);
                ConfigUtils.readConfigBase(root, "screen", screen);
                ConfigUtils.readConfigBase(root, "key", key);
            }
        }
    }

    @Override
    public Set<String> getModuleNames() {
        return Set.of();
    }

    @Override
    public HashMap<String, String> getConfigKeys(String modName) {
        return (HashMap<String, String>) wailaconfig.getKeybind();
    }

    @Override
    public boolean getConfig(String key, boolean defvalue) {
        return false;
    }

    @Override
    public boolean getConfig(String key) {
        return false;
    }
}
