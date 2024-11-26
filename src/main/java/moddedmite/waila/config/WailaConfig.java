package moddedmite.waila.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.util.JsonUtils;
import mcp.mobius.waila.api.IWailaConfigHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class WailaConfig extends SimpleConfigs implements IWailaConfigHandler {
    public static final ConfigInteger posX = new ConfigInteger("posX", 5000);
    public static final ConfigInteger posY = new ConfigInteger("posY", 100);
    public static final ConfigInteger alpha = new ConfigInteger("alpha", 80);
    public static final ConfigColor bgcolor = new ConfigColor("bgcolor", "#FF10010F");
    public static final ConfigColor gradient1 = new ConfigColor("gradient1", "#FF5001FE");
    public static final ConfigColor gradient2 = new ConfigColor("gradient2", "#FF28017E");
    public static final ConfigColor fontcolor = new ConfigColor("fontcolor", "#FFFFFF");
    public static final ConfigDouble scale = new ConfigDouble("scale", 1, 0.2, 2);

    public static final ConfigBoolean showTooltip = new ConfigBoolean("showTooltip", true);
    public static final ConfigBoolean showEnts = new ConfigBoolean("showEnts", true);
    public static final ConfigBoolean showSpawnerType = new ConfigBoolean("showSpawnerType", true, "+ for both server and client enabled, - for client only\\n [-]Show mobspawner types");
    public static final ConfigBoolean leverstate = new ConfigBoolean("leverstate", true);
    public static final ConfigBoolean repeater = new ConfigBoolean("repeater", true);
    public static final ConfigBoolean comparator = new ConfigBoolean("comparator", true);
    public static final ConfigBoolean redstone = new ConfigBoolean("redstone", true);
    public static final ConfigBoolean showInvisiblePlayers = new ConfigBoolean("showInvisiblePlayers", true);
    public static final ConfigBoolean backgroundTransparency = new ConfigBoolean("backgroundTransparency", false);
    public static final ConfigHotkey keyBind = new ConfigHotkey("KeyBind");
    public static final ConfigBoolean CATEGORY_SERVER = new ConfigBoolean("CATEGORY_SERVER", false);
    public static final ConfigBoolean CFG_WAILA_METADATA = new ConfigBoolean("CFG_WAILA_METADATA", false);
    public static final ConfigBoolean CFG_WAILA_SHIFTBLOCK = new ConfigBoolean("CFG_WAILA_SHIFTBLOCK", false);
    public static final ConfigBoolean CFG_WAILA_SHIFTENTS = new ConfigBoolean("CFG_WAILA_SHIFTENTS", false);
    public static final ConfigBoolean CFG_WAILA_LIQUID = new ConfigBoolean("CFG_WAILA_LIQUID", false);
    public static final ConfigBoolean showhp = new ConfigBoolean("showhp", true);
    public static final ConfigBoolean showcrop = new ConfigBoolean("showcrop", true);

    private static WailaConfig Instance;
    public static List<ConfigBase> show;
    public static List<ConfigBase> pos;
    public static List<ConfigBase> key;

    public WailaConfig(String name, List<ConfigHotkey> hotkeys, List<ConfigBase> values) {
        super(name, hotkeys, values);
    }

    public static void init() {
        show = List.of(showTooltip, showSpawnerType, leverstate, repeater, comparator, redstone,
                backgroundTransparency, CATEGORY_SERVER, CFG_WAILA_METADATA, showEnts, CFG_WAILA_SHIFTBLOCK, CFG_WAILA_SHIFTENTS, CFG_WAILA_LIQUID,
                showhp, showcrop);
        key = List.of(keyBind);
        pos = List.of(posX, posY, alpha, bgcolor, gradient1, gradient2, fontcolor, scale);
        ArrayList<ConfigBase> values = new ArrayList<>();
        values.addAll(show);
        values.addAll(pos);
        values.addAll(key);
        Instance = new WailaConfig("Waila", null, values);
    }

    public static WailaConfig getInstance() {
        return Instance;
    }

//    public void save() {
//        JsonObject configRoot = new JsonObject();
//        ConfigUtils.writeConfigBase(configRoot, "Values", show);
//        JsonObject valuesRoot = JsonUtils.getNestedObject(configRoot, "Values", true);
//        ConfigUtils.writeConfigBase(valuesRoot, "pos", pos);
//        JsonUtils.writeJsonToFile(configRoot, getOptionsFile());
//    }
//
//    public void load() {
//        if (!getOptionsFile().exists()) {
//            save();
//            return;
//        }
//        JsonElement jsonElement = JsonUtils.parseJsonFile(getOptionsFile());
//        if (jsonElement != null && jsonElement.isJsonObject()) {
//            JsonObject obj = jsonElement.getAsJsonObject();
//            ConfigUtils.readConfigBase(obj, "Values", show);
//            JsonObject valuesRoot = JsonUtils.getNestedObject(obj, "Values", true);
//            ConfigUtils.readConfigBase(valuesRoot, "pos", pos);
//        }
//    }

    @Override
    public Set<String> getModuleNames() {
        return Set.of();
    }

    @Override
    public HashMap<String, String> getConfigKeys(String modName) {
        return (HashMap<String, String>) keyBind.getKeybind();
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
