package mcp.mobius.waila.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import net.fabricmc.loader.api.ModContainer;
import net.minecraft.ItemStack;
import net.xiaoyu233.fml.FishModLoader;
import net.xiaoyu233.fml.api.block.IBlock;
import net.xiaoyu233.fml.api.item.IItem;

public class ModIdentification {

    public static HashMap<String, String> modSource_Name = new HashMap<>();
    public static HashMap<String, String> modSource_ID = new HashMap<>();

    public static void init() {

//        for (ModContainer mod : FishModLoader.getModList()) {
//            modSource_Name.put(mod.getSource().getName(), mod.getName());
//            modSource_ID.put(mod.getSource().getName(), mod.getModId());
//        }

        // TODO : Update this to match new version (1.7.2)
        modSource_Name.put("1.6.2.jar", "Minecraft");
        modSource_Name.put("1.6.3.jar", "Minecraft");
        modSource_Name.put("1.6.4.jar", "Minecraft");
        modSource_Name.put("1.7.2.jar", "Minecraft");
        modSource_Name.put("Forge", "Minecraft");
        modSource_ID.put("1.6.2.jar", "Minecraft");
        modSource_ID.put("1.6.3.jar", "Minecraft");
        modSource_ID.put("1.6.4.jar", "Minecraft");
        modSource_ID.put("1.7.2.jar", "Minecraft");
        modSource_ID.put("Forge", "Minecraft");
    }

    public static String nameFromObject(Object obj) {
        String objPath = obj.getClass().getProtectionDomain().getCodeSource().getLocation().toString();

        try {
            objPath = URLDecoder.decode(objPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String modName = "<Unknown>";
        for (String s : modSource_Name.keySet()) if (objPath.contains(s)) {
            modName = modSource_Name.get(s);
            break;
        }

        if (modName.equals("Minecraft Coder Pack")) modName = "Minecraft";

        return modName;
    }

    public static String nameFromStack(ItemStack stack) {
        try {
            int id = stack.itemID;
            String mod = "Minecraft";
            if (stack.isBlock()) {
                if (id < 256) {
                    if (id >= 164 && id < 170 || id >= 198 || id == 95) {
                        mod = "MITE";
                    } else if (id <= 163 || id >= 170 && id <= 174) {
                        mod = "Minecraft";
                    }
                } else {
                    mod = ((IBlock) stack.getItemAsBlock().getBlock()).getNamespace();
                }
                return mod;
            } else if (id >= 256) {
                if (!((id <= 955 || id == 1026 || id == 1027 || id >= 1058 && id <= 1066 || id == 1116 || id >= 1135 && id <= 1141 || id >= 1168 && id <= 1171 || id == 1238 || id >= 1265 && id <= 1275 || id >= 1283) && (id < 2276 || id > 2279))) {
                    mod = "MITE";
                } else if (id != 262 && id != 268 && (id <= 269 || id >= 280) && id != 290 && id != 291 && id != 293 && (id <= 309 || id >= 314) && (id <= 408 || id >= 417) && id != 419 && id <= 422 || id >= 2256 && id <= 2267) {
                    mod = "Minecraft";
                } else {
                    mod = ((IItem) stack.getItem()).getNamespace();
                }
                return mod;
            }
            return mod == null ? "Minecraft" : mod;
        } catch (NullPointerException e) {
            return "";
        }
    }
}
